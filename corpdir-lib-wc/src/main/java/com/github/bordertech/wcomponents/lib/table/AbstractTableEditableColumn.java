package com.github.bordertech.wcomponents.lib.table;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WTextField;
import java.util.Comparator;

/**
 * Editable column that defaults to a TextField instead of a WText.
 *
 * @author jonathan
 */
public abstract class AbstractTableEditableColumn<V, T> extends AbstractTableColumn<V, T> {

	public AbstractTableEditableColumn(final String label) {
		this(label, new WTextField());
	}

	/**
	 * @param label the column label
	 * @param renderer the column renderer
	 */
	public AbstractTableEditableColumn(final String label, final WComponent renderer) {
		this(label, renderer, null);
	}

	/**
	 * @param label the column label
	 * @param renderer the column renderer
	 * @param comparator the column comparator
	 */
	public AbstractTableEditableColumn(final String label, final WComponent renderer, final Comparator comparator) {
		this(null, label, renderer, comparator);
	}

	/**
	 * @param columnId the column id
	 * @param label the column label
	 * @param renderer the column renderer
	 * @param comparator the column comparator
	 */
	public AbstractTableEditableColumn(final String columnId, final String label, final WComponent renderer,
			final Comparator comparator) {
		super(columnId, label, renderer, comparator);
		// Set editable to true
		setEditable(true);
	}

}
