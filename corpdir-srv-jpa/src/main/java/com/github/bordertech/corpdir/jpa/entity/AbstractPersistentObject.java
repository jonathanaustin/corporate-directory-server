package com.github.bordertech.corpdir.jpa.entity;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Abstract persistent object.
 *
 * @author jonathan
 */
@MappedSuperclass
public abstract class AbstractPersistentObject implements PersistentObject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String businessKey;

	private boolean active = true;
	private boolean custom = true;

	@Version
	private Timestamp version;

	/**
	 * Default constructor.
	 */
	protected AbstractPersistentObject() {
		// Default constructor
	}

	/**
	 * @param id the entity id
	 * @param businessKey the business key
	 */
	public AbstractPersistentObject(final Long id, final String businessKey) {
		this.id = id;
		if (businessKey == null) {
			this.businessKey = UUID.randomUUID().toString();
		} else {
			this.businessKey = businessKey;
		}
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getBusinessKey() {
		return businessKey;
	}

	@Override
	public Timestamp getVersion() {
		return version;
	}

	@Override
	public void setVersion(final Timestamp version) {
		this.version = version;
	}

	/**
	 *
	 * @return true if active record
	 */
	@Override
	public boolean isActive() {
		return active;
	}

	/**
	 *
	 * @param active true if active record
	 */
	@Override
	public void setActive(final boolean active) {
		this.active = active;
	}

	/**
	 *
	 * @return true if custom record
	 */
	@Override
	public boolean isCustom() {
		return custom;
	}

	/**
	 *
	 * @param custom true if custom record
	 */
	@Override
	public void setCustom(final boolean custom) {
		this.custom = custom;
	}

	@Override
	public int hashCode() {
		return businessKey.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof AbstractPersistentObject && Objects.equals(businessKey, ((AbstractPersistentObject) obj).businessKey);
	}

}
