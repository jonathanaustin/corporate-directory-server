package com.github.bordertech.flux.wc.view.smart;

import com.github.bordertech.flux.wc.view.DefaultSmartView;
import com.github.bordertech.flux.wc.view.dumb.MessageView;
import com.github.bordertech.flux.wc.view.dumb.SearchView;
import com.github.bordertech.flux.wc.view.dumb.SelectSingleView;
import com.github.bordertech.flux.wc.view.dumb.ToolbarView;
import com.github.bordertech.flux.wc.view.smart.consumer.SearchStoreConsumer;

/**
 * Crud SMART view with a Search Store.
 *
 * @param <S> the criteria type
 * @param <T> the form entity type
 * @author jonathan
 */
public interface CrudSmartView<S, T> extends FormSmartView<T>, SearchStoreConsumer<S, T> {

	boolean isAutoSearch();

	void setAutoSearch(final boolean autoSearch);

	S getCriteria();

	SearchView<S> getSearchView();

	SelectSingleView<T> getSelectView();

	ToolbarView getSearchToolbar();

	MessageView getSearchMessages();

	DefaultSmartView getFormHolder();

	MessageView getFormMessages();
}
