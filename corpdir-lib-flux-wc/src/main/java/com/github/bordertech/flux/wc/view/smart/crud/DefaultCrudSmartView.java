package com.github.bordertech.flux.wc.view.smart.crud;

import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.crud.action.retrieve.CallType;
import com.github.bordertech.flux.crud.actioncreator.EntityActionCreator;
import com.github.bordertech.flux.crud.store.EntityStore;
import com.github.bordertech.flux.crud.store.SearchStore;
import com.github.bordertech.flux.store.StoreUtil;
import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.common.TemplateConstants;
import com.github.bordertech.flux.wc.mode.FormMode;
import com.github.bordertech.flux.wc.view.DefaultSmartView;
import com.github.bordertech.flux.wc.view.dumb.FormToolbarView;
import com.github.bordertech.flux.wc.view.dumb.FormView;
import com.github.bordertech.flux.wc.view.dumb.MessageView;
import com.github.bordertech.flux.wc.view.dumb.PollingView;
import com.github.bordertech.flux.wc.view.dumb.SearchView;
import com.github.bordertech.flux.wc.view.dumb.SelectSingleView;
import com.github.bordertech.flux.wc.view.dumb.ToolbarView;
import com.github.bordertech.flux.wc.view.dumb.form.DefaultFormView;
import com.github.bordertech.flux.wc.view.dumb.msg.DefaultMessageView;
import com.github.bordertech.flux.wc.view.dumb.polling.DefaultPollingView;
import com.github.bordertech.flux.wc.view.dumb.search.SearchTextView;
import com.github.bordertech.flux.wc.view.dumb.select.MenuSelectView;
import com.github.bordertech.flux.wc.view.dumb.toolbar.DefaultFormToolbarView;
import com.github.bordertech.flux.wc.view.dumb.toolbar.DefaultToolbarView;
import com.github.bordertech.flux.wc.view.dumb.toolbar.ToolbarModifyItemType;
import com.github.bordertech.flux.wc.view.event.base.FormBaseEventType;
import com.github.bordertech.flux.wc.view.event.base.FormBaseOutcomeEventType;
import com.github.bordertech.flux.wc.view.event.base.MessageBaseEventType;
import com.github.bordertech.flux.wc.view.event.base.PollingBaseEventType;
import com.github.bordertech.flux.wc.view.event.base.SearchBaseEventType;
import com.github.bordertech.flux.wc.view.event.base.SelectBaseEventType;
import com.github.bordertech.flux.wc.view.event.base.ToolbarBaseEventType;
import com.github.bordertech.flux.wc.view.event.util.FormEventUtil;
import com.github.bordertech.flux.wc.view.event.util.MessageEventUtil;
import com.github.bordertech.flux.wc.view.smart.CrudSmartView;
import com.github.bordertech.flux.wc.view.smart.msg.DefaultMessageSmartView;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.lib.polling.PollingStatus;
import com.google.common.base.Objects;
import java.util.List;

/**
 * Default CRUD view.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 */
public class DefaultCrudSmartView<S, T> extends DefaultMessageSmartView<T> implements CrudSmartView<S, T> {

	private final SearchView<S> searchView;
	private final SelectSingleView<T> selectView;
	private final PollingView pollingView = new DefaultPollingView<>("vw_poll");
	private final ToolbarView searchToolbar = new DefaultToolbarView("vw_toolbar_1");
	private final MessageView searchMessages = new DefaultMessageView("vw_crit_msg");
	// Form Details
	private final DefaultSmartView formHolder = new DefaultSmartView("vw_form", TemplateConstants.TEMPLATE_ENT_CRUD_FORM);
	private final MessageView formMessages = new DefaultMessageView("vw_form_msg");
	private final FormToolbarView<T> formToolbarView = new DefaultFormToolbarView("vw_form_toolbar");
	private final FormView<T> formView;

	public DefaultCrudSmartView(final String viewId, final String title, final WComponent panel) {
		this(viewId, title, null, null, null, panel);
	}

	public DefaultCrudSmartView(final String viewId, final String title, final WComponent panel, final SelectSingleView<T> selectView2) {
		this(viewId, title, null, selectView2, null, panel);
	}

	public DefaultCrudSmartView(final String viewId, final String title, final SearchView<S> criteriaView2, final SelectSingleView<T> selectView2, final FormView<T> formView2, final WComponent panel) {
		super(viewId, TemplateConstants.TEMPLATE_ENT_CRUD, false);

		setAjaxContext(true);

		// Setup Defaults
		searchView = criteriaView2 == null ? (SearchView<S>) new SearchTextView("vw_crit") : criteriaView2;
		selectView = selectView2 == null ? (SelectSingleView) new MenuSelectView("vw_list") : selectView2;
		formView = formView2 == null ? new DefaultFormView<T>("vw_form") : formView2;
		if (panel != null) {
			formView.getFormHolder().add(panel);
		}

		// Form Holder
		formHolder.setDumbMode(true);
		formHolder.setPassAllEvents(true);
		formHolder.addHtmlClass("wc-panel-type-box");
		formHolder.addComponentToTemplate(TemplateConstants.TAG_VW_FORM_TOOLBAR, formToolbarView);
		formHolder.addComponentToTemplate(TemplateConstants.TAG_VW_FORM_MSG, formMessages);
		formHolder.addComponentToTemplate(TemplateConstants.TAG_VW_FORM_VIEW, formView);

		// Add views
		addComponentToTemplate(TemplateConstants.TAG_VW_TOOLBAR_TOP, searchToolbar);
		addComponentToTemplate(TemplateConstants.TAG_VW_CRIT_MSG, searchMessages);
		addComponentToTemplate(TemplateConstants.TAG_VW_CRIT, searchView);
		addComponentToTemplate(TemplateConstants.TAG_VW_POLL, pollingView);
		addComponentToTemplate(TemplateConstants.TAG_VW_LIST, selectView);
		addComponentToTemplate(TemplateConstants.TAG_VW_FORM, formHolder);

		// Title
		getContent().addParameter(TemplateConstants.PARAM_TITLE, title);

		// Toolbar Defaults
		searchToolbar.addToolbarItem(ToolbarModifyItemType.ADD);

		// Default visibility
		selectView.setContentVisible(false);
		formHolder.setContentVisible(false);
		setQualifierContext(true);
	}

	public boolean isAutoSearch() {
		return getComponentModel().autoSearch;
	}

	public void setAutoSearch(final boolean autoSearch) {
		getOrCreateComponentModel().autoSearch = autoSearch;
	}

	@Override
	public String getEntityActionCreatorKey() {
		return getComponentModel().entityCreatorKey;
	}

	@Override
	public void setEntityActionCreatorKey(final String entityCreatorKey) {
		getOrCreateComponentModel().entityCreatorKey = entityCreatorKey;
	}

	@Override
	public EntityActionCreator<T> getEntityActionCreator() {
		return StoreUtil.getActionCreator(getEntityActionCreatorKey());
	}

	@Override
	public String getEntityStoreKey() {
		return getComponentModel().entityStoreKey;
	}

	@Override
	public void setEntityStoreKey(final String entityStoreKey) {
		getOrCreateComponentModel().entityStoreKey = entityStoreKey;
	}

	@Override
	public EntityStore<T> getEntityStore() {
		return StoreUtil.getStore(getEntityStoreKey());
	}

	@Override
	public void setSearchStoreKey(final String searchStoreKey) {
		getOrCreateComponentModel().searchStoreKey = searchStoreKey;
	}

	@Override
	public String getSearchStoreKey() {
		return getComponentModel().searchStoreKey;
	}

	@Override
	public SearchStore<S, T> getSearchStore() {
		return StoreUtil.getStore(getSearchStoreKey());
	}

	@Override
	public FormView<T> getFormView() {
		return formView;
	}

	@Override
	public FormToolbarView<T> getFormToolbarView() {
		return formToolbarView;
	}

	@Override
	public void resetFormViews() {
		formHolder.resetContent();
	}

	@Override
	protected void initViewContent(final Request request) {
		super.initViewContent(request);
		if (isAutoSearch()) {
			dispatchViewEvent(SearchBaseEventType.SEARCH);
		}
	}

	@Override
	protected void handleStoreChangedAction(final String storeKey, final Action action) {
		super.handleStoreChangedAction(storeKey, action);
		String key = getEntityActionCreatorKey();
		if (Objects.equal(key, storeKey) && formHolder.isContentVisible() && formView.getFormMode() == FormMode.VIEW) {
			// Do a refresh
			dispatchViewEvent(ToolbarBaseEventType.REFRESH);
		}
	}

	@Override
	protected void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {

		// Message Events
		if (event instanceof MessageBaseEventType) {
			if ((isView(viewId, formToolbarView) || isView(viewId, formView)) && formHolder.isContentVisible()) {
				MessageEventUtil.handleMessageBaseViewEvents(formMessages, (MessageBaseEventType) event, data);
			} else {
				MessageEventUtil.handleMessageBaseViewEvents(this, (MessageBaseEventType) event, data);
			}
			return;
		}
		super.handleViewEvent(viewId, event, data);

		// Handle the Form and Form Toolbar Events
		FormEventUtil.handleFormEvents(this, viewId, event, data);
		if (event instanceof FormBaseOutcomeEventType) {
			handleFormOutcomeEvents((FormBaseOutcomeEventType) event, (T) data);
		}

		// Search Validating
		if (isEvent(SearchBaseEventType.SEARCH_VALIDATING, event)) {
			selectView.resetView();
			pollingView.resetView();
			resetFormViews();
			// Search
		} else if (isEvent(SearchBaseEventType.SEARCH, event)) {
			selectView.resetView();
			doSearchAction();
			// Start Polling
			pollingView.resetView();
			pollingView.doManualStart();

			// POLLING
		} else if (isEvent(PollingBaseEventType.CHECK_STATUS, event)) {
			// Check if action is done
			if (isSearchActionDone()) {
				// Stop polling
				pollingView.setPollingStatus(PollingStatus.STOPPED);
				// Handle the result
				try {
					List<T> result = getSearchActionResult();
					selectView.setItems(result);
					selectView.setContentVisible(true);
					if (result == null || result.isEmpty()) {
						dispatchMessageInfo("No records found.");
					}
				} catch (Exception e) {
					dispatchMessageError("Error loading details. " + e.getMessage());
				}
			}

			// SELECT
		} else if (isEvent(SelectBaseEventType.SELECT, event)) {
			formHolder.resetView();
			formHolder.setContentVisible(true);
			dispatchViewEvent(FormBaseEventType.LOAD, (T) data);
		}
	}

	@Override
	protected void handleResetEvent(final String viewId) {
		if (isView(viewId, formToolbarView)) {
			resetFormViews();
		} else {
			super.handleResetEvent(viewId);
		}
	}

	/**
	 * Extra config for the FORM Outcome events.
	 *
	 * @param type
	 * @param entity
	 */
	protected void handleFormOutcomeEvents(final FormBaseOutcomeEventType type, final T entity) {
		switch (type) {
			case ADD_OK:
				dispatchMessageReset();
				selectView.clearSelected();
				dispatchViewEvent(FormBaseEventType.LOAD_NEW, entity);
				break;

			case LOAD_OK:
				formHolder.setContentVisible(true);
				break;

			case CREATE_OK:
				selectView.addItem(entity);
				selectView.setContentVisible(true);
				selectView.setSelectedItem(entity);
				dispatchViewEvent(FormBaseEventType.LOAD, entity);
				break;

			case UPDATE_OK:
			case REFRESH_OK:
				selectView.updateItem(entity);
				dispatchViewEvent(FormBaseEventType.LOAD, entity);
				break;

			case DELETE_OK:
				selectView.removeItem(entity);
				if (selectView.getViewBean().isEmpty()) {
					selectView.setContentVisible(false);
				}
				formHolder.setContentVisible(false);
				break;
		}
	}

	protected S getCriteria() {
		return searchView.getViewBean();
	}

	protected SearchView<S> getSearchView() {
		return searchView;
	}

	protected SelectSingleView<T> getSelectView() {
		return selectView;
	}

	protected PollingView getPollingView() {
		return pollingView;
	}

	protected ToolbarView getSearchToolbar() {
		return searchToolbar;
	}

	protected MessageView getSearchMessages() {
		return searchMessages;
	}

	protected DefaultSmartView getFormHolder() {
		return formHolder;
	}

	protected MessageView getFormMessages() {
		return formMessages;
	}

	protected void doSearchAction() {
		StoreUtil.dispatchSearchAction(getSearchStoreKey(), getCriteria(), CallType.REFRESH_ASYNC);
	}

	protected boolean isSearchActionDone() {
		return getSearchStore().isSearchDone(getCriteria());
	}

	protected List<T> getSearchActionResult() {
		return getSearchStore().search(getCriteria());
	}

	@Override
	protected CrudFormModel newComponentModel() {
		return new CrudFormModel();
	}

	@Override
	protected CrudFormModel getComponentModel() {
		return (CrudFormModel) super.getComponentModel();
	}

	@Override
	protected CrudFormModel getOrCreateComponentModel() {
		return (CrudFormModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class CrudFormModel extends SmartViewModel {

		private String searchStoreKey;

		private String entityStoreKey;

		private String entityCreatorKey;

		private boolean autoSearch = true;
	}

}
