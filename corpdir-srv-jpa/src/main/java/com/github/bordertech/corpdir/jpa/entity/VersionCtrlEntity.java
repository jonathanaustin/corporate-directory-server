package com.github.bordertech.corpdir.jpa.entity;

import com.github.bordertech.corpdir.jpa.common.DefaultIdObject;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Organization unit type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Entity
@Table(name = "VersionCtrl")
public class VersionCtrlEntity extends DefaultIdObject {

	/**
	 * Default constructor.
	 */
	protected VersionCtrlEntity() {
	}

	/**
	 *
	 * @param id the entity id
	 */
	public VersionCtrlEntity(final Long id) {
		super(id);
	}

}
