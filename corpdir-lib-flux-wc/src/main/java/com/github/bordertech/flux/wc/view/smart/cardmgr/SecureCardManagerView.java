package com.github.bordertech.flux.wc.view.smart.cardmgr;

import com.github.bordertech.flux.wc.common.TemplateConstants;
import com.github.bordertech.flux.wc.view.DefaultSmartView;
import com.github.bordertech.flux.wc.view.FluxDumbView;
import com.github.bordertech.flux.wc.view.FluxSmartView;
import com.github.bordertech.wcomponents.lib.security.AppPath;
import com.github.bordertech.wcomponents.lib.security.DefaultAppPath;
import com.github.bordertech.wcomponents.lib.security.SecureCardManager;
import java.util.List;
import java.util.Objects;

/**
 * Smart View that handles Views as a CardManager.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class SecureCardManagerView<T> extends DefaultSmartView<T> {

	private final SecureCardManager<FluxDumbView> mgr = new SecureCardManager();

	public SecureCardManagerView(final String viewId) {
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

	public void addCard(final FluxDumbView card, final String path) {
		addCard(card, new DefaultAppPath(path));
	}

	public void addCard(final FluxDumbView card, final AppPath path) {
		if (!getViews().contains(card)) {
			mgr.addWithPath(card, path);
		}
	}

	public boolean isCurrentCard(final FluxDumbView card) {
		return Objects.equals(card, getCurrentCard());
	}

	public void setCurrentCard(final FluxDumbView card) {
		mgr.makeVisible(card);
	}

	public FluxSmartView getCurrentCard() {
		return (FluxSmartView) mgr.getVisible();
	}

	public void setSecureMode(final boolean secureMode) {
		mgr.setSecureMode(secureMode);
	}

	public boolean isSecureMode() {
		return mgr.isSecureMode();
	}

}
