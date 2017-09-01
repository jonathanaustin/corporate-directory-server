package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.jpa.common.map.AbstractMapperVersionTree;
import com.github.bordertech.corpdir.jpa.entity.ContactEntity;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionTypeEntity;
import com.github.bordertech.corpdir.jpa.entity.links.PositionLinks;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Map {@link Position} and {@link PositionEntity}.
 *
 * @author jonathan
 */
public class PositionMapper extends AbstractMapperVersionTree<Position, PositionLinks, PositionEntity> {

	@Override
	public void copyApiToEntity(final EntityManager em, final Position from, final PositionEntity to, final Integer versionId) {
		super.copyApiToEntity(em, from, to, versionId);
		// Type
		String origId = MapperUtil.convertEntityIdforApi(to.getType());
		String newId = MapperUtil.cleanApiKey(from.getTypeId());
		if (!MapperUtil.keyMatch(origId, newId)) {
			to.setType(getPositionTypeEntity(em, newId));
		}
	}

	@Override
	public void copyEntityToApi(final EntityManager em, final PositionEntity from, final Position to, final Integer versionId) {
		super.copyEntityToApi(em, from, to, versionId);
		to.setTypeId(MapperUtil.convertEntityIdforApi(from.getType()));
	}

	@Override
	protected Position createApiObject() {
		return new Position();
	}

	@Override
	protected PositionEntity createEntityObject(final Long id) {
		return new PositionEntity(id);
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

	@Override
	protected Class<PositionEntity> getEntityClass() {
		return PositionEntity.class;
	}

	@Override
	protected void handleVersionDataApiToEntity(final EntityManager em, final Position from, final PositionEntity to, final Integer versionId) {
		// Get the tree version for this entity
		PositionLinks links = to.getDataVersion(versionId);

		// Belongs to OU
		String origId = MapperUtil.convertEntityIdforApi(links.getOrgUnit());
		String newId = MapperUtil.cleanApiKey(from.getOuId());
		if (!MapperUtil.keyMatch(origId, newId)) {
			// Remove from Orig OU
			if (origId != null) {
				OrgUnitEntity ou = getOrgUnitEntity(em, origId);
				ou.getDataVersion(versionId).removePosition(to);
			}
			// Add to New OU
			if (newId != null) {
				OrgUnitEntity ou = getOrgUnitEntity(em, newId);
				ou.getDataVersion(versionId).addPosition(to);
			}
		}

		// Contacts
		List<String> origIds = MapperUtil.convertEntitiesToApiKeys(links.getContacts());
		List<String> newIds = MapperUtil.cleanApiKeys(from.getContactIds());
		if (!MapperUtil.keysMatch(origIds, newIds)) {
			// Removed
			for (String id : MapperUtil.keysRemoved(origIds, newIds)) {
				ContactEntity con = getContactEntity(em, id);
				links.removeContact(con);
			}
			// Added
			for (String id : MapperUtil.keysAdded(origIds, newIds)) {
				ContactEntity con = getContactEntity(em, id);
				links.addContact(con);
			}
		}

		// Manages
		origIds = MapperUtil.convertEntitiesToApiKeys(links.getManageOrgUnits());
		newIds = MapperUtil.cleanApiKeys(from.getManageOuIds());
		if (!MapperUtil.keysMatch(origIds, newIds)) {
			// Removed
			for (String id : MapperUtil.keysRemoved(origIds, newIds)) {
				OrgUnitEntity ou = getOrgUnitEntity(em, id);
				links.addManageOrgUnit(ou);
			}
			// Added
			for (String id : MapperUtil.keysAdded(origIds, newIds)) {
				OrgUnitEntity ou = getOrgUnitEntity(em, id);
				links.removeManageOrgUnit(ou);
			}
		}
	}

	@Override
	protected void handleVersionDataEntityToApi(final EntityManager em, final PositionEntity from, final Position to, final Integer versionId) {
		PositionLinks links = from.getDataVersion(versionId);
		to.setOuId(MapperUtil.convertEntityIdforApi(links.getOrgUnit()));
		to.setContactIds(MapperUtil.convertEntitiesToApiKeys(links.getContacts()));
		to.setManageOuIds(MapperUtil.convertEntitiesToApiKeys(links.getManageOrgUnits()));
	}

}
