package com.github.bordertech.corpdir.web.ui.ctrl;

import com.github.bordertech.corpdir.web.ui.event.CardEventType;
import com.github.bordertech.corpdir.web.ui.event.CardType;
import com.github.bordertech.corpdir.web.ui.view.MainCardView;
import com.github.bordertech.corpdir.web.ui.view.MainToolbarView;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.store.DefaultStore;
import com.github.bordertech.flux.Action;

/**
 * Main Card Controller.
 *
 * @author jonathan
 */
public class MainCardCtrl extends DefaultStore {

	@Override
	public void setupController() {
		super.setupController();
		// Listeners

		// SHOW CARD EVENT
		registerListener(CardEventType.SHOW, new Listener() {
			@Override
			public void handleEvent(final Action event) {
				handleShowCard((CardType) event.getData());
			}
		});

		// RESET CARD
		registerListener(CardEventType.RESET, new Listener() {
			@Override
			public void handleEvent(final Action event) {
				handleResetCard((CardType) event.getData());
			}
		});
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		getToolbarView().setCurrentCard(getCardView().getCurrent());
	}

	@Override
	public void checkConfig() {
		super.checkConfig();
		if (getCardView() == null) {
			throw new IllegalStateException("A card view has not been set.");
		}
		if (getToolbarView() == null) {
			throw new IllegalStateException("A toolbar view has not been set.");
		}
	}

	public final MainCardView getCardView() {
		return getComponentModel().cardView;
	}

	public final void setCardView(final MainCardView cardView) {
		getOrCreateComponentModel().cardView = cardView;
		addView(cardView);
	}

	public final MainToolbarView getToolbarView() {
		return getComponentModel().toolbarView;
	}

	public final void setToolbarView(final MainToolbarView toolbarView) {
		getOrCreateComponentModel().toolbarView = toolbarView;
		addView(toolbarView);
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

		private MainToolbarView toolbarView;
	}

}
