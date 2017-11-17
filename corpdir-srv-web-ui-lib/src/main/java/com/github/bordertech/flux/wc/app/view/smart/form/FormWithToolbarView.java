package com.github.bordertech.flux.wc.app.view.smart.form;

import com.github.bordertech.flux.app.actioncreator.ModifyEntityCreator;
import com.github.bordertech.flux.event.ViewEventType;
import com.github.bordertech.flux.wc.app.mode.FormMode;
import com.github.bordertech.flux.wc.app.view.FormToolbarView;
import com.github.bordertech.flux.wc.app.view.FormView;
import com.github.bordertech.flux.wc.app.view.event.base.FormBaseViewEvent;
import com.github.bordertech.flux.wc.app.view.event.base.ToolbarBaseViewEvent;
import com.github.bordertech.flux.wc.app.view.form.DefaultFormView;
import com.github.bordertech.flux.wc.app.view.smart.msg.DefaultMessageSmartView;
import com.github.bordertech.flux.wc.app.view.toolbar.DefaultFormToolbarView;
import com.github.bordertech.wcomponents.WContainer;

/**
 * Form View with a Toolbar View.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public class FormWithToolbarView<T, M extends ModifyEntityCreator<T>> extends DefaultMessageSmartView<T> implements FormView<T> {

	private final M creator;

	private final FormView<T> formView;

	private final FormToolbarView toolbarView;

	public FormWithToolbarView(final M creator, final String viewId) {
		this(creator, viewId, new DefaultFormView<T>("vw-entity"));
	}

	public FormWithToolbarView(final M creator, final String viewId, final FormView<T> formView) {
		this(creator, viewId, formView, new DefaultFormToolbarView("vw-toolbar"));
	}

	public FormWithToolbarView(final M creator, final String viewId, final FormView<T> formView, final FormToolbarView toolbarView) {
		super(viewId, "wclib/hbs/layout/combo-ent-toolbar.hbs");

		this.creator = creator;

		// Views
		this.formView = formView;
		this.toolbarView = toolbarView;

		addComponentToTemplate("vw-toolbar", toolbarView);
		addComponentToTemplate("vw-form", formView);
	}

	public M getCreator() {
		return creator;
	}

	public final FormView<T> getFormView() {
		return formView;
	}

	public final FormToolbarView getToolbarView() {
		return toolbarView;
	}

	@Override
	public void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
		super.handleViewEvent(viewId, event, data);
		if (event instanceof FormBaseViewEvent) {
			handleFormBaseEvents((FormBaseViewEvent) event, data);
		} else if (event instanceof ToolbarBaseViewEvent) {
			handleToolbarBaseEvents((ToolbarBaseViewEvent) event, data);
		}
	}

	protected void handleFormBaseEvents(final FormBaseViewEvent type, final Object data) {

		switch (type) {
			case ENTITY_MODE_CHANGED:
				handleFormModeChangedEvent();
				break;
			case LOAD_OK:
				handleFormLoadOKEvent();
				break;
			case LOAD:
				handleFormLoadEvent((T) data);
				break;
			case LOAD_NEW:
				handleFormLoadNewEvent((T) data);
				break;
		}
	}

	protected void handleFormModeChangedEvent() {
		handleSyncToolbar();
	}

	protected void handleFormLoadOKEvent() {
		handleSyncToolbar();
	}

	protected void handleFormLoadEvent(final T entity) {
		doLoad(entity, FormMode.VIEW);
	}

	protected void handleFormLoadNewEvent(final T entity) {
		doLoad(entity, FormMode.ADD);
	}

	protected void handleToolbarBaseEvents(final ToolbarBaseViewEvent type, final Object data) {

		switch (type) {
			case ADD:
				handleToolbarAddEvent();
				break;
			case BACK:
				handleToolbarBackEvent();
				break;
			case CANCEL:
				handleToolbarCancelEvent();
				break;
			case CREATE:
				handleToolbarCreateEvent();
				break;
			case DELETE:
				handleToolbarDeleteEvent();
				break;
			case EDIT:
				handleToolbarEditEvent();
				break;
			case REFRESH:
				handleToolbarRefreshEvent();
				break;
			case UPDATE:
				handleToolbarUpdateEvent();
				break;
			case SELECT:
				handleToolbarSelectEvent();
				break;
// Handled
			case RESET:
				break;
		}
	}

	protected void handleToolbarRefreshEvent() {
	}

	protected void handleToolbarSelectEvent() {
	}

	protected void handleToolbarBackEvent() {
	}

	protected void handleToolbarCreateEvent() {
		handleSaveAction(true);
	}

	protected void handleToolbarUpdateEvent() {
		handleSaveAction(false);
	}

	protected void handleToolbarCancelEvent() {
		FormView<T> view = getFormView();
		if (view.getFormMode() == FormMode.EDIT) {
			T bean = view.getViewBean();
			handleMessageReset();
			dispatchViewEvent(FormBaseViewEvent.LOAD, bean);
			return;
		}
		// Do a BACK
		handleMessageReset();
		dispatchViewEvent(ToolbarBaseViewEvent.BACK);
	}

	protected void handleToolbarEditEvent() {
		FormView<T> view = getFormView();
		if (view.isLoaded()) {
			getFormView().setFormMode(FormMode.EDIT);
			dispatchViewEvent(FormBaseViewEvent.ENTITY_MODE_CHANGED);
		}
	}

	protected void handleToolbarDeleteEvent() {
		FormView<T> view = getFormView();
		if (!view.isLoaded()) {
			return;
		}
		T bean = view.getViewBean();
		try {
			doDelete(bean);
			resetView();
			// FIXME JA
//			dispatchEvent(FormEventType.RESET_FORM);
//			dispatchEvent(ModelOutcomeEventType.DELETE_OK, bean);
//			dispatchMessage(MessageEventType.SUCCESS, "Delete OK.");
		} catch (Exception e) {
			handleMessageError("Delete failed. " + e.getMessage());
		}
	}

// FIXME JA
//	protected void handleRetrieveAction(final T entity) {
//		try {
//			// Load the entity
//			T bean = doRefresh(entity);
//			// Load into the FORM
//			dispatchEvent(FormEventType.LOAD, bean);
//			dispatchEvent(ModelOutcomeEventType.RETRIEVE_OK, bean);
//		} catch (Exception e) {
//			dispatchMessage(MessageEventType.ERROR, "Retrieve failed. " + e.getMessage());
//			dispatchEvent(ModelOutcomeEventType.RETRIEVE_ERROR, entity, e);
//		}
//	}
//	protected void handleRefreshAction() {
//		FormView<T> view = getFormView();
//		if (!view.isLoaded()) {
//			return;
//		}
//		T bean = view.getViewBean();
//		try {
//			bean = doRefresh(bean);
//			dispatchEvent(FormEventType.LOAD, bean);
//			dispatchEvent(ModelOutcomeEventType.REFRESH_OK, bean);
//			dispatchMessage(MessageEventType.SUCCESS, "Refreshed OK.");
//		} catch (Exception e) {
//			dispatchMessage(MessageEventType.ERROR, "Refresh failed. " + e.getMessage());
//			dispatchEvent(ModelOutcomeEventType.REFRESH_ERROR, bean, e);
//		}
//	}
	protected void handleSaveAction(final boolean create) {
		FormView<T> view = getFormView();
		if (!view.isLoaded()) {
			return;
		}
		// Do Validation
		if (!view.validateView()) {
			return;
		}
		// Do Save
		try {
			view.updateViewBean();
			T bean = view.getViewBean();
			if (create) {
				bean = doCreate(bean);
//				dispatchEvent(FormEventType.RESET_FORM);
//				dispatchEvent(ModelOutcomeEventType.CREATE_OK, bean);
			} else {
				bean = doUpdate(bean);
//				dispatchEvent(FormEventType.RESET_FORM);
//				dispatchEvent(ModelOutcomeEventType.UPDATE_OK, bean);
			}
			handleMessageSuccess("Saved OK.");
		} catch (Exception e) {
			handleMessageSuccess("Save failed. " + e.getMessage());
		}
	}

	protected void handleToolbarAddEvent() {
		resetView();
		try {
			T bean = doCreateInstance();
			dispatchViewEvent(FormBaseViewEvent.LOAD_NEW, bean);
		} catch (Exception e) {
			handleMessageError("Add failed. " + e.getMessage());
		}
	}

	protected void handleSyncToolbar() {
		getToolbarView().setFormMode(getFormView().getFormMode());
		getToolbarView().setFormReady(getFormView().isLoaded());
	}

	protected void doLoad(final T entity, final FormMode mode) {
		resetView();
		getFormView().loadEntity(entity, mode);
		dispatchViewEvent(FormBaseViewEvent.ENTITY_MODE_CHANGED);
	}

	protected T doCreate(final T entity) {
		return getCreator().create(entity);
	}

	protected T doUpdate(final T entity) {
		return getCreator().update(entity);
	}

//	protected T doRefresh(final T entity) throws ServiceException {
//		return getCreator().refresh(entity);
//	}
	protected void doDelete(final T entity) {
		getCreator().delete(entity);
	}

	protected T doCreateInstance() {
		return getCreator().createInstance();
	}

	protected void doRefreshForm(final T entity) {

	}

	@Override
	public FormMode getFormMode() {
		return formView.getFormMode();
	}

	@Override
	public void setFormMode(final FormMode mode) {
		formView.setFormMode(mode);
	}

	@Override
	public boolean isFormReadonly() {
		return formView.isFormReadonly();
	}

	@Override
	public boolean isLoaded() {
		return formView.isLoaded();
	}

	@Override
	public void loadEntity(final T entity, final FormMode mode) {
		formView.loadEntity(entity, mode);
	}

	@Override
	public T getViewBean() {
		return formView.getViewBean();
	}

	@Override
	public void setViewBean(final T viewBean) {
		formView.setViewBean(viewBean);
	}

	@Override
	public void updateViewBean() {
		formView.updateViewBean();
	}

	@Override
	public boolean validateView() {
		return formView.validateView();
	}

	@Override
	public WContainer getFormHolder() {
		return formView.getFormHolder();
	}
}
