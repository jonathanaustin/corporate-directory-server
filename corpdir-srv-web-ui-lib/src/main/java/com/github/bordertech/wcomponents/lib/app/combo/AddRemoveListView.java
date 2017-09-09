package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.AddRemoveToolbar;
import com.github.bordertech.wcomponents.lib.app.ctrl.TranslateEventCtrl;
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

	private final TranslateEventCtrl ctrl = new TranslateEventCtrl();

	public AddRemoveListView(final String qualifier, final SelectView<T> selectView, final SelectView findView) {
		super("wclib/hbs/layout/combo-add-rem.hbs");

		// Setup qualifier context
		setQualifier(qualifier);
		setQualifierContext(true);
		ctrl.setQualifier("X");
		findView.setQualifier("X");
		// Make all the "Events" in FIND View unique with "X"
		findView.setQualifierContext(true);

		AddRemoveToolbar toolbarView = new AddRemoveToolbar();

		// Translate the "FIND" -> SELECT Event
		toolbarView.setDialogView(findView);

		WTemplate content = getContent();
		content.addTaggedComponent("vw-ctrl2", ctrl);
		content.addTaggedComponent("vw-select", selectView);
		content.addTaggedComponent("vw-toolbar", toolbarView);
		setBlocking(true);

		// Bean Property
		setBeanProperty(".");
		setSearchAncestors(false);
	}

	@Override
	public void configViews() {
		super.configViews();
		// Translate the "SELECT" from the "FIND" to an "ADD"
		ctrl.translate(ListEventType.SELECT, ListEventType.ADD_ITEM, getFullQualifier());
	}

}
