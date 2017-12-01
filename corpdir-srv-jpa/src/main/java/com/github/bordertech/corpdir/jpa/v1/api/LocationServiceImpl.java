package com.github.bordertech.corpdir.jpa.v1.api;

import com.github.bordertech.corpdir.api.v1.LocationService;
import com.github.bordertech.corpdir.api.v1.model.Location;
import com.github.bordertech.corpdir.jpa.common.map.MapperApi;
import com.github.bordertech.corpdir.jpa.common.svc.JpaBasicTreeService;
import com.github.bordertech.corpdir.jpa.entity.LocationEntity;
import com.github.bordertech.corpdir.jpa.v1.mapper.LocationMapper;
import javax.inject.Singleton;

/**
 * Location JPA service implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public class LocationServiceImpl extends JpaBasicTreeService<Location, LocationEntity> implements LocationService {

	private static final LocationMapper LOCATION_MAPPER = new LocationMapper();

	@Override
	protected Class<LocationEntity> getEntityClass() {
		return LocationEntity.class;
	}

	@Override
	protected MapperApi<Location, LocationEntity> getMapper() {
		return LOCATION_MAPPER;
	}

}
