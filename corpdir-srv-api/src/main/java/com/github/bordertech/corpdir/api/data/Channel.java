package com.github.bordertech.corpdir.api.data;

import java.io.Serializable;

/**
 * Channel of communication.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Channel implements Serializable {

	private Long id;
	private ChannelTypeEnum type;
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
	public ChannelTypeEnum getType() {
		return type;
	}

	/**
	 *
	 * @param type the channel type
	 */
	public void setType(final ChannelTypeEnum type) {
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
