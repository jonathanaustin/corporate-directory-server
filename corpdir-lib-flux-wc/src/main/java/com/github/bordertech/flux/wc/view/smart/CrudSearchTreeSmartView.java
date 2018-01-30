package com.github.bordertech.flux.wc.view.smart;

import com.github.bordertech.flux.crud.actioncreator.CrudTreeActionCreator;
import com.github.bordertech.flux.crud.store.CrudTreeStore;

/**
 * Crud SMART view with a TREE Entity.
 *
 * @param <S> the criteria type
 * @param <K> the form entity key type
 * @param <T> the form entity type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface CrudSearchTreeSmartView<S, K, T> extends CrudSearchSmartView<S, K, T> {

	@Override
	public CrudTreeActionCreator<T> getActionCreatorByKey();

	@Override
	public CrudTreeStore<S, K, T> getStoreByKey();

}
