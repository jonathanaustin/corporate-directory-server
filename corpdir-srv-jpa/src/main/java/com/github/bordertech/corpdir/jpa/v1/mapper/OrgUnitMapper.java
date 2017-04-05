package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.jpa.common.AbstractKeyIdApiEntityMapper;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import com.github.bordertech.corpdir.jpa.entity.UnitTypeEntity;
import com.github.bordertech.corpdir.jpa.util.EmfUtil;
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

		boolean root = EmfUtil.isRootOrgUnit(to);

		// Type
		String origId = MapperUtil.convertEntityIdforApi(to.getType());
		String newId = from.getTypeId();
		if (!MapperUtil.keyMatch(origId, newId)) {
			to.setType(getUnitTypeEntity(em, newId));
		}

		// Manager Position
		origId = MapperUtil.convertEntityIdforApi(to.getManagerPosition());
		newId = from.getManagerPosId();
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

		// Positions
		List<String> origIds = MapperUtil.convertEntitiesToApiKeys(to.getPositions());
		List<String> newIds = from.getPositionIds();
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

		// Parent Org Unit - (Never changes for ROOT)
		if (!root) {
			origId = MapperUtil.convertEntityIdforApi(to.getParentOrgUnit());
			newId = from.getParentId();
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
		}

		// Sub Org Units
		origIds = MapperUtil.convertEntitiesToApiKeys(to.getSubOrgUnits());
		newIds = from.getSubIds();
		// Make Sure ROOT always has itself as a sub unit
		if (root) {
			newIds.add(MapperUtil.convertEntityIdforApi(to));
		}
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

		boolean root = EmfUtil.isRootOrgUnit(from);

		// Key
		to.setTypeId(MapperUtil.convertEntityIdforApi(from.getType()));
		to.setManagerPosId(MapperUtil.convertEntityIdforApi(from.getManagerPosition()));
		to.setParentId(MapperUtil.convertEntityIdforApi(from.getParentOrgUnit()));
		// Keys
		to.setPositionIds(MapperUtil.convertEntitiesToApiKeys(from.getPositions()));
		List<String> subIds = MapperUtil.convertEntitiesToApiKeys(from.getSubOrgUnits());
		// For ROOT - Dont send itself as a child to the API
		if (root) {
			subIds.remove(MapperUtil.convertEntityIdforApi(from));
		}

		to.setSubIds(subIds);
	}

	@Override
	protected OrgUnit createApiObject() {
		return new OrgUnit();
	}

	@Override
	protected OrgUnitEntity createEntityObject(final Long id) {
		return new OrgUnitEntity(id);
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
