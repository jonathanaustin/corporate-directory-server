package com.github.bordertech.wcomponents.lib.app.view.list;

import com.github.bordertech.wcomponents.WTable;
import com.github.bordertech.wcomponents.lib.app.view.table.TextBeanColumn;
import com.github.bordertech.wcomponents.lib.table.TableBeanModel;
import com.github.bordertech.wcomponents.lib.table.TableColumn;
import java.util.Arrays;
import java.util.List;

/**
 * Table with view button.
 *
 * @author Jonathan Austin
 * @param <T> the item type
 * @since 1.0.0
 */
public class TableSelectView<T> extends AbstractSelectView<T> {

	private final WTable table = new WTable();

	public TableSelectView() {
		this(new TextBeanColumn<T>());
	}

	public TableSelectView(final TableColumn<?, T>... columns) {
		this(Arrays.asList(columns));
	}

	public TableSelectView(final List<TableColumn<?, T>> columns) {
		table.setBeanProperty(".");
		getContent().add(table);
		TableBeanModel model = new TableBeanModel<>(columns);
		TableBeanModel.configTable(table);
		table.setTableModel(model);
		table.setSelectMode(WTable.SelectMode.SINGLE);
	}

	public final WTable getTable() {
		return table;
	}

}
