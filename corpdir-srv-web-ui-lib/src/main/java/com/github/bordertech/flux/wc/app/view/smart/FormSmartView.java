package com.github.bordertech.flux.wc.app.view.smart;

import com.github.bordertech.flux.wc.app.view.FormToolbarView;
import com.github.bordertech.flux.wc.app.view.FormView;
import com.github.bordertech.flux.wc.app.view.smart.consumer.EntityActionCreatorConsumer;
import com.github.bordertech.flux.wc.app.view.smart.consumer.EntityStoreConsumer;
import com.github.bordertech.flux.wc.view.FluxSmartView;

/**
 * Smart form view with an Entity Creator and Retrieve Store.
 *
 * @param <T> the form entity type
 * @author jonathan
 */
public interface FormSmartView<T> extends FluxSmartView<T>, EntityActionCreatorConsumer<T>, EntityStoreConsumer<T> {

	FormView<T> getFormView();

	FormToolbarView<T> getFormToolbarView();

	void resetFormViews();
}
