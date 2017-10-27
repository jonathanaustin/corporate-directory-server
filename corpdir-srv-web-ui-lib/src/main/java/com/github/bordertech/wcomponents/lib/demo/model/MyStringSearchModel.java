package com.github.bordertech.wcomponents.lib.demo.model;

import com.github.bordertech.wcomponents.lib.app.model.SearchModel;
import com.github.bordertech.wcomponents.util.SystemException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class MyStringSearchModel implements SearchModel<String, String> {

	@Override
	public List<String> retrieveCollection(String criteria) {
		if ("error".equals(criteria)) {
			throw new SystemException("Big error");
		}
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
		}
		if ("error2".equals(criteria)) {
			throw new SystemException("Big error2");
		}
		List<String> items = new ArrayList<>();
		items.add("A1");
		items.add("A2");
		items.add("A3");
		items.add("A4");
		items.add("A5");
		return items;
	}

}
