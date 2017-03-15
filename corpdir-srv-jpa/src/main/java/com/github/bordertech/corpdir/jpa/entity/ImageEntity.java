package com.github.bordertech.corpdir.jpa.entity;

import java.awt.Dimension;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Image details.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Entity
@Table(name = "Image")
public class ImageEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String mimeType;
	private String description;
	private Dimension dimension;
	private byte[] bytes;
	private boolean active;
	private boolean custom;

	/**
	 *
	 * @return the unique id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the unique id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 *
	 * @return the location id
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 *
	 * @param mimeType the location id
	 */
	public void setMimeType(final String mimeType) {
		this.mimeType = mimeType;
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
	 * @return the image dimensions
	 */
	public Dimension getDimension() {
		return dimension;
	}

	/**
	 *
	 * @param dimension the image dimensions
	 */
	public void setDimension(final Dimension dimension) {
		this.dimension = dimension;
	}

	/**
	 *
	 * @return the image bytes
	 */
	public byte[] getBytes() {
		return bytes;
	}

	/**
	 *
	 * @param bytes the image bytes
	 */
	public void setBytes(final byte[] bytes) {
		this.bytes = bytes;
	}

	/**
	 *
	 * @return true if active record
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 *
	 * @param active true if active record
	 */
	public void setActive(final boolean active) {
		this.active = active;
	}

	/**
	 *
	 * @return true if custom record
	 */
	public boolean isCustom() {
		return custom;
	}

	/**
	 *
	 * @param custom true if custom record
	 */
	public void setCustom(final boolean custom) {
		this.custom = custom;
	}

}
