package com.github.bordertech.flux.wc.app.view.dumb.polling;

import com.github.bordertech.flux.view.DefaultDumbView;
import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.app.view.PollingView;
import com.github.bordertech.flux.wc.app.view.event.PollingViewEvent;
import com.github.bordertech.flux.wc.app.view.event.base.PollingBaseViewEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.lib.polling.PollingPanel;
import com.github.bordertech.wcomponents.lib.polling.PollingStartType;
import com.github.bordertech.wcomponents.lib.polling.PollingStatus;
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
		protected void handleInitContent(final Request request) {
			if (getStartType() == PollingStartType.AUTOMATIC) {
				doDispatchPollingEvent(PollingBaseViewEvent.START_AUTO);
			}
			super.handleInitContent(request);
		}

		@Override
		protected void handleStartButton() {
			doDispatchPollingEvent(PollingBaseViewEvent.START_BUTTON);
			super.handleStartButton();
		}

		@Override
		protected void handleRetryButton() {
			doDispatchPollingEvent(PollingBaseViewEvent.START_RETRY);
			super.handleRetryButton();
		}

		@Override
		protected void handleStartedPolling() {
			super.handleStartedPolling();
			doDispatchPollingEvent(PollingBaseViewEvent.STARTED);
		}

		@Override
		protected void handleStoppedPolling() {
			super.handleStoppedPolling();
			doDispatchPollingEvent(PollingBaseViewEvent.STOPPED);
		}

		@Override
		protected boolean checkForStopPolling() {
			doDispatchPollingEvent(PollingBaseViewEvent.CHECK_STATUS);
			return super.checkForStopPolling();
		}

	};

	public DefaultPollingView(final String viewId) {
		super(viewId);
		getContent().add(pollingPanel);
		// Default visibility
		setContentVisible(false);
		addAjaxTarget(this);
	}

	@Override
	public void addEventAjaxTarget(final AjaxTarget target, final ViewEventType... eventType) {
		super.addEventAjaxTarget(target, eventType);
		addAjaxTarget((AjaxTarget) target);
	}

	public final PollingPanel getPollingPanel() {
		return pollingPanel;
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

	protected void doDispatchPollingEvent(final PollingViewEvent pollingEvent) {
		dispatchViewEvent(pollingEvent);
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

}
