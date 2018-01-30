package com.github.bordertech.flux.wc.view.smart;

import com.github.bordertech.flux.crud.view.consumer.CrudActionCreatorConsumer;
import com.github.bordertech.flux.crud.view.consumer.CrudStoreConsumer;
import com.github.bordertech.flux.wc.view.FluxSmartView;
import com.github.bordertech.flux.wc.view.dumb.FormToolbarView;
import com.github.bordertech.flux.wc.view.dumb.FormView;

/**
 * CRUD Smart View.
 *
 * @param <S> the criteria type
 * @param <K> the form entity key type
 * @param <T> the form entity type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface CrudSmartView<S, K, T> extends FluxSmartView<T>, CrudActionCreatorConsumer<T>, CrudStoreConsumer<S, K, T> {

	FormView<T> getFormView();

	FormToolbarView<T> getFormToolbarView();

	void resetFormViews();
}
