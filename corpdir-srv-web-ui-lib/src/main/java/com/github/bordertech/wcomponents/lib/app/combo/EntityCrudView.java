package com.github.bordertech.wcomponents.lib.app.combo;

import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.app.DefaultEntityToolbarView;
import com.github.bordertech.wcomponents.lib.app.DefaultPollingView;
import com.github.bordertech.wcomponents.lib.app.DefaultToolbarView;
import com.github.bordertech.wcomponents.lib.app.ctrl.EntityWithSelectCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.EntityWithToolbarCtrl;
import com.github.bordertech.wcomponents.lib.app.ctrl.ListWithCriteriaCtrl;
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
public class EntityCrudView<S, T> extends DefaultComboView implements MessageContainer {

	private final WMessages messages = new WMessages();

//	private final CriteriaView<S> criteriaView;
//	private final SelectView<T> selectView;
//	private final EntityView<T> entityView;
	private final DefaultPollingView<S, List<T>> pollingView;
	private final EntityToolbarView entityToolbarView;
	private final ToolbarView toolbarView;

	private final DefaultController ctrl;
	private final EntityWithSelectCtrl<S, T> selectCtrl;
	private final EntityWithToolbarCtrl entityToolbarCtrl;
	private final ListWithCriteriaCtrl<S, T> criteriaCtrl;

	public EntityCrudView(final Dispatcher dispatcher, final String qualifier, final CriteriaView<S> criteriaView, final SelectView<T> selectView, final EntityView<T> entityView) {
		super("wclib/hbs/layout/combo-ent-crud.hbs", dispatcher, qualifier);

		// Views
//		this.criteriaView = criteriaView;
//		this.selectView = selectView;
//		this.entityView = entityView;
		this.pollingView = new DefaultPollingView<>(dispatcher, qualifier);
		this.entityToolbarView = new DefaultEntityToolbarView(dispatcher, qualifier);
		this.toolbarView = new DefaultToolbarView(dispatcher, qualifier);

		// Ctrls
		this.ctrl = new DefaultController(dispatcher, qualifier);
		this.selectCtrl = new EntityWithSelectCtrl<>(dispatcher, qualifier);
		this.entityToolbarCtrl = new EntityWithToolbarCtrl<>(dispatcher, qualifier);
		this.criteriaCtrl = new ListWithCriteriaCtrl<>(dispatcher, qualifier);

		// Set views on the Ctrls
		selectCtrl.setEntityView(entityView);
		selectCtrl.setSelectView(selectView);
		entityToolbarCtrl.setToolbarView(entityToolbarView);
		entityToolbarCtrl.setEntityView(entityView);
		criteriaCtrl.setCriteriaView(criteriaView);
		criteriaCtrl.setPollingView(pollingView);
		criteriaCtrl.setListView(selectView);

		WTemplate content = getContent();
		content.addTaggedComponent("vw-messages", messages);
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

	@Override
	public WMessages getMessages() {
		return messages;
	}

}
