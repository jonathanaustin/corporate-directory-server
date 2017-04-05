package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.jpa.common.AbstractKeyIdApiEntityMapper;
import com.github.bordertech.corpdir.jpa.entity.UnitTypeEntity;
import javax.persistence.EntityManager;

/**
 * Map {@link UnitType} and {@link UnitTypeEntity}.
 *
 * @author jonathan
 */
public class UnitTypeMapper extends AbstractKeyIdApiEntityMapper<UnitType, UnitTypeEntity> {

	@Override
	protected void copyApiToEntityFields(final EntityManager em, final UnitType from, final UnitTypeEntity to) {
	}

	@Override
	protected void copyEntityToApiFields(final EntityManager em, final UnitTypeEntity from, final UnitType to) {
	}

	@Override
	protected UnitType createApiObject() {
		return new UnitType();
	}

	@Override
	protected UnitTypeEntity createEntityObject(final Long id) {
		return new UnitTypeEntity(id);
	}

}
