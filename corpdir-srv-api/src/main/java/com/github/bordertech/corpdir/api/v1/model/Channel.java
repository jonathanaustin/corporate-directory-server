package com.github.bordertech.corpdir.api.v1.model;

import com.github.bordertech.corpdir.api.common.DefaultObject;

/**
 * Channel of communication.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Channel extends DefaultObject {

	private String contactId;
	private ChannelTypeEnum type;
	private String value;

	/**
	 *
	 * @return the contact key
	 */
	public String getContactId() {
		return contactId;
	}

	/**
	 *
	 * @param contactId the contact key
	 */
	public void setContactId(final String contactId) {
		this.contactId = contactId;
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
	 * @param channelValue the channel value (eg mobile number, email)
	 */
	public void setValue(final String channelValue) {
		this.value = channelValue;
	}

}
