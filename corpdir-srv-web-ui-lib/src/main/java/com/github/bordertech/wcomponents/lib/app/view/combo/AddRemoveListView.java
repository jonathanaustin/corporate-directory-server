package com.github.bordertech.wcomponents.lib.app.view.combo;

import com.github.bordertech.wcomponents.WDialog;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.ctrl.AddRemoveListCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.TranslateEventCtrl;
import com.github.bordertech.wcomponents.lib.app.event.ListEventType;
import com.github.bordertech.wcomponents.lib.app.event.ToolbarEventType;
import com.github.bordertech.wcomponents.lib.app.view.FormUpdateable;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;
import com.github.bordertech.wcomponents.lib.app.view.bar.AddRemoveButtonBar;
import com.github.bordertech.wcomponents.lib.app.view.bar.SelectButtonBar;
import com.github.bordertech.wcomponents.lib.app.view.form.FormUtil;
import com.github.bordertech.wcomponents.lib.div.WDiv;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.mvc.View;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultComboView;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultController;
import java.io.Serializable;

/**
 * ADD and REMOVE Toolbar.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AddRemoveListView<T> extends DefaultComboView<T> implements FormUpdateable {

	private final TranslateEventCtrl transCtrl = new TranslateEventCtrl();
	private final SelectView<T> findView;
	private final DefaultComboView dialogView = new DefaultComboView("wclib/hbs/layout/default-view.hbs") {
		@Override
		public void setContentVisible(final boolean visible) {
			super.setContentVisible(visible);
			if (visible) {
				showDialog();
			}
		}

		@Override
		public void configAjax(final View view) {
			super.configAjax(view);
			view.addEventTarget(selBar);
		}

	};
	private final WDiv dialogContent = new WDiv();
	private final WDialog dialog = new WDialog(dialogContent);
	private final AddRemoveButtonBar addRemToolbar = new AddRemoveButtonBar();

	// Add Select Button BAR to Bottom of View
	private final SelectButtonBar selBar = new SelectButtonBar();

	private final DefaultController selBarCtrl = new DefaultController() {
		@Override
		public void setupController() {
			super.setupController();
			// Select EVENT - Show Select BAR
			registerListener(ListEventType.SELECT, new Listener() {
				@Override
				public void handleEvent(final Event event) {
					selBar.getButton().setActionObject((Serializable) findView.getSelectedItem());
					selBar.setContentVisible(true);
				}
			});
			// Reset in the DIALOG
			registerListener(ToolbarEventType.RESET_VIEW, new Listener() {
				@Override
				public void handleEvent(final Event event) {
					selBar.reset();
				}
			});
		}
	};

	public AddRemoveListView(final String qualifier, final SelectView<T> selectView, final SelectView<T> findView) {
		super("wclib/hbs/layout/combo-add-rem.hbs");

		this.findView = findView;

		// Setup qualifier context
		setQualifier(qualifier);
		setQualifierContext(true);

		// Make all the "Events" in DIALOG unique with "X"
		transCtrl.setQualifier("X");
		dialogView.setQualifier("X");
		dialogView.setQualifierContext(true);
		dialogView.getContent().addTaggedComponent("vw-content", dialog);
		dialogView.getContent().addTaggedComponent("vw-ctrl1", selBarCtrl);
		dialog.setMode(WDialog.MODAL);
		dialog.setHeight(300);
		dialog.setWidth(600);
		selBarCtrl.addView(selBar);

		dialogContent.add(findView);
		selBar.setContentVisible(false);
		selBar.addTarget(this);
		dialogContent.add(selBar);

		AddRemoveListCtrl addCtrl = new AddRemoveListCtrl();
		addCtrl.setAddRemoveToolbar(addRemToolbar);
		addCtrl.setAddView(dialogView);
		addCtrl.setSelectView(selectView);

		WTemplate content = getContent();
		content.addTaggedComponent("vw-ctrl3", addCtrl);
		content.addTaggedComponent("vw-ctrl4", transCtrl);
		content.addTaggedComponent("vw-select", selectView);
		content.addTaggedComponent("vw-toolbar", addRemToolbar);
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
		transCtrl.translate(ToolbarEventType.SELECTED, ToolbarEventType.SELECTED, getFullQualifier());
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
