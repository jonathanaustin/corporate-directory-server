package com.github.bordertech.corpdir.jpa.common.svc;

import com.github.bordertech.corpdir.api.common.ApiTreeable;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.service.BasicTreeService;
import com.github.bordertech.corpdir.jpa.common.feature.PersistKeyIdTree;
import com.github.bordertech.corpdir.jpa.util.CriteriaUtil;
import java.util.List;
import java.util.Objects;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Tree Entity service.
 *
 * @param <A> the API object type
 * @param <P> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public abstract class JpaBasicTreeService<A extends ApiTreeable, P extends PersistKeyIdTree<P>> extends JpaBasicKeyIdService<A, P> implements BasicTreeService<A> {

	@Override
	protected void handleUpdateVerify(final EntityManager em, final A api, final P entity) {
		super.handleUpdateVerify(em, api, entity);
		if (Objects.equals(api.getId(), api.getParentId())) {
			throw new IllegalArgumentException("Cannot have itself as a parent.");
		}
		if (api.getSubIds().contains(api.getId())) {
			throw new IllegalArgumentException("Cannot have itself as a child.");
		}
		if (api.getParentId() != null && api.getSubIds().contains(api.getParentId())) {
			throw new IllegalArgumentException("A entity cannot be a child and parent of the same entity.");
		}
	}

	@Override
	public DataResponse<List<A>> getSubs(final String keyId) {
		EntityManager em = getEntityManager();
		try {
			P entity = getEntity(em, keyId);
			List<A> list = getMapper().convertEntitiesToApis(em, entity.getChildren());
			return new DataResponse<>(list);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<A> addSub(final String keyId, final String subKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the entity
			P entity = getEntity(em, keyId);
			// Get the sub entity
			P subEntity = getEntity(em, subKeyId);
			// Cant add an entity to itself
			if (Objects.equals(entity, subEntity)) {
				throw new IllegalArgumentException("Cannot add an Entity as a child to itself");
			}
			if (Objects.equals(entity.getParent(), subEntity)) {
				throw new IllegalArgumentException("A entity cannot be a child and parent of the same entity.");
			}
			// Remove Entity from its OLD parent (if it had one)
			P oldParent = subEntity.getParent();
			if (oldParent != null) {
				oldParent.removeChild(subEntity);
			}
			// Add to the new parent
			entity.addChild(subEntity);
			em.merge(entity);
			em.getTransaction().commit();
			return buildResponse(em, entity);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<A> removeSub(final String keyId, final String subKeyId) {
		EntityManager em = getEntityManager();
		try {
			if (Objects.equals(keyId, subKeyId)) {
				throw new IllegalArgumentException("Cannot remove an entity from itself");
			}
			em.getTransaction().begin();
			// Get the entity
			P entity = getEntity(em, keyId);
			// Get the sub entity
			P subOrgUnit = getEntity(em, subKeyId);
			// Remove the entity
			entity.removeChild(subOrgUnit);
			em.merge(entity);
			em.getTransaction().commit();
			return buildResponse(em, entity);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<List<A>> getRootItems() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery<P> qry = handleRootSearchCriteria(em);
			List<P> rows = em.createQuery(qry).getResultList();
			List<A> list = getMapper().convertEntitiesToApis(em, rows);
			return new DataResponse<>(list);
		} finally {
			em.close();
		}
	}

	protected CriteriaQuery<P> handleRootSearchCriteria(final EntityManager em) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<P> qry = cb.createQuery(getEntityClass());

		// Search (has null parent)
		Root<P> from = qry.from(getEntityClass());
		qry.select(from);
		qry.where(cb.isNull(from.<P>get("parent")));

		// Order by
		qry.orderBy(CriteriaUtil.getDefaultOrderBy(cb, from));
		return qry;
	}

}
