package com.github.bordertech.corpdir.jpa.common.map;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.corpdir.jpa.common.feature.PersistKeyIdObject;
import javax.persistence.EntityManager;
import com.github.bordertech.corpdir.api.common.ApiActivable;
import com.github.bordertech.corpdir.api.common.ApiCustomable;
import com.github.bordertech.corpdir.jpa.common.feature.PersistActivable;
import com.github.bordertech.corpdir.jpa.common.feature.PersistCustomable;

/**
 * Map {@link ApiKeyIdObject} and {@link PersistKeyIdObject}.
 *
 * @param <A> the API keyed object
 * @param <P> the keyed persistent object
 * @author jonathan
 */
public abstract class AbstractMapperIdCustom<A extends ApiActivable & ApiCustomable, P extends PersistActivable & PersistCustomable> extends AbstractMapperId<A, P> {

	@Override
	public void copyApiToEntity(final EntityManager em, final A from, final P to) {
		super.copyApiToEntity(em, from, to);
		to.setCustom(from.isCustom());
		to.setActive(from.isActive());
	}

	@Override
	public void copyEntityToApi(final EntityManager em, final P from, final A to) {
		super.copyEntityToApi(em, from, to);
		to.setCustom(from.isCustom());
		to.setActive(from.isActive());
	}

}
