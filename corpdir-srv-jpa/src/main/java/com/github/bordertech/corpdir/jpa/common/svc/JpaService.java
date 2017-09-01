package com.github.bordertech.corpdir.jpa.common.svc;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.corpdir.api.exception.NotFoundException;
import com.github.bordertech.corpdir.jpa.common.feature.PersistKeyIdObject;
import com.github.bordertech.corpdir.jpa.util.CriteriaUtil;
import com.github.bordertech.corpdir.jpa.util.EmfUtil;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import java.io.Serializable;
import java.util.Objects;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Abstract class that provides the entity manager.
 *
 * @param <A> the API object type
 * @param <P> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public abstract class JpaService<A extends ApiKeyIdObject, P extends PersistKeyIdObject> implements Serializable {

	/**
	 * @return the entity manager
	 */
	protected EntityManager getEntityManager() {
		return EmfUtil.getEMF().createEntityManager();
	}

	protected Integer getCurrentVersionId() {
		return 1;
	}

	protected CriteriaQuery<P> handleSearchCriteria(final EntityManager em, final String search) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<P> qry = cb.createQuery(getEntityClass());

		Root<P> from = qry.from(getEntityClass());
		qry.select(from);

		// Search
		if (search != null && !search.isEmpty()) {
			qry.where(CriteriaUtil.createSearchTextCriteria(cb, from, search));
		}

		// Order by
		qry.orderBy(CriteriaUtil.getDefaultOrderBy(cb, from));
		return qry;
	}

	protected void handleCreateVerify(final EntityManager em, final A api) {
		MapperUtil.checkBusinessKey(em, api.getBusinessKey(), getEntityClass());
		// Ignore ID and version
		api.setId(null);
		api.setTimestamp(null);
	}

	protected void handleUpdateVerify(final EntityManager em, final A api, final P entity) {
		// Check API/Entity ids match
		Long apiId = MapperUtil.convertApiIdforEntity(api.getId());
		if (!Objects.equals(apiId, entity.getId())) {
			throw new IllegalStateException("IDs do not match [" + apiId + "] and [" + entity.getId() + "].");
		}
		// Check if business key changed
		if (!Objects.equals(api.getBusinessKey(), entity.getBusinessKey())) {
			MapperUtil.checkBusinessKey(em, api.getBusinessKey(), entity.getClass());
		}
	}

	protected void handleDeleteVerify(final EntityManager em, final P entity) {
		// Do nothing
	}

	protected P getEntity(final EntityManager em, final String keyId) {
		P entity = MapperUtil.getEntity(em, keyId, getEntityClass());
		if (entity == null) {
			throw new NotFoundException("Entity [" + keyId + "] not found.");
		}
		return entity;
	}

	protected abstract Class<P> getEntityClass();

}
