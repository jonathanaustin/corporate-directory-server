package com.github.bordertech.corpdir.web.ui.demo;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.wcomponents.lib.app.model.SearchModel;
import com.github.bordertech.wcomponents.util.SystemException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class MockOrgUnitSearchModel implements SearchModel<String, OrgUnit> {

	@Override
	public List<OrgUnit> retrieveCollection(final String criteria) {
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
		List<OrgUnit> items = new ArrayList<>();
		for (int i = 1; i < 5; i++) {
			OrgUnit item = new OrgUnit("A" + i);
			item.setBusinessKey("A" + i);
			item.setDescription("DESC" + i);
			items.add(item);
		}
		return items;
	}

}
