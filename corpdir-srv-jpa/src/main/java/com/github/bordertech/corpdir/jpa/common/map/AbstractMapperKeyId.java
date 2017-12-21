package com.github.bordertech.corpdir.jpa.common.map;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.corpdir.jpa.common.feature.PersistKeyIdObject;
import javax.persistence.EntityManager;

/**
 * Map {@link ApiKeyIdObject} and {@link PersistKeyIdObject}.
 *
 * @param <A> the API keyed object
 * @param <P> the keyed persistent object
 * @author jonathan
 */
public abstract class AbstractMapperKeyId<A extends ApiKeyIdObject, P extends PersistKeyIdObject> extends AbstractMapperIdCustom<A, P> {

	@Override
	public void copyApiToEntity(final EntityManager em, final A from, final P to) {
		super.copyApiToEntity(em, from, to);
		to.setBusinessKey(from.getBusinessKey());
	}

	@Override
	public void copyEntityToApi(final EntityManager em, final P from, final A to) {
		super.copyEntityToApi(em, from, to);
		to.setBusinessKey(from.getBusinessKey());
	}

}
