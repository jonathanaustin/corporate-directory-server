package com.github.bordertech.corpdir.jpa.entity;

import com.github.bordertech.corpdir.jpa.common.AbstractPersistentKeyIdObject;
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
public class PositionTypeEntity extends AbstractPersistentKeyIdObject {

	private int typeLevel;

	/**
	 * Default constructor.
	 */
	protected PositionTypeEntity() {
	}

	/**
	 *
	 * @param id the entity id
	 */
	public PositionTypeEntity(final Long id) {
		super(id);
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
