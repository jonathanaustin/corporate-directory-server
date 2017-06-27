package com.github.bordertech.wcomponents.lib.view;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Request;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Default basic view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultBasicView extends WDiv implements BasicView {

	private final WDiv holder = new WDiv() {
		@Override
		protected void preparePaintComponent(final Request request) {
			super.preparePaintComponent(request);
			if (!isInitialised()) {
				initViewContent(request);
				setInitialised(true);
			}
		}
	};

	@Override
	public final WDiv getViewHolder() {
		return holder;
	}

	protected void initViewContent(final Request request) {
		// Do nothing
	}

	@Override
	public void addEventAjaxTarget(final AjaxTarget target, final ViewEvent... viewEvent) {
		// Do nothing by default
	}

	@Override
	public List<ViewAction> getRegisteredViewActions(final ViewEvent viewEvent) {
		List<ViewAction> actions = getViewActions().get(viewEvent);
		return actions == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(actions);
	}

	@Override
	public boolean hasRegisteredViewAction(final ViewEvent viewEvent) {
		ViewModel model = getComponentModel();
		if (model.viewActions != null) {
			return model.viewActions.containsKey(viewEvent);
		}
		return false;
	}

	protected void executeRegisteredViewActions(final ViewEvent viewEvent) {
		List<ViewAction> actions = getRegisteredViewActions(viewEvent);
		for (ViewAction action : actions) {
			action.execute(this, viewEvent);
		}
	}

	protected void addViewAction(final ViewAction viewAction, final ViewEvent... viewEvents) {
		if (viewEvents.length == 0) {
			return;
		}
		ViewModel model = getOrCreateComponentModel();
		if (model.viewActions == null) {
			model.viewActions = new HashMap<>();
		}
		for (ViewEvent event : viewEvents) {
			List<ViewAction> actions = model.viewActions.get(event);
			if (actions == null) {
				actions = new ArrayList<>();
				model.viewActions.put(event, actions);
			}
			actions.add(viewAction);
		}

	}

	protected Map<ViewEvent, List<ViewAction>> getViewActions() {
		ViewModel model = getComponentModel();
		return model.viewActions == null ? Collections.EMPTY_MAP : Collections.unmodifiableMap(model.viewActions);
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
	 * Holds the extrinsic state information of the event view.
	 */
	public static class ViewModel extends DivModel {

		private Map<ViewEvent, List<ViewAction>> viewActions;
	}
}
