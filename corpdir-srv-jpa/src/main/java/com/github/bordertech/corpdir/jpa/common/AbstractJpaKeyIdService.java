package com.github.bordertech.corpdir.jpa.common;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.corpdir.api.common.BasicService;
import com.github.bordertech.corpdir.api.exception.NotFoundException;
import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.jpa.util.CriteriaUtil;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import java.util.List;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Keyed Entity JPA service implementation.
 *
 * @param <A> the API object type
 * @param <P> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public abstract class AbstractJpaKeyIdService<A extends ApiKeyIdObject, P extends PersistentKeyIdObject> extends AbstractJpaService implements BasicService<A> {

	@Override
	public DataResponse<List<A>> search(final String search) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery<P> qry = handleSearch(em, search);
			List<P> rows = em.createQuery(qry).getResultList();
			List<A> list = getMapper().convertEntitiesToApis(em, rows);
			return new DataResponse<>(list);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<A> retrieve(final String keyId) {
		EntityManager em = getEntityManager();
		try {
			P entity = getEntity(em, keyId);
			return buildResponse(em, entity);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<A> create(final A apiObject) {
		EntityManager em = getEntityManager();
		try {
			P entity = handleCreate(em, apiObject);
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
			return buildResponse(em, entity);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<A> update(final String keyId, final A apiObject) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			P entity = handleUpdate(em, apiObject, keyId);
			em.getTransaction().commit();
			return buildResponse(em, entity);
		} finally {
			em.close();
		}
	}

	@Override
	public BasicResponse delete(final String keyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			P entity = getEntity(em, keyId);
			em.remove(entity);
			em.getTransaction().commit();
			return new BasicResponse();
		} finally {
			em.close();
		}
	}

	/**
	 *
	 * @param em the entity manager
	 * @param entity the entity
	 * @return the service response with API object
	 */
	protected DataResponse<A> buildResponse(final EntityManager em, final P entity) {
		A data = getMapper().convertEntityToApi(em, entity);
		return new DataResponse<>(data);
	}

	protected CriteriaQuery<P> handleSearch(final EntityManager em, final String search) {
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

	protected P handleCreate(final EntityManager em, final A apiObject) {
		MapperUtil.checkIdentifiersForCreate(em, apiObject, getEntityClass());
		P entity = getMapper().convertApiToEntity(em, apiObject);
		return entity;
	}

	protected P handleUpdate(final EntityManager em, final A apiObject, final String keyId) {
		P entity = getEntity(em, keyId);
		MapperUtil.checkIdentifiersForUpdate(em, apiObject, entity);
		getMapper().copyApiToEntity(em, apiObject, entity);
		return entity;
	}

	protected P getEntity(final EntityManager em, final String keyId) {
		P entity = MapperUtil.getEntity(em, keyId, getEntityClass());
		if (entity == null) {
			throw new NotFoundException("Entity [" + keyId + "] not found.");
		}
		return entity;
	}

	protected abstract Class<P> getEntityClass();

	protected abstract ApiEntityMapper<A, P> getMapper();

}
