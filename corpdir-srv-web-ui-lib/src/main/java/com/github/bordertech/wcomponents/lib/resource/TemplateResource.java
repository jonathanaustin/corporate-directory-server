package com.github.bordertech.wcomponents.lib.resource;

import com.github.bordertech.wcomponents.ContentStreamAccess;
import com.github.bordertech.wcomponents.template.TemplateRenderer;
import com.github.bordertech.wcomponents.template.TemplateRendererFactory;
import com.github.bordertech.wcomponents.util.ConfigurationProperties;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jonathan
 */
public class TemplateResource implements ContentStreamAccess {

	private static final String DEFAULT_ENGINE = ConfigurationProperties.getDefaultRenderingEngine();

	private final boolean inline;
	private final String template;
	private final String engineName;
	private final String description;
	private final String mimeType;
	private final Map<String, Object> parameters;
	private final Map<String, Object> engineOptions;

	public TemplateResource(final Builder builder) {
		this.inline = builder.isInline();
		this.template = builder.getTemplate();
		this.engineName = builder.getEngineName() == null ? DEFAULT_ENGINE : builder.getEngineName();
		this.description = builder.getDescription() == null ? "template" : builder.getDescription();
		this.mimeType = builder.getMimeType() == null ? "text/plain" : builder.getMimeType();
		this.parameters = Collections.unmodifiableMap(builder.getParameters());
		this.engineOptions = Collections.unmodifiableMap(builder.getEngineOptions());
	}

	public boolean isInline() {
		return inline;
	}

	public String getTemplate() {
		return template;
	}

	public String getEngineName() {
		return engineName;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getMimeType() {
		return mimeType;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public Map<String, Object> getEngineOptions() {
		return engineOptions;
	}

	@Override
	public InputStream getStream() throws IOException {
		return new ByteArrayInputStream(processTemplate());

	}

	@Override
	public byte[] getBytes() {
		return processTemplate();
	}

	protected byte[] processTemplate() {
		StringWriter output = new StringWriter();
		PrintWriter writer = new PrintWriter(output);
		TemplateRenderer templateRenderer = TemplateRendererFactory.newInstance(getEngineName());
		if (isInline()) {
			templateRenderer.renderInline(getTemplate(), getParameters(), Collections.EMPTY_MAP, writer, getEngineOptions());
		} else {
			templateRenderer.renderTemplate(getTemplate(), getParameters(), Collections.EMPTY_MAP, writer, getEngineOptions());
		}
		return output.toString().getBytes();
	}

	public static class Builder {

		private final String template;
		private final boolean inline;
		private String engineName;
		private String description;
		private String mimeType;
		private final Map<String, Object> parameters = new HashMap<>();
		private final Map<String, Object> engineOptions = new HashMap<>();

		public Builder(final String template) {
			this(template, false);
		}

		public Builder(final String template, final boolean inline) {
			this.template = template;
			this.inline = inline;
		}

		protected boolean isInline() {
			return inline;
		}

		protected String getTemplate() {
			return template;
		}

		protected String getEngineName() {
			return engineName;
		}

		protected String getDescription() {
			return description;
		}

		protected String getMimeType() {
			return mimeType;
		}

		protected Map<String, Object> getParameters() {
			return parameters;
		}

		protected Map<String, Object> getEngineOptions() {
			return engineOptions;
		}

		public Builder setEngineName(final TemplateRendererFactory.TemplateEngine engine) {
			this.engineName = engine.getEngineName();
			return this;
		}

		public Builder setEngineName(final String engineName) {
			this.engineName = engineName;
			return this;
		}

		public Builder setDescription(final String description) {
			this.description = description;
			return this;
		}

		public Builder setMimeType(final String mimeType) {
			this.mimeType = mimeType;
			return this;
		}

		public Builder addParameter(final String key, final Object value) {
			parameters.put(key, value);
			return this;
		}

		public Builder addEngineOption(final String key, final Object value) {
			engineOptions.put(key, value);
			return this;
		}

		public TemplateResource build() {
			return new TemplateResource(this);
		}

	}

}
