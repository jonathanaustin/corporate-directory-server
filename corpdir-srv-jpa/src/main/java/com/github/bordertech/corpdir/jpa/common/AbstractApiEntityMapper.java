package com.github.bordertech.corpdir.jpa.common;

import com.github.bordertech.corpdir.api.common.ApiObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Map {@link ApiObject} and {@link PersistentObject}.
 *
 * @param <A> the API object
 * @param <P> the persistent object
 * @author jonathan
 */
public abstract class AbstractApiEntityMapper<A extends ApiObject, P extends PersistentObject> implements ApiEntityMapper<A, P> {

	@Override
	public P convertApiToEntity(final EntityManager em, final A from) {
		if (from == null) {
			return null;
		}
		P to = createEntityObject();
		copyApiToEntity(em, from, to);
		return to;
	}

	@Override
	public A convertEntityToApi(final EntityManager em, final P from) {
		if (from == null) {
			return null;
		}
		A to = createApiObject();
		copyEntityToApi(em, from, to);
		return to;
	}

	@Override
	public List<A> convertEntitiesToApis(final EntityManager em, final Collection<P> rows) {
		if (rows == null || rows.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<A> items = new ArrayList<>();
		for (P row : rows) {
			items.add(convertEntityToApi(em, row));
		}
		return items;
	}

	@Override
	public List<P> convertApisToEntities(final EntityManager em, final Collection<A> rows) {
		if (rows == null || rows.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<P> items = new ArrayList<>();
		for (A row : rows) {
			items.add(convertApiToEntity(em, row));
		}
		return items;
	}

	abstract protected A createApiObject();

	abstract protected P createEntityObject();

}
