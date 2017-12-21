package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.flux.crud.actioncreator.EntityActionCreator;
import com.github.bordertech.flux.crud.store.EntityStore;
import com.github.bordertech.flux.crud.store.SearchStore;
import com.github.bordertech.flux.wc.common.TemplateConstants;
import com.github.bordertech.flux.wc.view.DefaultSmartView;
import com.github.bordertech.flux.wc.view.ViewUtil;
import com.github.bordertech.flux.wc.view.dumb.FormToolbarView;
import com.github.bordertech.flux.wc.view.dumb.FormView;
import com.github.bordertech.flux.wc.view.dumb.MessageView;
import com.github.bordertech.flux.wc.view.dumb.SearchView;
import com.github.bordertech.flux.wc.view.dumb.SelectSingleView;
import com.github.bordertech.flux.wc.view.dumb.ToolbarView;
import com.github.bordertech.flux.wc.view.smart.CrudSmartView;
import com.github.bordertech.flux.wc.view.smart.secure.DefaultSecureCardView;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WSection;
import com.github.bordertech.wcomponents.lib.security.DefaultAppPath;

/**
 * Secure card with a WSection Wrapper.
 *
 * @author jonathan
 */
public class AppSecureCrudWrapperView<S, T> extends DefaultSecureCardView<T> implements CrudSmartView<S, T> {

	private final WSection wrapper;
	private final CrudSmartView<S, T> crudView;

	public AppSecureCrudWrapperView(final String viewId, CardType cardType, final CrudSmartView<S, T> crudView) {
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
		S criteria = getRequestCriteria(request);
		if (criteria != null) {
			doRequestCriteria(criteria);
		}
	}

	@Override
	public void handleCheckCardRequest(final Request request) {
		SearchView<S> view = getSearchView();
		// Current search
		S current = view.getViewBean();
		// Search parameter on the request
		S criteria = getRequestCriteria(request);
		// Check state is OK
		if (criteria != null && !java.util.Objects.equals(current, criteria)) {
			doRequestCriteria(criteria);
		}
	}

	protected void doRequestCriteria(final S criteria) {
		resetView();
		setAutoSearch(true);
		getSearchView().setViewBean(criteria);
	}

	protected S getRequestCriteria(final Request request) {
		return (S) request.getParameter("id");
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
	public S getCriteria() {
		return crudView.getCriteria();
	}

	@Override
	public SearchView<S> getSearchView() {
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
	public void setSearchStoreKey(String storeKey) {
		crudView.setSearchStoreKey(storeKey);
	}

	@Override
	public String getSearchStoreKey() {
		return crudView.getSearchStoreKey();
	}

	@Override
	public SearchStore<S, T> getSearchStore() {
		return crudView.getSearchStore();
	}

	@Override
	public void setEntityActionCreatorKey(String creatorKey) {
		crudView.setEntityActionCreatorKey(creatorKey);
	}

	@Override
	public String getEntityActionCreatorKey() {
		return crudView.getEntityActionCreatorKey();
	}

	@Override
	public EntityActionCreator<T> getEntityActionCreator() {
		return crudView.getEntityActionCreator();
	}

	@Override
	public void setEntityStoreKey(String storeKey) {
		crudView.setEntityStoreKey(storeKey);
	}

	@Override
	public String getEntityStoreKey() {
		return crudView.getEntityStoreKey();
	}

	@Override
	public EntityStore<T> getEntityStore() {
		return crudView.getEntityStore();
	}

}
