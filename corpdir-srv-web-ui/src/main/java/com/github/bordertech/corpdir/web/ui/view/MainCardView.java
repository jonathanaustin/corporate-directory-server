package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.event.CardType;
import com.github.bordertech.wcomponents.WCardManager;
import com.github.bordertech.flux.wc.view.DefaultAppView;
import java.util.HashMap;
import java.util.Map;
import com.github.bordertech.flux.wc.view.AppView;

/**
 *
 * @author jonathan
 */
public class MainCardView extends DefaultAppView {

	private final WCardManager mgr = new WCardManager();

	public MainCardView() {
		super();
		getContent().add(mgr);

		int idx = 1;
		for (CardType card : CardType.values()) {
			AppView view = card.createCardViewInstance();
			view.setQualifierAndMessageQualifier("M-E" + idx++);
			view.setQualifierAndMessageQualifierContext(true);
			setupCard(card, view);
		}
		// Default
		showCard(CardType.CONTACT_CARD);
	}

	private void setupCard(final CardType type, final AppView view) {
		mgr.add(view);
		addCard(type, view);
	}

	public final WCardManager getCardManager() {
		return mgr;
	}

	public final void showCard(final CardType card) {
		AppView view = getCard(card);
		if (view != null) {
			mgr.makeVisible(view);
			getOrCreateComponentModel().current = card;
		}
	}

	public void resetCard(final CardType card) {
		AppView view = getCard(card);
		if (view != null) {
			view.reset();
		}
	}

	public final CardType getCurrent() {
		return getComponentModel().current;
	}

	protected final void addCard(final CardType card, final AppView view) {
		MainCardModel model = getOrCreateComponentModel();
		if (model.cards == null) {
			model.cards = new HashMap<>();
		}
		model.cards.put(card, view);
	}

	protected final AppView getCard(final CardType card) {
		MainCardModel model = getComponentModel();
		if (model.cards != null) {
			return model.cards.get(card);
		}
		return null;
	}

	@Override
	protected MainCardModel newComponentModel() {
		return new MainCardModel();
	}

	@Override
	protected MainCardModel getComponentModel() {
		return (MainCardModel) super.getComponentModel();
	}

	@Override
	protected MainCardModel getOrCreateComponentModel() {
		return (MainCardModel) super.getOrCreateComponentModel();
	}

	/**
	 * Just here as a place holder and easier for other Views to extend.
	 */
	public static class MainCardModel extends AppViewModel {

		private Map<CardType, AppView> cards;

		private CardType current;
	}

}
