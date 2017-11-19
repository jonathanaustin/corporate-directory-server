package com.github.bordertech.flux.view;

import com.github.bordertech.flux.wc.app.common.AppCardManager;
import java.util.List;
import java.util.Objects;

/**
 * Smart View that handles Views as a CardManager.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class CardManagerView<T> extends DefaultSmartView<T> {

	private final AppCardManager<DumbView> mgr = new AppCardManager();

	public CardManagerView(final String viewId) {
		super(viewId, "wclib/hbs/layout/default-view.hbs");
		setNamingContext(false);
		setSearchAncestors(true);
		setDumbMode(true);
		setPassAllEvents(true);
		getContent().addTaggedComponent("vw-content", mgr);
	}

	public List<DumbView> getViews() {
		return (List<DumbView>) mgr.getCards();
	}

	public DumbView getView(final String viewId) {
		for (DumbView view : mgr.getCards()) {
			if (Objects.equals(viewId, view.getViewId())) {
				return view;
			}
		}
		return null;
	}

	public void addCard(final SmartView card) {
		if (!getViews().contains(card)) {
			mgr.add(card);
		}
	}

	public boolean isCurrentCard(final SmartView card) {
		return Objects.equals(card, getCurrentCard());
	}

	public void setCurrentCard(SmartView card) {
		mgr.makeVisible(card);
	}

	public SmartView getCurrentCard() {
		return (SmartView) mgr.getVisible();
	}

}
