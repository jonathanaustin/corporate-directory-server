package com.github.bordertech.corpdir.jpa.mapper;

import com.github.bordertech.corpdir.api.data.Channel;
import com.github.bordertech.corpdir.api.data.ChannelTypeEnum;
import com.github.bordertech.corpdir.jpa.entity.ChannelEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Map {@link Channel} and {@link ChannelEntity}.
 *
 * @author jonathan
 */
public final class ChannelMapper {

	/**
	 * Private constructor to prevent instantiation.
	 */
	private ChannelMapper() {
		// prevent instatiation
	}

	/**
	 * @param from the API item
	 * @return the entity item
	 */
	public static ChannelEntity convertApiToEntity(final Channel from) {
		if (from == null) {
			return null;
		}
		Long id = MapperUtil.convertApiIdforEntity(from.getId());
		ChannelEntity to = new ChannelEntity(id, from.getBusinessKey());
		MapperUtil.handleCommonApiToEntity(from, to);
		to.setChannelValue(from.getChannelValue());
		if (from.getType() != null) {
			switch (from.getType()) {
				case EMAIL:
					to.setType(com.github.bordertech.corpdir.jpa.entity.ChannelTypeEnum.EMAIL);
					break;
				case MOBILE:
					to.setType(com.github.bordertech.corpdir.jpa.entity.ChannelTypeEnum.MOBILE);
					break;
				case OFFICE:
					to.setType(com.github.bordertech.corpdir.jpa.entity.ChannelTypeEnum.OFFICE);
					break;
				default:
					// TODO Throw exception
					to.setType(null);
			}
		}
		return to;
	}

	/**
	 * @param from the entity item
	 * @return the API item
	 */
	public static Channel convertEntityToApi(final ChannelEntity from) {
		if (from == null) {
			return null;
		}
		Channel to = new Channel();
		MapperUtil.handleCommonEntityToApi(from, to);
		to.setChannelValue(from.getChannelValue());
		if (from.getType() != null) {
			switch (from.getType()) {
				case EMAIL:
					to.setType(ChannelTypeEnum.EMAIL);
					break;
				case MOBILE:
					to.setType(ChannelTypeEnum.MOBILE);
					break;
				case OFFICE:
					to.setType(ChannelTypeEnum.OFFICE);
					break;
				default:
					// TODO Throw exception
					to.setType(null);
			}
		}
		return to;
	}

	/**
	 *
	 * @param rows the list of entity items
	 * @return the list of converted API items
	 */
	public static List<Channel> convertEntitiesToApis(final Set<ChannelEntity> rows) {
		if (rows == null || rows.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<Channel> items = new ArrayList<>();
		for (ChannelEntity row : rows) {
			items.add(convertEntityToApi(row));
		}
		return items;
	}

}
