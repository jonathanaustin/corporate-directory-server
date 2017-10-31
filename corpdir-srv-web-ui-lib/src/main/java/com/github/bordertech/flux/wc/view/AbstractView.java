package com.github.bordertech.flux.wc.view;

import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.EventType;
import com.github.bordertech.flux.View;
import com.github.bordertech.flux.ViewContainer;
import com.github.bordertech.flux.impl.DefaultEvent;
import com.github.bordertech.flux.EventKey;
import com.github.bordertech.flux.wc.DispatcherFactory;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.template.TemplateRendererFactory;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Dumb Flux View.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AbstractView extends WTemplate implements View {

	/**
	 * QUALIFIER pattern.
	 */
	private static final Pattern QUALIFIER_PATTERN = Pattern.compile(QUALIFIER_VALIDATION_PATTERN);

	public AbstractView(final String templateName) {
		super(templateName, TemplateRendererFactory.TemplateEngine.HANDLEBARS);
	}

	@Override
	public void registerStoreChangeListeners() {
		// None by default.
	}

	@Override
	public void unregisterStoreChangeListeners() {
		// None by default.
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
		String qualifier = getFullQualifier();
		DefaultEvent event = new DefaultEvent(new EventKey(eventType, qualifier), data, exception);
		dispatchEvent(event);
	}

	@Override
	public void dispatchEvent(final Event event) {
		getDispatcher().dispatch(event);
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
		ViewContainer parent = getParentQualifierContext(this);

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

	protected final Dispatcher getDispatcher() {
		return DispatcherFactory.getInstance();
	}

	/**
	 * Get this component's parent qualifier context.
	 *
	 * @param component the component to process
	 * @return the parent qualifier context or null
	 */
	public static ViewContainer getParentQualifierContext(final WComponent component) {
		if (component == null) {
			return null;
		}

		WComponent child = component;
		ViewContainer parent = null;
		while (true) {
			ViewContainer naming = WebUtilities.getAncestorOfClass(ViewContainer.class, child);
			if (naming == null) {
				break;
			}
			// Can only be considered active if a qualifier has been set and flagged as a context.
			if (naming.isQualifierContext() && naming.getQualifier() != null) {
				parent = naming;
				break;
			}
			child = (WComponent) naming;
		}
		return parent;
	}

	@Override
	protected ViewModel newComponentModel() {
		return new ViewModel();
	}

	@Override
	protected ViewModel getComponentModel() {
		return (ViewModel) super.getComponentModel();
	}

	@Override
	protected ViewModel getOrCreateComponentModel() {
		return (ViewModel) super.getOrCreateComponentModel();
	}

	/**
	 * Just here as a place holder and easier for other Views to extend.
	 */
	public static class ViewModel extends TemplateModel {

		private String qualifier;

	}

}
