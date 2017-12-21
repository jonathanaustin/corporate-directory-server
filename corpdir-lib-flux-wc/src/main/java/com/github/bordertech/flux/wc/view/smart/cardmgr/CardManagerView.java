package com.github.bordertech.flux.wc.view.smart.cardmgr;

import com.github.bordertech.flux.wc.common.FluxCardManager;
import com.github.bordertech.flux.wc.common.TemplateConstants;
import com.github.bordertech.flux.wc.view.DefaultSmartView;
import com.github.bordertech.flux.wc.view.FluxDumbView;
import java.util.List;
import java.util.Objects;

/**
 * Smart View that handles Views as a CardManager.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class CardManagerView<T> extends DefaultSmartView<T> {

	private final FluxCardManager<FluxDumbView> mgr = new FluxCardManager();

	public CardManagerView(final String viewId) {
		super(viewId);
		setNamingContext(false);
		setSearchAncestors(true);
		setDumbMode(true);
		setPassAllEvents(true);
		getContent().addTaggedComponent(TemplateConstants.TAG_VW_CONTENT, mgr);
	}

	@Override
	public List<FluxDumbView> getViews() {
		return (List<FluxDumbView>) mgr.getCards();
	}

	@Override
	public FluxDumbView getView(final String viewId) {
		for (FluxDumbView view : mgr.getCards()) {
			if (Objects.equals(viewId, view.getViewId())) {
				return view;
			}
		}
		return null;
	}

	public void addCard(final FluxDumbView card) {
		if (!getViews().contains(card)) {
			mgr.add(card);
		}
	}

	public boolean isCurrentCard(final FluxDumbView card) {
		return Objects.equals(card, getCurrentCard());
	}

	public void setCurrentCard(final FluxDumbView card) {
		mgr.makeVisible(card);
	}

	public FluxDumbView getCurrentCard() {
		return (FluxDumbView) mgr.getVisible();
	}

}
