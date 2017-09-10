package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.ListEventType;
import com.github.bordertech.wcomponents.lib.app.event.ToolbarEventType;
import com.github.bordertech.wcomponents.lib.app.toolbar.AddRemoveButtonBar;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.mvc.View;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultController;
import java.util.List;

/**
 * Add and Remove Controller.
 *
 * @param <T> the list item type
 * @author jonathan
 */
public class AddRemoveListCtrl<T> extends DefaultController {

	@Override
	public void setupController() {
		super.setupController();
		// Listeners

		// ADD EVENT
		Listener listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleAddEvent();
			}
		};
		registerListener(listener, ToolbarEventType.ADD);

		// Select EVENT
		listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleSelectEvent();
			}
		};
		registerListener(listener, ListEventType.SELECT);

		// Unselect EVENT
		listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleUnselectEvent();
			}
		};
		registerListener(listener, ListEventType.UNSELECT);

		// DELETE Event
		listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleDeleteEvent();
			}
		};
		registerListener(listener, ToolbarEventType.DELETE);

		// The ADD ITEM
		listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleSelectedItemEvent((T) event.getData());
			}
		};
		registerListener(listener, ToolbarEventType.SELECTED);
	}

	@Override
	public void checkConfig() {
		super.checkConfig();
		if (getAddRemoveToolbar() == null) {
			throw new IllegalStateException("An Add Remove Toolbar view has not been set.");
		}
		if (getAddView() == null) {
			throw new IllegalStateException("An Add View has not been set.");
		}
		if (getSelectView() == null) {
			throw new IllegalStateException("A list view has not been set.");
		}
	}

	public final AddRemoveButtonBar getAddRemoveToolbar() {
		return getComponentModel().addRemoveToolbar;
	}

	public final void setAddRemoveToolbar(final AddRemoveButtonBar addRemoveToolbar) {
		getOrCreateComponentModel().addRemoveToolbar = addRemoveToolbar;
		addView(addRemoveToolbar);
	}

	public final View getAddView() {
		return getComponentModel().addView;
	}

	public final void setAddView(final View addView) {
		getOrCreateComponentModel().addView = addView;
		addView(addView);
	}

	public final SelectView<T> getSelectView() {
		return getComponentModel().selectView;
	}

	public final void setSelectView(final SelectView<T> selectView) {
		getOrCreateComponentModel().selectView = selectView;
		addView(selectView);
	}

	protected void handleAddEvent() {
		getAddView().resetView();
		getAddView().setContentVisible(true);
	}

	protected void handleSelectEvent() {
		getAddRemoveToolbar().showRemoveButton(true);
	}

	protected void handleUnselectEvent() {
		getAddRemoveToolbar().showRemoveButton(false);
	}

	protected void handleDeleteEvent() {
		T item = getSelectView().getSelectedItem();
		if (item != null) {
			dispatchEvent(ListEventType.REMOVE_ITEM, item);
		}
		getAddRemoveToolbar().resetView();
	}

	protected void handleSelectedItemEvent(final T item) {
		List<T> beans = getSelectView().getViewBean();
		if (beans == null || !beans.contains(item)) {
			dispatchEvent(ListEventType.ADD_ITEM, item);
		}
		getAddRemoveToolbar().resetView();
		getAddView().resetView();
	}

	@Override
	protected AddRemoveModel newComponentModel() {
		return new AddRemoveModel();
	}

	@Override
	protected AddRemoveModel getComponentModel() {
		return (AddRemoveModel) super.getComponentModel();
	}

	@Override
	protected AddRemoveModel getOrCreateComponentModel() {
		return (AddRemoveModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class AddRemoveModel extends CtrlModel {

		private AddRemoveButtonBar addRemoveToolbar;

		private View addView;

		private SelectView selectView;
	}

}
