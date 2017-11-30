package com.github.bordertech.flux.wc.app.view.smart;

import com.github.bordertech.flux.crud.actioncreator.EntityTreeActionCreator;
import com.github.bordertech.flux.crud.store.EntityTreeStore;
import com.github.bordertech.flux.wc.app.view.smart.consumer.SearchStoreConsumer;

/**
 * Crud SMART view with a TREE Entity.
 *
 * @param <T> the form entity type
 * @author jonathan
 */
public interface CrudTreeSmartView<S, T> extends FormSmartView<T>, SearchStoreConsumer<S, T> {

	@Override
	public EntityTreeActionCreator<T> getEntityActionCreator();

	@Override
	public EntityTreeStore<T> getEntityStore();

}
