package com.github.bordertech.wcomponents.lib.table;

import com.github.bordertech.wcomponents.WComponent;
import java.io.Serializable;

/**
 * Table column.
 *
 * @param <V> the table column value
 * @param <T> the row bean type
 * @author Jonathan Austin
 */
public interface TableColumn<V, T> extends Serializable {

	/**
	 * @param bean the row bean
	 * @return the value for this column
	 */
	V getValue(final T bean);

	/**
	 * @return the column comparator
	 */
	ComparatorSerializable<V> getComparator();

	/**
	 * @return true if column is editable
	 */
	boolean isEditable();

	/**
	 * @return the column id used in config
	 */
	String getColumnId();

	/**
	 * @return the column label
	 */
	String getColumnLabel();

	/**
	 * @return the renderer for the column.
	 */
	Class<? extends WComponent> getRendererClass();

	/**
	 * @return an instance of the renderer
	 */
	WComponent getRenderer();
}
