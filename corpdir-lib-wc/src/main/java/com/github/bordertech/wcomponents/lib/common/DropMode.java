package com.github.bordertech.wcomponents.lib.common;

/**
 *
 * @author jonathan
 */
public enum DropMode {
	COPY, MOVE, LINK;

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}

}
