package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.jpa.common.AbstractKeyIdApiEntityMapper;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import com.github.bordertech.corpdir.jpa.entity.UnitTypeEntity;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
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

		// Type
		String origId = MapperUtil.getEntityBusinessKey(to.getType());
		String newId = from.getTypeKey();
		if (!MapperUtil.keyMatch(origId, newId)) {
			to.setType(getUnitTypeEntity(em, newId));
		}

		// Manager Position
		origId = MapperUtil.getEntityBusinessKey(to.getManagerPosition());
		newId = from.getManagerPosKey();
		if (!MapperUtil.keyMatch(origId, newId)) {
			// Remove from Orig Position
			if (origId != null) {
				PositionEntity pos = getPositionEntity(em, origId);
				pos.removeManageOrgUnit(to);
			}
			// Add to New Position
			if (newId != null) {
				PositionEntity pos = getPositionEntity(em, newId);
				pos.addManageOrgUnit(to);
			}
		}

		// Parent Org Unit
		origId = MapperUtil.getEntityBusinessKey(to.getParentOrgUnit());
		newId = from.getParentKey();
		if (!MapperUtil.keyMatch(origId, newId)) {
			// Remove from Orig Parent
			if (origId != null) {
				OrgUnitEntity ou = getOrgUnitEntity(em, origId);
				ou.removeSubOrgUnit(to);
			}
			// Add to New Parent
			if (newId != null) {
				OrgUnitEntity ou = getOrgUnitEntity(em, newId);
				ou.addSubOrgUnit(to);
			}
		}

		// Positions
		List<String> origIds = MapperUtil.convertEntitiesToApiKeys(to.getPositions());
		List<String> newIds = from.getPositionKeys();
		if (!MapperUtil.keysMatch(origIds, newIds)) {
			// Removed
			for (String id : MapperUtil.keysRemoved(origIds, newIds)) {
				PositionEntity pos = getPositionEntity(em, id);
				to.removePosition(pos);
			}
			// Added
			for (String id : MapperUtil.keysAdded(origIds, newIds)) {
				PositionEntity pos = getPositionEntity(em, id);
				to.addPosition(pos);
			}
		}

		// Sub Org Units
		origIds = MapperUtil.convertEntitiesToApiKeys(to.getSubOrgUnits());
		newIds = from.getSubKeys();
		if (!MapperUtil.keysMatch(origIds, newIds)) {
			// Removed
			for (String id : MapperUtil.keysRemoved(origIds, newIds)) {
				OrgUnitEntity ou = getOrgUnitEntity(em, id);
				to.removeSubOrgUnit(ou);
			}
			// Added
			for (String id : MapperUtil.keysAdded(origIds, newIds)) {
				OrgUnitEntity ou = getOrgUnitEntity(em, id);
				to.addSubOrgUnit(ou);
			}
		}

	}

	@Override
	protected void copyEntityToApiFields(final EntityManager em, final OrgUnitEntity from, final OrgUnit to) {
		to.setDescription(from.getDescription());
		// Key
		to.setTypeKey(MapperUtil.getEntityBusinessKey(from.getType()));
		to.setManagerPosKey(MapperUtil.getEntityBusinessKey(from.getManagerPosition()));
		to.setParentKey(MapperUtil.getEntityBusinessKey(from.getParentOrgUnit()));
		// Keys
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
