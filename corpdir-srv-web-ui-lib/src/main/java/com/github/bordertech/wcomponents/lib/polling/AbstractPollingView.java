package com.github.bordertech.wcomponents.lib.polling;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxHelper;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WProgressBar;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.flux.DefaultView;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.tasks.TaskFuture;
import com.github.bordertech.wcomponents.lib.tasks.TaskManager;
import com.github.bordertech.wcomponents.lib.tasks.TaskManagerFactory;
import java.util.List;
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
public abstract class AbstractPollingView<S, T> extends DefaultView implements PollingView<S, T> {

	/**
	 * The logger instance for this class.
	 */
	private static final Log LOG = LogFactory.getLog(AbstractPollingView.class);

	/**
	 * The TaskManager implementation.
	 */
	private static final TaskManager TASK_MANAGER = TaskManagerFactory.getInstance();

	/**
	 * Start polling manually button.
	 */
	private final WButton startButton = new WButton("Start");

	/**
	 * Root container that can be reset to refresh the panel.
	 */
	private final WContainer root = new WContainer() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void preparePaintComponent(final Request request) {
			super.preparePaintComponent(request);
			if (!isInitialised()) {
				if (!isManualStart()) {
					startButton.setVisible(false);
					doStartLoading();
				}
				setInitialised(true);
			}
		}
	};

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
	 * The container that holds the AJAX poller.
	 */
	private final WContainer pollingContainer = new WContainer();

	private final WText pollingText = new WText() {
		@Override
		public String getText() {
			return getPollingText();
		}
	};

	private final WProgressBar pollingProgressBar = new WProgressBar(100);

	private final WText progressBarScript = new WText() {
		@Override
		protected void preparePaintComponent(final Request request) {
			if (!isInitialised()) {
				setText(buildProgressBarScript());
				setInitialised(true);
			}
		}
	};

	/**
	 * The container that holds the AJAX poller.
	 */
	private final WPanel ajaxPollingPanel = new WPanel() {
		@Override
		public boolean isHidden() {
			return true;
		}
	};

	/**
	 * AJAX poller.
	 */
	private final WAjaxControl ajaxPolling = new WAjaxControl(null, ajaxPollingPanel) {
		@Override
		public int getDelay() {
			return getPollingInterval();
		}
	};

	/**
	 * AJAX control to reload whole panel.
	 */
	private final WAjaxControl ajaxReload = new WAjaxControl(null, this) {
		@Override
		protected void preparePaintComponent(final Request request) {
			super.preparePaintComponent(request);
			// Reloading
			if (AjaxHelper.isCurrentAjaxTrigger(this)) {
				handleStoppedPolling();
			}
		}
	};

	/**
	 * Default constructor.
	 *
	 * @param dispatcher the dispatcher for this view
	 */
	public AbstractPollingView(final Dispatcher dispatcher) {
		this(dispatcher, 174);
	}

	/**
	 * Construct polling panel.
	 *
	 * @param dispatcher the dispatcher for this view
	 * @param delay the AJAX polling delay
	 */
	public AbstractPollingView(final Dispatcher dispatcher, final int delay) {
		this(dispatcher, null, delay, false);
	}

	/**
	 * Construct polling panel.
	 *
	 * @param dispatcher the dispatcher for this view
	 * @param context the naming context
	 * @param delay the AJAX polling delay
	 */
	public AbstractPollingView(final Dispatcher dispatcher, final String context, final int delay) {
		this(dispatcher, context, delay, false);
	}

	/**
	 * Construct polling panel.
	 *
	 * @param dispatcher the dispatcher for this view
	 * @param context the naming context
	 * @param delay the AJAX polling delay
	 * @param manualStart true if start polling with manual start button action
	 */
	public AbstractPollingView(final Dispatcher dispatcher, final String context, final int delay, final boolean manualStart) {
		super(dispatcher);

		WDiv holder = getViewHolder();
		root.setSearchAncestors(false);
		holder.add(root);

		// AJAX polling details
		setPollingInterval(delay);
		ajaxPolling.setLoadOnce(true);
		ajaxReload.setLoadOnce(true);
		ajaxReload.setDelay(10);
		progressBarScript.setEncodeText(false);

		// AJAX Container
		root.add(pollingContainer);
		pollingContainer.add(pollingText);
		pollingContainer.add(pollingProgressBar);
		pollingContainer.add(progressBarScript);
		pollingContainer.add(ajaxPollingPanel);
		ajaxPollingPanel.add(ajaxPolling);
		ajaxPollingPanel.add(ajaxReload);

		messages.setMargin(new Margin(0, 0, 3, 0));
		root.add(messages);
		root.add(retryButton);
		root.add(contentResultHolder);

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
		pollingContainer.setVisible(false);
		contentResultHolder.setVisible(false);
		ajaxReload.setVisible(false);

		// Context
		if (context != null) {
			setIdName(context);
			setNamingContext(true);
			// IDs
			messages.setIdName("msgs");
			retryButton.setIdName("btnRetry");
			ajaxPollingPanel.setIdName("pollingPanel");
			ajaxPolling.setIdName("ajaxPoll");
			ajaxReload.setIdName("ajaxReload");
			startButton.setIdName("btnStart");
		}

	}

	@Override
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
	 * @return the AJAX polling interval in milli seconds
	 */
	@Override
	public int getPollingInterval() {
		return getComponentModel().pollingInterval;
	}

	/**
	 *
	 * @param interval the AJAX polling interval in milli seconds
	 */
	@Override
	public final void setPollingInterval(final int interval) {
		getOrCreateComponentModel().pollingInterval = interval;
	}

	/**
	 * @param text the text displayed while polling
	 */
	@Override
	public void setPollingText(final String text) {
		getOrCreateComponentModel().pollingText = text;
	}

	/**
	 * @return the text displayed while polling
	 */
	@Override
	public String getPollingText() {
		return getComponentModel().pollingText;
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

	/**
	 * @param panelStatus the panel status
	 */
	protected void setPollingStatus(final PollingStatus panelStatus) {
		getOrCreateComponentModel().pollingStatus = panelStatus;
	}

	/**
	 * @return the panel status
	 */
	@Override
	public PollingStatus getPollingStatus() {
		return getComponentModel().pollingStatus;
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
		return (T) root.getBean();
	}

	/**
	 * Start loading data.
	 */
	@Override
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
		root.reset();
		setPollingStatus(PollingStatus.NOT_STARTED);
		clearFuture();
	}

	@Override
	public void doManuallyLoadResult(final S criteria, final T result) {
		if (result == null || criteria == null) {
			return;
		}
		root.reset();
		startButton.setVisible(false);
		setPollingCriteria(criteria);
		handleResult(result);
	}

	@Override
	protected void initViewContent(final Request request) {
	}

	protected List<AjaxTarget> getAjaxTargets() {
		return getComponentModel().extraTargets;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		// Check if Polling
		if (isPollingTarget()) {
			checkForResult();
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
		handleStartPolling();
	}

	/**
	 * Start polling.
	 */
	protected void handleStartPolling() {
		// Start AJAX polling
		setPollingStatus(PollingStatus.STARTED);
		pollingContainer.setVisible(true);
		ajaxPolling.reset();
		ajaxReload.reset();
		dispatchEvent(new Event(this, PollingEvent.STARTED));
	}

	/**
	 * Stop polling.
	 */
	protected void handleStopPolling() {
		ajaxPolling.setVisible(false);
		ajaxReload.setVisible(true);
		List<AjaxTarget> targets = getAjaxTargets();
		if (targets != null && !targets.isEmpty()) {
			ajaxReload.addTargets(targets);
		}
	}

	/**
	 * Stopped polling and panel has been reloaded.
	 */
	protected void handleStoppedPolling() {
		// Stopped polling
		pollingContainer.setVisible(false);
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
			extractExceptionMessages("Error loading data. ", getMessages(), excp);
			retryButton.setVisible(true);
			// Log error
			LOG.error("Error loading data. " + excp.getMessage());
			// Status
			setPollingStatus(PollingStatus.ERROR);
			dispatchEvent(new Event(this, PollingEvent.ERROR, excp));
		} else {
			// Successful Result
			T result = (T) pollingResult;
			handleSuccessfulResult(result);
			contentResultHolder.setVisible(true);
			// Status
			setPollingStatus(PollingStatus.COMPLETE);
			dispatchEvent(new Event(this, PollingEvent.COMPLETE, result));
		}
	}

	/**
	 * Handle the result. Default behaviour is to set the result as the bean for the content.
	 *
	 * @param result the polling action result
	 */
	protected void handleSuccessfulResult(final T result) {
		// Set the result as the bean
		root.setBean(result);
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
	 * @return true if polling and is the current AJAX trigger.
	 */
	protected boolean isPollingTarget() {
		return isPolling() && AjaxHelper.isCurrentAjaxTrigger(ajaxPolling);
	}

	/**
	 * @return true if currently polling
	 */
	protected boolean isPolling() {
		return pollingContainer.isVisible();
	}

	/**
	 * Check the future holds the result.
	 */
	protected void checkForResult() {

		// Get the Future for the polling action
		TaskFuture<PollingResultHolder> future = getFuture();
		if (future == null) {
			// Stop polling as future must have expired
			handleResult(new PollingException("Future has expired so polling result not available"));
			handleStopPolling();
			return;
		}

		// Check if finished processing
		if (!future.isDone()) {
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
		// Stop polling
		handleStopPolling();
	}

	/**
	 * Handle the polling action.
	 *
	 * @param criteria the criteria for the polling action
	 * @throws PollingException the polling exception
	 */
	protected void handleAsyncPollingAction(final S criteria) throws PollingException {

		clearFuture();

		final PollingResultHolder result = new PollingResultHolder();
		Runnable task = new Runnable() {
			@Override
			public void run() {
				try {
					T resp = doPollingAction(criteria);
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
	 * @return the script to step the progress bar
	 */
	protected String buildProgressBarScript() {
		StringBuilder script = new StringBuilder();
		script.append("<script type='text/javascript'>");
		script.append("function startStepProgressBar() {");
		script.append("  var elem = document.getElementById('").append(pollingProgressBar.getId()).append("');");
		script.append("  window.setInterval(stepProgressBar, 250, elem);");
		script.append("}");
		script.append("function stepProgressBar(bar) {");
		script.append("   if (bar.value > 99) { bar.value = 0; }");
		script.append("   bar.value++;");
		script.append("}");
		script.append("window.setTimeout(startStepProgressBar, 1000);");
		script.append("</script>");
		return script.toString();
	}

	/**
	 * Dispatch the evvent.
	 */
	protected void dispatchEvent(final Event event) {
		getDispatcher().dispatch(event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PollingPanelModel<S> newComponentModel() {
		return new PollingPanelModel<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PollingPanelModel<S> getOrCreateComponentModel() {
		return (PollingPanelModel<S>) super.getOrCreateComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PollingPanelModel<S> getComponentModel() {
		return (PollingPanelModel<S>) super.getComponentModel();
	}

	/**
	 * This model holds the state information.
	 *
	 * @param <S> the criteria type
	 */
	public static class PollingPanelModel<S> extends ViewModel {

		/**
		 * Record id.
		 */
		private S criteria;

		/**
		 * Polling status.
		 */
		private PollingStatus pollingStatus = PollingStatus.NOT_STARTED;

		/**
		 * Polling text.
		 */
		private String pollingText = "Loading....";

		/**
		 * Holds the reference to the future for the polling action.
		 */
		private TaskFuture<PollingResultHolder> future;

		/**
		 * The polling interval in milli seconds.
		 */
		private int pollingInterval;

		/**
		 * Flag if start polling manually with the start button.
		 */
		private boolean manualStart;

		/**
		 * Extra AJAX targets when polling stops.
		 */
		private List<AjaxTarget> extraTargets;
	}

}
