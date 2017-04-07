package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.jpa.common.AbstractMapperTree;
import com.github.bordertech.corpdir.jpa.entity.LocationEntity;
import javax.persistence.EntityManager;

/**
 * Map {@link Location} and {@link LocationEntity}.
 *
 * @author jonathan
 */
public class LocationMapper extends AbstractMapperTree<Location, LocationEntity> {

	private static final AddressMapper ADDRESS_MAPPER = new AddressMapper();

	@Override
	public void copyApiToEntity(final EntityManager em, final Location from, final LocationEntity to) {
		super.copyApiToEntity(em, from, to);
		to.setAddress(ADDRESS_MAPPER.convertApiToEntity(em, from.getAddress()));
	}

	@Override
	public void copyEntityToApi(final EntityManager em, final LocationEntity from, final Location to) {
		super.copyEntityToApi(em, from, to);
		to.setAddress(ADDRESS_MAPPER.convertEntityToApi(em, from.getAddress()));
	}

	@Override
	protected Location createApiObject() {
		return new Location();
	}

	@Override
	protected LocationEntity createEntityObject(final Long id) {
		return new LocationEntity(id);
	}

	@Override
	protected Class<LocationEntity> getEntityClass() {
		return LocationEntity.class;
	}

}
