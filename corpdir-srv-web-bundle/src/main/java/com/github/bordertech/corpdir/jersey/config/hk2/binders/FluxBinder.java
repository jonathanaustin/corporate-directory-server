package com.github.bordertech.corpdir.jersey.config.hk2.binders;

import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.wc.dispatcher.DispatcherUicImpl;
import com.github.bordertech.taskmanager.FutureCache;
import com.github.bordertech.taskmanager.impl.FutureCacheImpl;
import javax.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class FluxBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(DispatcherUicImpl.class).to(Dispatcher.class).in(Singleton.class);
		bind(FutureCacheImpl.class).to(FutureCache.class).in(Singleton.class);
	}

}
