package com.github.bordertech.corpdir.api.common;

/**
 * API custom flag fields.
 *
 * @author jonathan
 */
public interface ApiCustomable extends ApiIdObject {

	boolean isCustom();

	void setCustom(final boolean custom);

}
