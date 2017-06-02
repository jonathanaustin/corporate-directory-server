package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.corpdir.web.ui.common.ActionMode;
import com.github.bordertech.corpdir.web.ui.common.RecordAction;
import com.github.bordertech.corpdir.web.ui.polling.AbstractPollingPanel;
import com.github.bordertech.corpdir.web.ui.polling.PollingController;
import com.github.bordertech.corpdir.web.ui.polling.PollingServiceException;
import com.github.bordertech.corpdir.web.ui.polling.PollingStatus;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WSection;
import com.github.bordertech.wcomponents.WebUtilities;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Abstract action view.
 *
 * @author Jonathan Austin
 * @param <T>
 * @since 1.0.0
 */
public abstract class AbstractActionView<T extends ApiKeyIdObject> extends WSection implements ActionView<T> {

	private static final Log LOG = LogFactory.getLog(AbstractActionView.class);

	private final WMessages messages = new WMessages();

	private final ActionMenu actionMenu;

	private final PollingController<T, String> pollingServicePanel = new AbstractPollingPanel<T, String>(173) {
		@Override
		public T doServiceCall(final String recordId) throws PollingServiceException {
			try {
				T bean = doRetrieveServiceCall(recordId);
				return bean;
			} catch (Exception e) {
				throw new PollingServiceException("Error retrieving record. " + e.getMessage(), e);
			}
		}

		@Override
		protected void handleStoppedPolling() {
			super.handleStoppedPolling();
			if (getPollingStatus() == PollingStatus.COMPLETE) {
				T bean = getServiceResponse();
				setApiBean(bean);
			}
		}
	};

	private final DetailView<T> detailView;

	/**
	 * @param title the view title
	 * @param detailView the detail panel
	 */
	public AbstractActionView(final String title, final DetailView<T> detailView) {
		super(title);
		this.detailView = detailView;
		this.actionMenu = new ActionMenu(this);

		WPanel content = getContent();
		content.add(actionMenu);
		content.add(messages);
		content.add(detailView);
		content.add(pollingServicePanel);

		// Default Visibility
		pollingServicePanel.setVisible(false);
		detailView.setVisible(false);

		// AJAX Target for menu items
		actionMenu.addAjaxTarget(content);
	}

	@Override
	public void load(final String id) {
		reset();
		pollingServicePanel.setVisible(true);
		pollingServicePanel.setRecordId(id);
	}

	@Override
	public void setApiBean(final T bean) {
		reset();
		detailView.setApiBean(bean);
		detailView.setVisible(true);
		if (bean.getId() == null) {
			setActionMode(ActionMode.Create);
		}
	}

	/**
	 * @return the bean being processed
	 */
	@Override
	public T getApiBean() {
		return detailView.getApiBean();
	}

	@Override
	public void addActionAjaxTarget(final AjaxTarget target) {
		actionMenu.addAjaxTarget(target);
	}

	protected void setActionMode(final ActionMode actionMode) {
		getOrCreateComponentModel().actionMode = actionMode;
	}

	@Override
	public ActionMode getActionMode() {
		return getComponentModel().actionMode;
	}

	@Override
	public void handleAction(final RecordAction action) {
		switch (action) {
			case Back:
				handleBack();
				break;
			case Cancel:
				handleCancel();
				break;
			case Delete:
				handleDelete();
				break;
			case Edit:
				handleEdit();
				break;
			case Refresh:
				handleRefresh();
				break;
			case Save:
				handleSave();
				break;
			default:
			// Nothing
		}
	}

	@Override
	public void setEventAction(final Action action, final RecordAction event) {
		ActionViewModel model = getOrCreateComponentModel();
		if (model.eventActions == null) {
			model.eventActions = new HashMap<>();
		}
		if (action == null) {
			model.eventActions.remove(event);
		} else {
			model.eventActions.put(event, action);
		}
	}

	@Override
	public Action getEventAction(final RecordAction event) {
		ActionViewModel model = getComponentModel();
		if (model.eventActions != null) {
			return model.eventActions.get(event);
		}
		return null;
	}

	protected void handleBack() {
		handleEventAction(RecordAction.Back, null);
	}

	protected void handleRefresh() {
		String id = getApiBean().getId();
		load(id);
		handleEventAction(RecordAction.Refresh, id);
	}

	protected void handleEdit() {
		detailView.setFormReadOnly(false);
		setActionMode(ActionMode.Edit);
		handleEventAction(RecordAction.Edit, getApiBean());
	}

	protected void handleCancel() {
		String id = getApiBean().getId();
		load(id);
		handleEventAction(RecordAction.Cancel, id);
	}

	protected void handleSave() {
		doUpdateDetailBean();
		T bean = getApiBean();
		T updated;
		try {
			updated = doSaveServiceCall(bean);
		} catch (Exception e) {
			String msg = "Error calling save service. " + e.getMessage();
			LOG.error(msg, e);
			getMessages().error(msg);
			return;
		}
		setApiBean(updated);
		handleEventAction(RecordAction.Save, updated);
	}

	protected void handleDelete() {
		T bean = getApiBean();
		try {
			doDeleteServiceCall(bean);
		} catch (Exception e) {
			String msg = "Error calling delete service. " + e.getMessage();
			LOG.error(msg, e);
			getMessages().error(msg);
			return;
		}
		detailView.reset();
		handleEventAction(RecordAction.Delete, bean);
	}

	protected void handleEventAction(final RecordAction action, final Object data) {
		Action eventAction = getEventAction(action);
		if (eventAction != null) {
			ActionEvent event = new ActionEvent(this, action.name(), data);
			eventAction.execute(event);
		}
	}

	protected void doUpdateDetailBean() {
		WebUtilities.updateBeanValue(detailView);
	}

	/**
	 * @return true if the bean has been loaded successfully
	 */
	@Override
	public boolean isLoaded() {
		return detailView.isVisible() && detailView.getApiBean() != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WMessages getMessages() {
		return messages;
	}

	@Override
	protected ActionViewModel newComponentModel() {
		return new ActionViewModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ActionViewModel getComponentModel() {
		return (ActionViewModel) super.getComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ActionViewModel getOrCreateComponentModel() {
		return (ActionViewModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class ActionViewModel extends WSection.SectionModel {

		private ActionMode actionMode = ActionMode.View;
		private Map<RecordAction, Action> eventActions;
	}

}
