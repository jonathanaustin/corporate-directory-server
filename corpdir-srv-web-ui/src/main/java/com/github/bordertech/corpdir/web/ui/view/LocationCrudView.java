package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.panel.LocationPanel;
import com.github.bordertech.wcomponents.lib.app.view.combo.DefaultCrudView;
import com.github.bordertech.wcomponents.lib.app.view.list.TreeSelectView;

/**
 * Location crud view.
 *
 * @author jonathan
 */
public class LocationCrudView extends DefaultCrudView {

	public LocationCrudView() {
		super("Location", new LocationPanel(), new TreeSelectView());
		setSearchModelKey("location.tree");
		setActionModelKey("location.action");
		TreeSelectView view = (TreeSelectView) getSelectView();
		view.setTreeModelKey("location.tree");
	}

}
