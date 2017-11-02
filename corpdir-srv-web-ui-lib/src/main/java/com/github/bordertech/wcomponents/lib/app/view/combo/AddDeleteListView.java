package com.github.bordertech.wcomponents.lib.app.view.combo;

import com.github.bordertech.wcomponents.WDialog;
import com.github.bordertech.wcomponents.lib.app.view.form.FormUpdateable;
import com.github.bordertech.wcomponents.lib.app.view.SelectSingleView;
import com.github.bordertech.wcomponents.lib.app.view.SelectableView;
import com.github.bordertech.wcomponents.lib.app.view.toolbar.AddDeleteButtonBar;
import com.github.bordertech.wcomponents.lib.app.view.toolbar.SelectButtonBar;
import com.github.bordertech.wcomponents.WDiv;
import com.github.bordertech.flux.wc.view.DefaultDumbView;
import com.github.bordertech.flux.wc.view.DefaultSmartView;
import com.github.bordertech.wcomponents.lib.util.FormUtil;
import com.github.bordertech.wcomponents.Request;

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
			super.initViewContent(request); //To change body of generated methods, choose Tools | Templates.
			addEventAjaxTarget(selBar);
		}
	};
	private final WDiv dialogContent = new WDiv();
	private final WDialog dialog = new WDialog(dialogContent);
	private final AddDeleteButtonBar addRemToolbar = new AddDeleteButtonBar("vw-toolbar");

	// Add Select Button BAR to Bottom of Dialog View
	private final SelectButtonBar selBar = new SelectButtonBar("vw-select");

	public AddDeleteListView(final String viewId, final SelectSingleView<T> selectView, final SelectableView<T> findView) {
		super(viewId, "wclib/hbs/layout/combo-add-rem.hbs");

		// Bean Property
		setBeanProperty(".");
		setSearchAncestors(false);

//		setBlocking(true);
//		// Setup qualifier context
//		setQualifier(qualifier);
//		setQualifierContext(true);
//		// Make all the "Events" in DIALOG unique with "X"
//		transCtrl.setQualifier("X");
//		dialogView.setQualifier("X");
//		dialogView.setQualifierContext(true);
//		transCtrl.setMessageQualifier("X");
//		dialogView.setMessageQualifier("X");
//		dialogView.setMessageQualifierContext(true);
//		selBarCtrl.addView(selBar);
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

//		AddDeleteListCtrl addCtrl = new AddDeleteListCtrl();
//		addCtrl.setAddRemoveToolbar(addRemToolbar);
//		addCtrl.setAddView(dialogView);
//		addCtrl.setSelectView(selectView);
		addViewToTemplate(selectView);
		addViewToTemplate(addRemToolbar);
		addViewToTemplate(dialogView);

		selBar.addHtmlClass("wc-margin-n-lg");
		addRemToolbar.addHtmlClass("wc-margin-n-sm");

	}

	public WDialog getDialog() {
		return dialog;
	}

//	@Override
//	public void configViews() {
//		super.configViews();
//		// Translate the "SELECTED" from the "FIND" to a "SELECTED"
//		transCtrl.translate(ModelEventType.SELECTED, ModelEventType.SELECTED, getFullQualifier());
//	}
	protected void showDialog() {
		dialogView.reset();
		dialog.display();
	}

	@Override
	public void doMakeFormReadonly(final boolean readonly) {
		FormUtil.doMakeInputsReadonly(this, readonly);
	}

//			// Select EVENT - Show Select BAR
//			registerListener(SelectViewEvent.SELECT, new Listener() {
//				@Override
//				public void handleEvent(final Event event) {
//					selBar.getButton().setActionObject((Serializable) event.getData());
//					selBar.setContentVisible(true);
//				}
//			});
//			// Reset in the DIALOG
//			registerListener(NavigationViewEvent.RESET_VIEW, new Listener() {
//				@Override
//				public void handleEvent(final Event event) {
//					selBar.reset();
//				}
//			});
//		// ADD EVENT
//		registerListener(ModelEventType.ADD, new Listener() {
//			@Override
//			public void handleEvent(final Event event) {
//				handleAddEvent();
//			}
//		});
//
//		// Select EVENT
//		registerListener(SelectViewEvent.SELECT, new Listener() {
//			@Override
//			public void handleEvent(final Event event) {
//				handleSelectEvent();
//			}
//		});
//
//		// Unselect EVENT
//		registerListener(SelectViewEvent.UNSELECT, new Listener() {
//			@Override
//			public void handleEvent(final Event event) {
//				handleUnselectEvent();
//			}
//		});
//
//		// DELETE Event
//		registerListener(ModelEventType.DELETE, new Listener() {
//			@Override
//			public void handleEvent(final Event event) {
//				handleDeleteEvent();
//			}
//		});
//
//		// The ADD ITEM
//		registerListener(ModelEventType.SELECTED, new Listener() {
//			@Override
//			public void handleEvent(final Event event) {
//				handleSelectedItemEvent((T) event.getData());
//			}
//		});
//	protected void handleAddEvent() {
//		getAddView().resetView();
//		getAddView().setContentVisible(true);
//	}
//
//	protected void handleSelectEvent() {
//		getAddRemoveToolbar().showRemoveButton(true);
//	}
//
//	protected void handleUnselectEvent() {
//		getAddRemoveToolbar().showRemoveButton(false);
//	}
//
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
