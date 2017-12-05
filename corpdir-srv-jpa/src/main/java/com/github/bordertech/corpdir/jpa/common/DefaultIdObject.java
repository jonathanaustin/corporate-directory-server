package com.github.bordertech.corpdir.jpa.common;

import com.github.bordertech.corpdir.jpa.common.feature.PersistIdObject;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Default persistent object with an ID.
 *
 * @author jonathan
 */
@MappedSuperclass
public class DefaultIdObject implements PersistIdObject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	@Version
	private Timestamp version;

	/**
	 * Default constructor.
	 */
	protected DefaultIdObject() {
		// Default constructor
	}

	/**
	 * @param id the entity id
	 */
	public DefaultIdObject(final Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public Timestamp getTimestamp() {
		return version;
	}

	@Override
	public void setTimestamp(final Timestamp version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		return id == null ? 31 : id.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof DefaultIdObject && Objects.equals(id, ((DefaultIdObject) obj).id);
	}

}
