package com.github.bordertech.wcomponents.lib.app.model;

import com.github.bordertech.flux.wc.DataApi;
import com.github.bordertech.flux.wc.DataApiProviderFactory;
import com.github.bordertech.wcomponents.lib.app.model.keys.ActionModelKey;
import com.github.bordertech.wcomponents.lib.app.model.keys.RetrieveListModelKey;
import com.github.bordertech.wcomponents.lib.app.model.keys.RetrieveModelKey;
import com.github.bordertech.wcomponents.lib.app.model.keys.SearchModelKey;
import com.github.bordertech.wcomponents.lib.app.model.keys.TreeModelKey;

/**
 *
 * @author jonathan
 */
public class ModelUtil {

	private ModelUtil() {
	}

	public static <T> ActionModel<T> getActionModelImpl(final ActionModelKey key) {
		return (ActionModel<T>) getDataApi(key.getActionModelKey());
	}

	public static <S, T> RetrieveListModel<S, T> getRetrieveListImpl(final RetrieveListModelKey key) {
		return (RetrieveListModel<S, T>) getDataApi(key.getRetrieveListModelKey());
	}

	public static <S, T> RetrieveModel<S, T> getRetrieveModelImpl(final RetrieveModelKey key) {
		return (RetrieveModel<S, T>) getDataApi(key.getRetrieveModelKey());
	}

	public static <S, T> SearchModel<S, T> getSearchModelImpl(final SearchModelKey key) {
		return (SearchModel<S, T>) getDataApi(key.getSearchModelKey());
	}

	public static <S, T> TreeModel<S, T> getTreeModelImpl(final TreeModelKey key) {
		return (TreeModel<S, T>) getDataApi(key.getTreeModelKey());
	}

	/**
	 * Helper method to load a data API.
	 *
	 * @param key
	 * @return
	 */
	public static DataApi getDataApi(final String key) {
		DataApi model = DataApiProviderFactory.getInstance().getDataApi(key);
		return model;
	}

}
