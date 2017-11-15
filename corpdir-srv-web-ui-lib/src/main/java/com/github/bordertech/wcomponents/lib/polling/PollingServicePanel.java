package com.github.bordertech.wcomponents.lib.polling;

import com.github.bordertech.taskmanager.service.ResultHolder;
import com.github.bordertech.taskmanager.service.ServiceAction;
import com.github.bordertech.taskmanager.service.ServiceUtil;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WDiv;
import com.github.bordertech.wcomponents.WMessages;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This panel is used to load data via a threaded polling action and polling AJAX.
 * <p>
 * Expected {@link #setPollingCriteria(Object)} to be set with the appropriate id to be loaded.
 * </p>
 * <p>
 * The successful polling result will be set as the bean available to the panel. The content of the panel will only be
 * displayed if the polling action was successful. If the polling action fails, then the error message will be displayed
 * along with a retry button.
 * </p>
 * <p>
 * Any caching of polling actions is expected to be handled at the lower level (eg service layer caching).
 * </p>
 *
 * @param <S> the polling criteria type
 * @param <T> the polling result type
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class PollingServicePanel<S extends Serializable, T extends Serializable> extends PollingPanel implements PollableService<S, T> {

	/**
	 * The logger instance for this class.
	 */
	private static final Log LOG = LogFactory.getLog(PollingServicePanel.class);

	/**
	 * Start polling manually button.
	 */
	private final WButton startButton = new WButton("Start");

	private final WDiv contentResultHolder = new WDiv() {
		@Override
		protected void preparePaintComponent(final Request request) {
			super.preparePaintComponent(request);
			if (!isInitialised()) {
				initResultContent(request);
				setInitialised(true);
			}
		}
	};

	/**
	 * Messages.
	 */
	private final WMessages messages = new WMessages(true);

	/**
	 * Retry load.
	 */
	private final WButton retryButton = new WButton("Retry");

	/**
	 * Default constructor.
	 */
	public PollingServicePanel() {
		this(174);
	}

	/**
	 * Construct polling panel.
	 *
	 * @param delay the AJAX polling delay
	 */
	public PollingServicePanel(final int delay) {
		this(delay, false);
	}

	/**
	 * Construct polling panel.
	 *
	 * @param delay the AJAX polling delay
	 * @param manualStart true if start polling with manual start button action
	 */
	public PollingServicePanel(final int delay, final boolean manualStart) {
		super(delay);

		WDiv holder = getHolder();

		messages.setMargin(new Margin(0, 0, 3, 0));
		holder.add(messages);
		holder.add(retryButton);
		holder.add(contentResultHolder);

		// Manual Start load
		startButton.setAjaxTarget(this);
		startButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doStartLoading();
				startButton.setVisible(false);
			}
		});

		// Retry load
		retryButton.setAjaxTarget(this);
		retryButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doRetry();
			}
		});

		// Set default visibility
		retryButton.setVisible(false);
		contentResultHolder.setVisible(false);
	}

	public final WDiv getContentResultHolder() {
		return contentResultHolder;
	}

	/**
	 * @return true if start polling manually with the start button.
	 */
	@Override
	public boolean isManualStart() {
		return getComponentModel().manualStart;
	}

	/**
	 *
	 * @param manualStart true if start polling manually with the start button
	 */
	@Override
	public void setManualStart(final boolean manualStart) {
		getOrCreateComponentModel().manualStart = manualStart;
	}

	/**
	 * @param criteria the id for the record
	 */
	@Override
	public void setPollingCriteria(final S criteria) {
		getOrCreateComponentModel().criteria = criteria;
	}

	/**
	 * @return the id for the record
	 */
	@Override
	public S getPollingCriteria() {
		return getComponentModel().criteria;
	}

	/**
	 * Initiliase the result content.
	 *
	 * @param request the request being processed
	 */
	protected void initResultContent(final Request request) {
		// Do Nothing
	}

	/**
	 * The messages for the panel.
	 *
	 * @return the messages for the panel
	 */
	protected WMessages getMessages() {
		return messages;
	}

	protected void handleErrorMessage(final String msg) {
		handleErrorMessage(Arrays.asList(msg));
	}

	protected void handleErrorMessage(final List<String> msgs) {
		for (String msg : msgs) {
			getMessages().error(msg);
		}
	}

	/**
	 * @return the retry button.
	 */
	@Override
	public WButton getRetryButton() {
		return retryButton;
	}

	/**
	 * @return the start button
	 */
	@Override
	public WButton getStartButton() {
		return startButton;
	}

	/**
	 * @return the polling result, or null if not processed successfully yet
	 */
	@Override
	public T getPollingResult() {
		return (T) getHolder().getBean();
	}

	/**
	 * Start loading data.
	 */
	@Override
	public void doStartLoading() {

		if (!isManualStart()) {
			startButton.setVisible(false);
		}

		// Check not started
		if (getPollingStatus() != PollingStatus.NOT_STARTED) {
			return;
		}

		// Check we have a criteria
		final S criteria = getPollingCriteria();
		if (criteria == null) {
			handleErrorMessage("No criteria set for polling action.");
			return;
		}

		handleStartPollingAction(criteria);
	}

	/**
	 * Retry the polling action.
	 */
	@Override
	public void doRetry() {
		doRefreshContent();
		if (isManualStart()) {
			doStartLoading();
		}
	}

	/**
	 * Reset to start load again.
	 */
	@Override
	public void doRefreshContent() {
		S criteria = getPollingCriteria();
		if (criteria == null) {
			return;
		}
		handleClearPollingCache();
		getHolder().reset();
		setPollingStatus(PollingStatus.NOT_STARTED);
	}

	@Override
	public void doManuallyLoadResult(final S criteria, final T result) {
		if (result == null || criteria == null) {
			return;
		}
		getHolder().reset();
		startButton.setVisible(false);
		setPollingCriteria(criteria);
		handleResult(new ResultHolder(result));
	}

	@Override
	protected void handleInitContent(final Request request) {
		super.handleInitContent(request);
		if (!isManualStart()) {
			doStartLoading();
		}
	}

	/**
	 * Clear the polling cache if necessary (eg Service Layer).
	 */
	protected void handleClearPollingCache() {
		// Do nothing
	}

	/**
	 * @param criteria the criteria for the polling action
	 */
	protected void handleStartPollingAction(final S criteria) {
		clearServiceKey();
		String key = generateServiceKey();
		// Start Service action
		ServiceUtil.handleAsyncServiceCall(key, criteria, getServiceAction());
		// Start polling
		doStartPolling();
	}

	/**
	 * Stopped polling and view has been reloaded.
	 */
	@Override
	protected void handleStoppedPolling() {
		String key = getServiceKey();
		ResultHolder result = ServiceUtil.getResultHolder(key);
		if (result == null) {
			throw new IllegalStateException("Result has expired so polling result not available");
		}
		clearServiceKey();
		handleResult(result);
	}

	/**
	 * Handle the result from the polling action.
	 *
	 * @param resultHolder the polling action result
	 * @return the polling status
	 */
	protected PollingStatus handleResult(final ResultHolder<S, T> resultHolder) {
		// Exception message
		final PollingStatus status;
		if (resultHolder.hasException()) {
			Exception excp = resultHolder.getException();
			handleExceptionResult(excp);
			// Log error
			LOG.error("Error loading data. " + excp.getMessage());
			status = PollingStatus.ERROR;
		} else {
			// Successful Result
			T result = resultHolder.getResult();
			handleSuccessfulResult(result);
			status = PollingStatus.COMPLETE;
		}
		setPollingStatus(PollingStatus.ERROR);
		return status;
	}

	/**
	 * Handle an exception occurred.
	 *
	 * @param excp the exception that occurred
	 */
	protected void handleExceptionResult(final Exception excp) {
		handleErrorMessage(excp.getMessage());
		retryButton.setVisible(true);
	}

	/**
	 * Handle the result. Default behaviour is to set the result as the bean for the content.
	 *
	 * @param result the polling action result
	 */
	protected void handleSuccessfulResult(final T result) {
		// Set the result as the bean
		getHolder().setBean(result);
		contentResultHolder.setVisible(true);
	}

	/**
	 * Check the result is available.
	 *
	 * @return true if have result and need to stop polling
	 */
	@Override
	protected boolean checkForStopPolling() {
		return ServiceUtil.checkASyncResult(getServiceKey()) != null;
	}

	protected String generateServiceKey() {
		String key = "polling=" + UUID.randomUUID().toString();
		getOrCreateComponentModel().serviceKey = key;
		return key;
	}

	protected String getServiceKey() {
		return getComponentModel().serviceKey;
	}

	protected void clearServiceKey() {
		String key = getServiceKey();
		if (key != null) {
			ServiceUtil.clearResult(key);
			getOrCreateComponentModel().serviceKey = null;
		}
	}

	/**
	 *
	 * @return the service call action
	 */
	@Override
	public ServiceAction<S, T> getServiceAction() {
		return getComponentModel().serviceAction;
	}

	/**
	 * Do the actual polling action (eg Service call).
	 * <p>
	 * As this method is called by a different thread, do not put any logic or functionality that needs the user
	 * context.
	 * </p>
	 *
	 * @param serviceAction the service call action
	 */
	@Override
	public void setServiceAction(final ServiceAction<S, T> serviceAction) {
		getOrCreateComponentModel().serviceAction = serviceAction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PollingServiceModel<S, T> newComponentModel() {
		return new PollingServiceModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PollingServiceModel<S, T> getOrCreateComponentModel() {
		return (PollingServiceModel) super.getOrCreateComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PollingServiceModel<S, T> getComponentModel() {
		return (PollingServiceModel) super.getComponentModel();
	}

	/**
	 * This model holds the state information.
	 *
	 * @param <S> the criteria type
	 * @param <T> the service action
	 */
	public static class PollingServiceModel<S, T> extends PollingModel {

		/**
		 * Record id.
		 */
		private S criteria;

		/**
		 * Holds the reference to the service action.
		 */
		private String serviceKey;

		/**
		 * Flag if start polling manually with the start button.
		 */
		private boolean manualStart;

		/**
		 * Service action.
		 */
		private ServiceAction<S, T> serviceAction;
	}

}
