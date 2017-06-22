package com.github.bordertech.wcomponents.lib.view;

import com.github.bordertech.wcomponents.AjaxTarget;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract basic event view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultBasicEventView extends DefaultBasicView implements BasicEventView {

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
		EventViewModel model = getComponentModel();
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
		EventViewModel model = getOrCreateComponentModel();
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
		EventViewModel model = getComponentModel();
		return model.viewActions == null ? Collections.EMPTY_MAP : Collections.unmodifiableMap(model.viewActions);
	}

	@Override
	protected EventViewModel newComponentModel() {
		return new EventViewModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected EventViewModel getComponentModel() {
		return (EventViewModel) super.getComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected EventViewModel getOrCreateComponentModel() {
		return (EventViewModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the event view.
	 */
	public static class EventViewModel extends PanelModel {

		private Map<ViewEvent, List<ViewAction>> viewActions;
	}
}
