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
import com.github.bordertech.wcomponents.lib.mvc.msg.MsgEvent;
import com.github.bordertech.wcomponents.lib.mvc.msg.MsgEventType;
import com.github.bordertech.wcomponents.lib.util.ViewUtil;
import com.github.bordertech.wcomponents.template.TemplateRendererFactory;
import com.github.bordertech.wcomponents.util.Util;
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
		dispatchMessage(MsgEventType.RESET, "");
	}

	@Override
	public void dispatchValidationMessages(final List<Diagnostic> diags) {
		String qualifier = getDispatcherQualifier(MsgEventType.VALIDATION);
		dispatchEvent(new MsgEvent(diags, qualifier));
	}

	@Override
	public void dispatchMessage(final MsgEventType type, final String text) {
		String qualifier = getDispatcherQualifier(type);
		dispatchEvent(new MsgEvent(type, qualifier, text));
	}

	@Override
	public void dispatchMessage(final MsgEventType type, final List<String> texts) {
		String qualifier = getDispatcherQualifier(type);
		dispatchEvent(new MsgEvent(type, qualifier, texts, true));
	}

	protected String getDispatcherQualifier(final EventType type) {
		String qualifier = getQualifierOverride(type);
		return qualifier == null ? getFullQualifier() : qualifier;
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

	protected String getQualifierOverride(final EventType type) {
		BaseModel model = getComponentModel();
		if (model.qualifierOverride != null) {
			return model.qualifierOverride.get(type);
		}
		return null;
	}

	protected ComboView findParentCombo() {
		return ViewUtil.findParentCombo(this);
	}

	@Override
	public final String getQualifier() {
		return getComponentModel().qualifier;
	}

	@Override
	public final void setQualifier(final String qualifier) {
		// Not allow empty or null
		if (Util.empty(qualifier)) {
			throw new IllegalArgumentException("qualifier cannot be null or empty");
		}

		// Must start with a letter and followed by letters, digits and or underscores
		Matcher matcher = QUALIFIER_PATTERN.matcher(qualifier);
		if (!matcher.matches()) {
			throw new IllegalArgumentException(
					"Qualifier "
					+ qualifier
					+ " must start with a letter and followed by letters, digits and or dash.");
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
	private String deriveFullQualifier(final String qualifier) {
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
			if (isActiveQualifierContext(naming)) {
				parent = naming;
				break;
			}
			child = naming;
		}
		return parent;
	}

	/**
	 * Determine if this VIEW is an active qualifier context.
	 * <p>
	 * Can only be considered active if a qualifier has been set and flagged as a context.
	 * </p>
	 *
	 * @param view the view to test for naming context
	 * @return true if view is an active context
	 */
	public static boolean isActiveQualifierContext(final View view) {
		return view.isQualifierContext() && view.getQualifier() != null;
	}

}
