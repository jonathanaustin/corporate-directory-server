package com.github.bordertech.corpdir.web.ui.smart.main;

import com.github.bordertech.corpdir.web.ui.CardType;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.flux.wc.crud.smart.CrudSearchSmartView;
import com.github.bordertech.flux.wc.view.smart.secure.DefaultSecureCardManagerView;
import com.github.bordertech.flux.wc.view.smart.secure.SecureCardView;
import java.util.HashMap;
import java.util.Map;

/**
 * The Card Manager that creates all the Cards via the Card Types.
 *
 * @author jonathan
 */
public class MainCardManagerView extends DefaultSecureCardManagerView<Object, SecureCardView> {

	public MainCardManagerView(final String viewId) {
		super(viewId);

		int idx = 1;
		for (CardType card : CardType.values()) {
			SecureCardView view = card.createCardViewInstance();
			view.setQualifier("M-E" + idx++);
			view.setQualifierContext(true);
			if (view instanceof CrudSearchSmartView) {
				CrudSearchSmartView form = (CrudSearchSmartView) view;
				CorpEntityType api = card.getApiType();
				form.setActionCreatorKey(api.getActionCreatorKey());
				form.setStoreKey(api.getStoreKey());
			}
			setupCard(card, view);
		}
		// Default
		showCard(CardType.CONTACT);
	}

	private void setupCard(final CardType type, final SecureCardView view) {
		addCardByType(type, view);
	}

	public final void showCard(final CardType type) {
		SecureCardView view = getCardByType(type);
		if (view != null) {
			setCurrentCard(view);
			getOrCreateComponentModel().current = type;
		}
	}

	public void resetCard(final CardType type) {
		SecureCardView view = getCardByType(type);
		if (view != null) {
			view.reset();
		}
	}

	public final CardType getCurrentType() {
		return getComponentModel().current;
	}

	protected final void addCardByType(final CardType type, final SecureCardView view) {
		// Add to Mgr
		addSecureCard(view);
		// App to Map by type
		MainCardModel model = getOrCreateComponentModel();
		if (model.cards == null) {
			model.cards = new HashMap<>();
		}
		model.cards.put(type, view);
	}

	protected final SecureCardView getCardByType(final CardType card) {
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
	public static class MainCardModel extends SmartViewModel {

		private Map<CardType, SecureCardView> cards;

		private CardType current;
	}

}
