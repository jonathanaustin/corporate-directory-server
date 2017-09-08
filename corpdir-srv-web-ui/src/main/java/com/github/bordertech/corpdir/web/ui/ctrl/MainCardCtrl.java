package com.github.bordertech.corpdir.web.ui.ctrl;

import com.github.bordertech.corpdir.web.ui.event.CardEventType;
import com.github.bordertech.corpdir.web.ui.event.CardType;
import com.github.bordertech.corpdir.web.ui.view.MainCardView;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.mvc.impl.DefaultController;

/**
 * Main Card Controller.
 *
 * @author jonathan
 */
public class MainCardCtrl extends DefaultController {

	@Override
	public void setupListeners() {
		super.setupListeners();
		// Listeners

		// SHOW CARD EVENT
		Listener listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleShowCard((CardType) event.getData());
			}
		};
		registerListener(listener, CardEventType.SHOW);

		// REST CARD
		listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleResetCard((CardType) event.getData());
			}
		};
		registerListener(listener, CardEventType.RESET);

	}

	@Override
	public void checkConfig() {
		super.checkConfig();
		if (getCardView() == null) {
			throw new IllegalStateException("A card view has not been set.");
		}
	}

	public final MainCardView getCardView() {
		return getComponentModel().cardView;
	}

	public final void setCardView(final MainCardView cardView) {
		getOrCreateComponentModel().cardView = cardView;
		addView(cardView);
	}

	protected void handleShowCard(final CardType card) {
		getCardView().showCard(card);
	}

	protected void handleResetCard(final CardType card) {
		getCardView().resetCard(card);
	}

	@Override
	protected CardCtrlModel newComponentModel() {
		return new CardCtrlModel();
	}

	@Override
	protected CardCtrlModel getComponentModel() {
		return (CardCtrlModel) super.getComponentModel();
	}

	@Override
	protected CardCtrlModel getOrCreateComponentModel() {
		return (CardCtrlModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class CardCtrlModel extends CtrlModel {

		private MainCardView cardView;
	}

}
