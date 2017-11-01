package com.github.bordertech.wcomponents.lib.app.ctrl;

import com.github.bordertech.wcomponents.lib.app.store.event.CollectionEventType;
import com.github.bordertech.wcomponents.lib.app.store.event.FormEventType;
import com.github.bordertech.wcomponents.lib.app.store.event.ModelEventType;
import com.github.bordertech.wcomponents.lib.app.store.event.ModelOutcomeEventType;
import com.github.bordertech.wcomponents.lib.app.view.event.SelectViewEvent;
import com.github.bordertech.wcomponents.lib.app.view.SelectSingleView;
import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.wc.DefaultStore;
import java.util.Collection;

/**
 * Controller for a Form View and Select View.
 *
 * @author jonathan
 * @param <T> the select entity
 * @param <C> the collection type
 */
public class FormSelectCtrl<T, C extends Collection<T>> extends DefaultStore {

	private boolean loadFresh;

	public FormSelectCtrl() {
		this(false);
	}

	public FormSelectCtrl(final boolean loadFresh) {
		this.loadFresh = loadFresh;
	}

	/**
	 * @return true if reload (ie refresh) the entity when selected.
	 */
	public final boolean isLoadFresh() {
		return loadFresh;
	}

	@Override
	public void setupController() {
		super.setupController();
		// Listeners

		// Model outcome event type Listeners
		for (ModelOutcomeEventType eventType : ModelOutcomeEventType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleEvent(final Event event) {
					handleModelOutcomeEvents(event);
				}
			};
			registerListener(eventType, listener);
		}

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
				T selected = (T) event.getData();
				handleSelectEvent(selected);
			}
		});

		// LIST Reset
		registerListener(CollectionEventType.RESET_COLLECTION, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleListResetEvent();
			}
		});

	}

	@Override
	public void checkConfig() {
		super.checkConfig();
		if (getSelectView() == null) {
			throw new IllegalStateException("A list view has not been set.");
		}
	}

	public final SelectSingleView<T, C> getSelectView() {
		return getComponentModel().selectView;
	}

	public final void setSelectView(final SelectSingleView<T, C> selectView) {
		getOrCreateComponentModel().selectView = selectView;
		addView(selectView);
	}

	protected void handleModelOutcomeEvents(final Event event) {
		ModelOutcomeEventType type = (ModelOutcomeEventType) event.getEventKey().getEventType();
		switch (type) {
			case CREATE_OK:
				handleCreateOkEvent((T) event.getData());
				break;
			case CREATE_ERROR:
				break;
			case DELETE_OK:
				handleDeleteOkEvent((T) event.getData());
				break;
			case UPDATE_OK:
				handleUpdateOkEvent((T) event.getData());
				break;

			case REFRESH_OK:
				handleRefreshOkEvent((T) event.getData());
				break;

			case RETRIEVE_OK:
				handleRetrieveOkEvent((T) event.getData());
				break;

			case DELETE_ERROR:
			case REFRESH_ERROR:
			case UPDATE_ERROR:
				break;
		}

	}

	protected void handleAddEvent() {
		dispatchMessageReset();
		getSelectView().clearSelected();
	}

	protected void handleSelectEvent(final T selected) {
		dispatchMessageReset();
		dispatchEvent(FormEventType.RESET_FORM);
		dispatchEvent(isLoadFresh() ? ModelEventType.RETRIEVE : FormEventType.LOAD, selected);
	}

	protected void handleListResetEvent() {
		dispatchMessageReset();
		dispatchEvent(FormEventType.RESET_FORM);
	}

	protected void handleUpdateOkEvent(final T entity) {
		getSelectView().updateItem(entity);
		// Reload Entity
		doReloadEntity(entity);
	}

	protected void handleRefreshOkEvent(final T entity) {
		getSelectView().updateItem(entity);
		getSelectView().setSelectedItem(entity);
	}

	protected void handleRetrieveOkEvent(final T entity) {
		getSelectView().updateItem(entity);
		getSelectView().setSelectedItem(entity);
	}

	protected void handleCreateOkEvent(final T entity) {
		getSelectView().addItem(entity);
		getSelectView().showView(true);
		getSelectView().setSelectedItem(entity);
		// Reload Entity
		doReloadEntity(entity);
	}

	protected void handleDeleteOkEvent(final T entity) {
		getSelectView().removeItem(entity);
		if (getSelectView().getViewBean().isEmpty()) {
			getSelectView().showView(false);
		}
		dispatchEvent(FormEventType.RESET_FORM);
	}

	protected void doReloadEntity(final T entity) {
		dispatchEvent(FormEventType.RESET_FORM);
		dispatchEvent(FormEventType.LOAD, entity);
	}

	@Override
	protected FormSelectModel<T, C> newComponentModel() {
		return new FormSelectModel();
	}

	@Override
	protected FormSelectModel<T, C> getComponentModel() {
		return (FormSelectModel) super.getComponentModel();
	}

	@Override
	protected FormSelectModel<T, C> getOrCreateComponentModel() {
		return (FormSelectModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class FormSelectModel<T, C extends Collection<T>> extends CtrlModel {

		private SelectSingleView<T, C> selectView;
	}

}
