package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.Request;
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

	private final SelectView<T> selectView;
	private final SelectView findView;
	private final AddRemoveCtrl ctrl2 = new AddRemoveCtrl() {
		@Override
		public void setupListeners() {
			String findQual = findView.getFullQualifier();
			String qual = getFullQualifier();
			// Listen for the "SELECT" ITEMS and "ADD" to the list
			ctrl2.addListenerOverride(findQual + "-I", ListEventType.SELECT);
			ctrl2.addDispatcherOverride(qual, ListEventType.ADD_ITEM);
			super.setupListeners(); //To change body of generated methods, choose Tools | Templates.
		}

	};

	public AddRemoveListView(final SelectView<T> selectView, final SelectView findView) {
		super("wclib/hbs/layout/combo-add-rem.hbs");

		this.selectView = selectView;
		this.findView = findView;

		AddRemoveToolbar toolbarView = new AddRemoveToolbar();

		// Translate the "FIND" -> SELECT Event
		toolbarView.setDialogView(findView);

		WTemplate content = getContent();
		content.addTaggedComponent("vw-ctrl2", ctrl2);
		content.addTaggedComponent("vw-select", selectView);
		content.addTaggedComponent("vw-toolbar", toolbarView);
		setBlocking(true);

		setBeanProperty(".");
		setSearchAncestors(false);
	}

	@Override
	public void configViews() {
		super.configViews();
	}

	@Override
	protected void preparePaintComponent(Request request) {
		super.preparePaintComponent(request); //To change body of generated methods, choose Tools | Templates.
		String findQual = findView.getFullQualifier();
		String qual = getFullQualifier();
		// Make the "ITEM" Select in the find unique
		findView.addDispatcherOverride(findQual + "-I", ListEventType.SELECT);
	}

}
