package com.github.bordertech.wcomponents.lib.polling;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.impl.ExecuteService;
import com.github.bordertech.wcomponents.lib.tasks.TaskFuture;
import com.github.bordertech.wcomponents.lib.tasks.TaskManager;
import com.github.bordertech.wcomponents.lib.tasks.TaskManagerFactory;
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
public class PollingServiceView<S, T> extends PollingView {

	/**
	 * The logger instance for this class.
	 */
	private static final Log LOG = LogFactory.getLog(PollingServiceView.class);

	/**
	 * The TaskManager implementation.
	 */
	private static final TaskManager TASK_MANAGER = TaskManagerFactory.getInstance();

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
	 *
	 * @param dispatcher the view controller
	 */
	public PollingServiceView(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	/**
	 * Construct polling panel.
	 *
	 * @param dispatcher the view controller
	 * @param delay the AJAX polling delay
	 */
	public PollingServiceView(final Dispatcher dispatcher, final String qualifier) {
		this(dispatcher, qualifier, 174);
	}

	/**
	 * Construct polling panel.
	 *
	 * @param dispatcher the view controller
	 * @param qualifier the naming context
	 * @param delay the AJAX polling delay
	 */
	public PollingServiceView(final Dispatcher dispatcher, final String qualifier, final int delay) {
		this(dispatcher, qualifier, delay, false);
	}

	/**
	 * Construct polling panel.
	 *
	 * @param dispatcher the view controller
	 * @param qualifier the naming context
	 * @param delay the AJAX polling delay
	 * @param manualStart true if start polling with manual start button action
	 */
	public PollingServiceView(final Dispatcher dispatcher, final String qualifier, final int delay, final boolean manualStart) {
		super(dispatcher, qualifier, delay);

		WDiv holder = getContent();

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
	public boolean isManualStart() {
		return getComponentModel().manualStart;
	}

	/**
	 *
	 * @param manualStart true if start polling manually with the start button
	 */
	public void setManualStart(final boolean manualStart) {
		getOrCreateComponentModel().manualStart = manualStart;
	}

	/**
	 * @param criteria the id for the record
	 */
	public void setPollingCriteria(final S criteria) {
		getOrCreateComponentModel().criteria = criteria;
	}

	/**
	 * @return the id for the record
	 */
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

	/**
	 * @return the retry button.
	 */
	public WButton getRetryButton() {
		return retryButton;
	}

	/**
	 * @return the start button
	 */
	public WButton getStartButton() {
		return startButton;
	}

	/**
	 * @return the polling result, or null if not processed successfully yet
	 */
	public T getPollingResult() {
		return (T) getContent().getBean();
	}

	/**
	 * Start loading data.
	 */
	public void doStartLoading() {

		// Check not started
		if (getPollingStatus() != PollingStatus.NOT_STARTED) {
			return;
		}

		// Check we have a criteria
		final S criteria = getPollingCriteria();
		if (criteria == null) {
			getMessages().error("No criteria set for polling action.");
			return;
		}

		handleStartPollingAction(criteria);
	}

	/**
	 * Retry the polling action.
	 */
	public void doRetry() {
		doRefreshContent();
		if (isManualStart()) {
			doStartLoading();
		}
	}

	/**
	 * Reset to start load again.
	 */
	public void doRefreshContent() {
		S criteria = getPollingCriteria();
		if (criteria == null) {
			return;
		}
		handleClearPollingCache();
		getContent().reset();
		setPollingStatus(PollingStatus.NOT_STARTED);
		clearFuture();
	}

	public void doManuallyLoadResult(final S criteria, final T result) {
		if (result == null || criteria == null) {
			return;
		}
		getContent().reset();
		startButton.setVisible(false);
		setPollingCriteria(criteria);
		handleResult(result);
	}

	@Override
	protected void initViewContent(final Request request) {
		super.initViewContent(request);
		if (!isManualStart()) {
			startButton.setVisible(false);
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
		// Start polling action
		try {
			handleAsyncPollingAction(criteria);
		} catch (Exception e) {
			handleResult(e);
			return;
		}

		// Start polling
		doStartPolling();
	}

	/**
	 * Stopped polling and view has been reloaded.
	 */
	@Override
	protected void handleStoppedPolling() {
		// Get the Future for the polling action
		TaskFuture<PollingResultHolder> future = getFuture();
		if (future == null) {
			// Stop polling as future must have expired
			handleResult(new PollingException("Future has expired so polling result not available"));
			return;
		}

		// Extract the result from the future
		Object result;
		try {
			PollingResultHolder holder = future.get();
			result = holder.getResult();
		} catch (Exception e) {
			result = e;
		}
		// Clear future
		clearFuture();
		handleResult(result);
	}

	/**
	 * Handle the result from the polling action.
	 *
	 * @param pollingResult the polling action result
	 */
	protected void handleResult(final Object pollingResult) {
		// Exception message
		if (pollingResult instanceof Exception) {
			Exception excp = (Exception) pollingResult;
			handleExceptionResult(excp);
			// Log error
			LOG.error("Error loading data. " + excp.getMessage());
			// Status
			setPollingStatus(PollingStatus.ERROR);
			dispatchViewEvent(PollingEventType.ERROR, getPollingCriteria(), excp);
		} else {
			// Successful Result
			T result = (T) pollingResult;
			handleSuccessfulResult(result);
			// Status
			setPollingStatus(PollingStatus.COMPLETE);
			dispatchViewEvent(PollingEventType.COMPLETE, result);
		}
	}

	/**
	 * Handle an exception occurred.
	 *
	 * @param excp the exception that occurred
	 */
	protected void handleExceptionResult(final Exception excp) {
		extractExceptionMessages("Error loading data. ", getMessages(), excp);
		retryButton.setVisible(true);
	}

	/**
	 * Handle the result. Default behaviour is to set the result as the bean for the content.
	 *
	 * @param result the polling action result
	 */
	protected void handleSuccessfulResult(final T result) {
		// Set the result as the bean
		getContent().setBean(result);
		contentResultHolder.setVisible(true);
	}

	/**
	 * Extract the exception messages from the polling exception.
	 *
	 * @param prefix the message prefix
	 * @param messages the message component
	 * @param exception the exception to setup messages
	 */
	protected void extractExceptionMessages(final String prefix, final WMessages messages, final Exception exception) {
		messages.error(prefix + exception.getMessage());
	}

	/**
	 * Check the future holds the result.
	 *
	 * @return true if have result and need to stop polling
	 */
	@Override
	protected boolean checkForStopPolling() {
		// Stop polling if future null (must have expired) or is done
		TaskFuture<PollingResultHolder> future = getFuture();
		return future == null || future.isDone();
	}

	/**
	 * Handle the polling action.
	 *
	 * @param criteria the criteria for the polling action
	 * @throws PollingException the polling exception
	 */
	protected void handleAsyncPollingAction(final S criteria) throws PollingException {

		clearFuture();

		final ExecuteService<S, T> action = getPollingServiceAction();
		if (action == null) {
			throw new PollingException("No polling service action has been defined. ");
		}

		final PollingResultHolder result = new PollingResultHolder();
		Runnable task = new Runnable() {
			@Override
			public void run() {
				try {
					T resp = action.executeService(criteria);
					result.setResult(resp);
				} catch (Exception e) {
					PollingException excp = new PollingException(e.getMessage(), e);
					result.setResult(excp);
				}
			}
		};
		try {
			TaskFuture future = TASK_MANAGER.submit(task, result);
			// Save the future
			setFuture(future);
		} catch (Exception e) {
			throw new PollingException("Could not start thread to process polling action. " + e.getMessage());
		}
	}

	/**
	 * @return the polling action future object
	 */
	protected TaskFuture<PollingResultHolder> getFuture() {
		return getComponentModel().future;
	}

	/**
	 * @param future the polling action future to save
	 */
	protected void setFuture(final TaskFuture<PollingResultHolder> future) {
		getOrCreateComponentModel().future = future;
	}

	/**
	 * Cancel and clear the future if there is one already running.
	 */
	protected void clearFuture() {
		TaskFuture future = getFuture();
		if (future != null) {
			if (!future.isDone() && !future.isCancelled()) {
				future.cancel(true);
			}
			getOrCreateComponentModel().future = null;
		}
	}

	/**
	 *
	 * @return the service call action
	 */
	public ExecuteService<S, T> getPollingServiceAction() {
		return getComponentModel().pollingServiceAction;
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
	public void setPollingServiceAction(final ExecuteService<S, T> serviceAction) {
		getOrCreateComponentModel().pollingServiceAction = serviceAction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PollingServiceModel<S, T> newComponentModel() {
		return new PollingServiceModel<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PollingServiceModel<S, T> getOrCreateComponentModel() {
		return (PollingServiceModel<S, T>) super.getOrCreateComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PollingServiceModel<S, T> getComponentModel() {
		return (PollingServiceModel<S, T>) super.getComponentModel();
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
		 * Holds the reference to the future for the polling action.
		 */
		private TaskFuture<PollingResultHolder> future;

		/**
		 * Flag if start polling manually with the start button.
		 */
		private boolean manualStart;

		private ExecuteService<S, T> pollingServiceAction;
	}

}
