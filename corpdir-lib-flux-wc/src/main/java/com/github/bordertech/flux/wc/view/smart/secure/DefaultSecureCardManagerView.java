package com.github.bordertech.flux.wc.view.smart.secure;

import com.github.bordertech.flux.wc.common.TemplateConstants;
import com.github.bordertech.flux.wc.view.DefaultSmartView;
import com.github.bordertech.flux.wc.view.FluxDumbView;
import com.github.bordertech.flux.wc.view.smart.SecureCardManagerView;
import com.github.bordertech.flux.wc.view.smart.SecureCardView;
import com.github.bordertech.wcomponents.addons.cardpath.SecureCardManager;
import com.github.bordertech.wcomponents.addons.cardpath.impl.SecureCardManagerImpl;
import java.util.List;
import java.util.Objects;

/**
 * Smart View that is a secure card manager.
 *
 * @param <T> the view bean type
 * @param <V> the view type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultSecureCardManagerView<T, V extends SecureCardView> extends DefaultSmartView<T> implements SecureCardManagerView<T, V> {

	private final SecureCardManager<V> mgr = new SecureCardManagerImpl();

	public DefaultSecureCardManagerView(final String viewId) {
		super(viewId);
		setNamingContext(false);
		setSearchAncestors(true);
		setDumbMode(true);
		setPassAllEvents(true);
		getContent().addTaggedComponent(TemplateConstants.TAG_VW_CONTENT, mgr);
	}

	@Override
	public List<V> getViews() {
		return (List<V>) mgr.getSecureCards();
	}

	@Override
	public FluxDumbView getView(final String viewId) {
		for (FluxDumbView view : mgr.getSecureCards()) {
			if (Objects.equals(viewId, view.getViewId())) {
				return view;
			}
		}
		return null;
	}

	@Override
	public void addSecureCard(final V card) {
		mgr.addSecureCard(card);
	}

	@Override
	public void removeSecureCard(final V card) {
		mgr.removeSecureCard(card);
	}

	@Override
	public List<V> getSecureCards() {
		return mgr.getSecureCards();
	}

	@Override
	public V getSecureCard(final String path) {
		return mgr.getSecureCard(path);
	}

	@Override
	public void setSecureMode(final boolean secureMode) {
		mgr.setSecureMode(secureMode);
	}

	@Override
	public boolean isSecureMode() {
		return mgr.isSecureMode();
	}

	@Override
	public V getCurrentCard() {
		return mgr.getCurrentCard();
	}

	@Override
	public void setCurrentCard(final V card) {
		mgr.setCurrentCard(card);
	}

	@Override
	public boolean isCurrentCard(final V card) {
		return mgr.isCurrentCard(card);
	}

}
