package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.jpa.common.AbstractKeyIdApiEntityMapper;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import com.github.bordertech.corpdir.jpa.entity.ContactEntity;
import com.github.bordertech.corpdir.jpa.entity.LocationEntity;
import javax.persistence.EntityManager;

/**
 * Map {@link Contact} and {@link ContactEntity}.
 *
 * @author jonathan
 */
public class ContactMapper extends AbstractKeyIdApiEntityMapper<Contact, ContactEntity> {

	private final AddressMapper addressMapper = new AddressMapper();
	private final ChannelMapper channelMapper = new ChannelMapper();

	@Override
	public void copyApiToEntityFields(final EntityManager em, final Contact from, final ContactEntity to) {
		to.setCompanyTitle(from.getCompanyTitle());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setAddress(addressMapper.convertApiToEntity(em, from.getAddress()));
		LocationEntity location = MapperUtil.getEntity(em, from.getLocationKey(), LocationEntity.class);
		to.setLocation(location);
//		to.setChannels(channelMapper.convertEntitiesToApis(em, from.getChannels()));
	}

	@Override
	public void copyEntityToApiFields(final EntityManager em, final ContactEntity from, final Contact to) {
		to.setCompanyTitle(from.getCompanyTitle());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setHasImage(from.getImage() != null);
		to.setAddress(addressMapper.convertEntityToApi(em, from.getAddress()));
		to.setLocationKey(MapperUtil.getEntityBusinessKey(from.getLocation()));
		to.setChannels(channelMapper.convertEntitiesToApis(em, from.getChannels()));
	}

	@Override
	protected Contact createApiObject() {
		return new Contact();
	}

	@Override
	protected ContactEntity createEntityObject(final Long id, final String businessKey) {
		return new ContactEntity(id, businessKey);
	}

}
