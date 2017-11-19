package com.github.bordertech.flux.wc.app.view.smart.crud;

import com.github.bordertech.flux.crud.actioncreator.EntityTreeActionCreator;
import com.github.bordertech.flux.crud.store.retrieve.EntityTreeStore;
import com.github.bordertech.flux.wc.app.view.FormView;
import com.github.bordertech.flux.wc.app.view.SearchView;
import com.github.bordertech.flux.wc.app.view.SelectSingleView;
import com.github.bordertech.flux.wc.app.view.smart.CrudTreeSmartView;
import com.github.bordertech.wcomponents.WComponent;

/**
 * Default CRUD Tree view.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 */
public class DefaultCrudTreeSmartView<S, T> extends DefaultCrudSmartView<S, T> implements CrudTreeSmartView<S, T> {

	public DefaultCrudTreeSmartView(final String viewId, final String title, final WComponent panel) {
		this(viewId, title, null, null, null, panel);
	}

	public DefaultCrudTreeSmartView(final String viewId, final String title, final WComponent panel, final SelectSingleView<T> selectView2) {
		this(viewId, title, null, selectView2, null, panel);
	}

	public DefaultCrudTreeSmartView(final String viewId, final String title, final SearchView<S> criteriaView2, final SelectSingleView<T> selectView2, final FormView<T> formView2, final WComponent panel) {
		super(viewId, title, criteriaView2, selectView2, formView2, panel);
	}

	@Override
	public EntityTreeActionCreator<T> getEntityActionCreator() {
		return (EntityTreeActionCreator<T>) super.getEntityActionCreator();
	}

	@Override
	public EntityTreeStore<T> getEntityStore() {
		return (EntityTreeStore<T>) super.getEntityStore();
	}

}
