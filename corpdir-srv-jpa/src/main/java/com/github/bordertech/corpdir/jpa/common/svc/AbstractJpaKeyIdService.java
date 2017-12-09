package com.github.bordertech.corpdir.jpa.common.svc;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.corpdir.api.exception.NotFoundException;
import com.github.bordertech.corpdir.jpa.common.feature.PersistKeyIdObject;
import com.github.bordertech.corpdir.jpa.util.CriteriaUtil;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import java.util.Objects;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
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
public abstract class AbstractJpaKeyIdService<A extends ApiKeyIdObject, P extends PersistKeyIdObject> extends AbstractJpaIdService<A, P> {

	@Override
	protected Predicate buildTextSearchPredicate(final CriteriaBuilder cb, final Root<P> from, final String search) {
		// Include business key
		return CriteriaUtil.createSearchTextKeyIdCriteria(cb, from, search);
	}

	@Override
	protected void handleCreateVerify(final EntityManager em, final A api) {
		super.handleCreateVerify(em, api);
		// Check business key
		MapperUtil.checkBusinessKey(em, api.getBusinessKey(), getEntityClass());
	}

	@Override
	protected void handleUpdateVerify(final EntityManager em, final A api, final P entity) {
		super.handleUpdateVerify(em, api, entity);
		// Check if business key changed
		if (!Objects.equals(api.getBusinessKey(), entity.getBusinessKey())) {
			MapperUtil.checkBusinessKey(em, api.getBusinessKey(), entity.getClass());
		}
	}

	@Override
	protected P getEntity(final EntityManager em, final String keyId) {
		// Get by ID or business key
		P entity = MapperUtil.getEntityByKeyId(em, keyId, getEntityClass());
		if (entity == null) {
			throw new NotFoundException("Entity [" + keyId + "] not found.");
		}
		return entity;
	}

}
