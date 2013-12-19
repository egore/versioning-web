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

import de.egore911.versioning.persistence.dao.UserDao;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.User;
import de.egore911.versioning.persistence.model.User_;
import de.egore911.versioning.persistence.selector.UserSelector;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@ManagedBean(name = "userList")
@RequestScoped
@RequiresPermission(Permission.ADMIN_USERS)
public class UserList extends AbstractList<User> {

	@Override
	protected UserDao getDao() {
		return new UserDao();
	}

	@Override
	protected UserSelector createInitialSelector() {
		UserSelector state = new UserSelector();
		state.setSortColumn(User_.name.getName());
		state.setAscending(Boolean.TRUE);
		state.setLimit(DEFAULT_LIMIT);
		return state;
	}

}