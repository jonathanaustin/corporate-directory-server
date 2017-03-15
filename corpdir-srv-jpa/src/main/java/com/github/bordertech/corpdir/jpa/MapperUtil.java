package com.github.bordertech.corpdir.jpa;

import com.github.bordertech.corpdir.api.data.Address;
import com.github.bordertech.corpdir.api.data.Channel;
import com.github.bordertech.corpdir.api.data.ChannelTypeEnum;
import com.github.bordertech.corpdir.api.data.Contact;
import com.github.bordertech.corpdir.api.data.Location;
import com.github.bordertech.corpdir.api.data.OrgUnit;
import com.github.bordertech.corpdir.api.data.UnitType;
import com.github.bordertech.corpdir.api.data.Position;
import com.github.bordertech.corpdir.jpa.entity.AddressEntity;
import com.github.bordertech.corpdir.jpa.entity.ChannelEntity;
import com.github.bordertech.corpdir.jpa.entity.ContactEntity;
import com.github.bordertech.corpdir.jpa.entity.LocationEntity;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.entity.UnitTypeEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Mapping utility between API objects and Entity types.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public final class MapperUtil {

	/**
	 * Private constructor to prevent instantiation.
	 */
	private MapperUtil() {
		// prevent instatiation
	}

	/**
	 * Convert {@link List} of {@link UnitTypeEntity} to {@link UnitType}.
	 *
	 * @param rows the list of entity items
	 * @return the list of converted API items
	 */
	public static List<UnitType> convertListOrgUnitTypeEntityToApi(final List<UnitTypeEntity> rows) {
		if (rows == null || rows.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<UnitType> items = new ArrayList<>();
		for (UnitTypeEntity row : rows) {
			items.add(convertOrgUnitTypeEntityToApi(row));
		}
		return items;
	}

	/**
	 * Convert {@link UnitType} to {@link UnitTypeEntity}.
	 *
	 * @param from the API item
	 * @return the entity item
	 */
	public static UnitTypeEntity convertOrgUnitTypeApiToEntity(final UnitType from) {
		if (from == null) {
			return null;
		}
		return copyOrgUnitTypeApiToEntity(from, new UnitTypeEntity());
	}

	/**
	 * Convert {@link UnitType} to {@link UnitTypeEntity}.
	 *
	 * @param from the API item
	 * @param to the entity item
	 * @return the entity item
	 */
	public static UnitTypeEntity copyOrgUnitTypeApiToEntity(final UnitType from, final UnitTypeEntity to) {
		if (from == null) {
			return to;
		}
		to.setId(from.getId());
		to.setAlternateKey(from.getAlternateKey());
		to.setCustom(from.isCustom());
		to.setDescription(from.getDescription());
		return to;
	}

	/**
	 * Convert {@link UnitTypeEntity} to {@link UnitType}.
	 *
	 * @param from the entity item
	 * @return the API item
	 */
	public static UnitType convertOrgUnitTypeEntityToApi(final UnitTypeEntity from) {
		if (from == null) {
			return null;
		}
		UnitType to = new UnitType();
		to.setId(from.getId());
		to.setAlternateKey(from.getAlternateKey());
		to.setCustom(from.isCustom());
		to.setDescription(from.getDescription());
		return to;
	}

	/**
	 * Convert {@link List} of {@link OrgUnitEntity} to {@link OrgUnit}.
	 *
	 * @param rows the list of entity items
	 * @return the list of converted API items
	 */
	public static List<OrgUnit> convertListOrgUnitEntityToApi(final List<OrgUnitEntity> rows) {
		if (rows == null || rows.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<OrgUnit> items = new ArrayList<>();
		for (OrgUnitEntity row : rows) {
			items.add(convertOrgUnitEntityToApi(row));
		}
		return items;
	}

	/**
	 * Convert {@link OrgUnit} to {@link OrgUnitEntity}.
	 *
	 * @param from the API item
	 * @return the entity item
	 */
	public static OrgUnitEntity convertOrgUnitApiToEntity(final OrgUnit from) {
		if (from == null) {
			return null;
		}
		return copyOrgUnitApiToEntity(from, new OrgUnitEntity());
	}

	/**
	 * Convert {@link OrgUnit} to {@link OrgUnitEntity}.
	 *
	 * @param from the API item
	 * @param to the entity item
	 * @return the entity item
	 */
	public static OrgUnitEntity copyOrgUnitApiToEntity(final OrgUnit from, final OrgUnitEntity to) {
		if (from == null) {
			return to;
		}
		to.setId(from.getId());
		to.setAlternateKey(from.getAlternateKey());
		to.setDescription(from.getDescription());
		to.setActive(from.isActive());
		to.setCustom(from.isCustom());
		UnitTypeEntity type = convertOrgUnitTypeApiToEntity(from.getType());
		to.setType(type);
		return to;
	}

	/**
	 * Convert {@link OrgUnitEntity} to {@link OrgUnit}.
	 *
	 * @param from the entity item
	 * @return the API item
	 */
	public static OrgUnit convertOrgUnitEntityToApi(final OrgUnitEntity from) {
		if (from == null) {
			return null;
		}
		OrgUnit to = new OrgUnit();
		to.setId(from.getId());
		to.setAlternateKey(from.getAlternateKey());
		to.setDescription(from.getDescription());
		to.setActive(from.isActive());
		to.setCustom(from.isCustom());
		UnitType type = convertOrgUnitTypeEntityToApi(from.getType());
		to.setType(type);
		return to;
	}

	/**
	 * Convert {@link List} of {@link PositionEntity} to {@link Position}.
	 *
	 * @param rows the list of entity items
	 * @return the list of converted API items
	 */
	public static List<Position> convertListPositionEntityToApi(final List<PositionEntity> rows) {
		if (rows == null || rows.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<Position> items = new ArrayList<>();
		for (PositionEntity row : rows) {
			items.add(convertPositionEntityToApi(row));
		}
		return items;
	}

	/**
	 * Convert {@link PositionEntity} to {@link Position}.
	 *
	 * @param from the entity item
	 * @return the API item
	 */
	public static Position convertPositionEntityToApi(final PositionEntity from) {
		if (from == null) {
			return null;
		}
		Position to = new Position();
		to.setId(from.getId());
		to.setAlternateKey(from.getAlternateKey());
		to.setDescription(from.getDescription());
		to.setActive(from.isActive());
		to.setCustom(from.isCustom());
		to.setHasContacts(!isListEmpty(from.getContacts()));
		to.setHasPositions(!isListEmpty(from.getSubPositions()));
		return to;
	}

	/**
	 * Convert {@link List} of {@link ContactEntity} to {@link Contact}.
	 *
	 * @param rows the list of entity items
	 * @return the list of converted API items
	 */
	public static List<Contact> convertListContactEntityToApi(final List<ContactEntity> rows) {
		if (rows == null || rows.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<Contact> items = new ArrayList<>();
		for (ContactEntity row : rows) {
			items.add(convertContactEntityToApi(row));
		}
		return items;
	}

	/**
	 * Convert {@link ContactEntity} to {@link Contact}.
	 *
	 * @param from the entity item
	 * @return the API item
	 */
	public static Contact convertContactEntityToApi(final ContactEntity from) {
		if (from == null) {
			return null;
		}
		Contact to = new Contact();
		to.setId(from.getId());
		to.setAlternateKey(from.getAlternateKey());
		to.setActive(from.isActive());
		to.setCustom(from.isCustom());
		to.setCompanyTitle(from.getCompanyTitle());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setHasImage(from.getImage() != null);
		to.setAddress(convertAddressEntityToApi(from.getAddress()));
		to.setLocation(convertLocationEntityToApi(from.getLocation()));
		to.setChannels(convertListChannelEntityToApi(from.getChannels()));
		return to;
	}

	/**
	 * Convert {@link AddressEntity} to {@link Address}.
	 *
	 * @param from the entity item
	 * @return the API item
	 */
	public static Address convertAddressEntityToApi(final AddressEntity from) {
		if (from == null) {
			return null;
		}
		Address to = new Address();
		to.setAddressLine1(from.getAddressLine1());
		to.setAddressLine2(from.getAddressLine2());
		to.setCountry(from.getCountry());
		to.setPostcode(from.getPostcode());
		to.setState(from.getState());
		to.setSuburb(from.getSuburb());
		return to;
	}

	/**
	 * Convert {@link LocationEntity} to {@link Location}.
	 *
	 * @param from the entity item
	 * @return the API item
	 */
	public static Location convertLocationEntityToApi(final LocationEntity from) {
		if (from == null) {
			return null;
		}
		Location to = new Location();
		to.setId(from.getId());
		to.setAlternateKey(from.getAlternateKey());
		to.setActive(from.isActive());
		to.setCustom(from.isCustom());
		to.setDescription(from.getDescription());
		to.setAddress(convertAddressEntityToApi(from.getAddress()));
		to.setHasSubLocations(!isListEmpty(from.getSubLocations()));
		return to;
	}

	/**
	 * Convert {@link List} of {@link ChannelEntity} to {@link Channel}.
	 *
	 * @param rows the list of entity items
	 * @return the list of converted API items
	 */
	public static List<Channel> convertListChannelEntityToApi(final List<ChannelEntity> rows) {
		if (rows == null || rows.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<Channel> items = new ArrayList<>();
		for (ChannelEntity row : rows) {
			items.add(convertChannelEntityToApi(row));
		}
		return items;
	}

	/**
	 * Convert {@link ChannelEntity} to {@link Channel}.
	 *
	 * @param from the entity item
	 * @return the API item
	 */
	public static Channel convertChannelEntityToApi(final ChannelEntity from) {
		if (from == null) {
			return null;
		}
		Channel to = new Channel();
		to.setId(from.getId());
		to.setCustom(from.isCustom());
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
	 * @param list the list of items
	 * @return true if null or empty
	 */
	private static boolean isListEmpty(final List<?> list) {
		return list == null || list.isEmpty();
	}

}
