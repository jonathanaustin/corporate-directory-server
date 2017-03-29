package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.PositionType;
import com.github.bordertech.corpdir.jpa.common.AbstractKeyIdApiEntityMapper;
import com.github.bordertech.corpdir.jpa.v1.entity.PositionTypeEntity;
import javax.persistence.EntityManager;

/**
 * Map {@link PositionType} and {@link PositionTypeEntity}.
 *
 * @author jonathan
 */
public class PositionTypeMapper extends AbstractKeyIdApiEntityMapper<PositionType, PositionTypeEntity> {

	@Override
	protected void copyApiToEntityFields(final EntityManager em, final PositionType from, final PositionTypeEntity to) {
		to.setDescription(from.getDescription());
		to.setTypeLevel(from.getTypeLevel());
	}

	@Override
	protected void copyEntityToApiFields(final EntityManager em, final PositionTypeEntity from, final PositionType to) {
		to.setDescription(from.getDescription());
		to.setTypeLevel(from.getTypeLevel());
	}

	@Override
	protected PositionType createApiObject() {
		return new PositionType();
	}

	@Override
	protected PositionTypeEntity createEntityObject(final Long id, final String businessKey) {
		return new PositionTypeEntity(id, businessKey);
	}

}
