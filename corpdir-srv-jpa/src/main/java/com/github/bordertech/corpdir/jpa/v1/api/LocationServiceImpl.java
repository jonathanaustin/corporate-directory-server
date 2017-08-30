package com.github.bordertech.corpdir.jpa.v1.api;

import com.github.bordertech.corpdir.api.v1.LocationService;
import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.jpa.common.svc.AbstractJpaKeyIdTreeService;
import javax.inject.Singleton;
import com.github.bordertech.corpdir.jpa.common.map.MapperApiEntity;
import com.github.bordertech.corpdir.jpa.entity.LocationEntity;
import com.github.bordertech.corpdir.jpa.v1.mapper.LocationMapper;

/**
 * Location JPA service implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public class LocationServiceImpl extends AbstractJpaKeyIdTreeService<Location, LocationEntity> implements LocationService {

	private static final LocationMapper LOCATION_MAPPER = new LocationMapper();

	@Override
	protected Class<LocationEntity> getEntityClass() {
		return LocationEntity.class;
	}

	@Override
	protected MapperApiEntity<Location, LocationEntity> getMapper() {
		return LOCATION_MAPPER;
	}

}
