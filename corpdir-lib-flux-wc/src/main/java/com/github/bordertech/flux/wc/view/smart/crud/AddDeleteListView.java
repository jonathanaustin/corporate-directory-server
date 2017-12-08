package com.github.bordertech.flux.wc.view.smart.crud;

import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.common.TemplateConstants;
import com.github.bordertech.flux.wc.view.DefaultDumbView;
import com.github.bordertech.flux.wc.view.DefaultSmartView;
import com.github.bordertech.flux.wc.view.FluxSmartView;
import com.github.bordertech.flux.wc.view.dumb.SelectSingleView;
import com.github.bordertech.flux.wc.view.dumb.SelectableView;
import com.github.bordertech.flux.wc.view.dumb.form.FormUpdateable;
import com.github.bordertech.flux.wc.view.dumb.form.FormUtil;
import com.github.bordertech.flux.wc.view.dumb.toolbar.AddDeleteButtonBarView;
import com.github.bordertech.flux.wc.view.dumb.toolbar.ApplyButtonBarView;
import com.github.bordertech.flux.wc.view.event.base.SelectBaseEventType;
import com.github.bordertech.flux.wc.view.event.base.ToolbarBaseEventType;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WDialog;
import com.github.bordertech.wcomponents.WDiv;
import java.io.Serializable;
import java.util.List;

/**
 * ADD and REMOVE Toolbar.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AddDeleteListView<T> extends DefaultSmartView<T> implements FormUpdateable {

	private final DefaultDumbView dialogView = new DefaultDumbView("vw_dialog") {
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
	private final AddDeleteButtonBarView addRemToolbar = new AddDeleteButtonBarView("vw_toolbar");

	// Add Select Button BAR to Bottom of Dialog View
	private final ApplyButtonBarView findSelBar = new ApplyButtonBarView("vw_select");
	private final SelectSingleView<T> selectView;
	private final SelectableView<T> findView;

	public AddDeleteListView(final String viewId, final SelectSingleView<T> selectView, final SelectableView<T> findView) {
		super(viewId, TemplateConstants.TEMPLATE_ADD_REM);

		this.selectView = selectView;
		this.findView = findView;

		// Put the find view into dumb mode so we get the SELECT event
		if (findView instanceof FluxSmartView) {
			FluxSmartView smart = (FluxSmartView) findView;
			smart.setDumbMode(true);
			smart.addPassThrough(ToolbarBaseEventType.APPLY);
		}

		// Bean Property
		setBeanProperty(".");
		setSearchAncestors(false);
		setAjaxContext(true);

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

		addComponentToTemplate(TemplateConstants.TAG_VW_SELECT, selectView);
		addComponentToTemplate(TemplateConstants.TAG_VW_TOOLBAR, addRemToolbar);
		addComponentToTemplate(TemplateConstants.TAG_VW_DIALOG, dialogView);

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
	protected void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
		super.handleViewEvent(viewId, event, data);

		// Select View Events
		if (isView(viewId, selectView)) {
			if (isEvent(event, SelectBaseEventType.SELECT)) {
				handleSelectEvent();
			}
			if (isEvent(event, SelectBaseEventType.UNSELECT)) {
				handleUnselectEvent();
			}
			if (isEvent(event, ToolbarBaseEventType.DELETE)) {
				handleDeleteEvent();
			}
			if (isEvent(event, ToolbarBaseEventType.ADD)) {
				handleAddEvent();
			}

			// Find View Events
		} else if (isView(viewId, findView)) {
			if (isEvent(event, SelectBaseEventType.SELECT)) {
				handleFindSelect((T) data);
			}
			if (isEvent(event, ToolbarBaseEventType.APPLY)) {
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
			selectView.removeItem(item);
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
			selectView.addItem(item);
		}
		addRemToolbar.resetView();
		dialogView.resetView();
	}

}
