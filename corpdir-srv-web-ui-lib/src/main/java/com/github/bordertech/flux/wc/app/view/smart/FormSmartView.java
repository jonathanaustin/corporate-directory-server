package com.github.bordertech.flux.wc.app.view.smart;

import com.github.bordertech.flux.app.actioncreator.ModifyEntityCreator;
import com.github.bordertech.flux.app.store.retrieve.RetrieveEntityStore;
import com.github.bordertech.flux.wc.app.view.FormView;
import com.github.bordertech.flux.view.SmartView;

/**
 * Smart form view with an Entity Creator and Retrieve Store.
 *
 * @author jonathan
 */
public interface FormSmartView<T> extends SmartView<T>, FormView<T> {

	ModifyEntityCreator<T> getEntityCreator();

	RetrieveEntityStore<T> getEntityStore();
}
