package com.github.bordertech.corpdir.jpa.common.map;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.corpdir.jpa.common.feature.PersistIdObject;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Map {@link ApiIdObject} and {@link PersistIdObject}.
 *
 * @param <A> the API keyed object
 * @param <P> the keyed persistent object
 * @author jonathan
 */
public abstract class AbstractMapperId<A extends ApiIdObject, P extends PersistIdObject> implements MapperApi<A, P> {

	@Override
	public P convertApiToEntity(final EntityManager em, final A from) {
		if (from == null) {
			return null;
		}
		Long id = MapperUtil.convertApiIdforEntity(from.getId());
		P to = createEntityObject(id);
		copyApiToEntity(em, from, to);
		return to;
	}

	@Override
	public void copyApiToEntity(final EntityManager em, final A from, final P to) {
		to.setDescription(from.getDescription());
		to.setTimestamp(from.getTimestamp());
	}

	@Override
	public A convertEntityToApi(final EntityManager em, final P from) {
		if (from == null) {
			return null;
		}
		String id = MapperUtil.convertEntityIdforApi(from.getId());
		A to = createApiObject(id);
		copyEntityToApi(em, from, to);
		return to;
	}

	@Override
	public void copyEntityToApi(final EntityManager em, final P from, final A to) {
		to.setDescription(from.getDescription());
		to.setTimestamp(from.getTimestamp());
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

	protected abstract A createApiObject(final String id);

	protected abstract P createEntityObject(final Long id);

}
