package com.github.bordertech.corpdir.api.v1.model;

import com.github.bordertech.corpdir.api.common.AbstractApiKeyIdObject;

/**
 * Channel of communication.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Channel extends AbstractApiKeyIdObject {

	private ChannelTypeEnum type;
	private String value;

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
	public String getChannelValue() {
		return value;
	}

	/**
	 *
	 * @param channelValue the channel value (eg mobile number, email)
	 */
	public void setChannelValue(final String channelValue) {
		this.value = channelValue;
	}

}
