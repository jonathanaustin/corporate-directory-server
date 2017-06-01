package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.corpdir.api.exception.ServiceException;
import com.github.bordertech.corpdir.web.ui.common.Constants;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WCheckBox;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WSection;
import com.github.bordertech.wcomponents.WTextField;
import com.github.bordertech.wcomponents.layout.ColumnLayout;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Abstract list view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public abstract class AbstractListView<T extends ApiKeyIdObject> extends WSection implements MessageContainer {

	/**
	 * The logger instance for this class.
	 */
	private static final Log LOG = LogFactory.getLog(AbstractListView.class);

	/**
	 * Messages for this view.
	 */
	private final WMessages messages = new WMessages();

	private final WPanel searchPanel = new WPanel();

	private final WFieldLayout searchLayout = new WFieldLayout();

	private final WPanel resultsPanel = new WPanel();

	private final WPanel buttonPanel = new WPanel(WPanel.Type.FEATURE);

	private final WPanel ajaxPanel = new WPanel() {
		@Override
		public boolean isHidden() {
			return true;
		}
	};

	private final WButton backButton = new WButton();
	private final WButton actionButton = new WButton();

	private final WAjaxControl ajaxBack = new WAjaxControl(backButton);
	private final WAjaxControl ajaxAction = new WAjaxControl(actionButton);

	/**
	 * @param title the view title
	 */
	public AbstractListView(final String title) {
		super(title);
	}

	protected WPanel getSearchPanel() {
		return searchPanel;
	}

	protected WFieldLayout getSearchLayout() {
		return searchLayout;
	}

	protected WPanel getResultsPanel() {
		return resultsPanel;
	}

	protected WPanel getButtonPanel() {
		return buttonPanel;
	}

	protected WPanel getAjaxPanel() {
		return ajaxPanel;
	}

	/**
	 * Setup the view content.
	 */
	protected void setupContent() {
		WPanel content = getContent();
		content.setSearchAncestors(false);
		content.setBeanProperty(".");
		content.add(messages);
		content.add(searchPanel);
		content.add(resultsPanel);
		content.add(buttonPanel);
		content.add(ajaxPanel);

		setupFormDefaults();
		setupFormDetails();
		setupButtonPanel();
		setupAjaxPanel();
	}

	/**
	 * @param detail the bean to be displayed
	 */
	public void setDetail(final T detail) {
		getContent().setBean(detail);
	}

	/**
	 * @return the bean being displayed.
	 */
	public T getDetail() {
		return (T) getContent().getBean();
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isInitialised()) {
			setInitialised(true);
		}
	}

	protected void setupFormDefaults() {
		WFieldLayout layout = getSearchLayout();

		WPanel panel = getSearchPanel();
		panel.add(layout);

		// Business Key
		WTextField txtBusKey = new WTextField();
		txtBusKey.setMandatory(true);
		txtBusKey.setBeanProperty("businessKey");
		layout.addField("Business key", txtBusKey);

		// Description
		WTextField txtDesc = new WTextField();
		txtDesc.setMandatory(true);
		txtDesc.setBeanProperty("description");
		layout.addField("Description", txtDesc);

		// Active
		WCheckBox chbActive = new WCheckBox();
		chbActive.setBeanProperty("active");
		layout.addField("Active", chbActive);

		// Defualt button
		panel.setDefaultSubmitButton(actionButton);
	}

	protected void setupButtonPanel() {

		WPanel panel = getButtonPanel();

		panel.setMargin(Constants.NORTH_MARGIN_LARGE);
		panel.setLayout(new ColumnLayout(new int[]{50, 50}, new ColumnLayout.Alignment[]{ColumnLayout.Alignment.LEFT, ColumnLayout.Alignment.RIGHT}));

		WContainer left = new WContainer();
		WContainer right = new WContainer();
		panel.add(left);
		panel.add(right);

		// Back
		backButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doBackAction();
			}
		});
		left.add(backButton);

//		// Process Action
//		actionButton.setAction(new ValidatingAction(getMessages().getValidationErrors(), getFormPanel()) {
//
//			@Override
//			public void executeOnValid(final ActionEvent event) {
//				doProcessAction();
//			}
//		});
//		right.add(actionButton);
	}

	protected void setupAjaxPanel() {

		WPanel panel = getAjaxPanel();

		// Back button ajax
		ajaxBack.setVisible(false);
		panel.add(ajaxBack);

		// Action button ajax
		ajaxAction.addTarget(messages);
		panel.add(ajaxAction);
	}

	public void addBackAjaxTarget(final AjaxTarget target) {
		ajaxBack.addTarget(target);
		ajaxBack.setVisible(true);
	}

	public void addActionAjaxTarget(final AjaxTarget target) {
		ajaxAction.addTarget(target);
	}

	protected void doProcessAction() {

	}

	protected abstract void setupFormDetails();

	protected abstract void doBackAction();

	protected abstract void doServiceCall(final T bean) throws ServiceException;

	protected abstract void doActionSuccessful();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WMessages getMessages() {
		return messages;
	}

}
