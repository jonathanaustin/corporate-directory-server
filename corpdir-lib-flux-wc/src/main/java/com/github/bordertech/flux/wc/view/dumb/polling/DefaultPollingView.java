package com.github.bordertech.flux.wc.view.dumb.polling;

import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.view.DefaultDumbView;
import com.github.bordertech.flux.wc.view.dumb.PollingView;
import com.github.bordertech.flux.wc.view.event.PollingEventType;
import com.github.bordertech.flux.wc.view.event.base.PollingBaseEventType;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WProgressBar;
import com.github.bordertech.wcomponents.addons.common.WDiv;
import com.github.bordertech.wcomponents.addons.polling.PollingPanel;
import com.github.bordertech.wcomponents.addons.polling.PollingStartType;
import com.github.bordertech.wcomponents.addons.polling.PollingStatus;
import java.util.List;

/**
 * Default polling view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultPollingView<T> extends DefaultDumbView<T> implements PollingView<T> {

	private final PollingPanel pollingPanel = new PollingPanel() {

		@Override
		protected void handleInitPollingPanel(final Request request) {
			if (getStartType() == PollingStartType.AUTOMATIC) {
				doDispatchPollingEvent(PollingBaseEventType.START_AUTO);
			}
			super.handleInitPollingPanel(request);
		}

		@Override
		protected void handleStartButton() {
			doDispatchPollingEvent(PollingBaseEventType.START_BUTTON);
			super.handleStartButton();
		}

		@Override
		protected void handleRetryButton() {
			doDispatchPollingEvent(PollingBaseEventType.START_RETRY);
			super.handleRetryButton();
		}

		@Override
		public void doManualStart() {
			doDispatchPollingEvent(PollingBaseEventType.START_MANUAL);
			super.doManualStart();
		}

		@Override
		protected void doStartPolling() {
			if (isContinueStart()) {
				super.doStartPolling();
			}
		}

		@Override
		protected void handleStartedPolling() {
			super.handleStartedPolling();
			dispatchMessageReset();
			doDispatchPollingEvent(PollingBaseEventType.STARTED);
		}

		@Override
		protected void handleStoppedPolling() {
			super.handleStoppedPolling();
			doDispatchPollingEvent(PollingBaseEventType.STOPPED);
		}

		@Override
		protected void handleTimeoutPolling() {
			dispatchMessageError("Polling timeout.");
			doDispatchPollingEvent(PollingBaseEventType.TIMEOUT);
			if (isUseRetryOnError()) {
				doShowRetry();
			}
		}

		@Override
		protected boolean checkForStopPolling() {
			doDispatchPollingEvent(PollingBaseEventType.CHECK_STATUS);
			return super.checkForStopPolling();
		}

	};

	public DefaultPollingView(final String viewId) {
		super(viewId);
		getContent().add(pollingPanel);
		// Default visibility
		addAjaxTarget(this);
	}

	@Override
	public void addEventAjaxTarget(final AjaxTarget target, final ViewEventType... eventType) {
		super.addEventAjaxTarget(target, eventType);
		addAjaxTarget((AjaxTarget) target);
	}

	protected void doDispatchPollingEvent(final PollingEventType pollingEvent) {
		dispatchViewEvent(pollingEvent);
	}

	@Override
	public boolean isContinueStart() {
		Boolean flag = (Boolean) pollingPanel.getAttribute("wc-cont");
		// Default to true if no flag
		return flag == null || flag;
	}

	@Override
	public void setContineStart(final boolean start) {
		// Store the state on the polling panel. So it gets reset at the same time as the polling panel.
		pollingPanel.setAttribute("wc-cont", start);
	}

	public final PollingPanel getPollingPanel() {
		return pollingPanel;
	}

	@Override
	public WDiv getContentHolder() {
		return pollingPanel.getContentHolder();
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
	public void doManualStart() {
		pollingPanel.doManualStart();
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
	public void doShowRetry() {
		pollingPanel.doShowRetry();
	}

	@Override
	public void doRetry() {
		pollingPanel.doRetry();
	}

	@Override
	public void doRefreshContent() {
		pollingPanel.doRefreshContent();
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
	public PollingStartType getStartType() {
		return pollingPanel.getStartType();
	}

	@Override
	public void setStartType(final PollingStartType startType) {
		pollingPanel.setStartType(startType);
	}

	@Override
	public void setPollingTimeout(final int pollingTimeout) {
		pollingPanel.setPollingTimeout(pollingTimeout);
	}

	@Override
	public int getPollingTimeout() {
		return pollingPanel.getPollingTimeout();
	}

	@Override
	public void setUseRetryOnError(boolean useRetryOnError) {
		pollingPanel.setUseRetryOnError(useRetryOnError);
	}

	@Override
	public boolean isUseRetryOnError() {
		return pollingPanel.isUseRetryOnError();
	}

	@Override
	public WProgressBar getProgressBar() {
		return pollingPanel.getProgressBar();
	}

}
