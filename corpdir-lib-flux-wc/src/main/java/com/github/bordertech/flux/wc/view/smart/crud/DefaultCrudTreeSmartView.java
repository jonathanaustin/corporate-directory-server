package com.github.bordertech.flux.wc.view.smart.crud;

import com.github.bordertech.flux.crud.action.base.RetrieveActionBaseType;
import com.github.bordertech.flux.crud.action.retrieve.CallType;
import com.github.bordertech.flux.crud.actioncreator.EntityTreeActionCreator;
import com.github.bordertech.flux.crud.store.EntityTreeStore;
import com.github.bordertech.flux.crud.store.RetrieveActionException;
import com.github.bordertech.flux.store.StoreUtil;
import com.github.bordertech.flux.wc.view.dumb.FormView;
import com.github.bordertech.flux.wc.view.dumb.SearchView;
import com.github.bordertech.flux.wc.view.event.base.FormBaseEventType;
import com.github.bordertech.flux.wc.view.event.base.FormBaseOutcomeEventType;
import com.github.bordertech.flux.wc.view.smart.CrudTreeSmartView;
import com.github.bordertech.flux.wc.view.smart.tree.DefaultListOrTreeSmartView;
import com.github.bordertech.flux.wc.view.smart.tree.ListOrTreeSelectView;
import com.github.bordertech.taskmanager.service.AsyncException;
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

	public DefaultCrudTreeSmartView(final String viewId, final String title, final WComponent panel, final ListOrTreeSelectView<T> selectView2) {
		this(viewId, title, null, selectView2, null, panel);
	}

	public DefaultCrudTreeSmartView(final String viewId, final String title, final SearchView<S> criteriaView2, final ListOrTreeSelectView<T> selectView2, final FormView<T> formView2, final WComponent panel) {
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
	public ListOrTreeSelectView<T> getSelectView() {
		return (ListOrTreeSelectView) super.getSelectView();
	}

	@Override
	protected void doDispatchSearchAction() {
		if (getCriteria() == null) {
			// Use Root Items
			StoreUtil.dispatchRetrieveAction(getEntityStoreKey(), RetrieveActionBaseType.ROOT, null, CallType.REFRESH_ASYNC);
		} else {
			super.doDispatchSearchAction();
		}
	}

	@Override
	protected boolean isSearchActionDone() throws AsyncException {
		if (getCriteria() == null) {
			return getEntityStore().isRootItemsDone();
		} else {
			return super.isSearchActionDone();
		}
	}

	@Override
	protected List<T> getSearchActionResult() throws RetrieveActionException {
		if (getCriteria() == null) {
			getSelectView().resetView();
			getSelectView().setUseTree(true);
			getSelectView().setEntityTreeStoreKey(getEntityStoreKey());
			return getEntityStore().getRootItems();
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
					StoreUtil.dispatchRetrieveAction(getEntityStoreKey(), RetrieveActionBaseType.ROOT, null, CallType.REFRESH_SYNC);
					getSelectView().resetView();
					List<T> items;
					try {
						items = getEntityStore().getRootItems();
					} catch (RetrieveActionException e) {
						dispatchMessageError("Error refreshing items. " + e.getMessage());
						return;
					}
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
