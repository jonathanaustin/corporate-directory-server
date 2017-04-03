package com.github.bordertech.corpdir.jpa.v1.mapper;

import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.jpa.common.AbstractKeyIdApiEntityMapper;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import com.github.bordertech.corpdir.jpa.entity.LocationEntity;
import javax.persistence.EntityManager;

/**
 * Map {@link Location} and {@link LocationEntity}.
 *
 * @author jonathan
 */
public class LocationMapper extends AbstractKeyIdApiEntityMapper<Location, LocationEntity> {

	private final AddressMapper addressMapper = new AddressMapper();

	@Override
	public void copyApiToEntityFields(final EntityManager em, final Location from, final LocationEntity to) {
		to.setDescription(from.getDescription());
		to.setAddress(addressMapper.convertApiToEntity(em, from.getAddress()));
//		to.setSubLocationKeys(MapperUtil.convertEntitiesToApiKeys(from.getSubLocations()));
	}

	@Override
	public void copyEntityToApiFields(final EntityManager em, final LocationEntity from, final Location to) {
		to.setDescription(from.getDescription());
		to.setAddress(addressMapper.convertEntityToApi(em, from.getAddress()));
		to.setSubKeys(MapperUtil.convertEntitiesToApiKeys(from.getSubLocations()));
	}

	@Override
	protected Location createApiObject() {
		return new Location();
	}

	@Override
	protected LocationEntity createEntityObject(final Long id, final String businessKey) {
		return new LocationEntity(id, businessKey);
	}

}
