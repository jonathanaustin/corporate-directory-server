package com.github.bordertech.wcomponents.lib.table;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WTableColumn;
import com.github.bordertech.wcomponents.WText;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

/**
 * Table column details.
 *
 * @param <V> the columns value type
 * @param <T> the row bean type
 * @author Jonathan Austin
 * @since POC1
 */
public abstract class AbstractTableColumn<V, T> implements TableColumn<V, T> {

	/**
	 * Column comparator.
	 */
	private final Comparator comparator;

	/**
	 * Column id.
	 */
	private final String columnId;

	/**
	 * Column label.
	 */
	private final String columnLabel;

	/**
	 * Renderer instance.
	 */
	private final WComponent renderer;

	/**
	 * Column is editable.
	 */
	private boolean editable;

	private int width;
	private WTableColumn.Alignment alignment;

	/**
	 * @param label the column label
	 */
	public AbstractTableColumn(final String label) {
		this(null, label, new WText(), null);
	}

	/**
	 * @param label the column label
	 * @param comparator the column comparator
	 */
	public AbstractTableColumn(final String label, final Comparator comparator) {
		this(null, label, new WText(), comparator);
	}

	/**
	 * @param label the column label
	 * @param renderer the column renderer
	 */
	public AbstractTableColumn(final String label, final WComponent renderer) {
		this(null, label, renderer, null);
	}

	/**
	 * @param label the column label
	 * @param renderer the column renderer
	 * @param comparator the column comparator
	 */
	public AbstractTableColumn(final String label, final WComponent renderer, final Comparator comparator) {
		this(null, label, renderer, comparator);
	}

	/**
	 * @param columnId the column id
	 * @param label the column label
	 * @param renderer the column renderer
	 * @param comparator the column comparator
	 */
	public AbstractTableColumn(final String columnId, final String label, final WComponent renderer,
			final Comparator comparator) {
		checkComparator(comparator);
		this.columnId = columnId == null ? UUID.randomUUID().toString() : columnId;
		this.columnLabel = label;
		this.comparator = comparator;
		this.renderer = renderer;
	}

	protected final void checkComparator(final Comparator comparator) {
		if (comparator != null && !(comparator instanceof Serializable)) {
			throw new IllegalArgumentException("Comparator must implement serializable");
		}
	}

	@Override
	public Comparator getComparator() {
		return comparator;
	}

	@Override
	public String getColumnId() {
		return columnId;
	}

	@Override
	public String getColumnLabel() {
		return columnLabel;
	}

	@Override
	public WComponent getRenderer() {
		return renderer;
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(final boolean editable) {
		this.editable = editable;
	}

	@Override
	public boolean equals(final Object obj) {
		return (obj instanceof AbstractTableColumn)
				&& Objects.equals(columnId, ((AbstractTableColumn) obj).getColumnId());
	}

	@Override
	public int hashCode() {
		return columnId.hashCode();
	}

	@Override
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public WTableColumn.Alignment getAlignment() {
		return alignment;
	}

	public void setAlignment(WTableColumn.Alignment align) {
		this.alignment = align;
	}

	@Override
	public void setValue(final T bean, final V value) {
		// NOP
	}

}
