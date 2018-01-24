package com.github.bordertech.corpdir.jersey.config.hk2;

import com.github.bordertech.didums.DidumsBinder;
import com.github.bordertech.didums.DidumsProvider;
import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.wc.dispatcher.DispatcherUicImpl;
import com.github.bordertech.taskmanager.FutureCache;
import com.github.bordertech.taskmanager.TaskManager;
import com.github.bordertech.taskmanager.impl.FutureCacheImpl;
import com.github.bordertech.taskmanager.impl.TaskManagerExecutorServiceImpl;

/**
 *
 * @author jonathan
 */
public class CorpDirDidumsBinder implements DidumsBinder {

	@Override
	public void configBindings(final DidumsProvider provider) {
		provider.bind(Dispatcher.class, DispatcherUicImpl.class, true);
		provider.bind(FutureCache.class, FutureCacheImpl.class, true);
		provider.bind(TaskManager.class, TaskManagerExecutorServiceImpl.class, true);
	}

}
