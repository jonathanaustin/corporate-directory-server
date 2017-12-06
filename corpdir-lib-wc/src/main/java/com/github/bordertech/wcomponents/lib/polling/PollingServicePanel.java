package com.github.bordertech.wcomponents.lib.polling;

import com.github.bordertech.taskmanager.TaskFuture;
import com.github.bordertech.taskmanager.service.ResultHolder;
import com.github.bordertech.taskmanager.service.ServiceAction;
import com.github.bordertech.taskmanager.service.ServiceUtil;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WDiv;
import java.io.Serializable;
import java.util.UUID;
import javax.cache.Cache;
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
		getHolder().add(contentResultHolder);
		contentResultHolder.setVisible(false);
	}

	public final WDiv getContentResultHolder() {
		return contentResultHolder;
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
	public void doStartPolling() {
		// Check not started
		if (getPollingStatus() == PollingStatus.PROCESSING) {
			return;
		}

		// Check we have a criteria
		final S criteria = getPollingCriteria();
		if (criteria == null) {
			handleErrorMessage("No criteria set for polling action.");
			return;
		}

		handleStartPollingAction(criteria);

		super.doStartPolling();
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
		super.doRefreshContent();
	}

	@Override
	public void doManuallyLoadResult(final S criteria, final T result) {
		if (result == null || criteria == null) {
			return;
		}
		getHolder().reset();
		getStartButton().setVisible(false);
		setPollingCriteria(criteria);
		handleResult(new ResultHolder(result));
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
		ServiceUtil.handleAsyncServiceCall(getPollingCache(), key, criteria, getServiceAction());
	}

	/**
	 * Stopped polling and view has been reloaded.
	 */
	@Override
	protected void handleStoppedPolling() {
		String key = getServiceKey();
		ResultHolder result = ServiceUtil.getResultHolder(getPollingCache(), key);
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
	 */
	protected void handleResult(final ResultHolder<S, T> resultHolder) {
		// Exception message
		if (resultHolder.isException()) {
			Exception excp = resultHolder.getException();
			handleExceptionResult(excp);
			// Log error
			LOG.error("Error loading data. " + excp.getMessage());
		} else {
			// Successful Result
			T result = resultHolder.getResult();
			handleSuccessfulResult(result);
		}
	}

	/**
	 * Handle an exception occurred.
	 *
	 * @param excp the exception that occurred
	 */
	protected void handleExceptionResult(final Exception excp) {
		handleErrorMessage(excp.getMessage());
		doShowRetry();
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
		if (ServiceUtil.checkASyncResult(getPollingCache(), getServiceKey()) != null) {
			setPollingStatus(PollingStatus.STOPPED);
		}
		return super.checkForStopPolling();
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
			ServiceUtil.clearResult(getPollingCache(), key);
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
	 * Use a cache to hold a reference to the future so the user context can be serialized. Future Objects are not
	 * serializable.
	 *
	 * @return the cache instance
	 */
	protected synchronized Cache<String, TaskFuture> getPollingCache() {
		return ServiceUtil.getFutureCache("wc-polling-service-default-future-cache");
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
		 * Service action.
		 */
		private ServiceAction<S, T> serviceAction;
	}

}
