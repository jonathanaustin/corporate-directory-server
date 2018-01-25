package com.github.bordertech.flux.wc.view;

import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.action.base.StateBaseActionType;
import com.github.bordertech.flux.crud.factory.FluxFactory;
import com.github.bordertech.flux.dispatcher.DispatcherModelUtil;
import com.github.bordertech.flux.key.ActionKey;
import com.github.bordertech.flux.key.ActionType;
import com.github.bordertech.flux.view.SmartView;
import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.common.TemplateConstants;
import com.github.bordertech.flux.wc.view.event.base.ToolbarBaseEventType;
import com.github.bordertech.flux.wc.view.smart.consumer.EntityActionCreatorConsumer;
import com.github.bordertech.flux.wc.view.smart.consumer.EntityStoreConsumer;
import com.github.bordertech.flux.wc.view.smart.consumer.RetrieveStoreConsumer;
import com.github.bordertech.flux.wc.view.smart.consumer.SearchStoreConsumer;
import com.github.bordertech.wcomponents.Container;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WebUtilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Smart view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultSmartView<T> extends DefaultDumbTemplateView<T> implements FluxSmartView<T> {

	public DefaultSmartView(final String viewId) {
		this(viewId, TemplateConstants.TEMPLATE_DEFAULT);
	}

	public DefaultSmartView(final String viewId, final String templateName) {
		super(viewId, templateName);
		setNamingContext(true);
	}

	@Override
	public void configAjax(final FluxDumbView view) {
		// By default ADD all the views as AJAX
		for (FluxDumbView vw : getViews()) {
			if (vw != view) {
				((FluxDumbView) view).addEventAjaxTarget(vw);
			}
		}
		if (isAjaxContext()) {
			return;
		}
		// Get the parent controller AJAX Targets
		FluxSmartView parent = findParentSmartView();
		if (parent != null) {
			parent.configAjax(view);
		}
	}

	@Override
	public boolean isAjaxContext() {
		return getComponentModel().ajaxContext;
	}

	@Override
	public void setAjaxContext(final boolean ajaxContext) {
		getOrCreateComponentModel().ajaxContext = ajaxContext;
	}

	@Override
	public void serviceViewEvent(final String viewId, final ViewEventType eventType, final Object data) {
		// Check if this Smart View will process this event
		boolean pass = isDumbMode() && (isPassAllEvents() || getPassThroughs().contains(eventType));
		if (pass) {
			// Try next parent smart view
			SmartView parent = findParentSmartView();
			if (parent != null) {
				parent.serviceViewEvent(viewId, eventType, data);
			}
			return;
		}
		// Handle event
		handleViewEvent(viewId, eventType, data);
	}

	@Override
	protected void initViewContent(Request request) {
		super.initViewContent(request);
		registerStoreConsumerListeners();
	}

	protected boolean isView(final String viewId, final FluxDumbView view) {
		return Objects.equals(viewId, view.getViewId());
	}

	protected boolean isEvent(final ViewEventType type1, final ViewEventType type2) {
		return Objects.equals(type1, type2);
	}

	protected boolean isResetEvent(final ViewEventType type) {
		return Objects.equals(ToolbarBaseEventType.RESET, type);
	}

	protected void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
		// Reset Event
		if (isResetEvent(event)) {
			handleResetEvent(viewId);
		}
	}

	protected void handleResetEvent(final String viewId) {
		resetView();
	}

	@Override
	public List<? extends FluxDumbView> getViews() {
		List<FluxDumbView> views = new ArrayList<>();
		for (WComponent child : getContent().getTaggedComponents().values()) {
			if (child instanceof FluxDumbView) {
				views.add((FluxDumbView) child);
			} else {
				// The child view maybe wrapped in another component
				findChildViews(views, child);
			}
		}
		return Collections.unmodifiableList(views);
	}

	protected void findChildViews(final List<FluxDumbView> views, final WComponent component) {
		if (component instanceof FluxDumbView) {
			views.add((FluxDumbView) component);
			return;
		}
		// Check its children
		if (component instanceof Container) {
			for (WComponent child : ((Container) component).getChildren()) {
				findChildViews(views, child);
			}
		}
	}

	@Override
	public FluxDumbView getView(final String viewId) {
		for (FluxDumbView view : getViews()) {
			if (Objects.equals(viewId, view.getViewId())) {
				return view;
			}
		}
		return null;
	}

	@Override
	public boolean isQualifierContext() {
		return getComponentModel().qualifierContext;
	}

	@Override
	public void setQualifierContext(final boolean qualifierContext) {
		getOrCreateComponentModel().qualifierContext = qualifierContext;
	}

	@Override
	public final String getQualifier() {
		return getComponentModel().qualifier;
	}

	@Override
	public final void setQualifier(final String qualifier) {
		DispatcherModelUtil.validateQualifier(qualifier);
		getOrCreateComponentModel().qualifier = qualifier;
	}

	@Override
	public boolean isDumbMode() {
		return getComponentModel().dumbMode;
	}

	@Override
	public void setDumbMode(final boolean dumbMode) {
		getOrCreateComponentModel().dumbMode = dumbMode;
	}

	@Override
	public void setPassAllEvents(boolean passAllEvents) {
		getOrCreateComponentModel().passAllEvents = passAllEvents;
	}

	@Override
	public boolean isPassAllEvents() {
		return getComponentModel().passAllEvents;
	}

	@Override
	public void addPassThrough(final ViewEventType type) {
		SmartViewModel model = getOrCreateComponentModel();
		if (model.passThroughs == null) {
			model.passThroughs = new HashSet();
		}
		model.passThroughs.add(type);
	}

	@Override
	public void removePassThrough(final ViewEventType type) {
		SmartViewModel model = getOrCreateComponentModel();
		if (model.passThroughs != null) {
			model.passThroughs.remove(type);
			if (model.passThroughs.isEmpty()) {
				model.passThroughs = null;
			}
		}
	}

	@Override
	public Set<ViewEventType> getPassThroughs() {
		SmartViewModel model = getComponentModel();
		return model.passThroughs == null ? Collections.EMPTY_SET : Collections.unmodifiableSet(model.passThroughs);
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
		FluxSmartView parent = getParentQualifierContext(this);

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
		return FluxFactory.getDispatcher();
	}

	protected void registerStoreConsumerListeners() {
		if (this instanceof EntityActionCreatorConsumer) {
			String key = ((EntityActionCreatorConsumer) this).getEntityActionCreatorKey();
			registerStoreKeyChangeListener(key);
		}
		if (this instanceof EntityStoreConsumer) {
			String key = ((EntityStoreConsumer) this).getEntityStoreKey();
			registerStoreKeyChangeListener(key);
		}
		if (this instanceof RetrieveStoreConsumer) {
			String key = ((RetrieveStoreConsumer) this).getRetrieveStoreKey();
			registerStoreKeyChangeListener(key);
		}
		if (this instanceof SearchStoreConsumer) {
			String key = ((SearchStoreConsumer) this).getSearchStoreKey();
			registerStoreKeyChangeListener(key);
		}
	}

	protected void registerStoreKeyChangeListener(final String storeKey) {
		Listener listener = new Listener() {
			@Override
			public void handleAction(final Action action) {
				handleStoreChangedAction(storeKey, action);
			}
		};
		registerListener(StateBaseActionType.STORE_CHANGED, storeKey, listener);
	}

	protected void handleStoreChangedAction(final String storeKey, final Action action) {
	}

	protected void registerListener(final ActionType actionType, final String qualifier, final Listener listener) {
		String id = getDispatcher().registerListener(new ActionKey(actionType, qualifier), listener);
		SmartViewModel model = getOrCreateComponentModel();
		if (model.registeredIds == null) {
			model.registeredIds = new HashSet<>();
		}
		model.registeredIds.add(id);
	}

	protected void unregisterListeners() {
		SmartViewModel model = getComponentModel();
		if (model.registeredIds != null) {
			Dispatcher dispatcher = getDispatcher();
			for (String id : model.registeredIds) {
				dispatcher.unregisterListener(id);
			}
			model.registeredIds = null;
		}
	}

	@Override
	public void reset() {
		unregisterListeners();
		super.reset();
	}

	/**
	 * Get this component's parent qualifier context.
	 *
	 * @param component the component to process
	 * @return the parent qualifier context or null
	 */
	public static FluxSmartView getParentQualifierContext(final WComponent component) {
		if (component == null) {
			return null;
		}

		WComponent child = component;
		FluxSmartView parent = null;
		while (true) {
			FluxSmartView naming = WebUtilities.getAncestorOfClass(FluxSmartView.class, child);
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
	protected SmartViewModel newComponentModel() {
		return new SmartViewModel();
	}

	@Override
	protected SmartViewModel getComponentModel() {
		return (SmartViewModel) super.getComponentModel();
	}

	@Override
	protected SmartViewModel getOrCreateComponentModel() {
		return (SmartViewModel) super.getOrCreateComponentModel();
	}

	/**
	 * Just here as a place holder and easier for other Views to extend.
	 */
	public static class SmartViewModel extends ViewModel {

		private String qualifier;

		private boolean qualifierContext;

		private boolean ajaxContext;

		private Set<String> registeredIds;

		private boolean dumbMode;

		private Set<ViewEventType> passThroughs;

		private boolean passAllEvents;
	}
}
