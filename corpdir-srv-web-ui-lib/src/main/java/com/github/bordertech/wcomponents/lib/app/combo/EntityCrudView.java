package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.DefaultEntityToolbarView;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultMessageView;
import com.github.bordertech.wcomponents.lib.app.DefaultPollingView;
import com.github.bordertech.wcomponents.lib.app.DefaultToolbarView;
import com.github.bordertech.wcomponents.lib.app.ctrl.EntityWithSelectCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.EntityWithToolbarCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.ListWithCriteriaCtrl;
import com.github.bordertech.wcomponents.lib.mvc.impl.MessageCtrl;
import com.github.bordertech.wcomponents.lib.app.view.CriteriaView;
import com.github.bordertech.wcomponents.lib.app.view.EntityToolbarView;
import com.github.bordertech.wcomponents.lib.app.view.EntityView;
import com.github.bordertech.wcomponents.lib.app.view.SelectView;
import com.github.bordertech.wcomponents.lib.app.view.ToolbarView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultComboView;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultController;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class EntityCrudView<S, T> extends DefaultComboView {

	private final MessageCtrl messageCtrl;

	private final DefaultPollingView<S, List<T>> pollingView;
	private final EntityToolbarView entityToolbarView;
	private final ToolbarView toolbarView;

	public EntityCrudView(final Dispatcher dispatcher, final String qualifier, final CriteriaView<S> criteriaView, final SelectView<T> selectView, final EntityView<T> entityView) {
		super("wclib/hbs/layout/combo-ent-crud.hbs", dispatcher, qualifier);

		// Messages (default to show all)
		this.messageCtrl = new MessageCtrl(dispatcher, qualifier);
		messageCtrl.setMessageView(new DefaultMessageView(dispatcher, qualifier));

		// Views
		this.pollingView = new DefaultPollingView<>(dispatcher, qualifier);
		this.entityToolbarView = new DefaultEntityToolbarView(dispatcher, qualifier);
		this.toolbarView = new DefaultToolbarView(dispatcher, qualifier);

		// Ctrls
		DefaultController ctrl = new DefaultController(dispatcher, qualifier);
		EntityWithSelectCtrl<S, T> selectCtrl = new EntityWithSelectCtrl<>(dispatcher, qualifier);
		EntityWithToolbarCtrl entityToolbarCtrl = new EntityWithToolbarCtrl<>(dispatcher, qualifier);
		ListWithCriteriaCtrl<S, T> criteriaCtrl = new ListWithCriteriaCtrl<>(dispatcher, qualifier);

		// Set views on the Ctrls
		selectCtrl.setEntityView(entityView);
		selectCtrl.setSelectView(selectView);
		entityToolbarCtrl.setToolbarView(entityToolbarView);
		entityToolbarCtrl.setEntityView(entityView);
		criteriaCtrl.setCriteriaView(criteriaView);
		criteriaCtrl.setPollingView(pollingView);
		criteriaCtrl.setListView(selectView);

		WTemplate content = getContent();
		content.addTaggedComponent("vw-messages", messageCtrl.getMessageView());
		content.addTaggedComponent("vw-ctrl-msg", messageCtrl);
		content.addTaggedComponent("vw-ctrl", ctrl);
		content.addTaggedComponent("vw-ctrl1", selectCtrl);
		content.addTaggedComponent("vw-ctrl2", entityToolbarCtrl);
		content.addTaggedComponent("vw-ctrl3", criteriaCtrl);
		content.addTaggedComponent("vw-toolbar-1", toolbarView);
		content.addTaggedComponent("vw-toolbar-2", entityToolbarView);
		content.addTaggedComponent("vw-crit", criteriaView);
		content.addTaggedComponent("vw-poll", pollingView);
		content.addTaggedComponent("vw-list", selectView);
		content.addTaggedComponent("vw-entity", entityView);

		// Default visibility
		selectView.setContentVisible(false);
		entityView.setContentVisible(false);

	}

	public final MessageCtrl getMessageCtrl() {
		return messageCtrl;
	}

}
