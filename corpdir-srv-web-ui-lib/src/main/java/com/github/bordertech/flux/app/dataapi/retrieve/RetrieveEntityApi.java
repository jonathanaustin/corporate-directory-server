package com.github.bordertech.flux.app.dataapi.retrieve;

import com.github.bordertech.taskmanager.service.ServiceException;
import java.util.List;

/**
 *
 * @author jonathan
 * @param <K> the entity key type
 * @param <T> the entity type
 * @param <S> the search criteria type
 */
public interface RetrieveEntityApi<K, T, S> extends RetrieveItemApi<K, T> {

	List<T> search(final S criteria) throws ServiceException;

}
