package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.PositionType;
import com.github.bordertech.corpdir.jpa.common.map.AbstractMapperKeyId;
import com.github.bordertech.corpdir.jpa.entity.PositionTypeEntity;
import javax.persistence.EntityManager;

/**
 * Map {@link PositionType} and {@link PositionTypeEntity}.
 *
 * @author jonathan
 */
public class PositionTypeMapper extends AbstractMapperKeyId<PositionType, PositionTypeEntity> {

	@Override
	public void copyApiToEntity(final EntityManager em, final PositionType from, final PositionTypeEntity to) {
		super.copyApiToEntity(em, from, to);
		to.setTypeLevel(from.getLevel());
	}

	@Override
	public void copyEntityToApi(final EntityManager em, final PositionTypeEntity from, final PositionType to) {
		super.copyEntityToApi(em, from, to);
		to.setLevel(from.getTypeLevel());
	}

	@Override
	protected PositionType createApiObject() {
		return new PositionType();
	}

	@Override
	protected PositionTypeEntity createEntityObject(final Long id) {
		return new PositionTypeEntity(id);
	}

}
