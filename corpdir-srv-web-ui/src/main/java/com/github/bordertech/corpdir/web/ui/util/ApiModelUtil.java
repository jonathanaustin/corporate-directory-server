package com.github.bordertech.corpdir.web.ui.util;

import com.github.bordertech.corpdir.api.common.ApiIdObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class ApiModelUtil {

	private ApiModelUtil() {
	}

	public static List<String> convertApiObjectsToIds(final List<? extends ApiIdObject> items) {
		List<String> ids = new ArrayList<>();
		if (items != null) {
			for (ApiIdObject item : items) {
				ids.add(item.getId());
			}
		}
		return ids;
	}

}
