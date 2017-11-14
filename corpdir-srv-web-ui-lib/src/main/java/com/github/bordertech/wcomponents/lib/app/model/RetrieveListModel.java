package com.github.bordertech.wcomponents.lib.app.model;

import com.github.bordertech.flux.wc.dataapi.DataApi;
import java.util.List;

/**
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 */
public interface RetrieveListModel<S, T> extends DataApi {

	List<T> retrieveCollection(final S criteria);

}
