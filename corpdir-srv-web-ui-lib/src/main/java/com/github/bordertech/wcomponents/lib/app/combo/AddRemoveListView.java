package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.WDialog;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.ctrl.AddRemoveListCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.TranslateEventCtrl;
import com.github.bordertech.wcomponents.lib.app.form.FormUtil;
import com.github.bordertech.wcomponents.lib.app.toolbar.AddRemoveButtonBar;
import com.github.bordertech.wcomponents.lib.app.view.FormUpdateable;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;
import com.github.bordertech.wcomponents.lib.div.WDiv;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultComboView;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultView;

/**
 * ADD and REMOVE Toolbar.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AddRemoveListView<T> extends DefaultComboView<T> implements FormUpdateable {

	private final TranslateEventCtrl ctrl = new TranslateEventCtrl();

	private final DefaultView dialogView = new DefaultView() {
		@Override
		public void setContentVisible(final boolean visible) {
			super.setContentVisible(visible);
			if (visible) {
				showDialog();
			}
		}
	};
	private final WDiv dialogContent = new WDiv();
	private final WDialog dialog = new WDialog(dialogContent);
	private final AddRemoveButtonBar toolbarView = new AddRemoveButtonBar();

	public AddRemoveListView(final String qualifier, final SelectView<T> selectView, final SelectView<T> findView) {
		super("wclib/hbs/layout/combo-add-rem.hbs");

		dialog.setMode(WDialog.MODAL);

		// Setup qualifier context
		setQualifier(qualifier);
		setQualifierContext(true);
		ctrl.setQualifier("X");
		findView.setQualifier("X");
		// Make all the "Events" in FIND View unique with "X"
		findView.setQualifierContext(true);

		AddRemoveListCtrl addCtrl = new AddRemoveListCtrl();
		addCtrl.setAddRemoveToolbar(toolbarView);
		addCtrl.setAddView(dialogView);
		addCtrl.setSelectView(selectView);

		dialogView.getContent().add(dialog);
		dialogContent.add(findView);
		dialog.setMode(WDialog.MODAL);
		dialog.setHeight(300);
		dialog.setWidth(600);

		WTemplate content = getContent();
		content.addTaggedComponent("vw-ctrl2", ctrl);
		content.addTaggedComponent("vw-ctrl3", addCtrl);
		content.addTaggedComponent("vw-select", selectView);
		content.addTaggedComponent("vw-toolbar", toolbarView);
		content.addTaggedComponent("vw-dialog", dialogView);
		setBlocking(true);

		// Bean Property
		setBeanProperty(".");
		setSearchAncestors(false);
	}

	public WDialog getDialog() {
		return dialog;
	}

	@Override
	public void configViews() {
		super.configViews();
		// Translate the "SELECT" from the "FIND" to a "SELECTED"
//		ctrl.translate(ListEventType.SELECT, ToolbarEventType.SELECTED, getFullQualifier());
	}

	protected void showDialog() {
		dialogView.reset();
		dialog.display();
	}

	@Override
	public void doMakeFormReadonly(final boolean readonly) {
		FormUtil.doMakeInputsReadonly(this, readonly);
	}

}
