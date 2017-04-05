package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.Channel;
import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.jpa.common.AbstractKeyIdApiEntityMapper;
import com.github.bordertech.corpdir.jpa.entity.ChannelEntity;
import com.github.bordertech.corpdir.jpa.entity.ContactEntity;
import com.github.bordertech.corpdir.jpa.entity.LocationEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Map {@link Contact} and {@link ContactEntity}.
 *
 * @author jonathan
 */
public class ContactMapper extends AbstractKeyIdApiEntityMapper<Contact, ContactEntity> {

	private static final AddressMapper ADDRESS_MAPPER = new AddressMapper();
	private static final ChannelMapper CHANNEL_MAPPER = new ChannelMapper();

	@Override
	public void copyApiToEntityFields(final EntityManager em, final Contact from, final ContactEntity to) {
		to.setCompanyTitle(from.getCompanyTitle());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setAddress(ADDRESS_MAPPER.convertApiToEntity(em, from.getAddress()));

		// Location
		String origId = MapperUtil.convertEntityIdforApi(to.getLocation());
		String newId = from.getLocationId();
		if (!MapperUtil.keyMatch(origId, newId)) {
			to.setLocation(getLocationEntity(em, newId));
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

		// Channels
		// Clear all channels and re-add (Use cascade delete, update, insert)
		to.getChannels().clear();
		for (Channel channel : from.getChannels()) {
			ChannelEntity entity = CHANNEL_MAPPER.convertApiToEntity(em, channel);
			to.addChannel(entity);
		}

	}

	@Override
	public void copyEntityToApiFields(final EntityManager em, final ContactEntity from, final Contact to) {
		to.setCompanyTitle(from.getCompanyTitle());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setHasImage(from.getImage() != null);
		to.setAddress(ADDRESS_MAPPER.convertEntityToApi(em, from.getAddress()));
		// Location
		to.setLocationId(MapperUtil.convertEntityIdforApi(from.getLocation()));
		// Positions
		to.setPositionIds(MapperUtil.convertEntitiesToApiKeys(from.getPositions()));
		// Channels
		to.setChannels(CHANNEL_MAPPER.convertEntitiesToApis(em, from.getChannels()));
	}

	@Override
	protected Contact createApiObject() {
		return new Contact();
	}

	@Override
	protected ContactEntity createEntityObject(final Long id) {
		return new ContactEntity(id);
	}

	protected LocationEntity getLocationEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntity(em, keyId, LocationEntity.class);
	}

	protected PositionEntity getPositionEntity(final EntityManager em, final String keyId) {
		return MapperUtil.getEntity(em, keyId, PositionEntity.class);
	}

}
