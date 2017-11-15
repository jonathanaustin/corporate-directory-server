package com.github.bordertech.wcomponents.lib.app.view.polling;

import com.github.bordertech.flux.event.ViewEventType;
import com.github.bordertech.flux.view.DefaultDumbView;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.wcomponents.lib.app.view.event.PollingViewEvent;
import com.github.bordertech.wcomponents.lib.app.view.event.base.PollingBaseViewEvent;
import com.github.bordertech.wcomponents.polling.PollingPanel;
import com.github.bordertech.wcomponents.polling.PollingStatus;
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

	protected void doDispatchPollingEvent(final PollingViewEvent pollingEvent) {
		dispatchViewEvent(pollingEvent);
	}

}
