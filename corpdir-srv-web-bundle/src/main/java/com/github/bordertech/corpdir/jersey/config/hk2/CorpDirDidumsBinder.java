package com.github.bordertech.corpdir.jersey.config.hk2;

import com.github.bordertech.corpdir.web.ui.actioncreator.ChannelActionCreator;
import com.github.bordertech.corpdir.web.ui.actioncreator.ContactActionCreator;
import com.github.bordertech.corpdir.web.ui.actioncreator.LocationActionCreator;
import com.github.bordertech.corpdir.web.ui.actioncreator.OrgUnitActionCreator;
import com.github.bordertech.corpdir.web.ui.actioncreator.PositionActionCreator;
import com.github.bordertech.corpdir.web.ui.actioncreator.PositionTypeActionCreator;
import com.github.bordertech.corpdir.web.ui.actioncreator.SystemCtrlActionCreator;
import com.github.bordertech.corpdir.web.ui.actioncreator.UnitTypeActionCreator;
import com.github.bordertech.corpdir.web.ui.actioncreator.VersionCtrlActionCreator;
import com.github.bordertech.corpdir.web.ui.actioncreator.impl.ChannelActionCreatorImpl;
import com.github.bordertech.corpdir.web.ui.actioncreator.impl.ContactActionCreatorImpl;
import com.github.bordertech.corpdir.web.ui.actioncreator.impl.LocationActionCreatorImpl;
import com.github.bordertech.corpdir.web.ui.actioncreator.impl.OrgUnitActionCreatorImpl;
import com.github.bordertech.corpdir.web.ui.actioncreator.impl.PositionActionCreatorImpl;
import com.github.bordertech.corpdir.web.ui.actioncreator.impl.PositionTypeActionCreatorImpl;
import com.github.bordertech.corpdir.web.ui.actioncreator.impl.SystemCtrlActionCreatorImpl;
import com.github.bordertech.corpdir.web.ui.actioncreator.impl.UnitTypeActionCreatorImpl;
import com.github.bordertech.corpdir.web.ui.actioncreator.impl.VersionCtrlActionCreatorImpl;
import com.github.bordertech.corpdir.web.ui.dataapi.ChannelApi;
import com.github.bordertech.corpdir.web.ui.dataapi.ContactApi;
import com.github.bordertech.corpdir.web.ui.dataapi.LocationApi;
import com.github.bordertech.corpdir.web.ui.dataapi.OrgUnitApi;
import com.github.bordertech.corpdir.web.ui.dataapi.PositionApi;
import com.github.bordertech.corpdir.web.ui.dataapi.PositionTypeApi;
import com.github.bordertech.corpdir.web.ui.dataapi.SystemCtrlApi;
import com.github.bordertech.corpdir.web.ui.dataapi.UnitTypeApi;
import com.github.bordertech.corpdir.web.ui.dataapi.VersionCtrlApi;
import com.github.bordertech.corpdir.web.ui.dataapi.impl.ChannelApiImpl;
import com.github.bordertech.corpdir.web.ui.dataapi.impl.ContactApiImpl;
import com.github.bordertech.corpdir.web.ui.dataapi.impl.LocationApiImpl;
import com.github.bordertech.corpdir.web.ui.dataapi.impl.OrgUnitApiImpl;
import com.github.bordertech.corpdir.web.ui.dataapi.impl.PositionApiImpl;
import com.github.bordertech.corpdir.web.ui.dataapi.impl.PositionTypeApiImpl;
import com.github.bordertech.corpdir.web.ui.dataapi.impl.SystemCtrlApiImpl;
import com.github.bordertech.corpdir.web.ui.dataapi.impl.UnitTypeApiImpl;
import com.github.bordertech.corpdir.web.ui.dataapi.impl.VersionCtrlApiImpl;
import com.github.bordertech.corpdir.web.ui.store.ContactStore;
import com.github.bordertech.corpdir.web.ui.store.LocationStore;
import com.github.bordertech.corpdir.web.ui.store.OrgUnitStore;
import com.github.bordertech.corpdir.web.ui.store.PositionStore;
import com.github.bordertech.corpdir.web.ui.store.PositionTypeStore;
import com.github.bordertech.corpdir.web.ui.store.SystemCtrlStore;
import com.github.bordertech.corpdir.web.ui.store.UnitTypeStore;
import com.github.bordertech.corpdir.web.ui.store.VersionCtrlStore;
import com.github.bordertech.corpdir.web.ui.store.impl.ContactStoreImpl;
import com.github.bordertech.corpdir.web.ui.store.impl.LocationStoreImpl;
import com.github.bordertech.corpdir.web.ui.store.impl.OrgUnitStoreImpl;
import com.github.bordertech.corpdir.web.ui.store.impl.PositionStoreImpl;
import com.github.bordertech.corpdir.web.ui.store.impl.PositionTypeStoreImpl;
import com.github.bordertech.corpdir.web.ui.store.impl.SystemCtrlStoreImpl;
import com.github.bordertech.corpdir.web.ui.store.impl.UnitTypeStoreImpl;
import com.github.bordertech.corpdir.web.ui.store.impl.VersionCtrlStoreImpl;
import com.github.bordertech.didums.DidumsBinder;
import com.github.bordertech.didums.DidumsProvider;
import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.wc.dispatcher.DispatcherUicImpl;
import com.github.bordertech.taskmanager.FutureCache;
import com.github.bordertech.taskmanager.TaskManager;
import com.github.bordertech.taskmanager.impl.DefaultFutureCache;
import com.github.bordertech.taskmanager.impl.TaskManagerExecutorServiceImpl;

/**
 *
 * @author jonathan
 */
public class CorpDirDidumsBinder implements DidumsBinder {

	@Override
	public void configBindings(final DidumsProvider provider) {
		provider.bind(Dispatcher.class, DispatcherUicImpl.class, true);
		provider.bind(FutureCache.class, DefaultFutureCache.class, true);
		provider.bind(TaskManager.class, TaskManagerExecutorServiceImpl.class, true);

		// DATA APIs
		provider.bind(ChannelApi.class, ChannelApiImpl.class, true);
		provider.bind(ContactApi.class, ContactApiImpl.class, true);
		provider.bind(LocationApi.class, LocationApiImpl.class, true);
		provider.bind(OrgUnitApi.class, OrgUnitApiImpl.class, true);
		provider.bind(PositionApi.class, PositionApiImpl.class, true);
		provider.bind(PositionTypeApi.class, PositionTypeApiImpl.class, true);
		provider.bind(SystemCtrlApi.class, SystemCtrlApiImpl.class, true);
		provider.bind(UnitTypeApi.class, UnitTypeApiImpl.class, true);
		provider.bind(VersionCtrlApi.class, VersionCtrlApiImpl.class, true);

		// Action Creators
		provider.bind(ChannelActionCreator.class, ChannelActionCreatorImpl.class, true);
		provider.bind(ContactActionCreator.class, ContactActionCreatorImpl.class, true);
		provider.bind(LocationActionCreator.class, LocationActionCreatorImpl.class, true);
		provider.bind(OrgUnitActionCreator.class, OrgUnitActionCreatorImpl.class, true);
		provider.bind(PositionActionCreator.class, PositionActionCreatorImpl.class, true);
		provider.bind(PositionTypeActionCreator.class, PositionTypeActionCreatorImpl.class, true);
		provider.bind(SystemCtrlActionCreator.class, SystemCtrlActionCreatorImpl.class, true);
		provider.bind(UnitTypeActionCreator.class, UnitTypeActionCreatorImpl.class, true);
		provider.bind(VersionCtrlActionCreator.class, VersionCtrlActionCreatorImpl.class, true);

		// Stores
		provider.bind(ContactStore.class, ContactStoreImpl.class, true);
		provider.bind(LocationStore.class, LocationStoreImpl.class, true);
		provider.bind(OrgUnitStore.class, OrgUnitStoreImpl.class, true);
		provider.bind(PositionStore.class, PositionStoreImpl.class, true);
		provider.bind(PositionTypeStore.class, PositionTypeStoreImpl.class, true);
		provider.bind(SystemCtrlStore.class, SystemCtrlStoreImpl.class, true);
		provider.bind(UnitTypeStore.class, UnitTypeStoreImpl.class, true);
		provider.bind(VersionCtrlStore.class, VersionCtrlStoreImpl.class, true);

	}

}
