package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.Channel;
import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.jpa.common.map.AbstractMapperVersion;
import com.github.bordertech.corpdir.jpa.entity.ChannelEntity;
import com.github.bordertech.corpdir.jpa.entity.ContactEntity;
import com.github.bordertech.corpdir.jpa.entity.LocationEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import com.github.bordertech.corpdir.jpa.entity.links.ContactLinksEntity;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Map {@link Contact} and {@link ContactEntity}.
 *
 * @author jonathan
 */
public class ContactMapper extends AbstractMapperVersion<Contact, ContactLinksEntity, ContactEntity> {

	private static final AddressMapper ADDRESS_MAPPER = new AddressMapper();
	private static final ChannelMapper CHANNEL_MAPPER = new ChannelMapper();

	@Override
	public void copyApiToEntity(final EntityManager em, final Contact from, final ContactEntity to, final Long versionId) {
		super.copyApiToEntity(em, from, to, versionId);
		to.setCompanyTitle(from.getCompanyTitle());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setAddress(ADDRESS_MAPPER.convertApiToEntity(em, from.getAddress()));

		// Location
		String origId = MapperUtil.convertEntityIdforApi(to.getLocation());
		String newId = MapperUtil.cleanApiKey(from.getLocationId());
		if (!MapperUtil.keyMatch(origId, newId)) {
			to.setLocation(getLocationEntity(em, newId));
		}

		// Channels
		// Clear all channels and re-add (Use cascade delete, update, insert)
		if (to.getChannels() != null) {
			to.getChannels().clear();
		}
		if (from.getChannels() != null) {
			for (Channel channel : from.getChannels()) {
				ChannelEntity entity = CHANNEL_MAPPER.convertApiToEntity(em, channel);
				to.addChannel(entity);
			}
		}
	}

	@Override
	public void copyEntityToApi(final EntityManager em, final ContactEntity from, final Contact to, final Long versionId) {
		super.copyEntityToApi(em, from, to, versionId);
		to.setCompanyTitle(from.getCompanyTitle());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setHasImage(from.getImage() != null);
		to.setAddress(ADDRESS_MAPPER.convertEntityToApi(em, from.getAddress()));
		// Location
		to.setLocationId(MapperUtil.convertEntityIdforApi(from.getLocation()));
		// Channels
		to.setChannels(CHANNEL_MAPPER.convertEntitiesToApis(em, from.getChannels()));
	}

	@Override
	protected Contact createApiObject(final String id) {
		return new Contact(id);
	}

	@Override
	protected ContactEntity createEntityObject(final Long id) {
		return new ContactEntity(id);
	}

	protected LocationEntity getLocationEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntityByKeyId(em, keyId, LocationEntity.class);
	}

	protected PositionEntity getPositionEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntityByKeyId(em, keyId, PositionEntity.class);
	}

	@Override
	protected Class<ContactEntity> getEntityClass() {
		return ContactEntity.class;
	}

	@Override
	protected void handleVersionDataApiToEntity(final EntityManager em, final Contact from, final ContactEntity to, final VersionCtrlEntity ctrl) {

		// Get the links version for this entity
		ContactLinksEntity links = to.getOrCreateDataVersion(ctrl);

		// Positions
		List<String> origIds = MapperUtil.convertEntitiesToApiKeys(links.getPositions());
		List<String> newIds = MapperUtil.cleanApiKeys(from.getPositionIds());
		if (!MapperUtil.keysMatch(origIds, newIds)) {
			// Removed
			for (String id : MapperUtil.keysRemoved(origIds, newIds)) {
				PositionEntity pos = getPositionEntity(em, id);
				pos.getOrCreateDataVersion(ctrl).removeContact(to);
			}
			// Added
			for (String id : MapperUtil.keysAdded(origIds, newIds)) {
				PositionEntity pos = getPositionEntity(em, id);
				pos.getOrCreateDataVersion(ctrl).addContact(to);
			}
		}
	}

	@Override
	protected void handleVersionDataEntityToApi(final EntityManager em, final ContactEntity from, final Contact to, final Long versionId) {
		// Get the tree version for this entity
		ContactLinksEntity links = from.getDataVersion(versionId);
		if (links != null) {
			to.setPositionIds(MapperUtil.convertEntitiesToApiKeys(links.getPositions()));
		}
	}

}
