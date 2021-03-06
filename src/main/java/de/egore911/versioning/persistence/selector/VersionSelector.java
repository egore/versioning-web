/*
 * Copyright 2013  Christoph Brill <egore911@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.egore911.versioning.persistence.selector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import de.egore911.appframework.persistence.selector.AbstractResourceSelector;
import de.egore911.versioning.persistence.model.ProjectEntity;
import de.egore911.versioning.persistence.model.ProjectEntity_;
import de.egore911.versioning.persistence.model.VersionEntity;
import de.egore911.versioning.persistence.model.VersionEntity_;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class VersionSelector extends AbstractResourceSelector<VersionEntity> {

	private ProjectEntity project;
	private String vcsTag;
	private String projectNameLike;
	private String vcsTagLike;
	private String search;

	@Nonnull
	@Override
	protected Class<VersionEntity> getEntityClass() {
		return VersionEntity.class;
	}

	@Nonnull
	@Override
	protected List<Predicate> generatePredicateList(@Nonnull CriteriaBuilder builder,
													@Nonnull Root<VersionEntity> from,
													@Nonnull CriteriaQuery<?> criteriaQuery) {
		List<Predicate> predicates = super.generatePredicateList(builder, from, criteriaQuery);

		if (project != null) {
			predicates.add(builder.equal(from.get(VersionEntity_.project), project));
		}

		if (vcsTag != null) {
			predicates.add(builder.equal(from.get(VersionEntity_.vcsTag), vcsTag));
		}

		if (StringUtils.isNotEmpty(projectNameLike)) {
			predicates.add(builder.like(
					from.get(VersionEntity_.project).get(ProjectEntity_.name), '%' + projectNameLike + '%'));
		}

		if (StringUtils.isNotEmpty(vcsTagLike)) {
			predicates.add(builder.like(from.get(VersionEntity_.vcsTag), '%' + vcsTagLike + '%'));
		}

		if (StringUtils.isNotEmpty(search)) {
			String likePattern = '%' + search + '%';
			predicates.add(
					builder.or(
							builder.like(from.get(VersionEntity_.vcsTag), likePattern),
							builder.like(from.get(VersionEntity_.project).get(ProjectEntity_.name), likePattern)
					)
			);
		}

		return predicates;
	}

	@Nonnull
	@Override
	protected List<Order> generateOrderList(@Nonnull CriteriaBuilder builder,
											@Nonnull Root<VersionEntity> from) {
		if (CollectionUtils.isNotEmpty(sortColumns)) {
			List<Order> result = new ArrayList<>(sortColumns.size());
			for (Pair<String, Boolean> sortColumn : sortColumns) {
				if ("project.name".equals(sortColumn.getKey())) {
					if (!Boolean.FALSE.equals(sortColumn.getValue())) {
						return Arrays
								.asList(builder.asc(from.get(VersionEntity_.project).get(ProjectEntity_.name)),
										builder.asc(from.get(VersionEntity_.created)));
					}
					return Arrays.asList(
							builder.desc(from.get(VersionEntity_.project).get(ProjectEntity_.name)),
							builder.asc(from.get(VersionEntity_.created)));
				} else {
					if (!Boolean.FALSE.equals(sortColumn.getValue())) {
						result.add(builder.asc(from.get(sortColumn.getKey())));
					} else {
						result.add(builder.desc(from.get(sortColumn.getKey())));
					}
				}
			}
			return result;
		} else {
			return Collections.singletonList(builder.desc(from.get(VersionEntity_.created)));
		}
	}

	@Override
	protected List<Order> getDefaultOrderList(@Nonnull CriteriaBuilder builder,
											  @Nonnull Root<VersionEntity> from) {
		return Arrays.asList(builder.asc(from.get(VersionEntity_.project)),
				builder.asc(from.get(VersionEntity_.vcsTag)));
	}

	public VersionSelector withProject(ProjectEntity project) {
		this.project = project;
		return this;
	}

	public VersionSelector withVcsTag(String vcsTag) {
		this.vcsTag = vcsTag;
		return this;
	}

	public VersionSelector withProjectNameLike(String projectNameLike) {
		this.projectNameLike = projectNameLike;
		return this;
	}

	public VersionSelector withVcsTagLike(String vcsTagLike) {
		this.vcsTagLike = vcsTagLike;
		return this;
	}

	@Override
	public VersionSelector withSearch(String search) {
		this.search = search;
		return this;
	}

}
