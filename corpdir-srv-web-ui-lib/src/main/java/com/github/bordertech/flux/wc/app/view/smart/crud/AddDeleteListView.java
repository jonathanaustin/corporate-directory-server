package com.github.bordertech.flux.wc.app.view.smart.crud;

import com.github.bordertech.flux.event.ViewEventType;
import com.github.bordertech.flux.view.DefaultDumbView;
import com.github.bordertech.flux.view.DefaultSmartView;
import com.github.bordertech.flux.view.SmartView;
import com.github.bordertech.flux.wc.app.view.SelectSingleView;
import com.github.bordertech.flux.wc.app.view.SelectableView;
import com.github.bordertech.flux.wc.app.view.event.base.SelectableBaseViewEvent;
import com.github.bordertech.flux.wc.app.view.event.base.ToolbarBaseViewEvent;
import com.github.bordertech.flux.wc.app.view.form.FormUpdateable;
import com.github.bordertech.flux.wc.app.view.toolbar.AddDeleteButtonBarView;
import com.github.bordertech.flux.wc.app.view.toolbar.ApplyButtonBarView;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WDialog;
import com.github.bordertech.wcomponents.WDiv;
import com.github.bordertech.wcomponents.lib.util.FormUtil;
import java.io.Serializable;
import java.util.List;

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
			addEventAjaxTarget(findSelBar);
		}
	};
	private final WDiv dialogContent = new WDiv();
	private final WDialog dialog = new WDialog(dialogContent);
	private final AddDeleteButtonBarView addRemToolbar = new AddDeleteButtonBarView("vw-toolbar");

	// Add Select Button BAR to Bottom of Dialog View
	private final ApplyButtonBarView findSelBar = new ApplyButtonBarView("vw-select");
	private final SelectSingleView<T> selectView;
	private final SelectableView<T> findView;

	public AddDeleteListView(final String viewId, final SelectSingleView<T> selectView, final SelectableView<T> findView) {
		super(viewId, "wclib/hbs/layout/combo-add-rem.hbs");

		this.selectView = selectView;
		this.findView = findView;

		// Put the find view into dumb mode so we get the SELECT event
		if (findView instanceof SmartView) {
			SmartView smart = (SmartView) findView;
			smart.setDumbMode(true);
			smart.addPassThrough(ToolbarBaseViewEvent.APPLY);
		}

		// Bean Property
		setBeanProperty(".");
		setSearchAncestors(false);

		dialogView.getContent().add(dialog);
		dialog.setMode(WDialog.MODAL);
		dialog.setHeight(300);
		dialog.setWidth(600);

		// Setup dialog content
		dialogContent.add(findView);
		findSelBar.setContentVisible(false);
		// Add this as the AJAX button to the "Select Bar" so the dialog closes via AJAX
		findSelBar.addTarget(this);
		dialogContent.add(findSelBar);

		addComponentToTemplate("vw-select", selectView);
		addComponentToTemplate("vw-toolbar", addRemToolbar);
		addComponentToTemplate("vw-dialog", dialogView);

		findSelBar.addHtmlClass("wc-margin-n-lg");
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

		// Select View Events
		if (isView(viewId, selectView)) {
			if (isEvent(event, SelectableBaseViewEvent.SELECT)) {
				handleSelectEvent();
			}
			if (isEvent(event, SelectableBaseViewEvent.UNSELECT)) {
				handleUnselectEvent();
			}
			if (isEvent(event, ToolbarBaseViewEvent.DELETE)) {
				handleDeleteEvent();
			}
			if (isEvent(event, ToolbarBaseViewEvent.ADD)) {
				handleAddEvent();
			}

			// Find View Events
		} else if (isView(viewId, findView)) {
			if (isEvent(event, SelectableBaseViewEvent.SELECT)) {
				handleFindSelect((T) data);
			}
			if (isEvent(event, ToolbarBaseViewEvent.APPLY)) {
				handleFindSelectedItemEvent((T) data);
			}
		}
	}

	protected void handleAddEvent() {
		dialogView.resetView();
		dialogView.setContentVisible(true);
	}

	protected void handleSelectEvent() {
		addRemToolbar.showRemoveButton(true);
	}

	protected void handleUnselectEvent() {
		addRemToolbar.showRemoveButton(false);
	}

	protected void handleDeleteEvent() {
		T item = selectView.getSelectedItem();
		if (item != null) {
			// FIXME JA - Remove ITEM
//			dispatchEvent(CollectionEventType.REMOVE_ITEM, item);
		}
		addRemToolbar.resetView();
	}

	protected void handleFindSelect(final T item) {
		// Select EVENT - Show Select BAR
		findSelBar.getButton().setActionObject((Serializable) item);
		addRemToolbar.showRemoveButton(true);
	}

	protected void handleFindSelectedItemEvent(final T item) {
		List<T> beans = selectView.getViewBean();
		if (beans == null || !beans.contains(item)) {
			// Add Items
//			dispatchEvent(CollectionEventType.ADD_ITEM, item);
		}
		addRemToolbar.resetView();
		dialogView.resetView();
	}

}
