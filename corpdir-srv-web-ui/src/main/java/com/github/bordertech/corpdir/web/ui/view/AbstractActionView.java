package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.corpdir.web.ui.common.ActionMode;
import com.github.bordertech.corpdir.web.ui.common.BasicDetailPanel;
import com.github.bordertech.corpdir.web.ui.polling.AbstractPollingPanel;
import com.github.bordertech.corpdir.web.ui.polling.PollingController;
import com.github.bordertech.corpdir.web.ui.polling.PollingServiceException;
import com.github.bordertech.corpdir.web.ui.polling.PollingStatus;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.AjaxTrigger;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WSection;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.validation.ValidatingAction;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Abstract edit view.
 *
 * @author Jonathan Austin
 * @param <T>
 * @since 1.0.0
 */
public abstract class AbstractActionView<T extends ApiKeyIdObject> extends WSection implements MessageContainer, ActionView<T> {

	/**
	 * The logger instance for this class.
	 */
	private static final Log LOG = LogFactory.getLog(AbstractActionView.class);

	/**
	 * Messages for this view.
	 */
	private final WMessages messages = new WMessages();

	private final WMenu actionMenu = new WMenu();

	private final WMenuItem itemBack = new WMenuItem("Back") {
		@Override
		public boolean isVisible() {
			return getOnBackAction() != null;
		}
	};

	private final WMenuItem itemEdit = new WMenuItem("Edit") {
		@Override
		public boolean isVisible() {
			return isLoaded();
		}

		@Override
		public boolean isDisabled() {
			return getActionMode() != ActionMode.View;
		}
	};

	private final WMenuItem itemCancel = new WMenuItem("Cancel") {
		@Override
		public boolean isVisible() {
			return isLoaded();
		}

		@Override
		public boolean isDisabled() {
			return getActionMode() != ActionMode.Edit;
		}

		@Override
		public boolean isCancel() {
			return true;
		}
	};

	private final WMenuItem itemRefresh = new WMenuItem("Refresh") {
		@Override
		public boolean isVisible() {
			return isLoaded();
		}

		@Override
		public boolean isDisabled() {
			return getActionMode() == ActionMode.Create;
		}

	};

	private final WMenuItem itemSave = new WMenuItem("Save") {
		@Override
		public boolean isVisible() {
			return isLoaded();
		}

		@Override
		public boolean isDisabled() {
			return getActionMode() == ActionMode.View;
		}
	};

	private final WMenuItem itemDelete = new WMenuItem("Delete") {
		@Override
		public boolean isVisible() {
			return isLoaded();
		}

		@Override
		public boolean isDisabled() {
			return getActionMode() != ActionMode.View;
		}
	};

	private final WPanel ajaxPanel = new WPanel() {
		@Override
		public boolean isHidden() {
			return true;
		}
	};

	private final WAjaxControl ajaxBack = new MyAjaxControl(itemBack);
	private final WAjaxControl ajaxSave = new MyAjaxControl(itemSave);
	private final WAjaxControl ajaxEdit = new MyAjaxControl(itemEdit);
	private final WAjaxControl ajaxCancel = new MyAjaxControl(itemCancel);
	private final WAjaxControl ajaxDelete = new MyAjaxControl(itemDelete);
	private final WAjaxControl ajaxRefresh = new MyAjaxControl(itemRefresh);

	private final PollingController<T, String> pollingDetailPanel = new AbstractPollingPanel<T, String>(173) {
		@Override
		public T doServiceCall(final String recordId) throws PollingServiceException {
			try {
				T bean = doRetrieveServiceCall(recordId);
				return bean;
			} catch (Exception e) {
				throw new PollingServiceException("Error retrieving record. " + e.getMessage(), e);
			}
		}
	};

	private final BasicDetailPanel<T> detailPanel;

	/**
	 * @param title the view title
	 */
	public AbstractActionView(final String title, final BasicDetailPanel<T> detailPanel) {
		super(title);
		this.pollingDetailPanel.getContent().add(detailPanel);
		this.detailPanel = detailPanel;

		WPanel content = getContent();
		content.add(actionMenu);
		content.add(messages);
		content.add(detailPanel);
		content.add(ajaxPanel);

		setupMenu();
		setupAjaxPanel();
	}

	private void setupMenu() {

		WMenu menu = getActionMenu();

		// Items
		menu.add(itemBack);
		menu.add(itemEdit);
		menu.add(itemSave);
		menu.add(itemCancel);
		menu.add(itemDelete);
		menu.add(itemRefresh);

		// Back
		itemBack.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				handleBack();
			}
		});

		// Cancel Action
		itemCancel.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				handleCancel();
			}
		});

		// Edit Action
		itemEdit.setAction(new ValidatingAction(getMessages().getValidationErrors(), detailPanel) {
			@Override
			public void executeOnValid(final ActionEvent event) {
				handleEdit();
			}
		});

		// Save Action
		itemSave.setAction(new ValidatingAction(getMessages().getValidationErrors(), detailPanel) {
			@Override
			public void executeOnValid(final ActionEvent event) {
				handleSave();
			}
		});

		// Delete
		itemDelete.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				handleDelete();
			}
		});

		// Refresh
		itemRefresh.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				handleRefresh();
			}
		});

	}

	private void setupAjaxPanel() {

		WPanel panel = getAjaxPanel();

		panel.add(ajaxBack);
		panel.add(ajaxEdit);
		panel.add(ajaxCancel);
		panel.add(ajaxSave);
		panel.add(ajaxDelete);
		panel.add(ajaxRefresh);

		// Targets
		ajaxBack.addTarget(getContent());
		ajaxSave.addTarget(getContent());
		ajaxDelete.addTarget(getContent());
		// Internal Actions
		ajaxEdit.addTarget(getContent());
		ajaxCancel.addTarget(getContent());
		ajaxRefresh.addTarget(getContent());
	}

	private WPanel getAjaxPanel() {
		return ajaxPanel;
	}

	private WMenu getActionMenu() {
		return actionMenu;
	}

	@Override
	public void load(final String id) {
		reset();
		pollingDetailPanel.setRecordId(id);
		setActionMode(ActionMode.View);
	}

	@Override
	public void preLoad(final T bean) {
		reset();
		pollingDetailPanel.preloadRecord(bean, bean.getId());
		if (bean.getId() == null) {
			setActionMode(ActionMode.Create);
		}
	}

	/**
	 * @return the bean being processed
	 */
	@Override
	public T getApiBean() {
		return pollingDetailPanel.getServiceResponse();
	}

	@Override
	public List<ActionMode> getAllowedModes() {
		return Arrays.asList(ActionMode.values());
	}

	@Override
	public void addActionAjaxTarget(final AjaxTarget target) {
		ajaxBack.addTarget(target);
		ajaxSave.addTarget(target);
		ajaxDelete.addTarget(target);
	}

	@Override
	public void setOnBackAction(final Action action) {
		getOrCreateComponentModel().backAction = action;
	}

	@Override
	public Action getOnBackAction() {
		return getComponentModel().backAction;
	}

	@Override
	public void setOnSaveAction(final Action action) {
		getOrCreateComponentModel().saveAction = action;
	}

	@Override
	public Action getOnSaveAction() {
		return getComponentModel().saveAction;
	}

	@Override
	public void setOnDeleteAction(final Action action) {
		getOrCreateComponentModel().deleteAction = action;
	}

	@Override
	public Action getOnDeleteAction() {
		return getComponentModel().deleteAction;
	}

	protected void setActionMode(final ActionMode actionMode) {
		getOrCreateComponentModel().actionMode = actionMode;
	}

	protected ActionMode getActionMode() {
		return getComponentModel().actionMode;
	}

	protected void handleBack() {
		runOnBackAction();
	}

	protected void handleRefresh() {
		pollingDetailPanel.doRefreshContent();
		setActionMode(ActionMode.View);
	}

	protected void handleEdit() {
		detailPanel.setFormReadOnly(false);
		setActionMode(ActionMode.Edit);
	}

	protected void handleCancel() {
		pollingDetailPanel.doRefreshContent();
		setActionMode(ActionMode.View);
	}

	protected void handleSave() {
		doUpdateDetailBean();
		T bean = getApiBean();
		try {
			doSaveServiceCall(bean);
		} catch (Exception e) {
			String msg = "Error calling save service. " + e.getMessage();
			LOG.error(msg, e);
			getMessages().error(msg);
			return;
		}
		pollingDetailPanel.doRefreshContent();
		setActionMode(ActionMode.View);
		runOnSaveAction();
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
		pollingDetailPanel.reset();
		runOnDeleteAction();
	}

	protected void doUpdateDetailBean() {
		WebUtilities.updateBeanValue(getContent());
	}

	protected void runOnBackAction() {
		Action action = getOnBackAction();
		if (action != null) {
			ActionEvent event = new ActionEvent(this, "back");
			action.execute(event);
		}
	}

	protected void runOnSaveAction() {
		Action action = getOnSaveAction();
		if (action != null) {
			ActionEvent event = new ActionEvent(this, "save", getApiBean());
			action.execute(event);
		}
	}

	protected void runOnDeleteAction() {
		Action action = getOnDeleteAction();
		if (action != null) {
			ActionEvent event = new ActionEvent(this, "delete", getApiBean());
			action.execute(event);
		}
	}

	/**
	 * @return true if the bean has been loaded successfully
	 */
	private boolean isLoaded() {
		return pollingDetailPanel.getPollingStatus() == PollingStatus.COMPLETE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WMessages getMessages() {
		return messages;
	}

	@Override
	protected ViewModel newComponentModel() {
		return new ViewModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ViewModel getComponentModel() {
		return (ViewModel) super.getComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ViewModel getOrCreateComponentModel() {
		return (ViewModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class ViewModel extends WSection.SectionModel {

		private ActionMode actionMode = ActionMode.View;
		private Action backAction;
		private Action saveAction;
		private Action deleteAction;
	}

	private static class MyAjaxControl extends WAjaxControl {

		public MyAjaxControl(final AjaxTrigger trigger) {
			super(trigger);
		}

		@Override
		public boolean isVisible() {
			return getTrigger().isVisible();
		}
	}

}
