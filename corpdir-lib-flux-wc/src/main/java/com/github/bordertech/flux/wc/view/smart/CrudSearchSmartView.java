package com.github.bordertech.flux.wc.view.smart;

import com.github.bordertech.flux.wc.view.DefaultSmartView;
import com.github.bordertech.flux.wc.view.dumb.MessageView;
import com.github.bordertech.flux.wc.view.dumb.SearchView;
import com.github.bordertech.flux.wc.view.dumb.SelectSingleView;
import com.github.bordertech.flux.wc.view.dumb.ToolbarView;

/**
 * CRUD Smart View with a Search View.
 *
 * @param <S> the criteria type
 * @param <K> the form entity key type
 * @param <T> the form entity type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface CrudSearchSmartView<S, K, T> extends CrudSmartView<S, K, T> {

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
