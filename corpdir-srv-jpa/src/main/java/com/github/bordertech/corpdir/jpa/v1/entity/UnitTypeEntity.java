package com.github.bordertech.corpdir.jpa.v1.entity;

import com.github.bordertech.corpdir.jpa.common.AbstractPersistentObject;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Organization unit type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Entity
@Table(name = "UnitType")
public class UnitTypeEntity extends AbstractPersistentObject {

	private String description;

	/**
	 * Default constructor.
	 */
	protected UnitTypeEntity() {
	}

	/**
	 *
	 * @param id the entity id
	 * @param businessKey the business key.
	 */
	public UnitTypeEntity(final Long id, final String businessKey) {
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

}
