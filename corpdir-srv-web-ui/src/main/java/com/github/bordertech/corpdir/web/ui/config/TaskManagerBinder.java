package com.github.bordertech.corpdir.web.ui.config;

import com.github.bordertech.taskmanager.FutureCache;
import com.github.bordertech.taskmanager.TaskManager;
import com.github.bordertech.taskmanager.impl.FutureCacheImpl;
import com.github.bordertech.taskmanager.impl.TaskManagerExecutorServiceImpl;
import javax.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class TaskManagerBinder extends AbstractBinder {

	@Override
	protected void configure() {
		// Task Manager Details
		bind(TaskManagerExecutorServiceImpl.class).to(TaskManager.class).in(Singleton.class);
		bind(FutureCacheImpl.class).to(FutureCache.class).in(Singleton.class);
	}
}
