package com.github.bordertech.wcomponents.lib.app.model;

import com.github.bordertech.wcomponents.lib.mvc.Model;
import java.util.List;

/**
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the collection type
 */
public interface RetrieveCollection<S, T> extends Model {

	List<T> retrieveCollection(final S criteria);

}
