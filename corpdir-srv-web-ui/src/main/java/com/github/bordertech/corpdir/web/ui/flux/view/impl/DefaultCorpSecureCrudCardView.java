package com.github.bordertech.corpdir.web.ui.flux.view.impl;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.corpdir.web.ui.CardType;
import com.github.bordertech.flux.crud.actioncreator.CrudActionCreator;
import com.github.bordertech.flux.crud.store.CrudStore;
import com.github.bordertech.flux.wc.common.TemplateConstants;
import com.github.bordertech.flux.wc.crud.smart.CrudSearchSmartView;
import com.github.bordertech.flux.wc.view.DefaultSmartView;
import com.github.bordertech.flux.wc.view.dumb.FormToolbarView;
import com.github.bordertech.flux.wc.view.dumb.FormView;
import com.github.bordertech.flux.wc.view.dumb.MessageView;
import com.github.bordertech.flux.wc.view.dumb.SearchView;
import com.github.bordertech.flux.wc.view.dumb.SelectSingleView;
import com.github.bordertech.flux.wc.view.dumb.ToolbarView;
import com.github.bordertech.flux.wc.view.smart.secure.DefaultSecureCardView;
import com.github.bordertech.flux.wc.view.util.ViewUtil;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WSection;
import com.github.bordertech.wcomponents.addons.cardpath.impl.DefaultAppPath;

/**
 * Secure card with a WSection Wrapper.
 *
 * @param <T> the Corp Dir entity type
 */
public class DefaultCorpSecureCrudCardView<T extends ApiIdObject> extends DefaultSecureCardView<T> implements CrudSearchSmartView<String, String, T> {

	private final WSection wrapper;
	private final CrudSearchSmartView<String, String, T> crudView;

	public DefaultCorpSecureCrudCardView(final String viewId, final CardType cardType, final CrudSearchSmartView<String, String, T> crudView) {
		super(viewId, new DefaultAppPath(cardType.getPath()));
		this.wrapper = new WSection(cardType.getDesc());
		this.crudView = crudView;
		wrapper.getContent().add(crudView);
		if (cardType.getImageUrl() != null) {
			ViewUtil.addImageToLabelHead(cardType.getImageUrl(), wrapper.getDecoratedLabel(), true);
			wrapper.getDecoratedLabel().addHtmlClass("app-valign");
		}
		addComponentToTemplate(TemplateConstants.TAG_VW_CONTENT, wrapper);
	}

	protected WSection getWrapper() {
		return wrapper;
	}

	@Override
	public void handleShowCardRequest(final Request request) {
		String criteria = getRequestCriteria(request);
		if (criteria != null) {
			doRequestCriteria(criteria);
		}
	}

	@Override
	public void handleCheckCardRequest(final Request request) {
		SearchView<String> view = getSearchView();
		// Current search
		String current = view.getViewBean();
		// Search parameter on the request
		String criteria = getRequestCriteria(request);
		// Check state is OK
		if (criteria != null && !java.util.Objects.equals(current, criteria)) {
			doRequestCriteria(criteria);
		}
	}

	protected void doRequestCriteria(final String criteria) {
		resetView();
		setAutoSearch(true);
		getSearchView().setViewBean(criteria);
	}

	protected String getRequestCriteria(final Request request) {
		return (String) request.getParameter("id");
	}

	@Override
	public boolean isAutoSearch() {
		return crudView.isAutoSearch();
	}

	@Override
	public void setAutoSearch(boolean autoSearch) {
		crudView.setAutoSearch(autoSearch);
	}

	@Override
	public String getCriteria() {
		return crudView.getCriteria();
	}

	@Override
	public SearchView<String> getSearchView() {
		return crudView.getSearchView();
	}

	@Override
	public SelectSingleView<T> getSelectView() {
		return crudView.getSelectView();
	}

	@Override
	public ToolbarView getSearchToolbar() {
		return crudView.getSearchToolbar();
	}

	@Override
	public MessageView getSearchMessages() {
		return crudView.getSearchMessages();
	}

	@Override
	public DefaultSmartView getFormHolder() {
		return crudView.getFormHolder();
	}

	@Override
	public MessageView getFormMessages() {
		return crudView.getFormMessages();
	}

	@Override
	public FormView<T> getFormView() {
		return crudView.getFormView();
	}

	@Override
	public FormToolbarView<T> getFormToolbarView() {
		return crudView.getFormToolbarView();
	}

	@Override
	public void resetFormViews() {
		crudView.resetFormViews();
	}

	@Override
	public void setActionCreatorKey(String creatorKey) {
		crudView.setActionCreatorKey(creatorKey);
	}

	@Override
	public String getActionCreatorKey() {
		return crudView.getActionCreatorKey();
	}

	@Override
	public CrudActionCreator<T> getActionCreatorByKey() {
		return crudView.getActionCreatorByKey();
	}

	@Override
	public void setStoreKey(String storeKey) {
		crudView.setStoreKey(storeKey);
	}

	@Override
	public String getStoreKey() {
		return crudView.getStoreKey();
	}

	@Override
	public CrudStore<String, String, T> getStoreByKey() {
		return crudView.getStoreByKey();
	}

}
