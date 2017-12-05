package com.github.bordertech.corpdir.api.common;

/**
 * API active flags fields.
 *
 * @author jonathan
 */
public interface ApiActivable extends ApiIdObject {

	boolean isActive();

	void setActive(final boolean active);

}
