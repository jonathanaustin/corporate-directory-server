package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.jpa.common.AbstractMapperNested;
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
public class PositionMapper extends AbstractMapperNested<Position, PositionEntity> {

	@Override
	protected void copyApiToEntityFields(final EntityManager em, final Position from, final PositionEntity to) {

		// Type
		String origId = MapperUtil.convertEntityIdforApi(to.getType());
		String newId = from.getTypeId();
		if (!MapperUtil.keyMatch(origId, newId)) {
			to.setType(getPositionTypeEntity(em, newId));
		}

		// Belongs to OU
		origId = MapperUtil.convertEntityIdforApi(to.getOrgUnit());
		newId = from.getOuId();
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

		// Contacts
		List<String> origIds = MapperUtil.convertEntitiesToApiKeys(to.getContacts());
		List<String> newIds = from.getContactIds();
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
		newIds = from.getManageOuIds();
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
		to.setTypeId(MapperUtil.convertEntityIdforApi(from.getType()));
		to.setOuId(MapperUtil.convertEntityIdforApi(from.getOrgUnit()));
		to.setContactIds(MapperUtil.convertEntitiesToApiKeys(from.getContacts()));
		to.setManageOuIds(MapperUtil.convertEntitiesToApiKeys(from.getManageOrgUnits()));
	}

	@Override
	protected Position createApiObject() {
		return new Position();
	}

	@Override
	protected PositionEntity createEntityObject(final Long id) {
		return new PositionEntity(id);
	}

	@Override
	protected Class<PositionEntity> getEntityClass() {
		return PositionEntity.class;
	}

	protected PositionTypeEntity getPositionTypeEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntity(em, keyId, PositionTypeEntity.class);
	}

	protected OrgUnitEntity getOrgUnitEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntity(em, keyId, OrgUnitEntity.class);
	}

	protected ContactEntity getContactEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntity(em, keyId, ContactEntity.class);
	}

}
