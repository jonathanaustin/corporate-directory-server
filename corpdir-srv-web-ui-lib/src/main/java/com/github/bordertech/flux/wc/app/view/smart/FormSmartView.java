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

	void setEntityCreatorKey(final String creatorKey);

	String getEntityCreatorKey();

	ModifyEntityCreator<T> getEntityCreator();

	void setEntityStoreKey(final String storeKey);

	String getEntityStoreKey();

	RetrieveEntityStore<T> getEntityStore();

	FormView<T> getFormView();

	FormToolbarView<T> getToolbarView();

	void resetFormViews();
}
