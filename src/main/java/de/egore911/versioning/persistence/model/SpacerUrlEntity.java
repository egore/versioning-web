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
package de.egore911.versioning.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import de.egore911.appframework.persistence.model.IntegerDbObject;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Entity
@Table(name = "spacer_url")
public class SpacerUrlEntity extends IntegerDbObject {

	private static final long serialVersionUID = 7091720075959159414L;

	private ActionCopyEntity actionCopy;
	private ActionExtractionEntity actionExtraction;

	private String url;

	@OneToOne
	@JoinColumn(name = "id_action_copy")
	public ActionCopyEntity getActionCopy() {
		return actionCopy;
	}

	public void setActionCopy(ActionCopyEntity actionCopy) {
		this.actionCopy = actionCopy;
	}

	@OneToOne
	@JoinColumn(name = "id_action_extraction")
	public ActionExtractionEntity getActionExtraction() {
		return actionExtraction;
	}

	public void setActionExtraction(ActionExtractionEntity actionExtraction) {
		this.actionExtraction = actionExtraction;
	}

	@Column(nullable = false, length = 1023)
	@NotNull
	@Size(max = 1023)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
