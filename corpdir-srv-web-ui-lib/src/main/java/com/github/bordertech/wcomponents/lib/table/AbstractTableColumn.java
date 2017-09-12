package com.github.bordertech.wcomponents.lib.table;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WText;
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
	private final transient Comparator<V> comparator;

	/**
	 * Column id.
	 */
	private final String columnId;

	/**
	 * Column label.
	 */
	private final String columnLabel;

	/**
	 * Renderer class.
	 */
	private final Class<? extends WComponent> rendererClass;

	/**
	 * Renderer instance.
	 */
	private final WComponent renderer;

	/**
	 * Column is editable.
	 */
	private boolean editable;

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
	public AbstractTableColumn(final String label, final Comparator<V> comparator) {
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
	public AbstractTableColumn(final String label, final WComponent renderer, final Comparator<V> comparator) {
		this(null, label, renderer, comparator);
	}

	/**
	 * @param columnId the column id
	 * @param label the column label
	 * @param renderer the column renderer
	 * @param comparator the column comparator
	 */
	public AbstractTableColumn(final String columnId, final String label, final WComponent renderer,
			final Comparator<V> comparator) {
		this.columnId = columnId == null ? UUID.randomUUID().toString() : columnId;
		this.columnLabel = label;
		this.comparator = comparator;
		this.renderer = renderer;
		this.rendererClass = null;
	}

	/**
	 * @param columnId the column id
	 * @param label the column label
	 * @param rendererClass the column renderer class
	 * @param comparator the column comparator
	 */
	public AbstractTableColumn(final String columnId, final String label,
			final Class<? extends WComponent> rendererClass, final Comparator<V> comparator) {
		this.columnId = columnId == null ? UUID.randomUUID().toString() : columnId;
		this.columnLabel = label;
		this.comparator = comparator;
		this.renderer = null;
		this.rendererClass = rendererClass;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Comparator<V> getComparator() {
		return comparator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getColumnId() {
		return columnId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getColumnLabel() {
		return columnLabel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WComponent getRenderer() {
		return renderer;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<? extends WComponent> getRendererClass() {
		return rendererClass;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEditable() {
		return editable;
	}

	/**
	 * @param editable true if editable
	 */
	public void setEditable(final boolean editable) {
		this.editable = editable;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj) {
		return (obj instanceof AbstractTableColumn)
				&& Objects.equals(columnId, ((AbstractTableColumn) obj).getColumnId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return columnId.hashCode();
	}

}
