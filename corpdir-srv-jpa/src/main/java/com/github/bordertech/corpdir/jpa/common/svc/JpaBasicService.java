package com.github.bordertech.corpdir.jpa.common.svc;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.service.BasicService;
import com.github.bordertech.corpdir.jpa.common.feature.PersistKeyIdObject;
import com.github.bordertech.corpdir.jpa.common.map.MapperApi;
import java.util.List;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Keyed Entity JPA service implementation.
 *
 * @param <A> the API object type
 * @param <P> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public abstract class JpaBasicService<A extends ApiKeyIdObject, P extends PersistKeyIdObject> extends JpaKeyIdService<A, P> implements BasicService<A> {

	@Override
	public DataResponse<List<A>> search(final String search) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery<P> qry = handleSearchCriteria(em, search);
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
			handleCreateVerify(em, apiObject);
			P entity = getMapper().convertApiToEntity(em, apiObject);
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
			P entity = getEntity(em, keyId);
			handleUpdateVerify(em, apiObject, entity);
			getMapper().copyApiToEntity(em, apiObject, entity);
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
			handleDeleteVerify(em, entity);
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

	protected abstract MapperApi<A, P> getMapper();

}
