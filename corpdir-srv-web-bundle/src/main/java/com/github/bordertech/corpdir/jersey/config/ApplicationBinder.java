package com.github.bordertech.corpdir.jersey.config;

import com.github.bordertech.corpdir.api.OrgUnitTypeService;
import com.github.bordertech.corpdir.jpa.OrgUnitTypeServiceImpl;
import javax.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		// services
		bind(OrgUnitTypeServiceImpl.class).to(OrgUnitTypeService.class).in(Singleton.class);
	}
}
