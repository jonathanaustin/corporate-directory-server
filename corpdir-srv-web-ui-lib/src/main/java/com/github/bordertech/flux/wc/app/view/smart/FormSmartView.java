package com.github.bordertech.flux.wc.app.view.smart;

import com.github.bordertech.flux.app.actioncreator.ModifyEntityCreator;
import com.github.bordertech.flux.app.store.retrieve.RetrieveEntityStore;
import com.github.bordertech.flux.view.SmartView;
import com.github.bordertech.flux.wc.app.view.FormToolbarView;
import com.github.bordertech.flux.wc.app.view.FormView;

/**
 * Smart form view with an Entity Creator and Retrieve Store.
 *
 * @author jonathan
 */
public interface FormSmartView<T> extends SmartView<T> {

	void setEntityActionCreatorKey(final String creatorKey);

	String getEntityActionCreatorKey();

	ModifyEntityCreator<T> getEntityActionCreator();

	void setRetrieveEntityStoreKey(final String storeKey);

	String getRetrieveEntityStoreKey();

	RetrieveEntityStore<T> getRetrieveEntityStore();

	FormView<T> getFormView();

	FormToolbarView<T> getFormToolbarView();

	void resetFormViews();
}
