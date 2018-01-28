package com.github.bordertech.corpdir.jpa.common.svc;

import com.github.bordertech.corpdir.api.common.ApiVersionable;
import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.service.BasicVersionKeyIdService;
import com.github.bordertech.corpdir.jpa.common.feature.PersistVersionableKeyId;
import com.github.bordertech.corpdir.jpa.common.map.MapperApiVersion;
import com.github.bordertech.corpdir.jpa.common.version.ItemVersion;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import java.util.List;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Keyed Entity JPA service implementation.
 *
 * @param <A> the API object type
 * @param <U> the versionable data
 * @param <P> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public abstract class JpaBasicVersionKeyIdService<A extends ApiVersionable, U extends ItemVersion<P>, P extends PersistVersionableKeyId<P, U>> extends AbstractJpaKeyIdService<A, P> implements BasicVersionKeyIdService<A> {

	@Override
	public DataResponse<List<A>> search(final String search) {
		return search(getCurrentVersionId(), search);
	}

	@Override
	public DataResponse<A> retrieve(final String keyId) {
		return retrieve(getCurrentVersionId(), keyId);
	}

	@Override
	public DataResponse<List<A>> search(final Long versionId, final String search) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery<P> qry = handleSearchCriteria(em, search);
			List<P> rows = em.createQuery(qry).getResultList();
			List<A> list = getMapper().convertEntitiesToApis(em, rows, versionId);
			return new DataResponse<>(list);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<A> retrieve(final Long versionId, final String keyId) {
		EntityManager em = getEntityManager();
		try {
			P entity = getEntity(em, keyId);
			return buildResponse(em, entity, versionId);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<A> create(final A apiObject) {
		EntityManager em = getEntityManager();
		try {
			Long versionId = apiObject.getVersionId();
			// Add the current version id (if not set)
			if (versionId == null) {
				versionId = getCurrentVersionId();
				apiObject.setVersionId(versionId);
			}
			handleCreateVerify(em, apiObject);
			P entity = getMapper().convertApiToEntity(em, apiObject, versionId);
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
			return buildResponse(em, entity, versionId);
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
			Long versionId = apiObject.getVersionId();
			getMapper().copyApiToEntity(em, apiObject, entity, versionId);
			em.getTransaction().commit();
			em.merge(entity);
			return buildResponse(em, entity, versionId);
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

	@Override
	protected void handleCreateVerify(final EntityManager em, final A api) {
		super.handleCreateVerify(em, api);
		if (api.getVersionId() == null) {
			throw new IllegalStateException("No version provided.");
		}
	}

	@Override
	protected void handleUpdateVerify(final EntityManager em, final A api, final P entity) {
		super.handleUpdateVerify(em, api, entity);
		if (api.getVersionId() == null) {
			throw new IllegalStateException("No version provided.");
		}
	}

	/**
	 *
	 * @param em the entity manager
	 * @param entity the entity
	 * @param versionId the version id
	 * @return the service response with API object
	 */
	protected DataResponse<A> buildResponse(final EntityManager em, final P entity, final Long versionId) {
		A data = getMapper().convertEntityToApi(em, entity, versionId);
		return new DataResponse<>(data);
	}

	protected VersionCtrlEntity getVersionCtrl(final EntityManager em, final Long versionId) {
		VersionCtrlEntity ctrl = em.find(VersionCtrlEntity.class, versionId);
		if (ctrl == null) {
			throw new IllegalStateException("No System Control Record Available.");
		}
		return ctrl;
	}

	protected abstract MapperApiVersion<A, U, P> getMapper();

}
