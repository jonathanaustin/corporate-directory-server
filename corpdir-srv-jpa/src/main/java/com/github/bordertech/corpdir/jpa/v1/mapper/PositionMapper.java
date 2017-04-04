package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.jpa.common.AbstractKeyIdApiEntityMapper;
import com.github.bordertech.corpdir.jpa.entity.ContactEntity;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionTypeEntity;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Map {@link Position} and {@link PositionEntity}.
 *
 * @author jonathan
 */
public class PositionMapper extends AbstractKeyIdApiEntityMapper<Position, PositionEntity> {

	@Override
	protected void copyApiToEntityFields(final EntityManager em, final Position from, final PositionEntity to) {
		to.setDescription(from.getDescription());

		// Type
		String origId = MapperUtil.getEntityBusinessKey(to.getType());
		String newId = from.getTypeKey();
		if (!MapperUtil.keyMatch(origId, newId)) {
			to.setType(getPositionTypeEntity(em, newId));
		}

		// Parent Position
		origId = MapperUtil.getEntityBusinessKey(to.getParentPosition());
		newId = from.getParentKey();
		if (!MapperUtil.keyMatch(origId, newId)) {
			// Remove from Orig Parent
			if (origId != null) {
				PositionEntity pos = getPositionEntity(em, origId);
				pos.removeSubPosition(to);
			}
			// Add to New Parent
			if (newId != null) {
				PositionEntity pos = getPositionEntity(em, newId);
				pos.addSubPosition(to);
			}
		}

		// Belongs to OU
		origId = MapperUtil.getEntityBusinessKey(to.getOrgUnit());
		newId = from.getOuKey();
		if (!MapperUtil.keyMatch(origId, newId)) {
			// Remove from Orig OU
			if (origId != null) {
				OrgUnitEntity ou = getOrgUnitEntity(em, origId);
				ou.removePosition(to);
			}
			// Add to New OU
			if (newId != null) {
				OrgUnitEntity ou = getOrgUnitEntity(em, newId);
				ou.addPosition(to);
			}
		}

		// Sub Positions
		List<String> origIds = MapperUtil.convertEntitiesToApiKeys(to.getSubPositions());
		List<String> newIds = from.getSubKeys();
		if (!MapperUtil.keysMatch(origIds, newIds)) {
			// Removed
			for (String id : MapperUtil.keysRemoved(origIds, newIds)) {
				PositionEntity pos = getPositionEntity(em, id);
				to.removeSubPosition(pos);
			}
			// Added
			for (String id : MapperUtil.keysAdded(origIds, newIds)) {
				PositionEntity pos = getPositionEntity(em, id);
				to.addSubPosition(pos);
			}
		}

		// Contacts
		origIds = MapperUtil.convertEntitiesToApiKeys(to.getContacts());
		newIds = from.getContactKeys();
		if (!MapperUtil.keysMatch(origIds, newIds)) {
			// Removed
			for (String id : MapperUtil.keysRemoved(origIds, newIds)) {
				ContactEntity con = getContactEntity(em, id);
				to.removeContact(con);
			}
			// Added
			for (String id : MapperUtil.keysAdded(origIds, newIds)) {
				ContactEntity con = getContactEntity(em, id);
				to.addContact(con);
			}
		}

		// Manages
		origIds = MapperUtil.convertEntitiesToApiKeys(to.getManageOrgUnits());
		newIds = from.getManageOuKeys();
		if (!MapperUtil.keysMatch(origIds, newIds)) {
			// Removed
			for (String id : MapperUtil.keysRemoved(origIds, newIds)) {
				OrgUnitEntity ou = getOrgUnitEntity(em, id);
				to.addManageOrgUnit(ou);
			}
			// Added
			for (String id : MapperUtil.keysAdded(origIds, newIds)) {
				OrgUnitEntity ou = getOrgUnitEntity(em, id);
				to.removeManageOrgUnit(ou);
			}
		}

	}

	@Override
	protected void copyEntityToApiFields(final EntityManager em, final PositionEntity from, final Position to) {
		to.setDescription(from.getDescription());
		// Key
		to.setTypeKey(MapperUtil.getEntityBusinessKey(from.getType()));
		to.setParentKey(MapperUtil.getEntityBusinessKey(from.getParentPosition()));
		to.setOuKey(MapperUtil.getEntityBusinessKey(from.getOrgUnit()));
		// Keys
		to.setSubKeys(MapperUtil.convertEntitiesToApiKeys(from.getSubPositions()));
		to.setContactKeys(MapperUtil.convertEntitiesToApiKeys(from.getContacts()));
		to.setManageOuKeys(MapperUtil.convertEntitiesToApiKeys(from.getManageOrgUnits()));
	}

	@Override
	protected Position createApiObject() {
		return new Position();
	}

	@Override
	protected PositionEntity createEntityObject(final Long id, final String businessKey) {
		return new PositionEntity(id, businessKey);
	}

	protected PositionTypeEntity getPositionTypeEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntity(em, keyId, PositionTypeEntity.class);
	}

	protected PositionEntity getPositionEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntity(em, keyId, PositionEntity.class);
	}

	protected OrgUnitEntity getOrgUnitEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntity(em, keyId, OrgUnitEntity.class);
	}

	protected ContactEntity getContactEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntity(em, keyId, ContactEntity.class);
	}

}
