package com.github.bordertech.corpdir.jpa.mapper;

import com.github.bordertech.corpdir.api.data.Contact;
import com.github.bordertech.corpdir.jpa.entity.ContactEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Map {@link Contact} and {@link ContactEntity}.
 *
 * @author jonathan
 */
public final class ContactMapper {

	/**
	 * Private constructor to prevent instantiation.
	 */
	private ContactMapper() {
		// prevent instatiation
	}

	/**
	 *
	 * @param from the entity item
	 * @return the API item
	 */
	public static Contact convertEntityToApi(final ContactEntity from) {
		if (from == null) {
			return null;
		}
		Contact to = new Contact();
		MapperUtil.handleCommonEntityToApi(from, to);
		to.setCompanyTitle(from.getCompanyTitle());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setHasImage(from.getImage() != null);
		to.setAddress(AddressMapper.convertEntityToApi(from.getAddress()));
		to.setLocation(LocationMapper.convertEntityToApi(from.getLocation()));
		to.setChannels(ChannelMapper.convertEntitiesToApis(from.getChannels()));
		return to;
	}

	/**
	 * @param rows the list of entity items
	 * @return the list of converted API items
	 */
	public static List<Contact> convertEntitiesToApis(final Set<ContactEntity> rows) {
		if (rows == null || rows.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<Contact> items = new ArrayList<>();
		for (ContactEntity row : rows) {
			items.add(convertEntityToApi(row));
		}
		return items;
	}

}
