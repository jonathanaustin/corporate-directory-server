package com.github.bordertech.wcomponents.lib.polling;

import com.github.bordertech.wcomponents.AjaxHelper;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WProgressBar;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultView;
import java.util.ArrayList;
import java.util.List;

/**
 * This panel is used to poll via AJAX and also do a Reload of Views via AJAX.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class PollingView extends DefaultView {

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
	 * The container that holds the AJAX control.
	 */
	private final WDiv ajaxPollingPanel = new WDiv() {
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
	 * AJAX control to reload whole view and any other views.
	 */
	private final WAjaxControl ajaxReload = new WAjaxControl(null, this) {
		@Override
		protected void preparePaintComponent(final Request request) {
			super.preparePaintComponent(request);
			// Reloading
			if (AjaxHelper.isCurrentAjaxTrigger(this)) {
				pollingContainer.reset();
				handleStoppedPolling();
			}
		}
	};

	public PollingView(final Dispatcher disptacher) {
		this(disptacher, 174);
	}

	/**
	 * Construct polling panel.
	 *
	 * @param dispatcher the view controller
	 * @param delay the delay for polling
	 */
	public PollingView(final Dispatcher dispatcher, final int delay) {
		super(dispatcher);

		WDiv holder = getContent();
		holder.setSearchAncestors(false);

		// AJAX polling details
		setPollingInterval(delay);
		ajaxPolling.setLoadOnce(true);
		ajaxReload.setLoadOnce(true);
		ajaxReload.setDelay(10);
		progressBarScript.setEncodeText(false);

		// Polling container is outside AJAX panel so it does not pulse)
		holder.add(pollingContainer);
		pollingContainer.add(pollingText);
		pollingContainer.add(pollingProgressBar);
		pollingContainer.add(progressBarScript);
		pollingContainer.add(ajaxPollingPanel);
		ajaxPollingPanel.add(ajaxPolling);
		ajaxPollingPanel.add(ajaxReload);

		// Set default visibility
		pollingContainer.setVisible(false);
		ajaxPolling.setVisible(false);
		ajaxReload.setVisible(false);
	}

	/**
	 * @return the AJAX polling interval in milli seconds
	 */
	public int getPollingInterval() {
		return getComponentModel().pollingInterval;
	}

	/**
	 *
	 * @param interval the AJAX polling interval in milli seconds
	 */
	public final void setPollingInterval(final int interval) {
		getOrCreateComponentModel().pollingInterval = interval;
	}

	/**
	 * @param text the text displayed while polling
	 */
	public void setPollingText(final String text) {
		getOrCreateComponentModel().pollingText = text;
	}

	/**
	 * @return the text displayed while polling
	 */
	public String getPollingText() {
		return getComponentModel().pollingText;
	}

	/**
	 * @param panelStatus the panel status
	 */
	public void setPollingStatus(final PollingStatus panelStatus) {
		getOrCreateComponentModel().pollingStatus = panelStatus;
	}

	/**
	 * @return the panel status
	 */
	public PollingStatus getPollingStatus() {
		return getComponentModel().pollingStatus;
	}

	/**
	 * Start AJAX polling.
	 */
	public void doStartPolling() {
		// Start AJAX polling
		setPollingStatus(PollingStatus.STARTED);
		pollingContainer.reset();
		pollingContainer.setVisible(true);
		ajaxPolling.setVisible(true);
		dispatchViewEvent(PollingEventType.STARTED);
	}

	/**
	 * Do AJAX Reload.
	 */
	public void doReload() {
		boolean alreadyPolling = isPolling();
		pollingContainer.reset();
		List<AjaxTarget> targets = getAjaxTargets();
		if (targets != null && !targets.isEmpty()) {
			ajaxReload.addTargets(targets);
		}
		if (!alreadyPolling) {
			dispatchViewEvent(PollingEventType.STARTED);
		}
		pollingContainer.setVisible(true);
		ajaxReload.setVisible(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		// Check if Polling
		if (isPollingTarget() && checkForStopPolling()) {
			doReload();
		}
	}

	@Override
	protected void initViewContent(final Request request) {
		super.initViewContent(request);
	}

	@Override
	public void addEventTarget(final AjaxTarget target, final EventType... eventType) {
		addAjaxTarget(target);
	}

	protected List<AjaxTarget> getAjaxTargets() {
		return getComponentModel().extraTargets;
	}

	protected void addAjaxTarget(final AjaxTarget target) {
		PollingModel model = getOrCreateComponentModel();
		if (model.extraTargets == null) {
			model.extraTargets = new ArrayList();
		}
		if (!model.extraTargets.contains(target)) {
			model.extraTargets.add(target);
		}
	}

	/**
	 *
	 * @return true if need to stop polling
	 */
	protected boolean checkForStopPolling() {
		PollingStatus status = getPollingStatus();
		return status != PollingStatus.STARTED;
	}

	/**
	 * Stopped polling and panel has been reloaded.
	 */
	protected void handleStoppedPolling() {
		dispatchViewEvent(PollingEventType.COMPLETE);
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
	 * {@inheritDoc}
	 */
	@Override
	protected PollingModel newComponentModel() {
		return new PollingModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PollingModel getOrCreateComponentModel() {
		return (PollingModel) super.getOrCreateComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PollingModel getComponentModel() {
		return (PollingModel) super.getComponentModel();
	}

	/**
	 * This model holds the state information.
	 */
	public static class PollingModel extends ViewModel {

		/**
		 * Polling status.
		 */
		private PollingStatus pollingStatus = PollingStatus.NOT_STARTED;

		/**
		 * Polling text.
		 */
		private String pollingText = "Loading....";

		/**
		 * The polling interval in milli seconds.
		 */
		private int pollingInterval;

		/**
		 * Extra AJAX targets when polling stops.
		 */
		private List<AjaxTarget> extraTargets;
	}

}
