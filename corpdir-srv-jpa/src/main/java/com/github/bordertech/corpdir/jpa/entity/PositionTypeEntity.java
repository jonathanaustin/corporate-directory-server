package com.github.bordertech.corpdir.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Position type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Entity
@Table(name = "PositionType")
public class PositionTypeEntity extends AbstractPersistentObject {

	private String description;
	private int typeLevel;

	/**
	 * Default constructor.
	 */
	protected PositionTypeEntity() {
	}

	/**
	 *
	 * @param id the entity id
	 * @param businessKey the business key.
	 */
	public PositionTypeEntity(final Long id, final String businessKey) {
		super(id, businessKey);
	}

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
