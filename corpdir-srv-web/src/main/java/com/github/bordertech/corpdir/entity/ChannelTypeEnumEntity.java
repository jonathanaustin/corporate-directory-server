package com.github.bordertech.corpdir.entity;

/**
 * Type of channel communication.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum ChannelTypeEnumEntity {

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
	ChannelTypeEnumEntity(final String type) {
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
	public static ChannelTypeEnumEntity fromValue(final String value) {
		for (ChannelTypeEnumEntity c : ChannelTypeEnumEntity.values()) {
			if (c.value.equals(value)) {
				return c;
			}
		}
		throw new IllegalArgumentException(value);
	}

}
