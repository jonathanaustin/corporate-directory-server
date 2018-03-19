package com.github.bordertech.flux.wc.view;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WContainer;

/**
 * Container that holds a view. Can be used as a placeholder in a template.
 *
 * @param <V> the view type being held
 */
public class ViewHolder<V extends FluxDumbView> extends WContainer {

	@Override
	public void add(final WComponent component) {
		throw new UnsupportedOperationException("Use setView method.");
	}

	@Override
	public void add(final WComponent component, final String tag) {
		throw new UnsupportedOperationException("Use setView method.");
	}

	public void setView(final FluxDumbView view) {
		if (getChildCount() > 0) {
			removeAll();
		}
		super.add(view);
	}

	public <V extends FluxDumbView> V getView() {
		if (getChildCount() == 0) {
			throw new IllegalStateException("Holder does not have a view.");
		}
		return (V) getChildAt(0);
	}

}
