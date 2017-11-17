package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.event.CardType;
import com.github.bordertech.wcomponents.WCardManager;
import com.github.bordertech.flux.view.DefaultDumbView;
import java.util.HashMap;
import java.util.Map;
import com.github.bordertech.flux.view.DumbView;

/**
 *
 * @author jonathan
 */
public class MainCardView extends DefaultDumbView {

	private final WCardManager mgr = new WCardManager();

	public MainCardView() {
		super();
		getContent().add(mgr);

		int idx = 1;
		for (CardType card : CardType.values()) {
			DumbView view = card.createCardViewInstance();
			view.setQualifierAndMessageQualifier("M-E" + idx++);
			view.setQualifierAndMessageQualifierContext(true);
			setupCard(card, view);
		}
		// Default
		showCard(CardType.CONTACT_CARD);
	}

	private void setupCard(final CardType type, final DumbView view) {
		mgr.add(view);
		addCard(type, view);
	}

	public final WCardManager getCardManager() {
		return mgr;
	}

	public final void showCard(final CardType card) {
		DumbView view = getCard(card);
		if (view != null) {
			mgr.makeVisible(view);
			getOrCreateComponentModel().current = card;
		}
	}

	public void resetCard(final CardType card) {
		DumbView view = getCard(card);
		if (view != null) {
			view.reset();
		}
	}

	public final CardType getCurrent() {
		return getComponentModel().current;
	}

	protected final void addCard(final CardType card, final DumbView view) {
		MainCardModel model = getOrCreateComponentModel();
		if (model.cards == null) {
			model.cards = new HashMap<>();
		}
		model.cards.put(card, view);
	}

	protected final DumbView getCard(final CardType card) {
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
	public static class MainCardModel extends ViewModel {

		private Map<CardType, DumbView> cards;

		private CardType current;
	}

}
