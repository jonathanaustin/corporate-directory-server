package com.github.bordertech.flux.store.retrieve;

import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.dataapi.DataApiType;
import com.github.bordertech.flux.dataapi.retrieve.RetrieveListApi;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class DefaultRetrieveListStore<S, T, R extends RetrieveListApi<S, T>> extends DefaultRetrieveStore<S, List<T>, R> implements RetrieveListStore<S, T> {

	public DefaultRetrieveListStore(final DataApiType apiType, final StoreType storeType) {
		super(apiType, storeType);
	}

	public DefaultRetrieveListStore(final DataApiType apiType, final StoreType storeType, final String qualifier) {
		super(apiType, storeType, qualifier);
	}

}
