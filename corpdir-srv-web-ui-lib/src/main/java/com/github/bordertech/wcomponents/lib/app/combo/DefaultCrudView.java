package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.DefaultFormToolbarView;
import com.github.bordertech.wcomponents.lib.app.DefaultPollingView;
import com.github.bordertech.wcomponents.lib.app.DefaultToolbarView;
import com.github.bordertech.wcomponents.lib.app.ctrl.FormWithSelectCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.FormWithToolbarCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.ListWithCriteriaCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.ResetViewCtrl;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.app.view.FormToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.FormView;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultComboView;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultController;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultMessageView;
import com.github.bordertech.wcomponents.lib.mvc.impl.MessageCtrl;
import java.util.List;

/**
 * Default CRUD view.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the entity type
 */
public class DefaultCrudView<S, T> extends DefaultComboView {

	private final MessageCtrl messageCtrl;

	private final PollingView<S, List<T>> pollingView;
	private final FormToolbarView formToolbarView;
	private final ToolbarView toolbarView;

	public DefaultCrudView(final Dispatcher dispatcher, final String qualifier, final CriteriaView<S> criteriaView, final SelectView<T> selectView, final FormView<T> formView) {
		super("wclib/hbs/layout/combo-ent-crud.hbs", dispatcher, qualifier);

		// Messages (default to show all)
		this.messageCtrl = new MessageCtrl(dispatcher, qualifier);
		messageCtrl.setMessageView(new DefaultMessageView(dispatcher, qualifier));

		// Views
		this.pollingView = new DefaultPollingView<>(dispatcher, qualifier);
		this.formToolbarView = new DefaultFormToolbarView(dispatcher, qualifier);
		this.toolbarView = new DefaultToolbarView(dispatcher, qualifier);

		// Ctrls
		DefaultController ctrl = new DefaultController(dispatcher, qualifier);
		FormWithSelectCtrl<S, T> selectCtrl = new FormWithSelectCtrl<>(dispatcher, qualifier);
		FormWithToolbarCtrl entityToolbarCtrl = new FormWithToolbarCtrl<>(dispatcher, qualifier);
		ListWithCriteriaCtrl<S, T> criteriaCtrl = new ListWithCriteriaCtrl<>(dispatcher, qualifier);
		ResetViewCtrl resetCtrl = new ResetViewCtrl(dispatcher, qualifier);

		// Set views on the Ctrls
		selectCtrl.setFormView(formView);
		selectCtrl.setSelectView(selectView);
		entityToolbarCtrl.setToolbarView(formToolbarView);
		entityToolbarCtrl.setFormView(formView);
		criteriaCtrl.setCriteriaView(criteriaView);
		criteriaCtrl.setPollingView(pollingView);
		criteriaCtrl.setListView(selectView);

		WTemplate content = getContent();
		content.addTaggedComponent("vw-messages", messageCtrl.getMessageView());
		content.addTaggedComponent("vw-ctrl-msg", messageCtrl);
		content.addTaggedComponent("vw-ctrl-res", resetCtrl);
		content.addTaggedComponent("vw-ctrl", ctrl);
		content.addTaggedComponent("vw-ctrl1", selectCtrl);
		content.addTaggedComponent("vw-ctrl2", entityToolbarCtrl);
		content.addTaggedComponent("vw-ctrl3", criteriaCtrl);
		content.addTaggedComponent("vw-toolbar-1", toolbarView);
		content.addTaggedComponent("vw-toolbar-2", formToolbarView);
		content.addTaggedComponent("vw-crit", criteriaView);
		content.addTaggedComponent("vw-poll", pollingView);
		content.addTaggedComponent("vw-list", selectView);
		content.addTaggedComponent("vw-entity", formView);

		// Default visibility
		selectView.setContentVisible(false);
		formView.setContentVisible(false);

	}

	public final MessageCtrl getMessageCtrl() {
		return messageCtrl;
	}

}
