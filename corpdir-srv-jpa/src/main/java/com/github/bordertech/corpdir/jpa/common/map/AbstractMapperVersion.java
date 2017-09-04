package com.github.bordertech.corpdir.jpa.common.map;

import com.github.bordertech.corpdir.api.common.ApiVersionable;
import com.github.bordertech.corpdir.jpa.common.feature.PersistVersionData;
import com.github.bordertech.corpdir.jpa.common.feature.PersistVersionable;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Map {@link ApiVersionable} and {@link PersistVersionData}.
 *
 * @param <A> the API object
 * @param <U> the data version type
 * @param <P> the persistent object
 * @author jonathan
 */
public abstract class AbstractMapperVersion<A extends ApiVersionable, U extends PersistVersionable<U, P>, P extends PersistVersionData<U>> implements MapperApiVersion<A, U, P> {

	@Override
	public P convertApiToEntity(final EntityManager em, final A from, final Long versionId) {
		if (from == null) {
			return null;
		}
		Long id = MapperUtil.convertApiIdforEntity(from.getId());
		P to = null;
		// As this Object versioned data, important to load the object to keep all the version details (if exists)
		if (id != null) {
			to = MapperUtil.getEntityById(em, id, getEntityClass());
		}
		if (to == null) {
			to = createEntityObject(id);
		}
		copyApiToEntity(em, from, to, versionId);
		return to;
	}

	@Override
	public void copyApiToEntity(final EntityManager em, final A from, final P to, final Long versionId) {
		to.setBusinessKey(from.getBusinessKey());
		to.setDescription(from.getDescription());
		to.setCustom(from.isCustom());
		to.setActive(from.isActive());
		to.setTimestamp(from.getTimestamp());
		handleVersionDataApiToEntity(em, from, to, versionId);
	}

	@Override
	public A convertEntityToApi(final EntityManager em, final P from, final Long versionId) {
		if (from == null) {
			return null;
		}
		String id = MapperUtil.convertEntityIdforApi(from.getId());
		A to = createApiObject(id);
		copyEntityToApi(em, from, to, versionId);
		return to;
	}

	@Override
	public void copyEntityToApi(final EntityManager em, final P from, final A to, final Long versionId) {
		to.setBusinessKey(from.getBusinessKey());
		to.setDescription(from.getDescription());
		to.setCustom(from.isCustom());
		to.setActive(from.isActive());
		to.setTimestamp(from.getTimestamp());
		to.setVersionId(versionId);
		handleVersionDataEntityToApi(em, from, to, versionId);
	}

	@Override
	public List<A> convertEntitiesToApis(final EntityManager em, final Collection<P> rows, final Long versionId) {
		if (rows == null || rows.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<A> items = new ArrayList<>();
		for (P row : rows) {
			items.add(convertEntityToApi(em, row, versionId));
		}
		return items;
	}

	@Override
	public List<P> convertApisToEntities(final EntityManager em, final Collection<A> rows, final Long versionId) {
		if (rows == null || rows.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<P> items = new ArrayList<>();
		for (A row : rows) {
			items.add(convertApiToEntity(em, row, versionId));
		}
		return items;
	}

	protected P getEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntity(em, keyId, getEntityClass());
	}

	protected abstract A createApiObject(final String id);

	protected abstract P createEntityObject(final Long id);

	protected abstract Class<P> getEntityClass();

	protected abstract void handleVersionDataApiToEntity(final EntityManager em, final A from, final P to, final Long versionId);

	protected abstract void handleVersionDataEntityToApi(final EntityManager em, final P from, final A to, final Long versionId);

}
