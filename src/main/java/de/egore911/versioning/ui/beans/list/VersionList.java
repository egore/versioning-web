/*
 * Copyright 2013  Christoph Brill <egore911@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.egore911.versioning.ui.beans.list;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import de.egore911.versioning.persistence.dao.VersionDao;
import de.egore911.versioning.persistence.model.DbObject_;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Version;
import de.egore911.versioning.ui.model.SortDirection;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@ManagedBean(name = "versionList")
@RequestScoped
@RequiresPermission({ Permission.CREATE_VERSIONS, Permission.DEPLOY })
public class VersionList extends AbstractList<Version> {

	@Override
	protected VersionDao getDao() {
		return new VersionDao();
	}

	@Override
	protected State createInitialState() {
		State state = new State();
		state.setSortColumn(DbObject_.created.getName());
		state.setSortDirection(SortDirection.DESC);
		return state;
	}

}
