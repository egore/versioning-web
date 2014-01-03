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
package de.egore911.versioning.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.User;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class UserUtil {

	private UserUtil() {
	}

	/**
	 * Calculate the SHA-1 sum for a given password (or any string for that
	 * matter).
	 * 
	 * @param password
	 *            the password to be hashed
	 * @return the SHA-1 sum for the given password
	 */
	public static String hashPassword(String password) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return byteArrayToHexString(md.digest(password.getBytes(Charset
				.forName("UTF-8"))));
	}

	/**
	 * Convert a byte array to a hexadecimal represenation.
	 * 
	 * @param b
	 *            the byte array
	 * @return the hexadecimal represenation
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuilder result = new StringBuilder(b.length);
		for (int i = 0; i < b.length; i++) {
			result.append(Integer.toString((b[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return result.toString();
	}

	public static String getStartpage(User user) {
		if (user.hasPermission(Permission.CREATE_VERSIONS)) {
			return "/versions.xhtml";
		} else if (user.hasPermission(Permission.DEPLOY)) {
			return "/deployments.xhtml";
		} else if (user.hasPermission(Permission.ADMIN_SETTINGS)) {
			return "/projects.xthml";
		} else if (user.hasPermission(Permission.ADMIN_USERS)) {
			return "/users.xthml";
		} else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceBundle bundle = SessionUtil.getBundle();
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					bundle.getString("missing_permission"),
					bundle.getString("missing_permission_detail"));
			facesContext.addMessage("main:user_login", message);
			return "";
		}
	}
}
