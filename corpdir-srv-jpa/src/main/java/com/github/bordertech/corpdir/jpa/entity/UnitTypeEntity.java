package com.github.bordertech.corpdir.jpa.entity;

import com.github.bordertech.corpdir.jpa.common.DefaultKeyIdObject;
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
public class UnitTypeEntity extends DefaultKeyIdObject {

	/**
	 * Default constructor.
	 */
	protected UnitTypeEntity() {
	}

	/**
	 *
	 * @param id the entity id
	 */
	public UnitTypeEntity(final Long id) {
		super(id);
	}

}
