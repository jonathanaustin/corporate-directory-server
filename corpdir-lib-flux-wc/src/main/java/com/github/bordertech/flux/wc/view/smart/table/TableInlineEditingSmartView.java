package com.github.bordertech.flux.wc.view.smart.table;

import com.github.bordertech.flux.crud.actioncreator.EntityActionCreator;
import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.view.DefaultSmartView;
import com.github.bordertech.flux.wc.view.dumb.table.TableInlineEditingView;
import com.github.bordertech.flux.wc.view.event.base.ToolbarBaseEventType;
import com.github.bordertech.flux.wc.view.smart.consumer.EntityActionCreatorConsumer;
import com.github.bordertech.wcomponents.lib.table.TableColumn;
import java.util.List;

/**
 * Smart Table view with row edit actions and config for action creator.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class TableInlineEditingSmartView<T> extends DefaultSmartView<List<T>> implements EntityActionCreatorConsumer<T> {

	private final TableInlineEditingView tableView;

	/**
	 * @param viewId the view id
	 * @param columns the table columns
	 */
	public TableInlineEditingSmartView(final String viewId, final List<TableColumn<?, T>> columns) {
		this(viewId, columns, true);
	}

	/**
	 * @param viewId the view id
	 * @param columns the table columns
	 * @param addActionColumn true if add action column or false if action column included in columns
	 */
	public TableInlineEditingSmartView(final String viewId, final List<TableColumn<?, T>> columns, final boolean addActionColumn) {
		super(viewId);
		tableView = new TableInlineEditingView<>(viewId + "_tbl", columns, addActionColumn);
		// Use the bean on the smart view
		tableView.setBeanProperty(".");
		tableView.setSearchAncestors(true);
		addComponentToTemplate("vw-content", tableView);
	}

	public final TableInlineEditingView<T> getTableView() {
		return tableView;
	}

	@Override
	public void setEntityActionCreatorKey(final String actionCreatorKey) {
		getOrCreateComponentModel().actionCreatorKey = actionCreatorKey;
	}

	@Override
	public String getEntityActionCreatorKey() {
		return getComponentModel().actionCreatorKey;
	}

	@Override
	public EntityActionCreator<T> getEntityActionCreator() {
		return (EntityActionCreator<T>) getDispatcher().getActionCreator(getEntityActionCreatorKey());
	}

	@Override
	public void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
		super.handleViewEvent(viewId, event, data);
		if (isEvent(event, ToolbarBaseEventType.ADD)) {
			handleAddEvent();
		}
	}

	protected void handleAddEvent() {
		T bean = getEntityActionCreator().createInstance();
		tableView.addItem(bean);
	}

	@Override
	protected TableInlineSmartViewModel newComponentModel() {
		return new TableInlineSmartViewModel();
	}

	@Override
	protected TableInlineSmartViewModel getComponentModel() {
		return (TableInlineSmartViewModel) super.getComponentModel();
	}

	@Override
	protected TableInlineSmartViewModel getOrCreateComponentModel() {
		return (TableInlineSmartViewModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class TableInlineSmartViewModel extends SmartViewModel {

		private String actionCreatorKey;
	}

}
