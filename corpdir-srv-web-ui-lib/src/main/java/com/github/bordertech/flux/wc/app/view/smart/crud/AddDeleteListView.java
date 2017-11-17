package com.github.bordertech.flux.wc.app.view.smart.crud;

import com.github.bordertech.flux.event.ViewEventType;
import com.github.bordertech.flux.wc.app.view.SelectSingleView;
import com.github.bordertech.flux.wc.app.view.SelectableView;
import com.github.bordertech.flux.wc.app.view.form.FormUpdateable;
import com.github.bordertech.flux.wc.app.view.toolbar.AddDeleteButtonBarView;
import com.github.bordertech.flux.wc.app.view.toolbar.SelectButtonBarView;
import com.github.bordertech.flux.view.DefaultDumbView;
import com.github.bordertech.flux.view.DefaultSmartView;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WDialog;
import com.github.bordertech.wcomponents.WDiv;
import com.github.bordertech.wcomponents.lib.util.FormUtil;
import java.io.Serializable;

/**
 * ADD and REMOVE Toolbar.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AddDeleteListView<T> extends DefaultSmartView<T> implements FormUpdateable {

	private final DefaultDumbView dialogView = new DefaultDumbView("vw-dialog") {
		@Override
		public void setContentVisible(final boolean visible) {
			super.setContentVisible(visible);
			if (visible) {
				showDialog();
			}
		}

		@Override
		protected void initViewContent(Request request) {
			super.initViewContent(request);
			addEventAjaxTarget(selBar);
		}
	};
	private final WDiv dialogContent = new WDiv();
	private final WDialog dialog = new WDialog(dialogContent);
	private final AddDeleteButtonBarView addRemToolbar = new AddDeleteButtonBarView("vw-toolbar");

	// Add Select Button BAR to Bottom of Dialog View
	private final SelectButtonBarView selBar = new SelectButtonBarView("vw-select");

	public AddDeleteListView(final String viewId, final SelectSingleView<T> selectView, final SelectableView<T> findView) {
		super(viewId, "wclib/hbs/layout/combo-add-rem.hbs");

		// Bean Property
		setBeanProperty(".");
		setSearchAncestors(false);

		dialogView.getContent().add(dialog);
		dialog.setMode(WDialog.MODAL);
		dialog.setHeight(300);
		dialog.setWidth(600);

		// Setup dialog content
		dialogContent.add(findView);
		selBar.setContentVisible(false);
		// Add this as the AJAX button to the "Select Bar" so the dialog closes via AJAX
		selBar.addTarget(this);
		dialogContent.add(selBar);

		addComponentToTemplate("vw-select", selectView);
		addComponentToTemplate("vw-toolbar", addRemToolbar);
		addComponentToTemplate("vw-dialog", dialogView);

		selBar.addHtmlClass("wc-margin-n-lg");
		addRemToolbar.addHtmlClass("wc-margin-n-sm");

	}

	public WDialog getDialog() {
		return dialog;
	}

	protected void showDialog() {
		dialogView.reset();
		dialog.display();
	}

	@Override
	public void doMakeFormReadonly(final boolean readonly) {
		FormUtil.doMakeInputsReadonly(this, readonly);
	}

	@Override
	public void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
		super.handleViewEvent(viewId, event, data);

		// FIXME JA
// SelectViewEvent.SELECT
// NavigationViewEvent.RESET_VIEW
// ModelEventType.ADD
// SelectViewEvent.SELECT
// SelectViewEvent.UNSELECT
// ModelEventType.DELETE
// ModelEventType.SELECTED
	}

	protected void handleSelect(final T item) {
		// Select EVENT - Show Select BAR
		selBar.getButton().setActionObject((Serializable) item);
		selBar.setContentVisible(true);
		addRemToolbar.showRemoveButton(true);
	}

	protected void handleUnselect() {
	}

	// Reset in the DIALOG
	protected void handleReset() {
		selBar.reset();
	}

	protected void handleAddEvent() {
		addRemToolbar.resetView();
		addRemToolbar.setContentVisible(true);
	}

	protected void handleSelectEvent() {
		addRemToolbar.showRemoveButton(true);
	}

	protected void handleUnselectEvent() {
		addRemToolbar.showRemoveButton(false);
	}

//	protected void handleDeleteEvent() {
//		T item = getSelectView().getSelectedItem();
//		if (item != null) {
//			dispatchEvent(CollectionEventType.REMOVE_ITEM, item);
//		}
//		getAddRemoveToolbar().resetView();
//	}
//
//	protected void handleSelectedItemEvent(final T item) {
//		C beans = getSelectView().getViewBean();
//		if (beans == null || !beans.contains(item)) {
//			dispatchEvent(CollectionEventType.ADD_ITEM, item);
//		}
//		getAddRemoveToolbar().resetView();
//		getAddView().resetView();
//	}
}
