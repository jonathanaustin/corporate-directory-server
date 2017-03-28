package com.github.bordertech.corpdir.jersey.config;

import com.github.bordertech.corpdir.api.v1.OrgUnitService;
import com.github.bordertech.corpdir.api.v1.UnitTypeService;
import com.github.bordertech.corpdir.jpa.v1.OrgUnitServiceImpl;
import com.github.bordertech.corpdir.jpa.v1.UnitTypeServiceImpl;
import javax.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		// services
		bind(UnitTypeServiceImpl.class).to(UnitTypeService.class).in(Singleton.class);
		bind(OrgUnitServiceImpl.class).to(OrgUnitService.class).in(Singleton.class);
	}
}
