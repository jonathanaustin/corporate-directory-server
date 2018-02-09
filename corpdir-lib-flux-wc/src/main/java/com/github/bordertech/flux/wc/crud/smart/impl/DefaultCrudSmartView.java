package com.github.bordertech.flux.wc.crud.smart.impl;

import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.crud.actioncreator.CrudActionCreator;
import com.github.bordertech.flux.crud.store.CrudStore;
import com.github.bordertech.flux.store.StoreUtil;
import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.common.TemplateConstants;
import com.github.bordertech.flux.wc.crud.smart.CrudSearchSmartView;
import com.github.bordertech.flux.wc.crud.util.FormEventUtil;
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
import com.github.bordertech.flux.wc.view.smart.msg.DefaultMessageSmartView;
import com.github.bordertech.flux.wc.view.util.MessageEventUtil;
import com.github.bordertech.taskmaster.service.CallType;
import com.github.bordertech.taskmaster.service.ResultHolder;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.addons.polling.PollingStatus;
import com.google.common.base.Objects;
import java.util.List;

/**
 * Default CRUD view.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 */
public class DefaultCrudSmartView<S, K, T> extends DefaultMessageSmartView<T> implements CrudSearchSmartView<S, K, T> {

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
	public String getActionCreatorKey() {
		return getComponentModel().entityCreatorKey;
	}

	@Override
	public void setActionCreatorKey(final String entityCreatorKey) {
		getOrCreateComponentModel().entityCreatorKey = entityCreatorKey;
	}

	@Override
	public CrudActionCreator<T> getActionCreatorByKey() {
		return StoreUtil.getActionCreator(getActionCreatorKey());
	}

	@Override
	public String getStoreKey() {
		return getComponentModel().entityStoreKey;
	}

	@Override
	public void setStoreKey(final String entityStoreKey) {
		getOrCreateComponentModel().entityStoreKey = entityStoreKey;
	}

	@Override
	public CrudStore<S, K, T> getStoreByKey() {
		return StoreUtil.getStore(getStoreKey());
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
	public S getCriteria() {
		return searchView.getViewBean();
	}

	@Override
	public SearchView<S> getSearchView() {
		return searchView;
	}

	@Override
	public SelectSingleView<T> getSelectView() {
		return selectView;
	}

	@Override
	public ToolbarView getSearchToolbar() {
		return searchToolbar;
	}

	@Override
	public MessageView getSearchMessages() {
		return searchMessages;
	}

	@Override
	public DefaultSmartView getFormHolder() {
		return formHolder;
	}

	@Override
	public MessageView getFormMessages() {
		return formMessages;
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
		String key = getActionCreatorKey();
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
			handleSearchEvent();
			// POLLING
		} else if (isEvent(PollingBaseEventType.CHECK_STATUS, event)) {
			// Check if action is done
			ResultHolder<S, List<T>> resultHolder = handleCheckSearchResult();
			if (resultHolder != null) {
				// Stop polling
				pollingView.setPollingStatus(PollingStatus.STOPPED);
				// Handle the result
				handleResult(resultHolder);
			}

			// SELECT
		} else if (isEvent(SelectBaseEventType.SELECT, event)) {
			formHolder.resetView();
			formHolder.setContentVisible(true);
			dispatchViewEvent(FormBaseEventType.LOAD, (T) data);
		}
	}

	protected void handleResult(final ResultHolder<S, List<T>> resultHolder) {
		if (resultHolder.isResult()) {
			handleSearchSuccessful(resultHolder.getResult());
		} else {
			handleSearchException(resultHolder.getException());
		}
	}

	protected void handleSearchSuccessful(final List<T> items) {
		selectView.setItems(items);
		selectView.setContentVisible(true);
		if (items == null || items.isEmpty()) {
			dispatchMessageInfo("No records found.");
		} else if (items.size() == 1) {
			// Select the first
			T item = items.get(0);
			selectView.setSelectedItem(item);
			dispatchViewEvent(SelectBaseEventType.SELECT, item);
		}
	}

	protected void handleSearchException(final Exception e) {
		dispatchMessageError("Error loading details. " + e.getMessage());
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
				resetFormViews();
				selectView.addItem(entity);
				selectView.setContentVisible(true);
				selectView.setSelectedItem(entity);
				dispatchViewEvent(FormBaseEventType.LOAD, entity);
				break;

			case UPDATE_OK:
			case REFRESH_OK:
				resetFormViews();
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

	protected PollingView getPollingView() {
		return pollingView;
	}

	protected void handleSearchEvent() {
		selectView.resetView();
		ResultHolder<S, List<T>> resultHolder = handleStartSearch();
		if (resultHolder != null) {
			handleResult(resultHolder);
			return;
		}
		// Start Polling
		pollingView.resetView();
		pollingView.doManualStart();
	}

	protected ResultHolder<S, List<T>> handleStartSearch() {
		return doSearchAction(CallType.REFRESH_ASYNC);
	}

	protected ResultHolder<S, List<T>> handleCheckSearchResult() {
		return doSearchAction(CallType.CALL_ASYNC);
	}

	protected ResultHolder<S, List<T>> doSearchAction(final CallType callType) {
		return getStoreByKey().search(getCriteria(), callType);
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

		private String entityStoreKey;

		private String entityCreatorKey;

		private boolean autoSearch = true;
	}

}
