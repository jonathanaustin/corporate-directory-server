package com.github.bordertech.wcomponents.lib.mvc.impl;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.impl.DefaultEvent;
import com.github.bordertech.wcomponents.lib.flux.impl.EventQualifier;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.mvc.ComboView;
import com.github.bordertech.wcomponents.lib.mvc.View;
import com.github.bordertech.wcomponents.lib.mvc.msg.MsgEvent;
import com.github.bordertech.wcomponents.lib.mvc.msg.MsgEventType;
import com.github.bordertech.wcomponents.template.TemplateRendererFactory;
import com.github.bordertech.wcomponents.validation.Diagnostic;
import java.util.ArrayList;
import java.util.List;
import com.github.bordertech.wcomponents.lib.mvc.msg.MessageComboView;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public abstract class AbstractView extends WTemplate implements View {

	private final Dispatcher dispatcher;

	private final String qualifier;

	public AbstractView(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public AbstractView(final Dispatcher dispatcher, final String qualifier) {
		super("wclib/hbs/layout/default-view.hbs", TemplateRendererFactory.TemplateEngine.HANDLEBARS);
		this.dispatcher = dispatcher;
		this.qualifier = qualifier;
		setSearchAncestors(false);
		setBeanProperty(".");
	}

	@Override
	public final Dispatcher getDispatcher() {
		return dispatcher;
	}

	@Override
	public final String getQualifier() {
		return qualifier;
	}

	@Override
	public void resetContent() {
		getContent().reset();
	}

	@Override
	public boolean isContentVisible() {
		return getComponentModel().contentVisible;
	}

	@Override
	public void setContentVisible(final boolean visible) {
		if (isContentVisible() != visible) {
			getOrCreateComponentModel().contentVisible = visible;
		}
	}

	@Override
	public void resetView() {
		reset();
	}

	@Override
	public boolean isHidden() {
		return super.isHidden() || !isContentVisible();
	}

	@Override
	public void addEventTarget(final AjaxTarget target, final EventType... eventType) {
		// Do Nothing
	}

	@Override
	public boolean validateView() {

		// Validate content
		List<Diagnostic> diags = new ArrayList<>();
		WComponent content = getContent();
		content.validate(diags);
		content.showWarningIndicators(diags);
		content.showErrorIndicators(diags);

		// Check if contains errors
		if (containsError(diags)) {
			dispatchValidationMessages(diags);
			return false;
		} else {
			dispatchMessageReset();
			return true;
		}
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		setupTemplateParameters();
	}

	protected void setupTemplateParameters() {
		addParameter("vw-id", getId());
		addParameter("vw-class", getHtmlClass());
		addParameter("vw-hidden", isHidden() ? "hidden=\"true\"" : "");
	}

	/**
	 * Helper method to dispatch an event for this view with the view qualifier automatically added.
	 *
	 * @param eventType the event type
	 */
	protected void dispatchViewEvent(final EventType eventType) {
		dispatchViewEvent(eventType, null, null);
	}

	/**
	 * Helper method to dispatch an event for this view with the view qualifier automatically added.
	 *
	 * @param eventType the event type
	 * @param data the event data
	 */
	protected void dispatchViewEvent(final EventType eventType, final Object data) {
		dispatchViewEvent(eventType, data, null);
	}

	/**
	 * Helper method to dispatch an event for this view with the view qualifier automatically added.
	 *
	 * @param eventType the event type
	 * @param data the event data
	 * @param exception an exception
	 */
	protected void dispatchViewEvent(final EventType eventType, final Object data, final Exception exception) {
		DefaultEvent event = new DefaultEvent(new EventQualifier(eventType, getQualifier()), data, exception);
		getDispatcher().dispatch(event);
	}

	protected void dispatchMessageReset() {
		dispatchMessageEvent(new MsgEvent(MsgEventType.RESET, ""));
	}

	protected void dispatchValidationMessages(final List<Diagnostic> diags) {
		dispatchMessageEvent(new MsgEvent(diags));
	}

	protected void dispatchMessage(final MsgEventType type, final String text) {
		dispatchMessageEvent(new MsgEvent(type, text));
	}

	protected void dispatchMessage(final MsgEventType type, final List<String> texts) {
		dispatchMessageEvent(new MsgEvent(type, texts, true));
	}

	@Override
	public void dispatchMessageEvent(final MsgEvent event) {
		MessageComboView combo = WebUtilities.getClosestOfClass(MessageComboView.class, this);
		if (combo != null) {
			combo.handleMessageEvent(event);
		}
	}

	/**
	 * Helper method to add target to an Ajax Control.
	 *
	 * @param ajax the AJAX control
	 * @param target the AJAX target
	 */
	protected void addEventTargetsToAjaxCtrl(final WAjaxControl ajax, final AjaxTarget target) {
		// Make Sure the Targets have not already been added
		List<AjaxTarget> current = ajax.getTargets();
		if (!current.contains(target)) {
			ajax.addTarget(target);
		}
	}

	protected void initViewContent(final Request request) {
		ComboView combo = findParentCombo();
		if (combo != null) {
			combo.configAjax(this);
		}
	}

	protected ComboView findParentCombo() {
		return WebUtilities.getAncestorOfClass(ComboView.class, this);
	}

	protected MessageComboView findComboMessageView() {
		return WebUtilities.getAncestorOfClass(MessageComboView.class, this);
	}

	@Override
	protected ViewModel newComponentModel() {
		return new ViewModel();
	}

	@Override
	protected ViewModel getComponentModel() {
		return (ViewModel) super.getComponentModel();
	}

	@Override
	protected ViewModel getOrCreateComponentModel() {
		return (ViewModel) super.getOrCreateComponentModel();
	}

	/**
	 * Just here as a place holder and easier for other Views to extend.
	 */
	public static class ViewModel extends TemplateModel {

		private boolean contentVisible = true;
	}

	/**
	 * Borrowed from ValidatinAction (Should be public). Indicates whether the given list of diagnostics contains any
	 * errors.
	 *
	 * @param diags the list into which any validation diagnostics were added.
	 * @return true if any of the diagnostics in the list are errors, false otherwise.
	 */
	private static boolean containsError(final List<Diagnostic> diags) {
		if (diags == null || diags.isEmpty()) {
			return false;
		}

		for (Diagnostic diag : diags) {
			if (diag.getSeverity() == Diagnostic.ERROR) {
				return true;
			}
		}

		return false;
	}
}
