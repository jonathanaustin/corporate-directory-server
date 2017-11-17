package com.github.bordertech.flux.key;

/**
 * Key used to identify stores and their events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class StoreKey extends AbstractKey<StoreType> {

	public StoreKey(final StoreType storeType) {
		this(storeType, null);
	}

	public StoreKey(final StoreType storeType, final String qualifier) {
		super(storeType, qualifier);
	}

}
