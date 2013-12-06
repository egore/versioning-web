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
package de.egore911.versioning.persistence.model;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public enum Permission {
	/** VCS Hosts, Projects, and Servers */
	ADMIN_SETTINGS,
	/** Users, Roles */
	ADMIN_USERS,
	/** Create versions */
	CREATE_VERSIONS,
	/** Create versions */
	DEPLOY,
}
