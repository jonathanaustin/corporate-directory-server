package com.github.bordertech.corpdir.web.ui.shell;

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
public class AbstractBasicEventView extends AbstractBasicView implements BasicEventView {

	@Override
	public void addAjaxTarget(final AjaxTarget target) {
		// Do nothing by default
	}

	@Override
	public List<ViewAction> getViewActions(final ViewEvent viewEvent) {
		List<ViewAction> actions = getViewActions().get(viewEvent);
		return actions == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(actions);
	}

	protected void executeEventActions(final ViewEvent viewEvent) {
		List<ViewAction> actions = getViewActions(viewEvent);
		for (ViewAction action : actions) {
			action.execute(this, viewEvent);
		}
	}

	protected void addViewAction(final ViewEvent viewEvent, final ViewAction viewAction) {
		EventViewModel model = getOrCreateComponentModel();
		if (model.viewActions == null) {
			model.viewActions = new HashMap<>();
		}
		List<ViewAction> actions = model.viewActions.get(viewEvent);
		if (actions == null) {
			actions = new ArrayList<>();
			model.viewActions.put(viewEvent, actions);
		}
		actions.add(viewAction);
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
