package com.github.bordertech.wcomponents.lib.app.view.combo;

import com.github.bordertech.wcomponents.WDialog;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.ctrl.AddDeleteListCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.TranslateEventCtrl;
import com.github.bordertech.wcomponents.lib.app.event.ModelEventType;
import com.github.bordertech.wcomponents.lib.app.event.NavigationEventType;
import com.github.bordertech.wcomponents.lib.app.event.SelectEventType;
import com.github.bordertech.wcomponents.lib.app.view.form.FormUpdateable;
import com.github.bordertech.wcomponents.lib.app.view.SelectSingleView;
import com.github.bordertech.wcomponents.lib.app.view.SelectableView;
import com.github.bordertech.wcomponents.lib.app.view.toolbar.AddDeleteButtonBar;
import com.github.bordertech.wcomponents.lib.app.view.toolbar.SelectButtonBar;
import com.github.bordertech.wcomponents.WDiv;
import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultComboView;
import com.github.bordertech.flux.wc.AbstractStore;
import com.github.bordertech.wcomponents.lib.util.FormUtil;
import java.io.Serializable;
import java.util.Collection;
import com.github.bordertech.flux.wc.view.AppView;

/**
 * ADD and REMOVE Toolbar.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AddDeleteListView<T, C extends Collection<T>> extends DefaultComboView<T> implements FormUpdateable {

	private final TranslateEventCtrl transCtrl = new TranslateEventCtrl();
	private final DefaultComboView dialogView = new DefaultComboView() {
		@Override
		public void setContentVisible(final boolean visible) {
			super.setContentVisible(visible);
			if (visible) {
				showDialog();
			}
		}

		@Override
		public void configAjax(final AppView view) {
			super.configAjax(view);
			view.addEventAjaxTarget(selBar);
		}

	};
	private final WDiv dialogContent = new WDiv();
	private final WDialog dialog = new WDialog(dialogContent);
	private final AddDeleteButtonBar addRemToolbar = new AddDeleteButtonBar();

	// Add Select Button BAR to Bottom of Dialog View
	private final SelectButtonBar selBar = new SelectButtonBar();

	// Controller for the Select Bar
	private final AbstractStore selBarCtrl = new AbstractStore() {
		@Override
		public void setupController() {
			super.setupController();
			// Select EVENT - Show Select BAR
			registerListener(SelectEventType.SELECT, new Listener() {
				@Override
				public void handleEvent(final Event event) {
					selBar.getButton().setActionObject((Serializable) event.getData());
					selBar.setContentVisible(true);
				}
			});
			// Reset in the DIALOG
			registerListener(NavigationEventType.RESET_VIEW, new Listener() {
				@Override
				public void handleEvent(final Event event) {
					selBar.reset();
				}
			});
		}
	};

	public AddDeleteListView(final String qualifier, final SelectSingleView<T, C> selectView, final SelectableView<T, C> findView) {
		super("wclib/hbs/layout/combo-add-rem.hbs");

		// Setup qualifier context
		setQualifier(qualifier);
		setQualifierContext(true);

		// Make all the "Events" in DIALOG unique with "X"
		transCtrl.setQualifier("X");
		dialogView.setQualifier("X");
		dialogView.setQualifierContext(true);

		transCtrl.setMessageQualifier("X");
		dialogView.setMessageQualifier("X");
		dialogView.setMessageQualifierContext(true);

		dialogView.getContent().addTaggedComponent("vw-content", dialog);
		dialogView.getContent().addTaggedComponent("vw-ctrl1", selBarCtrl);
		dialog.setMode(WDialog.MODAL);
		dialog.setHeight(300);
		dialog.setWidth(600);
		selBarCtrl.addView(selBar);

		// Setup dialog content
		dialogContent.add(findView);
		selBar.setContentVisible(false);
		// Add this as the AJAX button to the "Select Bar" so the dialog closes via AJAX
		selBar.addTarget(this);
		dialogContent.add(selBar);

		AddDeleteListCtrl addCtrl = new AddDeleteListCtrl();
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

		selBar.addHtmlClass("wc-margin-n-lg");
		addRemToolbar.addHtmlClass("wc-margin-n-sm");

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
		// Translate the "SELECTED" from the "FIND" to a "SELECTED"
		transCtrl.translate(ModelEventType.SELECTED, ModelEventType.SELECTED, getFullQualifier());
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
