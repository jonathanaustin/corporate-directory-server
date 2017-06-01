package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.corpdir.web.ui.common.BasicDetailPanel;
import com.github.bordertech.corpdir.web.ui.common.DetailViewMode;
import com.github.bordertech.corpdir.web.ui.polling.AbstractPollingPanel;
import com.github.bordertech.corpdir.web.ui.polling.PollingController;
import com.github.bordertech.corpdir.web.ui.polling.PollingServiceException;
import com.github.bordertech.corpdir.web.ui.polling.PollingStatus;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Container;
import com.github.bordertech.wcomponents.Input;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WSection;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.validation.ValidatingAction;
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
public abstract class AbstractDetailView<T extends ApiKeyIdObject> extends WSection implements MessageContainer, DetailView<T> {

	/**
	 * The logger instance for this class.
	 */
	private static final Log LOG = LogFactory.getLog(AbstractDetailView.class);

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

	private final WMenuItem itemCancel = new WMenuItem("Cancel") {
		@Override
		public boolean isDisabled() {
			return getViewMode() == DetailViewMode.View;
		}

		@Override
		public boolean isCancel() {
			return true;
		}

	};

	private final WMenuItem itemSave = new WMenuItem("Save") {
		@Override
		public boolean isDisabled() {
			return getViewMode() != DetailViewMode.View;
		}
	};

	private final WMenuItem itemDelete = new WMenuItem("Delete") {
		@Override
		public boolean isDisabled() {
			return getViewMode() != DetailViewMode.View;
		}
	};

	private final WPanel ajaxPanel = new WPanel() {
		@Override
		public boolean isHidden() {
			return true;
		}
	};

	private final WAjaxControl ajaxBack = new WAjaxControl(itemBack);
	private final WAjaxControl ajaxSave = new WAjaxControl(itemSave);
	private final WAjaxControl ajaxDelete = new WAjaxControl(itemDelete);

	private final PollingController<T, String> pollingDetailPanel = new AbstractPollingPanel<T, String>("dt", 173) {
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

	/**
	 * @param title the view title
	 */
	public AbstractDetailView(final String title, final BasicDetailPanel<T> detailPanel) {
		super(title);
		this.pollingDetailPanel.getContent().add(detailPanel);

		WPanel content = getContent();
		content.setSearchAncestors(false);
		content.setBeanProperty(".");
		content.add(actionMenu);
		content.add(messages);
		content.add(detailPanel);
		content.add(ajaxPanel);

		setupMenu();
		setupAjaxPanel();
	}

	private void setupMenu() {

		WMenu menu = getActionMenu();

		// Back
		menu.add(itemBack);
		itemBack.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				runOnBackAction();
			}
		});

		// Save Action
		menu.add(itemSave);
		itemSave.setAction(new ValidatingAction(getMessages().getValidationErrors(), pollingDetailPanel.getContent()) {
			@Override
			public void executeOnValid(final ActionEvent event) {
				handleSave();
			}
		});

		// Delete
		menu.add(itemDelete);
		itemDelete.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				handleDelete();
			}
		});

	}

	private WPanel getAjaxPanel() {
		return ajaxPanel;
	}

	private WMenu getActionMenu() {
		return actionMenu;
	}

	private void setupAjaxPanel() {

		WPanel panel = getAjaxPanel();

		// Back ajax
		ajaxBack.setVisible(false);
		panel.add(ajaxBack);

		// Save ajax
		ajaxSave.addTarget(getMessages());
		ajaxSave.addTarget(pollingDetailPanel.getContent());
		panel.add(ajaxSave);

		// Delete ajax
		ajaxDelete.addTarget(getMessages());
		panel.add(ajaxDelete);
	}

	/**
	 *
	 * @param id the id being displayed
	 */
	@Override
	public void setApiId(final String id) {
		pollingDetailPanel.setRecordId(id);
	}

	/**
	 * @return the id being displayed
	 */
	@Override
	public String getApiId() {
		return pollingDetailPanel.getRecordId();
	}

	/**
	 * @return the bean being processed
	 */
	protected T getApiBean() {
		return pollingDetailPanel.getServiceResponse();
	}

	/**
	 * @return the bean has been loaded successfully
	 */
	protected boolean isLoaded() {
		return pollingDetailPanel.getPollingStatus() == PollingStatus.COMPLETE;
	}

	@Override
	public void setAllowedModes(final List<DetailViewMode> allowedModes) {
		getOrCreateComponentModel().allowedModes = allowedModes;
	}

	@Override
	public List<DetailViewMode> getAllowedModes() {
		return getComponentModel().allowedModes;
	}

	@Override
	public void addActionAjaxTarget(final AjaxTarget target) {
		ajaxBack.addTarget(target);
		ajaxBack.setVisible(true);
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

	protected void setViewMode(final DetailViewMode viewMode) {
		getOrCreateComponentModel().viewMode = viewMode;
	}

	protected DetailViewMode getViewMode() {
		return getComponentModel().viewMode;
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isInitialised()) {
			initDetail();
			setInitialised(true);
		}
	}

	protected void initDetail() {
		if (getViewMode() == DetailViewMode.View) {
			doMakeReadOnly(getContent());
		}
	}

	protected void doMakeReadOnly(final WComponent component) {
		if (component instanceof Input) {
			((Input) component).setReadOnly(true);
		}

		if (component instanceof Container) {
			for (WComponent child : ((Container) component).getChildren()) {
				doMakeReadOnly(child);
			}
		}
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

		private DetailViewMode viewMode = DetailViewMode.View;
		private List<DetailViewMode> allowedModes;
		private Action backAction;
		private Action saveAction;
		private Action deleteAction;
	}

}
