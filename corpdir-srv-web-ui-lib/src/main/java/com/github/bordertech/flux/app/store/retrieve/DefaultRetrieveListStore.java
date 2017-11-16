package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.app.dataapi.retrieve.RetrieveItemApi;
import com.github.bordertech.flux.dataapi.DataApiType;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class DefaultRetrieveListStore<K, T, R extends RetrieveItemApi<K, List<T>>> extends DefaultRetrieveItemStore<K, List<T>, R> implements RetrieveListStore<K, T> {

	public DefaultRetrieveListStore(final DataApiType apiType, final StoreType storeType) {
		super(apiType, storeType);
	}

	public DefaultRetrieveListStore(final DataApiType apiType, final StoreType storeType, final String qualifier) {
		super(apiType, storeType, qualifier);
	}

}
