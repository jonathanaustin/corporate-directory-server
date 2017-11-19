package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.panel.LocationPanel;
import com.github.bordertech.flux.wc.app.view.smart.crud.DefaultCrudSmartView;
import com.github.bordertech.flux.wc.app.view.dumb.list.TreeSelectView;

/**
 * Location crud view.
 *
 * @author jonathan
 */
public class LocationCrudView extends DefaultCrudSmartView {

	public LocationCrudView() {
		super("Location", new LocationPanel(), new TreeSelectView());
		setSearchModelKey("location.tree");
		setActionModelKey("location.action");
		TreeSelectView view = (TreeSelectView) getSelectView();
		view.setTreeModelKey("location.tree");
	}

}
