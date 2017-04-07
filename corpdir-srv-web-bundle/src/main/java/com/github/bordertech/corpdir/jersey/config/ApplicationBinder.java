package com.github.bordertech.corpdir.jersey.config;

import com.github.bordertech.corpdir.api.v1.ContactService;
import com.github.bordertech.corpdir.api.v1.LocationService;
import com.github.bordertech.corpdir.api.v1.OrgUnitService;
import com.github.bordertech.corpdir.api.v1.PositionService;
import com.github.bordertech.corpdir.api.v1.PositionTypeService;
import com.github.bordertech.corpdir.api.v1.UnitTypeService;
import com.github.bordertech.corpdir.jpa.v1.api.ContactServiceImpl;
import com.github.bordertech.corpdir.jpa.v1.api.LocationServiceImpl;
import com.github.bordertech.corpdir.jpa.v1.api.OrgUnitServiceImpl;
import com.github.bordertech.corpdir.jpa.v1.api.PositionServiceImpl;
import com.github.bordertech.corpdir.jpa.v1.api.PositionTypeServiceImpl;
import com.github.bordertech.corpdir.jpa.v1.api.UnitTypeServiceImpl;
import javax.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		// services
		bind(LocationServiceImpl.class).to(LocationService.class).in(Singleton.class);
		bind(UnitTypeServiceImpl.class).to(UnitTypeService.class).in(Singleton.class);
		bind(OrgUnitServiceImpl.class).to(OrgUnitService.class).in(Singleton.class);
		bind(PositionTypeServiceImpl.class).to(PositionTypeService.class).in(Singleton.class);
		bind(PositionServiceImpl.class).to(PositionService.class).in(Singleton.class);
		bind(ContactServiceImpl.class).to(ContactService.class).in(Singleton.class);
	}
}
