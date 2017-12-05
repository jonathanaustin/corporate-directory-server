package com.github.bordertech.flux.wc.view.dumb.table;

import com.github.bordertech.flux.wc.view.dumb.form.FormUpdateable;
import com.github.bordertech.flux.wc.view.dumb.list.AbstractListView;
import com.github.bordertech.flux.wc.view.event.base.ToolbarBaseEventType;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WTable;
import com.github.bordertech.wcomponents.WTable.SortMode;
import com.github.bordertech.wcomponents.lib.table.DefaultTableColumn;
import com.github.bordertech.wcomponents.lib.table.TableBeanModel;
import com.github.bordertech.wcomponents.lib.table.TableColumn;
import com.github.bordertech.wcomponents.lib.table.edit.RowActionPanel;
import java.util.ArrayList;
import java.util.List;

/**
 * Table with row edit actions.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class TableInlineEditingView<T> extends AbstractListView<T> implements FormUpdateable {

	private final WTable table = new WTable();

	private final WButton addButton = new WButton("Add") {
		@Override
		public boolean isVisible() {
			return table.isEditable();
		}
	};

	/**
	 * @param viewId the view id
	 * @param columns the table columns
	 */
	public TableInlineEditingView(final String viewId, final List<TableColumn<?, T>> columns) {
		this(viewId, columns, true);
	}

	/**
	 * @param viewId the view id
	 * @param columns the table columns
	 * @param addActionColumn true if add action column or false if action column included in columns
	 */
	public TableInlineEditingView(final String viewId, final List<TableColumn<?, T>> columns, final boolean addActionColumn) {
		super(viewId);

		// Setup table
		getContent().add(table);

		// Add the actionColumn
		List<TableColumn<?, T>> cols = new ArrayList<>(columns);
		if (addActionColumn) {
			DefaultTableColumn actionCol = new DefaultTableColumn("Action", new RowActionPanel());
			actionCol.setWidth(5);
			cols.add(actionCol);
		}

		// Setup model (Use the bean on the view)
		table.setBeanProperty(".");
		table.setTableModel(new TableBeanModel<>(cols));
		TableBeanModel.configTable(table);

		// Set table options
		table.setEditable(true);
		table.setSortMode(SortMode.DYNAMIC);

		// Create an add button
		addButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				handleAddButtonAction();
			}
		});
		table.addAction(addButton);

		// Add AJAX
		addButton.setAjaxTarget(table);
	}

	public final WTable getTable() {
		return table;
	}

	public final WButton getAddButton() {
		return addButton;
	}

	protected void handleAddButtonAction() {
		dispatchViewEvent(ToolbarBaseEventType.ADD);
	}

	@Override
	public void doMakeFormReadonly(final boolean readonly) {
		table.setEditable(!readonly);
	}

	@Override
	public void addItem(final T item) {
		super.addItem(item);
		table.handleDataChanged();
	}

	@Override
	public void removeItem(final T item) {
		super.removeItem(item);
		table.handleDataChanged();
	}

	@Override
	public void updateItem(final T item) {
		super.updateItem(item);
		table.handleDataChanged();
	}

}
