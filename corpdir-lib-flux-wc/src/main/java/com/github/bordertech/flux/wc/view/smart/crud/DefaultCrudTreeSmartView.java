package com.github.bordertech.flux.wc.view.smart.crud;

import com.github.bordertech.flux.crud.actioncreator.CrudTreeActionCreator;
import com.github.bordertech.flux.crud.store.CrudTreeStore;
import com.github.bordertech.flux.wc.view.dumb.FormView;
import com.github.bordertech.flux.wc.view.dumb.SearchView;
import com.github.bordertech.flux.wc.view.event.base.FormBaseEventType;
import com.github.bordertech.flux.wc.view.event.base.FormBaseOutcomeEventType;
import com.github.bordertech.flux.wc.view.smart.CrudSearchTreeSmartView;
import com.github.bordertech.flux.wc.view.smart.tree.DefaultListOrTreeSmartView;
import com.github.bordertech.flux.wc.view.smart.tree.ListOrTreeSelectView;
import com.github.bordertech.wcomponents.WComponent;
import java.util.ArrayList;
import java.util.List;

/**
 * Default CRUD Tree view.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 */
public class DefaultCrudTreeSmartView<S, K, T> extends DefaultCrudSmartView<S, K, T> implements CrudSearchTreeSmartView<S, K, T> {

	public DefaultCrudTreeSmartView(final String viewId, final String title, final WComponent panel) {
		this(viewId, title, panel, new DefaultListOrTreeSmartView<K, T>("vw_list"));
	}

	public DefaultCrudTreeSmartView(final String viewId, final String title, final WComponent panel, final ListOrTreeSelectView<K, T> selectView2) {
		this(viewId, title, null, selectView2, null, panel);
	}

	public DefaultCrudTreeSmartView(final String viewId, final String title, final SearchView<S> criteriaView2, final ListOrTreeSelectView<K, T> selectView2, final FormView<T> formView2, final WComponent panel) {
		super(viewId, title, criteriaView2, selectView2, formView2, panel);
		selectView2.setDumbMode(true);
		selectView2.setPassAllEvents(true);
	}

	@Override
	public CrudTreeActionCreator<T> getEntityActionCreator() {
		return (CrudTreeActionCreator<T>) super.getEntityActionCreator();
	}

	@Override
	public CrudTreeStore<S, K, T> getEntityStore() {
		return (CrudTreeStore<S, K, T>) super.getEntityStore();
	}

	@Override
	public ListOrTreeSelectView<K, T> getSelectView() {
		return (ListOrTreeSelectView) super.getSelectView();
	}

	@Override
	protected void doDispatchSearchAction() {
		// FIXME
//		if (getCriteria() == null) {
//			// Use Root Items
//			StoreUtil.dispatchRetrieveAction(getEntityStoreKey(), RetrieveActionBaseType.ROOT, null, CallType.REFRESH_ASYNC);
//		} else {
//			super.doDispatchSearchAction();
//		}
	}

	@Override
	protected boolean isSearchActionDone() {
		return true;
// FIXME
//		if (getCriteria() == null) {
//			return getEntityStore().isRootItemsDone();
//		} else {
//			return super.isSearchActionDone();
//		}
	}

	@Override
	protected List<T> getSearchActionResult() {
		return null;
		// FIXME
//		if (getCriteria() == null) {
//			getSelectView().resetView();
//			getSelectView().setUseTree(true);
//			getSelectView().setEntityTreeStoreKey(getEntityStoreKey());
//			return getEntityStore().getRootItems();
//		} else {
//			return super.getSearchActionResult();
//		}
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
//					StoreUtil.dispatchRetrieveAction(getEntityStoreKey(), RetrieveActionBaseType.ROOT, null, CallType.REFRESH_SYNC);
//					getSelectView().resetView();
					List<T> items = new ArrayList<>();
					// FIXME
//					try {
//						items = getEntityStore().getRootItems();
//					} catch (RetrieveActionException e) {
//						dispatchMessageError("Error refreshing items. " + e.getMessage());
//						return;
//					}
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
