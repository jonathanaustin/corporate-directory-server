package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.AddRemoveToolbar;
import com.github.bordertech.wcomponents.lib.app.ctrl.AddRemoveCtrl;
import com.github.bordertech.wcomponents.lib.app.event.ListEventType;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultComboView;

/**
 * ADD and REMOVE Toolbar.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AddRemoveListView<S, T> extends DefaultComboView<T> {

	public AddRemoveListView(final SelectView<T> selectView, final SelectView findView, final String qualifier) {
		super("wclib/hbs/layout/combo-add-rem.hbs", qualifier);

		AddRemoveToolbar toolbarView = new AddRemoveToolbar(qualifier);

//		FormAndSelectCtrl ctrl = new FormAndSelectCtrl(qualifier);
//		ctrl.setSelectView(selectView);
//		ctrl.setTargetView(toolbarView);
		String qual = findView.getQualifier();

		// Translate the "FIND" -> SELECT Event
		AddRemoveCtrl ctrl2 = new AddRemoveCtrl(qual);
		findView.addDispatcherOverride(qual + "-I", ListEventType.SELECT);
		ctrl2.addListenerOverride(qual + "-I", ListEventType.SELECT);
		ctrl2.addDispatcherOverride(qualifier, ListEventType.ADD_ITEM);

		toolbarView.setDialogView(findView);

		WTemplate content = getContent();
//		content.addTaggedComponent("vw-ctrl1", ctrl);
		content.addTaggedComponent("vw-ctrl2", ctrl2);
		content.addTaggedComponent("vw-select", selectView);
		content.addTaggedComponent("vw-toolbar", toolbarView);
		setBlocking(true);

		setBeanProperty(".");
		setSearchAncestors(false);
	}

}
