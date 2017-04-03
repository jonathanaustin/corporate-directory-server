package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.jpa.common.AbstractKeyIdApiEntityMapper;
import com.github.bordertech.corpdir.jpa.common.MapperUtil;
import com.github.bordertech.corpdir.jpa.v1.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.v1.entity.PositionEntity;
import com.github.bordertech.corpdir.jpa.v1.entity.UnitTypeEntity;
import java.util.List;
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
		to.setType(getUnitTypeEntity(em, from.getTypeKey()));
		to.setManagerPosition(getPositionEntity(em, from.getManagerPosKey()));
		to.setParentOrgUnit(getOrgUnitEntity(em, from.getParentKey()));

		// Positions
		List<String> prevIds = MapperUtil.convertEntitiesToApiKeys(to.getPositions());
		List<String> newIds = from.getPositionKeys();
		if (!MapperUtil.keysMatch(newIds, prevIds)) {
			// Removed positions
			for (String id : prevIds) {
				if (!newIds.contains(id)) {
					PositionEntity pos = getPositionEntity(em, id);
					to.removePosition(pos);
				}
			}
			// Added positions
			for (String id : newIds) {
				if (!prevIds.contains(id)) {
					PositionEntity pos = getPositionEntity(em, id);
					to.addPosition(pos);
				}
			}
		}

		// Sub Org Units
		prevIds = MapperUtil.convertEntitiesToApiKeys(to.getSubOrgUnits());
		newIds = from.getSubKeys();
		if (!MapperUtil.keysMatch(newIds, prevIds)) {
			// Removed Sub OU
			for (String id : prevIds) {
				if (!newIds.contains(id)) {
					OrgUnitEntity ou = getOrgUnitEntity(em, id);
					to.removeSubOrgUnit(ou);
				}
			}
			// Added Sub OU
			for (String id : newIds) {
				if (!prevIds.contains(id)) {
					OrgUnitEntity ou = getOrgUnitEntity(em, id);
					to.addSubOrgUnit(ou);
				}
			}
		}

	}

	@Override
	protected void copyEntityToApiFields(final EntityManager em, final OrgUnitEntity from, final OrgUnit to) {
		to.setDescription(from.getDescription());
		to.setTypeKey(MapperUtil.getEntityBusinessKey(from.getType()));
		to.setManagerPosKey(MapperUtil.getEntityBusinessKey(from.getManagerPosition()));
		to.setParentKey(MapperUtil.getEntityBusinessKey(from.getParentOrgUnit()));
		to.setPositionKeys(MapperUtil.convertEntitiesToApiKeys(from.getPositions()));
		to.setSubKeys(MapperUtil.convertEntitiesToApiKeys(from.getSubOrgUnits()));
	}

	@Override
	protected OrgUnit createApiObject() {
		return new OrgUnit();
	}

	@Override
	protected OrgUnitEntity createEntityObject(final Long id, final String businessKey) {
		return new OrgUnitEntity(id, businessKey);
	}

	protected UnitTypeEntity getUnitTypeEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntity(em, keyId, UnitTypeEntity.class);
	}

	protected PositionEntity getPositionEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntity(em, keyId, PositionEntity.class);
	}

	protected OrgUnitEntity getOrgUnitEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntity(em, keyId, OrgUnitEntity.class);
	}

}
