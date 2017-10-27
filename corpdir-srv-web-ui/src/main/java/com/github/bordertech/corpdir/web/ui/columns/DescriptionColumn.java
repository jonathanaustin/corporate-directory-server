package com.github.bordertech.corpdir.web.ui.columns;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.wcomponents.lib.table.AbstractTableColumn;
import org.hibernate.internal.util.compare.ComparableComparator;

/**
 * Description table column.
 *
 * @author jonathan
 * @param <T> the row bean type
 */
public class DescriptionColumn<T extends ApiKeyIdObject> extends AbstractTableColumn<String, T> {

	public DescriptionColumn() {
		super("Description", new ComparableComparator<String>());
	}

	@Override
	public String getValue(final T bean) {
		return bean.getBusinessKey();
	}

}
