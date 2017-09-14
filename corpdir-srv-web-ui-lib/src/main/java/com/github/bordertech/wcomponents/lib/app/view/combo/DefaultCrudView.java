package com.github.bordertech.wcomponents.lib.app.view.combo;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.ctrl.FormMainCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.FormSelectCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.FormToolbarCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.ListMainCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.PollingListCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.PollingSearchCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.ResetViewCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.TranslateEventCtrl;
import com.github.bordertech.wcomponents.lib.app.model.ActionModelKey;
import com.github.bordertech.wcomponents.lib.app.model.SearchModelKey;
import com.github.bordertech.wcomponents.lib.app.view.FormToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.FormView;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.wcomponents.lib.app.view.SearchView;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.form.AbstractFormView;
import com.github.bordertech.wcomponents.lib.app.view.list.SelectMenuView;
import com.github.bordertech.wcomponents.lib.app.view.polling.DefaultPollingView;
import com.github.bordertech.wcomponents.lib.app.view.search.SearchTextView;
import com.github.bordertech.wcomponents.lib.app.view.toolbar.DefaultFormToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.toolbar.DefaultToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.toolbar.ToolbarModelItem;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultComboView;
import com.github.bordertech.wcomponents.lib.mvc.msg.DefaultMessageComboView;
import com.github.bordertech.wcomponents.lib.mvc.msg.DefaultMessageCtrl;
import com.github.bordertech.wcomponents.lib.mvc.msg.DefaultMessageView;
import com.github.bordertech.wcomponents.lib.mvc.msg.MessageCtrl;
import com.github.bordertech.wcomponents.lib.mvc.msg.MessageView;
import com.github.bordertech.wcomponents.lib.mvc.msg.MsgEventType;
import java.util.List;

/**
 * Default CRUD view.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 */
public class DefaultCrudView<S, T> extends DefaultMessageComboView<T> implements ActionModelKey, SearchModelKey {

	private final FormToolbarCtrl formToolbarCtrl = new FormToolbarCtrl();
	private final PollingSearchCtrl<S, T> pollingSearchCtrl = new PollingSearchCtrl();
	private final PollingListCtrl<S, T> pollingListCtrl = new PollingListCtrl();

	private final SearchView searchView;
	private final FormView<T> formView;
	private final FormToolbarView formToolbarView = new DefaultFormToolbarView();

	private final TranslateEventCtrl msgTransSearch = new TranslateEventCtrl() {
		@Override
		public void setupController() {
			super.setupController();
			translateMessage(DefaultCrudView.this.getFullQualifier());
		}
	};
	private final TranslateEventCtrl msgTransForm = new TranslateEventCtrl() {
		@Override
		public void setupController() {
			super.setupController();
			translateMessage(DefaultCrudView.this.getFullQualifier());
		}
	};

	public DefaultCrudView(final String title, final WComponent panel) {
		this(title, null, null, null, panel);
	}

	public DefaultCrudView(final String title, final SearchView<S> criteriaView2, final SelectView<T> selectView2, final FormView<T> formView2, final WComponent panel) {
		super("wclib/hbs/layout/combo-ent-crud.hbs");

		// Setup Defaults
		searchView = criteriaView2 == null ? new SearchTextView() : criteriaView2;
		SelectView<T> selectView = selectView2 == null ? new SelectMenuView<T>() : selectView2;
		formView = formView2 == null ? new AbstractFormView<T>() : formView2;
		if (panel != null) {
			formView.getFormHolder().add(panel);
		}

		// Views
		PollingView<S, List<T>> pollingView = new DefaultPollingView<>();
		ToolbarView toolbarView = new DefaultToolbarView();

		// Ctrls
		FormSelectCtrl<T> formSelectCtrl = new FormSelectCtrl<>();
		FormMainCtrl formCtrl = new FormMainCtrl();
		ResetViewCtrl resetCtrl = new ResetViewCtrl();
		ListMainCtrl listCtrl = new ListMainCtrl();

		// Search Messages
		MessageView searchMessages = new DefaultMessageView();
		MessageCtrl searchMessagesCtrl = new DefaultMessageCtrl();
		searchMessagesCtrl.setMessageView(searchMessages);
		searchMessagesCtrl.setQualifier("CM");
		msgTransSearch.setQualifier("CM");

		searchView.addDispatcherOverride("CM", MsgEventType.values());
		pollingView.addDispatcherOverride("CM", MsgEventType.values());
		selectView.addDispatcherOverride("CM", MsgEventType.values());
		listCtrl.addDispatcherOverride("CM", MsgEventType.values());
		pollingListCtrl.addDispatcherOverride("CM", MsgEventType.values());
		formSelectCtrl.addDispatcherOverride("CM", MsgEventType.values());

		// Form Messages
		MessageView formMessages = new DefaultMessageView();
		MessageCtrl formMessagesCtrl = new DefaultMessageCtrl();
		formMessagesCtrl.setMessageView(formMessages);
		formMessagesCtrl.setQualifier("EM");
		formCtrl.addDispatcherOverride("EM", MsgEventType.values());
		formToolbarCtrl.addDispatcherOverride("EM", MsgEventType.values());
		formView.addDispatcherOverride("EM", MsgEventType.values());
		formToolbarView.addDispatcherOverride("EM", MsgEventType.values());
		msgTransForm.setQualifier("EM");

		getMessageCtrl().removeHandleMsgType(MsgEventType.ERROR);
		getMessageCtrl().removeHandleMsgType(MsgEventType.VALIDATION);
		searchMessagesCtrl.addValidationAndErrorMsgTypes();
		formMessagesCtrl.addValidationAndErrorMsgTypes();

		// Set views on the Ctrls
		formSelectCtrl.setSelectView(selectView);
		formCtrl.setFormView(formView);
		formToolbarCtrl.setToolbarView(formToolbarView);
		formToolbarCtrl.setFormView(formView);
		pollingSearchCtrl.setPollingView(pollingView);
		pollingListCtrl.setPollingView(pollingView);
		listCtrl.setListView(selectView);

		// Add message views so they get refreshed
		formSelectCtrl.addView(searchMessages);
		formSelectCtrl.addView(formMessages);
		formCtrl.addView(formMessages);

		// Form Holder
		DefaultComboView formCombo = new DefaultComboView("wclib/hbs/layout/combo-ent-crud-form.hbs");
		formCombo.addHtmlClass("wc-panel-type-box");

		WTemplate formContent = formCombo.getContent();
		formContent.addTaggedComponent("vw-form-toolbar", formToolbarView);
		formContent.addTaggedComponent("vw-form-msg", formMessages);
		formContent.addTaggedComponent("vw-form", formView);

		formCtrl.addGroupFormView(formCombo);

		WTemplate content = getContent();
		content.addTaggedComponent("vw-ctrl1", formSelectCtrl);
		content.addTaggedComponent("vw-ctrl2", pollingSearchCtrl);
		content.addTaggedComponent("vw-ctrl3", resetCtrl);
		content.addTaggedComponent("vw-ctrl4", listCtrl);
		content.addTaggedComponent("vw-ctrl5", pollingListCtrl);
		content.addTaggedComponent("vw-ctrl6", searchMessagesCtrl);
		content.addTaggedComponent("vw-ctrl7", formCtrl);
		content.addTaggedComponent("vw-ctrl8", msgTransSearch);
		content.addTaggedComponent("vw-ctrl9", msgTransForm);
		content.addTaggedComponent("vw-ctrl10", formMessagesCtrl);
		content.addTaggedComponent("vw-ctrl11", formToolbarCtrl);
		content.addTaggedComponent("vw-toolbar-1", toolbarView);
		content.addTaggedComponent("vw-crit-msg", searchMessages);
		content.addTaggedComponent("vw-crit", searchView);
		content.addTaggedComponent("vw-poll", pollingView);
		content.addTaggedComponent("vw-list", selectView);
		content.addTaggedComponent("vw-form", formCombo);

		content.addParameter("vw-title", title);

		// Toolbar Defaults
		toolbarView.addToolbarItem(ToolbarModelItem.ADD);

		// Default visibility
		selectView.setContentVisible(false);
		formCombo.setContentVisible(false);

		// Margins
		setBlocking(true);
	}

	@Override
	public void setActionModelKey(final String key) {
		formToolbarCtrl.setActionModelKey(key);
	}

	@Override
	public String getActionModelKey() {
		return formToolbarCtrl.getActionModelKey();
	}

	@Override
	public void setSearchModelKey(final String key) {
		pollingListCtrl.setSearchModelKey(key);
	}

	@Override
	public String getSearchModelKey() {
		return pollingListCtrl.getSearchModelKey();
	}

}
