package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.jpa.common.map.AbstractMapperKeyIdTreeVersion;
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
public class OrgUnitMapper extends AbstractMapperKeyIdTreeVersion<OrgUnit, OrgUnitEntity> {

	@Override
	public void copyApiToEntity(final EntityManager em, final OrgUnit from, final OrgUnitEntity to) {
		super.copyApiToEntity(em, from, to);
		// Type
		String origId = MapperUtil.convertEntityIdforApi(to.getType());
		String newId = MapperUtil.cleanApiKey(from.getTypeId());
		if (!MapperUtil.keyMatch(origId, newId)) {
			to.setType(getUnitTypeEntity(em, newId));
		}

		// Manager Position
		origId = MapperUtil.convertEntityIdforApi(to.getManagerPosition());
		newId = MapperUtil.cleanApiKey(from.getManagerPosId());
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
		List<String> newIds = MapperUtil.cleanApiKeys(from.getPositionIds());
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
	}

	@Override
	public void copyEntityToApi(final EntityManager em, final OrgUnitEntity from, final OrgUnit to) {
		super.copyEntityToApi(em, from, to);
		to.setTypeId(MapperUtil.convertEntityIdforApi(from.getType()));
		to.setManagerPosId(MapperUtil.convertEntityIdforApi(from.getManagerPosition()));
		to.setPositionIds(MapperUtil.convertEntitiesToApiKeys(from.getPositions()));
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

	@Override
	protected Class<OrgUnitEntity> getEntityClass() {
		return OrgUnitEntity.class;
	}
}
