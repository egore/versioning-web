package de.egore911.versioning.ui.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class XmlRenderer extends Renderer {

	protected void renderHeader(StringBuilder builder, HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("application/xml;charset=UTF-8");
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		builder.append("<server xmlns=\"http://versioning.egore911.de/server/1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://versioning.egore911.de/server/1.0 ");
		builder.append(req.getScheme());
		builder.append("://");
		builder.append(req.getServerName());
		switch (req.getScheme()) {
		case "http":
			if (req.getServerPort() != 80) {
				builder.append(":");
				builder.append(req.getServerPort());
			}
			break;
		case "https":
			if (req.getServerPort() != 443) {
				builder.append(":");
				builder.append(req.getServerPort());
			}
			break;
		default:
			builder.append(":");
			builder.append(req.getServerPort());
		}
		builder.append(req.getServletContext().getContextPath());
		builder.append("/xsd/server-1.0.xsd\">\n");
	}
	
	@Override
	protected void renderFooter(StringBuilder builder) {
		builder.append("</server>\n");
	}

	@Override
	protected void renderField(StringBuilder builder, int indent, String name, String value) {
		for (int i = 0; i < indent; i++) {
			builder.append("	");
		}
		builder.append("<" + name + ">");
		builder.append(value);
		builder.append("</" + name + ">\n");
	}

	@Override
	protected void renderComment(StringBuilder builder, int indent, String comment) {
		if (StringUtils.isNotEmpty(comment)) {
			for (int i = 0; i < indent; i++) {
				builder.append("	");
			}
			builder.append("<!-- ");
			builder.append(comment.replace("--", "__"));
			builder.append("-->\n");
		}
	}
	
	@Override
	protected void renderArrayStart(StringBuilder builder, int indent,
			String name) {
		for (int i = 0; i < indent; i++) {
			builder.append("	");
		}
		builder.append("<" + name + ">\n");
	}
	
	@Override
	protected void renderArrayEnd(StringBuilder builder, int indent, String name) {
		for (int i = 0; i < indent; i++) {
			builder.append("	");
		}
		builder.append("</" + name + ">\n");
	}
	
	@Override
	protected void renderArrayElementStart(StringBuilder builder, int indent,
			String name) {
		renderArrayStart(builder, indent, name);
	}

	@Override
	protected void renderArrayElementEnd(StringBuilder builder, int indent,
			String name) {
		renderArrayEnd(builder, indent, name);
	}
	
	@Override
	protected void renderArrayElement(StringBuilder builder, int indent,
			String name, String value) {
		renderField(builder, indent, name, value);
	}
	
	@Override
	protected void renderObjectStart(StringBuilder builder, int indent,
			String name) {
		renderArrayStart(builder, indent, name);
	}
	
	@Override
	protected void renderObjectEnd(StringBuilder builder, int indent,
			String name) {
		renderArrayEnd(builder, indent, name);
	}
	
}
