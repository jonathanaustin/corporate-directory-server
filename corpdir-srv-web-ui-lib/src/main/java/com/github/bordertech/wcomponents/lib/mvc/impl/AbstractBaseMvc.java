package com.github.bordertech.wcomponents.lib.mvc.impl;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultEvent;
import com.github.bordertech.wcomponents.lib.flux.impl.EventQualifier;
import com.github.bordertech.wcomponents.lib.flux.wc.DispatcherFactory;
import com.github.bordertech.wcomponents.lib.mvc.BaseMvc;
import com.github.bordertech.wcomponents.lib.mvc.ComboView;
import com.github.bordertech.wcomponents.lib.mvc.View;
import com.github.bordertech.wcomponents.lib.mvc.msg.MessageEvent;
import com.github.bordertech.wcomponents.lib.mvc.msg.MessageEventType;
import com.github.bordertech.wcomponents.lib.util.ViewUtil;
import com.github.bordertech.wcomponents.template.TemplateRendererFactory;
import com.github.bordertech.wcomponents.validation.Diagnostic;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base MVC class.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AbstractBaseMvc extends WTemplate implements BaseMvc {

	/**
	 * QUALIFIER pattern.
	 */
	private static final Pattern QUALIFIER_PATTERN = Pattern.compile(QUALIFIER_VALIDATION_PATTERN);

	public AbstractBaseMvc(final String templateName) {
		super(templateName, TemplateRendererFactory.TemplateEngine.HANDLEBARS);
	}

	@Override
	public final Dispatcher getDispatcher() {
		return DispatcherFactory.getInstance();
	}

	/**
	 * Helper method to dispatch an event for this view with the view qualifier automatically added.
	 *
	 * @param eventType the event type
	 */
	@Override
	public void dispatchEvent(final EventType eventType) {
		dispatchEvent(eventType, null, null);
	}

	/**
	 * Helper method to dispatch an event for this view with the view qualifier automatically added.
	 *
	 * @param eventType the event type
	 * @param data the event data
	 */
	@Override
	public void dispatchEvent(final EventType eventType, final Object data) {
		dispatchEvent(eventType, data, null);
	}

	/**
	 * Helper method to dispatch an event for this view with the view qualifier automatically added.
	 *
	 * @param eventType the event type
	 * @param data the event data
	 * @param exception an exception
	 */
	@Override
	public void dispatchEvent(final EventType eventType, final Object data, final Exception exception) {
		String qualifier = getDispatcherQualifier(eventType);
		DefaultEvent event = new DefaultEvent(new EventQualifier(eventType, qualifier), data, exception);
		dispatchEvent(event);
	}

	@Override
	public void dispatchEvent(final Event event) {
		getDispatcher().dispatch(event);
	}

	@Override
	public void dispatchMessageReset() {
		dispatchMessage(MessageEventType.RESET, "");
	}

	@Override
	public void dispatchValidationMessages(final List<Diagnostic> diags) {
		String qualifier = getMessageFullQualifier();
		dispatchEvent(new MessageEvent(diags, qualifier));
	}

	@Override
	public void dispatchMessage(final MessageEventType type, final String text) {
		String qualifier = getMessageFullQualifier();
		dispatchEvent(new MessageEvent(type, qualifier, text));
	}

	@Override
	public void dispatchMessage(final MessageEventType type, final List<String> texts) {
		String qualifier = getMessageFullQualifier();
		dispatchEvent(new MessageEvent(type, qualifier, texts, true));
	}

	protected String getDispatcherQualifier(final EventType type) {
		Map<EventType, String> overrides = getComponentModel().qualifierOverride;
		String qualifier;
		if (overrides != null && overrides.containsKey(type)) {
			qualifier = overrides.get(type);
		} else {
			qualifier = getQualifier();
		}
		return deriveFullQualifier(qualifier);
	}

	@Override
	public void addDispatcherOverride(final String qualifier, final EventType... types) {
		BaseModel model = getOrCreateComponentModel();
		if (model.qualifierOverride == null) {
			model.qualifierOverride = new HashMap<>();
		}
		for (EventType type : types) {
			model.qualifierOverride.put(type, qualifier);
		}
	}

	protected ComboView findParentCombo() {
		return ViewUtil.findParentCombo(this);
	}

	@Override
	public void setQualifierAndMessageQualifier(final String qualifier) {
		setQualifier(qualifier);
		setMessageQualifier(qualifier);
	}

	@Override
	public final String getQualifier() {
		return getComponentModel().qualifier;
	}

	@Override
	public final void setQualifier(final String qualifier) {
		if (qualifier != null) {
			// Must start with a letter and followed by letters, digits and or underscores
			Matcher matcher = QUALIFIER_PATTERN.matcher(qualifier);
			if (!matcher.matches()) {
				throw new IllegalArgumentException(
						"Qualifier "
						+ qualifier
						+ " must start with a letter and followed by letters, digits and or dash.");
			}
		}
		getOrCreateComponentModel().qualifier = qualifier;
	}

	@Override
	public String getFullQualifier() {
		// As determining the qualifier involves a fair bit of tree traversal, it is cached in the scratch map.
		// Try to retrieve the cached name first.
		Map scratchMap = getScratchMap();
		if (scratchMap != null) {
			String name = (String) scratchMap.get("vw-qualifier");
			if (name != null) {
				return name;
			}
		}

		// Get qualifier
		String qualifier = getQualifier();
		// Derive its full context name
		String name = deriveFullQualifier(qualifier);
		if (scratchMap != null) {
			scratchMap.put("vw-qualifier", name);
		}

		return name;
	}

	/**
	 * Derive the full qualifier from its context.
	 *
	 * @param qualifier the component id name
	 * @return the derived id in its context
	 */
	protected String deriveFullQualifier(final String qualifier) {
		// Find parent naming context
		View parent = getParentQualifierContext(this);

		// No Parent
		if (parent == null) {
			return qualifier;
		}

		// Get prefix
		String prefix = parent.getFullQualifier();

		// No Prefix, just use qualifier
		if (prefix.length() == 0) {
			return qualifier;
		}

		// No qualifier, just use prefix
		if (qualifier == null || qualifier.isEmpty()) {
			return prefix;
		}

		// Add Prefix
		StringBuilder nameBuf = new StringBuilder(prefix.length() + qualifier.length() + 1);
		nameBuf.append(prefix);
		nameBuf.append(QUALIFIER_CONTEXT_SEPERATOR);
		nameBuf.append(qualifier);

		return nameBuf.toString();
	}

	@Override
	public final String getMessageQualifier() {
		return getComponentModel().messageQualifier;
	}

	@Override
	public final void setMessageQualifier(final String messageQualifier) {
		if (messageQualifier != null) {
			// Must start with a letter and followed by letters, digits and or underscores
			Matcher matcher = QUALIFIER_PATTERN.matcher(messageQualifier);
			if (!matcher.matches()) {
				throw new IllegalArgumentException(
						"Message qualifier "
						+ messageQualifier
						+ " must start with a letter and followed by letters, digits and or dash.");
			}
		}
		getOrCreateComponentModel().messageQualifier = messageQualifier;
	}

	@Override
	public String getMessageFullQualifier() {
		// As determining the qualifier involves a fair bit of tree traversal, it is cached in the scratch map.
		// Try to retrieve the cached name first.
		Map scratchMap = getScratchMap();
		if (scratchMap != null) {
			String name = (String) scratchMap.get("vw-msg-qualifier");
			if (name != null) {
				return name;
			}
		}

		// Get qualifier
		String qualifier = getMessageQualifier();
		// Derive its full context name
		String name = deriveMessageFullQualifier(qualifier);
		if (scratchMap != null) {
			scratchMap.put("vw-msg-qualifier", name);
		}

		return name;
	}

	/**
	 * Derive the full qualifier from its context.
	 *
	 * @param messageQualifier the component id name
	 * @return the derived id in its context
	 */
	protected String deriveMessageFullQualifier(final String messageQualifier) {
		// Find parent naming context
		View parent = getParentMessageQualifierContext(this);

		// No Parent
		if (parent == null) {
			return messageQualifier;
		}

		// Get prefix
		String prefix = parent.getMessageFullQualifier();

		// No Prefix, just use qualifier
		if (prefix.length() == 0) {
			return messageQualifier;
		}

		// No qualifier, just use prefix
		if (messageQualifier == null || messageQualifier.isEmpty()) {
			return prefix;
		}

		// Add Prefix
		StringBuilder nameBuf = new StringBuilder(prefix.length() + messageQualifier.length() + 1);
		nameBuf.append(prefix);
		nameBuf.append(QUALIFIER_CONTEXT_SEPERATOR);
		nameBuf.append(messageQualifier);

		return nameBuf.toString();
	}

	@Override
	protected BaseModel newComponentModel() {
		return new BaseModel();
	}

	@Override
	protected BaseModel getComponentModel() {
		return (BaseModel) super.getComponentModel();
	}

	@Override
	protected BaseModel getOrCreateComponentModel() {
		return (BaseModel) super.getOrCreateComponentModel();
	}

	/**
	 * Just here as a place holder and easier for other Views to extend.
	 */
	public static class BaseModel extends TemplateModel {

		private String qualifier;

		private Map<EventType, String> qualifierOverride;

		private String messageQualifier;

	}

	/**
	 * Get this component's parent qualifier context.
	 *
	 * @param component the component to process
	 * @return the parent qualifier context or null
	 */
	public static View getParentQualifierContext(final WComponent component) {
		if (component == null) {
			return null;
		}

		WComponent child = component;
		View parent = null;
		while (true) {
			View naming = WebUtilities.getAncestorOfClass(View.class, child);
			if (naming == null) {
				break;
			}
			// Can only be considered active if a qualifier has been set and flagged as a context.
			if (naming.isQualifierContext() && naming.getQualifier() != null) {
				parent = naming;
				break;
			}
			child = naming;
		}
		return parent;
	}

	/**
	 * Get this component's parent message qualifier context.
	 *
	 * @param component the component to process
	 * @return the parent qualifier context or null
	 */
	public static View getParentMessageQualifierContext(final WComponent component) {
		if (component == null) {
			return null;
		}

		WComponent child = component;
		View parent = null;
		while (true) {
			View naming = WebUtilities.getAncestorOfClass(View.class, child);
			if (naming == null) {
				break;
			}
			// Can only be considered active if a qualifier has been set and flagged as a context.
			if (naming.isMessageQualifierContext() && naming.getMessageQualifier() != null) {
				parent = naming;
				break;
			}
			child = naming;
		}
		return parent;
	}

}
