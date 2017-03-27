package com.github.bordertech.corpdir.jpa.v1.entity;

/**
 * Type of channel communication.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum ChannelTypeEnum {

	/**
	 * Mobile phone.
	 */
	MOBILE("mobile"),
	/**
	 * Office number.
	 */
	OFFICE("office"),
	/**
	 * Email.
	 */
	EMAIL("email");

	private final String value;

	/**
	 *
	 * @param type the communication type
	 */
	ChannelTypeEnum(final String type) {
		value = type;
	}

	/**
	 *
	 * @return the type value
	 */
	public String value() {
		return value;
	}

	/**
	 *
	 * @param value the type value
	 * @return the channel type
	 */
	public static ChannelTypeEnum fromValue(final String value) {
		for (ChannelTypeEnum c : ChannelTypeEnum.values()) {
			if (c.value.equals(value)) {
				return c;
			}
		}
		throw new IllegalArgumentException(value);
	}

}
