package com.github.bordertech.corpdir.jpa.common;

import com.github.bordertech.corpdir.jpa.common.feature.PersistKeyIdObject;
import javax.persistence.MappedSuperclass;

/**
 * Default persistent keyed object.
 *
 * @author jonathan
 */
@MappedSuperclass
public class DefaultKeyIdObject extends DefaultIdCustomObject implements PersistKeyIdObject {

	private String businessKey;

	/**
	 * Default constructor.
	 */
	protected DefaultKeyIdObject() {
		// Default constructor
	}

	/**
	 * @param id the entity id
	 */
	public DefaultKeyIdObject(final Long id) {
		super(id);
	}

	@Override
	public String getBusinessKey() {
		return businessKey;
	}

	@Override
	public void setBusinessKey(final String businessKey) {
		this.businessKey = businessKey;
	}

}
