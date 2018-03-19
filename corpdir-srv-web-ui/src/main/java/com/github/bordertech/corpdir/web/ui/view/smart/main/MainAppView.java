package com.github.bordertech.corpdir.web.ui.view.smart.main;

import com.github.bordertech.corpdir.web.ui.CardType;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.event.CardEventType;
import com.github.bordertech.corpdir.web.ui.view.dumb.toolbar.MainToolbarSecureView;
import com.github.bordertech.didums.Didums;
import com.github.bordertech.flux.ActionCreator;
import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.common.TemplateConstants;
import com.github.bordertech.flux.wc.view.smart.msg.DefaultMessageSmartView;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WTemplate;

/**
 * Main Application View.
 *
 * @author jonathan
 */
public class MainAppView extends DefaultMessageSmartView {

	private final MainCardManagerView cardView = new MainCardManagerView("MNC");
	private final MainToolbarSecureView toolbar = new MainToolbarSecureView("MNT");

	public MainAppView() {
		super("MNV", TemplateConstants.TEMPLATE_APP_MAIN, false);
		cardView.addHtmlClass("wc-margin-all-lg");

		WTemplate content = getContent();
		content.addTaggedComponent(TemplateConstants.TAG_VW_TOOLBAR, toolbar);
		content.addTaggedComponent(TemplateConstants.TAG_VW_MAIN, cardView);
	}

	public String getCardTitle() {
		CardType card = cardView.getCurrentType();
		return card == null ? "" : card.getDesc();
	}

	@Override
	protected void initViewContent(Request request) {
		super.initViewContent(request);
		// Register Stores and Action Creators (for this user session)

		Dispatcher dispatcher = getDispatcher();
		for (CorpEntityType api : CorpEntityType.values()) {

			// Action Creator
			if (api.isActionCreator()) {
				ActionCreator creator = Didums.getService(api.getActionCreatorClass());
				dispatcher.registerActionCreator(creator);
			}

			// Store
			if (api.isStore()) {
				Store store = Didums.getService(api.getStoreClass());
				dispatcher.registerStore(store);
			}
		}
	}

	@Override
	protected void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
		super.handleViewEvent(viewId, event, data);
		if (isEvent(CardEventType.RESET, event)) {
			handleResetCard((CardType) data);
		} else if (isEvent(CardEventType.SHOW, event)) {
			handleShowCard((CardType) data);
		}
	}

	protected void handleShowCard(final CardType card) {
		cardView.showCard(card);
	}

	protected void handleResetCard(final CardType card) {
		cardView.resetCard(card);
	}

}
