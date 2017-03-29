package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.jpa.common.AbstractKeyIdApiEntityMapper;
import com.github.bordertech.corpdir.jpa.common.MapperUtil;
import com.github.bordertech.corpdir.jpa.v1.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.v1.entity.UnitTypeEntity;
import javax.persistence.EntityManager;

/**
 * Map {@link OrgUnit} and {@link OrgUnitEntity}.
 *
 * @author jonathan
 */
public class OrgUnitMapper extends AbstractKeyIdApiEntityMapper<OrgUnit, OrgUnitEntity> {

	@Override
	protected void copyApiToEntityFields(final EntityManager em, final OrgUnit from, final OrgUnitEntity to) {
		to.setDescription(from.getDescription());
		UnitTypeEntity type = MapperUtil.getEntity(em, from.getTypeKey(), UnitTypeEntity.class);
		to.setType(type);
	}

	@Override
	protected void copyEntityToApiFields(final EntityManager em, final OrgUnitEntity from, final OrgUnit to) {
		to.setDescription(from.getDescription());
		to.setTypeKey(MapperUtil.getEntityBusinessKey(from.getType()));
	}

	@Override
	protected OrgUnit createApiObject() {
		return new OrgUnit();
	}

	@Override
	protected OrgUnitEntity createEntityObject(final Long id, final String businessKey) {
		return new OrgUnitEntity(id, businessKey);
	}

}
