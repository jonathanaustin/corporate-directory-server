package com.github.bordertech.flux.wc.view.smart.polling;

import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.common.TemplateConstants;
import com.github.bordertech.flux.wc.view.DefaultSmartView;
import com.github.bordertech.flux.wc.view.dumb.PollingView;
import com.github.bordertech.flux.wc.view.dumb.polling.DefaultPollingView;
import com.github.bordertech.flux.wc.view.event.base.PollingBaseEventType;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.lib.polling.PollingStartType;
import com.github.bordertech.wcomponents.lib.polling.PollingStatus;
import java.util.List;

/**
 * Smart View that contains a polling view.
 *
 * @author jonathan
 * @param <T> the item type
 */
public class DefaultPollingSmartView<T> extends DefaultSmartView<T> implements PollingSmartView<T> {

	private final PollingView pollingView = new DefaultPollingView<>("vw_poll");

	public DefaultPollingSmartView(final String viewId, final String templateName) {
		super(viewId, templateName);
		addComponentToTemplate(TemplateConstants.TAG_VW_POLL, pollingView);
	}

	@Override
	public PollingView<T> getPollingView() {
		return pollingView;
	}

	@Override
	protected void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
		super.handleViewEvent(viewId, event, data);
		if (event instanceof PollingBaseEventType) {
			handlePollingBaseEvents((PollingBaseEventType) event, data);
		}
	}

	protected void handlePollingBaseEvents(final PollingBaseEventType type, final Object data) {

		switch (type) {
			case START_AUTO:
			case START_BUTTON:
			case START_RETRY:
			case START_MANUAL:
				handlePollingStartEvent(type);
				break;
			case STARTED:
				handlePollingStartedEvent();
				break;
			case STOPPED:
				handlePollingStoppedEvent();
				break;
			case TIMEOUT:
				handlePollingTimeoutEvent();
				break;
			case CHECK_STATUS:
				handlePollingCheckStatusEvent();
				break;
		}

	}

	protected void handlePollingStartEvent(final PollingBaseEventType type) {
	}

	protected void handlePollingStartedEvent() {
	}

	protected void handlePollingStoppedEvent() {
	}

	protected void handlePollingTimeoutEvent() {
	}

	protected void handlePollingCheckStatusEvent() {
	}

	@Override
	public List<AjaxTarget> getAjaxTargets() {
		return pollingView.getAjaxTargets();
	}

	@Override
	public void addAjaxTarget(final AjaxTarget target) {
		pollingView.addAjaxTarget(target);
	}

	@Override
	public void doManualStart() {
		pollingView.doManualStart();
	}

	@Override
	public void doRetry() {
		pollingView.doRetry();
	}

	@Override
	public void doRefreshContent() {
		pollingView.doRefreshContent();
	}

	@Override
	public int getPollingInterval() {
		return pollingView.getPollingInterval();
	}

	@Override
	public PollingStatus getPollingStatus() {
		return pollingView.getPollingStatus();
	}

	@Override
	public String getPollingText() {
		return pollingView.getPollingText();
	}

	@Override
	public void setPollingInterval(final int interval) {
		pollingView.setPollingInterval(interval);
	}

	@Override
	public void setPollingStatus(final PollingStatus pollingStatus) {
		pollingView.setPollingStatus(pollingStatus);
	}

	@Override
	public void setPollingText(final String text) {
		pollingView.setPollingText(text);
	}

	@Override
	public WButton getRetryButton() {
		return pollingView.getRetryButton();
	}

	@Override
	public WButton getStartButton() {
		return pollingView.getStartButton();
	}

	@Override
	public PollingStartType getStartType() {
		return pollingView.getStartType();
	}

	@Override
	public void setStartType(final PollingStartType startType) {
		pollingView.setStartType(startType);
	}

	@Override
	public void doShowRetry() {
		pollingView.doShowRetry();
	}

	@Override
	public void setPollingTimeout(final int pollingTimeout) {
		pollingView.setPollingTimeout(pollingTimeout);
	}

	@Override
	public int getPollingTimeout() {
		return pollingView.getPollingTimeout();
	}

	@Override
	public void setUseRetryOnError(boolean useRetryOnError) {
		pollingView.setUseRetryOnError(useRetryOnError);
	}

	@Override
	public boolean isUseRetryOnError() {
		return pollingView.isUseRetryOnError();
	}

	@Override
	public void setContineStart(final boolean start) {
		pollingView.setContineStart(start);
	}

	@Override
	public boolean isContinueStart() {
		return pollingView.isContinueStart();
	}

}
