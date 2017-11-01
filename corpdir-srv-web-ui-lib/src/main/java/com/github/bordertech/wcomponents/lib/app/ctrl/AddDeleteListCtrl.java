package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.event.CollectionEventType;
import com.github.bordertech.wcomponents.lib.app.event.ModelEventType;
import com.github.bordertech.wcomponents.lib.app.view.event.SelectViewEvent;
import com.github.bordertech.wcomponents.lib.app.view.SelectSingleView;
import com.github.bordertech.wcomponents.lib.app.view.toolbar.AddDeleteButtonBar;
import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.wc.DefaultStore;
import java.util.Collection;
import com.github.bordertech.flux.wc.view.View;

/**
 * Add and Remove Controller.
 *
 * @param <T> the list item type
 * @param <C> the collection type
 * @author jonathan
 */
public class AddDeleteListCtrl<T, C extends Collection<T>> extends DefaultStore {

	@Override
	public void setupController() {
		super.setupController();
		// Listeners

		// ADD EVENT
		registerListener(ModelEventType.ADD, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleAddEvent();
			}
		});

		// Select EVENT
		registerListener(SelectViewEvent.SELECT, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleSelectEvent();
			}
		});

		// Unselect EVENT
		registerListener(SelectViewEvent.UNSELECT, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleUnselectEvent();
			}
		});

		// DELETE Event
		registerListener(ModelEventType.DELETE, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleDeleteEvent();
			}
		});

		// The ADD ITEM
		registerListener(ModelEventType.SELECTED, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleSelectedItemEvent((T) event.getData());
			}
		});
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

	public final AddDeleteButtonBar getAddRemoveToolbar() {
		return getComponentModel().addRemoveToolbar;
	}

	public final void setAddRemoveToolbar(final AddDeleteButtonBar addRemoveToolbar) {
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

	public final SelectSingleView<T, C> getSelectView() {
		return getComponentModel().selectView;
	}

	public final void setSelectView(final SelectSingleView<T, C> selectView) {
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
			dispatchEvent(CollectionEventType.REMOVE_ITEM, item);
		}
		getAddRemoveToolbar().resetView();
	}

	protected void handleSelectedItemEvent(final T item) {
		C beans = getSelectView().getViewBean();
		if (beans == null || !beans.contains(item)) {
			dispatchEvent(CollectionEventType.ADD_ITEM, item);
		}
		getAddRemoveToolbar().resetView();
		getAddView().resetView();
	}

	@Override
	protected AddDeleteModel<T, C> newComponentModel() {
		return new AddDeleteModel();
	}

	@Override
	protected AddDeleteModel<T, C> getComponentModel() {
		return (AddDeleteModel) super.getComponentModel();
	}

	@Override
	protected AddDeleteModel<T, C> getOrCreateComponentModel() {
		return (AddDeleteModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class AddDeleteModel<T, C extends Collection<T>> extends CtrlModel {

		private AddDeleteButtonBar addRemoveToolbar;

		private View addView;

		private SelectSingleView<T, C> selectView;
	}

}
