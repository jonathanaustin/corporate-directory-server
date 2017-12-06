package com.github.bordertech.flux.wc.view;

import com.github.bordertech.flux.wc.common.AppCardManager;
import com.github.bordertech.flux.wc.common.TemplateConstants;
import java.util.List;
import java.util.Objects;

/**
 * Smart View that handles Views as a CardManager.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class CardManagerView<T> extends DefaultSmartView<T> {

	private final AppCardManager<FluxDumbView> mgr = new AppCardManager();

	public CardManagerView(final String viewId) {
		super(viewId);
		setNamingContext(false);
		setSearchAncestors(true);
		setDumbMode(true);
		setPassAllEvents(true);
		getContent().addTaggedComponent(TemplateConstants.TAG_VW_CONTENT, mgr);
	}

	public List<FluxDumbView> getViews() {
		return (List<FluxDumbView>) mgr.getCards();
	}

	public FluxDumbView getView(final String viewId) {
		for (FluxDumbView view : mgr.getCards()) {
			if (Objects.equals(viewId, view.getViewId())) {
				return view;
			}
		}
		return null;
	}

	public void addCard(final FluxSmartView card) {
		if (!getViews().contains(card)) {
			mgr.add(card);
		}
	}

	public boolean isCurrentCard(final FluxSmartView card) {
		return Objects.equals(card, getCurrentCard());
	}

	public void setCurrentCard(FluxSmartView card) {
		mgr.makeVisible(card);
	}

	public FluxSmartView getCurrentCard() {
		return (FluxSmartView) mgr.getVisible();
	}

}
