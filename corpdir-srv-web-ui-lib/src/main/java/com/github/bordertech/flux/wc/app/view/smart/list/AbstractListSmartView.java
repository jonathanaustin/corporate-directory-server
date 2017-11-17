package com.github.bordertech.flux.wc.app.view.smart.list;

import com.github.bordertech.flux.wc.app.view.smart.polling.AbstractPollingRetrieveStoreSmartView;
import java.util.List;

/**
 * Abstract Polling Smart View that retrieves a list.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 */
public abstract class AbstractListSmartView<S, T> extends AbstractPollingRetrieveStoreSmartView<S, List<T>, List<T>> {

	public AbstractListSmartView(final String viewId, final String template) {
		super(viewId, template);
	}

}
