package com.github.bordertech.flux.wc.common;

import com.github.bordertech.wcomponents.WCardManager;
import com.github.bordertech.wcomponents.WComponent;
import java.util.List;

/**
 * Extend CardManager to provide a list of the cards.
 *
 * @author jonathan
 */
public class FluxCardManager<T extends WComponent> extends WCardManager {

	public List<T> getCards() {
		return (List<T>) getCardContainer().getChildren();
	}

}
