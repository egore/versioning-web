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
package de.egore911.versioning.ui.beans.detail;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import de.egore911.versioning.persistence.dao.TagTransformerDao;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.TagTransformer;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@ManagedBean(name = "tagtransformerDetail")
@RequestScoped
@RequiresPermission(Permission.ADMIN_SETTINGS)
public class TagTransformerDetail extends AbstractDetail<TagTransformer> {

	private final SessionUtil sessionUtil = new SessionUtil();

	@Override
	protected TagTransformerDao getDao() {
		return new TagTransformerDao();
	}

	@Override
	protected TagTransformer createEmpty() {
		return new TagTransformer();
	}

	public String save() {
		try {
			Pattern.compile(getInstance().getSearchPattern());
		} catch (PatternSyntaxException e) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceBundle bundle = sessionUtil.getBundle();
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					bundle.getString("invalid_search_pattern"),
					MessageFormat.format(
							bundle.getString("invalid_search_pattern_detail"),
							e.getMessage()));
			facesContext.addMessage("main:server_name", message);
			return "";
		}

		getDao().save(getInstance());
		return "/tagtransformers.xhtml";
	}

}