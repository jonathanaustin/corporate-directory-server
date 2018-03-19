package com.github.bordertech.corpdir.jersey.config.hk2;

import com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl.ChannelActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl.ContactActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl.LocationActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl.OrgUnitActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl.PositionActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl.PositionTypeActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl.SystemCtrlActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl.UnitTypeActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl.VersionCtrlActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.ChannelApi;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.ContactApi;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.LocationApi;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.OrgUnitApi;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.PositionApi;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.PositionTypeApi;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.SystemCtrlApi;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.UnitTypeApi;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.VersionCtrlApi;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.ChannelApiImpl;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.ContactApiImpl;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.LocationApiImpl;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.OrgUnitApiImpl;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.PositionApiImpl;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.PositionTypeApiImpl;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.SystemCtrlApiImpl;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.UnitTypeApiImpl;
import com.github.bordertech.corpdir.web.ui.flux.dataapi.impl.VersionCtrlApiImpl;
import com.github.bordertech.corpdir.web.ui.flux.store.impl.ContactStore;
import com.github.bordertech.corpdir.web.ui.flux.store.impl.LocationStore;
import com.github.bordertech.corpdir.web.ui.flux.store.impl.OrgUnitStore;
import com.github.bordertech.corpdir.web.ui.flux.store.impl.PositionStore;
import com.github.bordertech.corpdir.web.ui.flux.store.impl.PositionTypeStore;
import com.github.bordertech.corpdir.web.ui.flux.store.impl.SystemCtrlStore;
import com.github.bordertech.corpdir.web.ui.flux.store.impl.UnitTypeStore;
import com.github.bordertech.corpdir.web.ui.flux.store.impl.VersionCtrlStore;
import com.github.bordertech.didums.DidumsBinder;
import com.github.bordertech.didums.DidumsProvider;
import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.wc.dispatcher.DispatcherUicImpl;

/**
 *
 * @author jonathan
 */
public class CorpDirDidumsBinder implements DidumsBinder {

	@Override
	public void configBindings(final DidumsProvider provider) {
		provider.bind(Dispatcher.class, DispatcherUicImpl.class, true);
//		provider.bind(TaskFutureWrapperCache.class, TaskFutureWrapperCacheImpl.class, true);
//		provider.bind(TaskManager.class, TaskManagerExecutorServiceImpl.class, true);

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
		provider.bind(ChannelActionCreator.class, ChannelActionCreator.class, true);
		provider.bind(ContactActionCreator.class, ContactActionCreator.class, true);
		provider.bind(LocationActionCreator.class, LocationActionCreator.class, true);
		provider.bind(OrgUnitActionCreator.class, OrgUnitActionCreator.class, true);
		provider.bind(PositionActionCreator.class, PositionActionCreator.class, true);
		provider.bind(PositionTypeActionCreator.class, PositionTypeActionCreator.class, true);
		provider.bind(SystemCtrlActionCreator.class, SystemCtrlActionCreator.class, true);
		provider.bind(UnitTypeActionCreator.class, UnitTypeActionCreator.class, true);
		provider.bind(VersionCtrlActionCreator.class, VersionCtrlActionCreator.class, true);

		// Stores
		provider.bind(ContactStore.class, ContactStore.class, true);
		provider.bind(LocationStore.class, LocationStore.class, true);
		provider.bind(OrgUnitStore.class, OrgUnitStore.class, true);
		provider.bind(PositionStore.class, PositionStore.class, true);
		provider.bind(PositionTypeStore.class, PositionTypeStore.class, true);
		provider.bind(SystemCtrlStore.class, SystemCtrlStore.class, true);
		provider.bind(UnitTypeStore.class, UnitTypeStore.class, true);
		provider.bind(VersionCtrlStore.class, VersionCtrlStore.class, true);

	}

}
