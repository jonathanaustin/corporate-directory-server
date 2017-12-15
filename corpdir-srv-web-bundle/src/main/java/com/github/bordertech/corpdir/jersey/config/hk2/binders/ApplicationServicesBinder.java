package com.github.bordertech.corpdir.jersey.config.hk2.binders;

import com.github.bordertech.corpdir.api.v1.ContactService;
import com.github.bordertech.corpdir.api.v1.LocationService;
import com.github.bordertech.corpdir.api.v1.OrgUnitService;
import com.github.bordertech.corpdir.api.v1.PositionService;
import com.github.bordertech.corpdir.api.v1.PositionTypeService;
import com.github.bordertech.corpdir.api.v1.SystemCtrlService;
import com.github.bordertech.corpdir.api.v1.UnitTypeService;
import com.github.bordertech.corpdir.api.v1.VersionCtrlService;
import com.github.bordertech.corpdir.jpa.v1.api.ContactServiceImpl;
import com.github.bordertech.corpdir.jpa.v1.api.LocationServiceImpl;
import com.github.bordertech.corpdir.jpa.v1.api.OrgUnitServiceImpl;
import com.github.bordertech.corpdir.jpa.v1.api.PositionServiceImpl;
import com.github.bordertech.corpdir.jpa.v1.api.PositionTypeServiceImpl;
import com.github.bordertech.corpdir.jpa.v1.api.SystemCtrlServiceImpl;
import com.github.bordertech.corpdir.jpa.v1.api.UnitTypeServiceImpl;
import com.github.bordertech.corpdir.jpa.v1.api.VersionCtrlServiceImpl;
import javax.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationServicesBinder extends AbstractBinder {

	@Override
	protected void configure() {
		// Services
		bind(LocationServiceImpl.class).to(LocationService.class).in(Singleton.class);
		bind(UnitTypeServiceImpl.class).to(UnitTypeService.class).in(Singleton.class);
		bind(OrgUnitServiceImpl.class).to(OrgUnitService.class).in(Singleton.class);
		bind(PositionTypeServiceImpl.class).to(PositionTypeService.class).in(Singleton.class);
		bind(PositionServiceImpl.class).to(PositionService.class).in(Singleton.class);
		bind(ContactServiceImpl.class).to(ContactService.class).in(Singleton.class);
		bind(SystemCtrlServiceImpl.class).to(SystemCtrlService.class).in(Singleton.class);
		bind(VersionCtrlServiceImpl.class).to(VersionCtrlService.class).in(Singleton.class);
	}
}
