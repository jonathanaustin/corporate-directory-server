package com.github.bordertech.corpdir.jpa.common.svc;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import com.github.bordertech.corpdir.api.exception.NotFoundException;
import com.github.bordertech.corpdir.jpa.common.feature.PersistIdObject;
import com.github.bordertech.corpdir.jpa.util.CriteriaUtil;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import java.util.Objects;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Abstract class that provides entity helper methods.
 *
 * @param <A> the API object type
 * @param <P> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public abstract class AbstractJpaIdService<A extends ApiIdObject, P extends PersistIdObject> extends AbstractJpaBaseService<A, P> {

	protected CriteriaQuery<P> handleSearchCriteria(final EntityManager em, final String search) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<P> qry = cb.createQuery(getEntityClass());

		Root<P> from = qry.from(getEntityClass());
		qry.select(from);

		// Query condition
		if (search != null && !search.isEmpty()) {
			if (MapperUtil.isEntityId(search)) {
				// ID Search
				Long id = MapperUtil.convertApiIdforEntity(search);
				qry.where(CriteriaUtil.createSearchIDCriteria(cb, from, id));
			} else {
				// Text Search
				qry.where(buildTextSearchPredicate(cb, from, search));
			}
		}

		// Order by
		qry.orderBy(CriteriaUtil.getDefaultOrderBy(cb, from));
		return qry;
	}

	protected Predicate buildTextSearchPredicate(final CriteriaBuilder cb, final Root<P> from, final String search) {
		return CriteriaUtil.createSearchTextIdCriteria(cb, from, search);
	}

	protected void handleCreateVerify(final EntityManager em, final A api) {
		api.setTimestamp(null);
	}

	protected void handleUpdateVerify(final EntityManager em, final A api, final P entity) {
		// Check API/Entity ids match
		Long apiId = MapperUtil.convertApiIdforEntity(api.getId());
		if (!Objects.equals(apiId, entity.getId())) {
			throw new IllegalStateException("IDs do not match [" + apiId + "] and [" + entity.getId() + "].");
		}
	}

	protected void handleDeleteVerify(final EntityManager em, final P entity) {
		// Do nothing
	}

	protected P getEntity(final EntityManager em, final String id) {
		P entity = MapperUtil.getEntityByApiId(em, id, getEntityClass());
		if (entity == null) {
			throw new NotFoundException("Entity [" + id + "] not found.");
		}
		return entity;
	}

	protected abstract Class<P> getEntityClass();

}
