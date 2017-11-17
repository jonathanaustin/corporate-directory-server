package com.github.bordertech.flux.key;

/**
 * Key used to identify action creators and their events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class CreatorKey extends AbstractKey<CreatorType> {

	public CreatorKey(final CreatorType creatorType) {
		this(creatorType, null);
	}

	public CreatorKey(final CreatorType creatorType, final String qualifier) {
		super(creatorType, qualifier);
	}
}
