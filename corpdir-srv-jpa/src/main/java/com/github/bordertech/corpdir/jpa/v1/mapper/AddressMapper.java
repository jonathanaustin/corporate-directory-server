package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.Address;
import com.github.bordertech.corpdir.jpa.common.map.AbstractMapper;
import com.github.bordertech.corpdir.jpa.entity.AddressEntity;
import javax.persistence.EntityManager;

/**
 * Map {@link Address} and {@link AddressEntity}.
 *
 * @author jonathan
 */
public class AddressMapper extends AbstractMapper<Address, AddressEntity> {

	@Override
	public void copyApiToEntity(final EntityManager em, final Address from, final AddressEntity to) {
		to.setWorkStation(from.getWorkStation());
		to.setLine1(from.getLine1());
		to.setLine2(from.getLine2());
		to.setCountry(from.getCountry());
		to.setPostcode(from.getPostcode());
		to.setState(from.getState());
		to.setSuburb(from.getSuburb());
	}

	@Override
	public void copyEntityToApi(final EntityManager em, final AddressEntity from, final Address to) {
		to.setWorkStation(from.getWorkStation());
		to.setLine1(from.getLine1());
		to.setLine2(from.getLine2());
		to.setCountry(from.getCountry());
		to.setPostcode(from.getPostcode());
		to.setState(from.getState());
		to.setSuburb(from.getSuburb());
	}

	@Override
	protected Address createApiObject() {
		return new Address();
	}

	@Override
	protected AddressEntity createEntityObject() {
		return new AddressEntity();
	}

}
