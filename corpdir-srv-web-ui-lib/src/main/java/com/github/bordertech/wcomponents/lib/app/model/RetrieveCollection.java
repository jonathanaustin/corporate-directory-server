package com.github.bordertech.wcomponents.lib.app.model;

import com.github.bordertech.wcomponents.lib.mvc.Model;
import java.util.Collection;

/**
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the item type
 * @param <C> the collection type
 */
public interface RetrieveCollection<S, T, C extends Collection<T>> extends Model {

	C retrieveCollection(final S criteria);

}
