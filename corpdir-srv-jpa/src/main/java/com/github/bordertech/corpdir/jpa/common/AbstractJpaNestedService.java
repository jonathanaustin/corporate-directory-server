package com.github.bordertech.corpdir.jpa.common;

import com.github.bordertech.corpdir.api.common.ApiNestedObject;
import com.github.bordertech.corpdir.api.common.BasicNestedService;
import com.github.bordertech.corpdir.api.response.DataResponse;
import java.util.List;
import java.util.Objects;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

/**
 * Nested Entity service.
 *
 * @param <A> the API object type
 * @param <P> the entity type
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public abstract class AbstractJpaNestedService<A extends ApiNestedObject, P extends PersistentNestedObject<P>> extends AbstractJpaKeyIdService<A, P> implements BasicNestedService<A> {

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
			// Cant add an OU to itself
			if (Objects.equals(entity, subEntity)) {
				throw new IllegalArgumentException("Cannot add an Entity as a child to itself");
			}
			// Remove Entity from its OLD parent (if it had one)
			P oldParent = subEntity.getParent();
			if (oldParent != null) {
				oldParent.removeChild(subEntity);
			}
			// Add to the new parent
			entity.addChild(subEntity);
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
			em.getTransaction().commit();
			return buildResponse(em, entity);
		} finally {
			em.close();
		}
	}

}
