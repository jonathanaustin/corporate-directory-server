package com.github.bordertech.wcomponents.lib.app.view.polling;

import com.github.bordertech.flux.EventType;
import com.github.bordertech.flux.wc.view.DefaultAppView;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.lib.app.event.PollingEventType;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.wcomponents.polling.PollingServicePanel;
import com.github.bordertech.wcomponents.polling.PollingStatus;
import com.github.bordertech.wcomponents.polling.ServiceAction;
import java.util.List;

/**
 * Default polling view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultPollingView<S, T> extends DefaultAppView<T> implements PollingView<S, T> {

	private final PollingServicePanel<S, T> pollingPanel = new PollingServicePanel<S, T>() {
		@Override
		protected void handleStartedPolling() {
			super.handleStartedPolling();
			doDispatchPollingEvent(PollingEventType.STARTED);
		}

		@Override
		protected void handleExceptionResult(final Exception excp) {
			super.handleExceptionResult(excp);
			doDispatchPollingEvent(PollingEventType.ERROR, getPollingCriteria(), excp);
		}

		@Override
		protected void handleSuccessfulResult(final T result) {
			super.handleSuccessfulResult(result);
			doDispatchPollingEvent(PollingEventType.COMPLETE, result, null);
		}

		@Override
		protected void handleErrorMessage(final List<String> msgs) {
			doHandlePollingError(msgs);
		}
	};

	public DefaultPollingView() {
		getContent().add(pollingPanel);
		// Default visibility
		setContentVisible(false);
		addAjaxTarget(this);
	}

	@Override
	public void addEventAjaxTarget(final AjaxTarget target, final EventType... eventType) {
		super.addEventAjaxTarget(target, eventType);
		addAjaxTarget((AjaxTarget) target);
	}

	public final PollingServicePanel getPollingPanel() {
		return pollingPanel;
	}

	@Override
	public void doManuallyLoadResult(final S criteria, final T result) {
		pollingPanel.doManuallyLoadResult(criteria, result);
	}

	@Override
	public void doRefreshContent() {
		pollingPanel.doRefreshContent();
	}

	@Override
	public void doRetry() {
		pollingPanel.doRetry();
	}

	@Override
	public void doStartLoading() {
		pollingPanel.doStartLoading();
	}

	@Override
	public S getPollingCriteria() {
		return pollingPanel.getPollingCriteria();
	}

	@Override
	public T getPollingResult() {
		return pollingPanel.getPollingResult();
	}

	@Override
	public WButton getRetryButton() {
		return pollingPanel.getRetryButton();
	}

	@Override
	public WButton getStartButton() {
		return pollingPanel.getStartButton();
	}

	@Override
	public boolean isManualStart() {
		return pollingPanel.isManualStart();
	}

	@Override
	public void setManualStart(final boolean manualStart) {
		pollingPanel.setManualStart(manualStart);
	}

	@Override
	public void setPollingCriteria(final S criteria) {
		pollingPanel.setPollingCriteria(criteria);
	}

	@Override
	public List<AjaxTarget> getAjaxTargets() {
		return pollingPanel.getAjaxTargets();
	}

	@Override
	public void addAjaxTarget(final AjaxTarget target) {
		pollingPanel.addAjaxTarget(target);
	}

	@Override
	public void doReload() {
		pollingPanel.doReload();
	}

	@Override
	public void doStartPolling() {
		pollingPanel.doStartPolling();
	}

	@Override
	public int getPollingInterval() {
		return pollingPanel.getPollingInterval();
	}

	@Override
	public PollingStatus getPollingStatus() {
		return pollingPanel.getPollingStatus();
	}

	@Override
	public String getPollingText() {
		return pollingPanel.getPollingText();
	}

	@Override
	public void setPollingInterval(final int interval) {
		pollingPanel.setPollingInterval(interval);
	}

	@Override
	public void setPollingStatus(final PollingStatus panelStatus) {
		pollingPanel.setPollingStatus(panelStatus);
	}

	@Override
	public void setPollingText(final String text) {
		pollingPanel.setPollingText(text);
	}

	@Override
	public ServiceAction<S, T> getServiceAction() {
		return pollingPanel.getServiceAction();
	}

	@Override
	public void setServiceAction(final ServiceAction<S, T> serviceModel) {
		pollingPanel.setServiceAction(serviceModel);
	}

	protected void doDispatchPollingEvent(final PollingEventType pollingEvent) {
		doDispatchPollingEvent(pollingEvent, null, null);

	}

	protected void doDispatchPollingEvent(final PollingEventType pollingEvent, final Object data, final Exception excp) {
		dispatchEvent(pollingEvent, data, excp);
	}

	protected void doHandlePollingError(final List<String> msgs) {
		for (String msg : msgs) {
			getViewMessages().error(msg);
		}
	}

	@Override
	public void doSetupAndStartPolling(final S criteria, final ServiceAction<S, T> serviceModel) {
		resetView();
		setPollingCriteria(criteria);
		// Wrap the service model

		setServiceAction(serviceModel);
		// Making the panel visible starts the polling
		setContentVisible(true);
	}

}
