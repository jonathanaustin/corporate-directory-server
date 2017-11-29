package com.github.bordertech.flux.wc.app.view.smart.crud;

import com.github.bordertech.flux.crud.action.CallType;
import com.github.bordertech.flux.crud.action.base.RetrieveBaseActionType;
import com.github.bordertech.flux.crud.actioncreator.EntityTreeActionCreator;
import com.github.bordertech.flux.crud.store.retrieve.EntityTreeStore;
import com.github.bordertech.flux.store.StoreUtil;
import com.github.bordertech.flux.wc.app.view.FormView;
import com.github.bordertech.flux.wc.app.view.SearchView;
import com.github.bordertech.flux.wc.app.view.event.base.FormBaseEventType;
import com.github.bordertech.flux.wc.app.view.event.base.FormBaseOutcomeEventType;
import com.github.bordertech.flux.wc.app.view.smart.CrudTreeSmartView;
import com.github.bordertech.flux.wc.app.view.smart.tree.DefaultListOrTreeSmartView;
import com.github.bordertech.flux.wc.app.view.smart.tree.ListOrTreeSmartView;
import com.github.bordertech.wcomponents.WComponent;
import java.util.List;

/**
 * Default CRUD Tree view.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 */
public class DefaultCrudTreeSmartView<S, T> extends DefaultCrudSmartView<S, T> implements CrudTreeSmartView<S, T> {

	public DefaultCrudTreeSmartView(final String viewId, final String title, final WComponent panel) {
		this(viewId, title, panel, new DefaultListOrTreeSmartView<T>("vw_list"));
	}

	public DefaultCrudTreeSmartView(final String viewId, final String title, final WComponent panel, final ListOrTreeSmartView<T> selectView2) {
		this(viewId, title, null, selectView2, null, panel);
	}

	public DefaultCrudTreeSmartView(final String viewId, final String title, final SearchView<S> criteriaView2, final ListOrTreeSmartView<T> selectView2, final FormView<T> formView2, final WComponent panel) {
		super(viewId, title, criteriaView2, selectView2, formView2, panel);
		selectView2.setDumbMode(true);
		selectView2.setPassAllEvents(true);
	}

	@Override
	public EntityTreeActionCreator<T> getEntityActionCreator() {
		return (EntityTreeActionCreator<T>) super.getEntityActionCreator();
	}

	@Override
	public EntityTreeStore<T> getEntityStore() {
		return (EntityTreeStore<T>) super.getEntityStore();
	}

	@Override
	protected DefaultListOrTreeSmartView<T> getSelectView() {
		return (DefaultListOrTreeSmartView) super.getSelectView();
	}

	@Override
	protected void doSearchAction() {
		if (getCriteria() == null) {
			// Use Root Items
			StoreUtil.dispatchRetrieveAction(getEntityStoreKey(), RetrieveBaseActionType.ROOT, null, CallType.REFRESH_ASYNC);
		} else {
			super.doSearchAction();
		}
	}

	@Override
	protected boolean isSearchActionDone() {
		if (getCriteria() == null) {
			return getEntityStore().isRootItemsDone();
		} else {
			return super.isSearchActionDone();
		}
	}

	@Override
	protected List<T> getSearchActionResult() {
		if (getCriteria() == null) {
			// Setup the Select View for "Tree" View.
			List<T> items = getEntityStore().getRootItems();
			getSelectView().resetView();
			getSelectView().setUseTree(true);
			getSelectView().setEntityTreeStoreKey(getEntityStoreKey());
			return items;
		} else {
			return super.getSearchActionResult();
		}
	}

	@Override
	protected void handleFormOutcomeEvents(final FormBaseOutcomeEventType type, final T entity) {
		if (getSelectView().isUseTree()) {
			switch (type) {
				case CREATE_OK:
				case DELETE_OK:
				case UPDATE_OK:
					// Refresh Tree
					// Get Root Items (SYNC)
					StoreUtil.dispatchRetrieveAction(getEntityStoreKey(), RetrieveBaseActionType.ROOT, null, CallType.REFRESH_SYNC);
					List<T> items = getEntityStore().getRootItems();
					getSelectView().resetView();
					getSelectView().setUseTree(true);
					getSelectView().setEntityTreeStoreKey(getEntityStoreKey());
					getSelectView().setContentVisible(true);
					getSelectView().setItems(items);
					if (type != FormBaseOutcomeEventType.DELETE_OK) {
						getSelectView().setSelectedItem(entity);
						dispatchViewEvent(FormBaseEventType.LOAD, entity);
					}
					return;
			}
		}
		super.handleFormOutcomeEvents(type, entity);
	}

}
