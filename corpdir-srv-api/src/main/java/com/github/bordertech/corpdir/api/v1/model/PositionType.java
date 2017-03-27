package com.github.bordertech.corpdir.api.v1.model;

/**
 * Position type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class PositionType extends AbstractApiObject {

	private String description;
	private int typeLevel;

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
	public int getTypeLevel() {
		return typeLevel;
	}

	/**
	 *
	 * @param typeLevel the position level
	 */
	public void setTypeLevel(final int typeLevel) {
		this.typeLevel = typeLevel;
	}

}
