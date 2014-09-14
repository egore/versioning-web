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
import java.util.regex.Matcher;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import de.egore911.versioning.persistence.dao.ProjectDao;
import de.egore911.versioning.persistence.model.ActionCheckout;
import de.egore911.versioning.persistence.model.ActionCopy;
import de.egore911.versioning.persistence.model.ActionExtraction;
import de.egore911.versioning.persistence.model.ActionReplacement;
import de.egore911.versioning.persistence.model.Extraction;
import de.egore911.versioning.persistence.model.IntegerDbObject;
import de.egore911.versioning.persistence.model.MavenArtifact;
import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.persistence.model.Replacement;
import de.egore911.versioning.persistence.model.Replacementfile;
import de.egore911.versioning.persistence.model.Server;
import de.egore911.versioning.persistence.model.SpacerUrl;
import de.egore911.versioning.persistence.model.Variable;
import de.egore911.versioning.persistence.model.Wildcard;
import de.egore911.versioning.util.SessionUtil;
import de.egore911.versioning.util.security.RequiresPermission;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Named
@RequiresPermission(Permission.ADMIN_SETTINGS)
public class ProjectDetail extends AbstractDetail<Project> {

	private static final long serialVersionUID = 8323695136698374637L;

	@Inject
	private EntityManager entityManager;

	@Override
	protected ProjectDao getDao() {
		return BeanProvider.getContextualReference(ProjectDao.class);
	}

	@Override
	protected Project createEmpty() {
		return new Project();
	}

	// Copy
	public String chooseCopy() {
		Project project = getInstance();
		ActionCopy actionCopy = new ActionCopy();
		project.getActionCopies().add(actionCopy);
		actionCopy.setProject(project);
		setInstance(project);
		return "";
	}

	public String chooseCopyMavenArtifact(ActionCopy actionCopy) {
		MavenArtifact mavenArtifact = new MavenArtifact();
		actionCopy.setMavenArtifact(mavenArtifact);
		mavenArtifact.setActionCopy(actionCopy);
		setInstance(getInstance());
		return "";
	}

	public String chooseCopySpacerUrl(ActionCopy actionCopy) {
		SpacerUrl spacerUrl = new SpacerUrl();
		actionCopy.setSpacerUrl(spacerUrl);
		spacerUrl.setActionCopy(actionCopy);
		setInstance(getInstance());
		return "";
	}

	public String deleteActionCopy(ActionCopy actionCopy) {
		Project project = getInstance();
		project.getActionCopies().remove(actionCopy);
		markDelete(actionCopy);
		setInstance(project);
		return "";
	}

	public String deleteMavenArtifact(MavenArtifact mavenArtifact) {
		if (mavenArtifact.getActionCopy() != null) {
			mavenArtifact.getActionCopy().setMavenArtifact(null);
		} else if (mavenArtifact.getActionExtraction() != null) {
			mavenArtifact.getActionExtraction().setMavenArtifact(null);
		} else {
			throw new IllegalArgumentException(
					"Neither attached to copy or extraction action");
		}
		markDelete(mavenArtifact);
		setInstance(getInstance());
		return "";
	}

	public String deleteSpacerUrl(SpacerUrl spacerUrl) {
		if (spacerUrl.getActionCopy() != null) {
			spacerUrl.getActionCopy().setSpacerUrl(null);
		} else if (spacerUrl.getActionExtraction() != null) {
			spacerUrl.getActionExtraction().setSpacerUrl(null);
		} else {
			throw new IllegalArgumentException(
					"Neither attached to copy or extraction action");
		}
		markDelete(spacerUrl);
		setInstance(getInstance());
		return "";
	}

	// Extraction
	public String chooseExtraction() {
		Project project = getInstance();
		ActionExtraction actionExtraction = new ActionExtraction();
		addExtraction(actionExtraction);
		project.getActionExtractions().add(actionExtraction);
		actionExtraction.setProject(project);
		setInstance(project);
		return "";
	}

	public String chooseExtractionMavenArtifact(
			ActionExtraction actionExtraction) {
		MavenArtifact mavenArtifact = new MavenArtifact();
		actionExtraction.setMavenArtifact(mavenArtifact);
		mavenArtifact.setActionExtraction(actionExtraction);
		setInstance(getInstance());
		return "";
	}

	public String chooseExtractionSpacerUrl(ActionExtraction actionExtraction) {
		SpacerUrl spacerUrl = new SpacerUrl();
		actionExtraction.setSpacerUrl(spacerUrl);
		spacerUrl.setActionExtraction(actionExtraction);
		setInstance(getInstance());
		return "";
	}

	public String addExtraction(ActionExtraction actionExtraction) {
		Extraction extraction = new Extraction();
		extraction.setActionExtraction(actionExtraction);
		actionExtraction.getExtractions().add(extraction);
		return "";
	}

	public String deleteActionExtraction(ActionExtraction actionExtraction) {
		Project project = getInstance();
		project.getActionExtractions().remove(actionExtraction);
		markDelete(actionExtraction);
		setInstance(project);
		return "";
	}

	public String deleteExtraction(Extraction extraction) {
		extraction.getActionExtraction().getExtractions().remove(extraction);
		markDelete(extraction);
		setInstance(getInstance());
		return "";
	}

	// Replacement
	public String chooseReplacement() {
		Project project = getInstance();
		ActionReplacement actionReplacement = new ActionReplacement();
		addWildcard(actionReplacement);
		addReplacement(actionReplacement);
		project.getActionReplacements().add(actionReplacement);
		actionReplacement.setProject(project);
		setInstance(project);
		return "";
	}

	public String deleteActionReplacement(ActionReplacement actionReplacement) {
		Project project = getInstance();
		project.getActionReplacements().remove(actionReplacement);
		markDelete(actionReplacement);
		setInstance(project);
		return "";
	}

	public String addWildcard(ActionReplacement actionReplacement) {
		Wildcard wildcard = new Wildcard();
		wildcard.setActionReplacement(actionReplacement);
		actionReplacement.getWildcards().add(wildcard);
		return "";
	}

	public String deleteWildcard(Wildcard wildcard) {
		wildcard.getActionReplacement().getWildcards().remove(wildcard);
		markDelete(wildcard);
		return "";
	}

	public String addReplacement(ActionReplacement actionReplacement) {
		Replacement replacement = new Replacement();
		replacement.setActionReplacement(actionReplacement);
		actionReplacement.getReplacements().add(replacement);
		return "";
	}

	public String deleteReplacement(Replacement replacement) {
		replacement.getActionReplacement().getReplacements()
				.remove(replacement);
		markDelete(replacement);
		return "";
	}

	public String addReplacementFile(ActionReplacement actionReplacement) {
		Replacementfile replacementFile = new Replacementfile();
		replacementFile.setActionReplacement(actionReplacement);
		actionReplacement.getReplacementFiles().add(replacementFile);
		return "";
	}

	public String deleteReplacementfile(Replacementfile replacementfile) {
		replacementfile.getActionReplacement().getReplacementFiles()
				.remove(replacementfile);
		markDelete(replacementfile);
		return "";
	}

	// Checkout
	public String chooseCheckout() {
		Project project = getInstance();
		ActionCheckout actionCheckout = new ActionCheckout();
		project.getActionCheckouts().add(actionCheckout);
		actionCheckout.setProject(project);
		setInstance(project);
		return "";
	}

	public String deleteActionCheckout(ActionCheckout actionCheckout) {
		Project project = getInstance();
		project.getActionCheckouts().remove(actionCheckout);
		markDelete(actionCheckout);
		setInstance(project);
		return "";
	}

	@Transactional
	public String save() {

		if (!validate("project")) {
			return "";
		}

		for (ActionCopy actionCopy : getInstance().getActionCopies()) {
			checkVariableExists(actionCopy.getTargetPath());
		}
		for (ActionExtraction actionExtraction : getInstance()
				.getActionExtractions()) {
			for (Extraction extraction : actionExtraction.getExtractions()) {
				checkVariableExists(extraction.getSource());
				checkVariableExists(extraction.getDestination());
			}
		}

		getDao().save(getInstance());

		for (IntegerDbObject deletion : getDeletions()) {
			if (deletion.getId() != null) {
				deletion = entityManager.merge(deletion);
				entityManager.remove(deletion);
			}
		}

		setInstance(null);
		return "/projects.xhtml";
	}

	private void checkVariableExists(String s) {
		Matcher m = Variable.VARIABLE_PATTERN.matcher(s);
		while (m.find()) {
			String variableName = m.group(1);
			for (Server server : getInstance().getConfiguredServers()) {
				boolean found = false;
				for (Variable variable : server.getVariables()) {
					if (variable.getName().equals(variableName)) {
						found = true;
						break;
					}
				}
				if (!found) {
					FacesContext facesContext = FacesContext
							.getCurrentInstance();
					ResourceBundle bundle = SessionUtil.getBundle();
					FacesMessage message = new FacesMessage(
							FacesMessage.SEVERITY_WARN,
							MessageFormat.format(
									bundle.getString("missing_variable_X_for_server_Y"),
									variableName, server.getName()),
							MessageFormat.format(
									bundle.getString("missing_variable_X_for_server_Y_detail"),
									variableName, server.getName()));
					facesContext.addMessage("main:server_name", message);
				}
			}
		}
	}

}
