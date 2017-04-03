package com.github.bordertech.corpdir.api.v1.model;

import com.github.bordertech.corpdir.api.common.AbstractApiKeyIdObject;

/**
 * Position type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class PositionType extends AbstractApiKeyIdObject {

	private String description;
	private int level;

	/**
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 *
	 * @param description the description
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 *
	 * @return the position level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 *
	 * @param level the position level
	 */
	public void setLevel(final int level) {
		this.level = level;
	}

}
