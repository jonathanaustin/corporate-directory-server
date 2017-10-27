package com.github.bordertech.wcomponents.lib.app.view.combo;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.ctrl.CollectionMainCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.FormMainCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.FormSelectCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.FormToolbarCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.PollingCollectionCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.PollingSearchCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.ResetViewCtrl;
import com.github.bordertech.wcomponents.lib.app.model.keys.ActionModelKey;
import com.github.bordertech.wcomponents.lib.app.model.keys.SearchModelKey;
import com.github.bordertech.wcomponents.lib.app.view.FormToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.FormView;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.wcomponents.lib.app.view.SearchView;
import com.github.bordertech.wcomponents.lib.app.view.SelectSingleView;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.form.AbstractFormView;
import com.github.bordertech.wcomponents.lib.app.view.list.MenuSelectView;
import com.github.bordertech.wcomponents.lib.app.view.polling.DefaultPollingView;
import com.github.bordertech.wcomponents.lib.app.view.search.SearchTextView;
import com.github.bordertech.wcomponents.lib.app.view.toolbar.DefaultFormToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.toolbar.DefaultToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.toolbar.ToolbarModelEventItem;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultComboView;
import com.github.bordertech.wcomponents.lib.mvc.msg.DefaultMessageComboView;
import com.github.bordertech.wcomponents.lib.mvc.msg.DefaultMessageCtrl;
import com.github.bordertech.wcomponents.lib.mvc.msg.DefaultMessageView;
import com.github.bordertech.wcomponents.lib.mvc.msg.MessageCtrl;
import com.github.bordertech.wcomponents.lib.mvc.msg.MessageEventType;
import com.github.bordertech.wcomponents.lib.mvc.msg.MessageView;
import java.util.Collection;

/**
 * Default CRUD view.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 * @param <C> the collection type
 */
public class DefaultCrudView<S, T, C extends Collection<T>> extends DefaultMessageComboView<T> implements ActionModelKey, SearchModelKey {

	private final FormToolbarCtrl formToolbarCtrl = new FormToolbarCtrl();
	private final PollingSearchCtrl<S, T, C> pollingSearchCtrl = new PollingSearchCtrl();
	private final PollingCollectionCtrl<S, T, C> pollingListCtrl = new PollingCollectionCtrl();

	private final SearchView searchView;
	private final SelectSingleView<T, C> selectView;
	private final FormView<T> formView;
	private final FormToolbarView formToolbarView = new DefaultFormToolbarView();

	public DefaultCrudView(final String title, final WComponent panel) {
		this(title, null, null, null, panel);
	}

	public DefaultCrudView(final String title, final WComponent panel, final SelectSingleView<T, C> selectView2) {
		this(title, null, selectView2, null, panel);
	}

	public DefaultCrudView(final String title, final SearchView<S> criteriaView2, final SelectSingleView<T, C> selectView2, final FormView<T> formView2, final WComponent panel) {
		super("wclib/hbs/layout/combo-ent-crud.hbs");

		// Setup Defaults
		searchView = criteriaView2 == null ? new SearchTextView() : criteriaView2;
		selectView = selectView2 == null ? (SelectSingleView<T, C>) new MenuSelectView<T>() : selectView2;
		formView = formView2 == null ? new AbstractFormView<T>() : formView2;
		if (panel != null) {
			formView.getFormHolder().add(panel);
		}

		// Views
		PollingView<S, C> pollingView = new DefaultPollingView<>();
		ToolbarView toolbarView = new DefaultToolbarView();

		// Ctrls
		FormSelectCtrl<T, C> formSelectCtrl = new FormSelectCtrl<>(true);
		FormMainCtrl formCtrl = new FormMainCtrl();
		ResetViewCtrl resetCtrl = new ResetViewCtrl();
		CollectionMainCtrl listCtrl = new CollectionMainCtrl();

		// View messages needs to listen to other message event contexts
		getMessageCtrl().addMessageListenerQualifier("CM");
		getMessageCtrl().addMessageListenerQualifier("EM");

		// Search Messages
		MessageView searchMessages = new DefaultMessageView();
		MessageCtrl searchMessagesCtrl = new DefaultMessageCtrl();
		searchMessagesCtrl.setMessageView(searchMessages);
		searchMessagesCtrl.setMessageQualifier("CM");
		// Other views for this message context
		searchView.setMessageQualifier("CM");
		pollingView.setMessageQualifier("CM");
		listCtrl.setMessageQualifier("CM");
		pollingListCtrl.setMessageQualifier("CM");
		formSelectCtrl.setMessageQualifier("CM");

		// Form Messages
		MessageView formMessages = new DefaultMessageView();
		MessageCtrl formMessagesCtrl = new DefaultMessageCtrl();
		formMessagesCtrl.setMessageView(formMessages);
		formMessagesCtrl.setMessageQualifier("EM");
		// Other views for this message context
		formCtrl.setMessageQualifier("EM");
		formToolbarCtrl.setMessageQualifier("EM");
		formView.setMessageQualifier("EM");
		formToolbarView.setMessageQualifier("EM");

		getMessageCtrl().removeHandleMsgType(MessageEventType.ERROR);
		getMessageCtrl().removeHandleMsgType(MessageEventType.VALIDATION);
		searchMessagesCtrl.addValidationAndErrorMsgTypes();
		formMessagesCtrl.addValidationAndErrorMsgTypes();

		// Set views on the Ctrls
		formSelectCtrl.setSelectView(selectView);
		formCtrl.setFormView(formView);
		formToolbarCtrl.setToolbarView(formToolbarView);
		formToolbarCtrl.setFormView(formView);
		pollingSearchCtrl.setPollingView(pollingView);
		pollingListCtrl.setPollingView(pollingView);
		listCtrl.setCollectionView(selectView);

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
		content.addTaggedComponent("vw-ctrl8", formMessagesCtrl);
		content.addTaggedComponent("vw-ctrl9", formToolbarCtrl);
		content.addTaggedComponent("vw-toolbar-1", toolbarView);
		content.addTaggedComponent("vw-crit-msg", searchMessages);
		content.addTaggedComponent("vw-crit", searchView);
		content.addTaggedComponent("vw-poll", pollingView);
		content.addTaggedComponent("vw-list", selectView);
		content.addTaggedComponent("vw-form", formCombo);

		content.addParameter("vw-title", title);

		// Toolbar Defaults
		toolbarView.addToolbarItem(ToolbarModelEventItem.ADD);

		// Default visibility
		selectView.setContentVisible(false);
		formCombo.setContentVisible(false);

		setBlocking(true);

		setQualifierAndMessageQualifierContext(true);
	}

	public final SelectSingleView<T, C> getSelectView() {
		return selectView;
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
		pollingListCtrl.setRetrieveCollectionModelKey(key);
	}

	@Override
	public String getSearchModelKey() {
		return pollingListCtrl.getRetrieveCollectionModelKey();
	}

}
