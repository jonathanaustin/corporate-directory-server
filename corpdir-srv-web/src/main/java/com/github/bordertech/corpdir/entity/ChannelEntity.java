package com.github.bordertech.corpdir.entity;

import java.io.Serializable;

/**
 * Channel of communication.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ChannelEntity implements Serializable {

	private Long id;
	private ChannelTypeEnumEntity type;
	private String value;
	private boolean custom;

	/**
	 *
	 * @return the unique id
	 */
	public Long getId() {
		return id;
	}

	/**
	 *
	 * @param id the unique id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 *
	 * @return the channel type
	 */
	public ChannelTypeEnumEntity getType() {
		return type;
	}

	/**
	 *
	 * @param type the channel type
	 */
	public void setType(final ChannelTypeEnumEntity type) {
		this.type = type;
	}

	/**
	 * @return the channel value (eg mobile number, email)
	 */
	public String getValue() {
		return value;
	}

	/**
	 *
	 * @param value the channel value (eg mobile number, email)
	 */
	public void setValue(final String value) {
		this.value = value;
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
