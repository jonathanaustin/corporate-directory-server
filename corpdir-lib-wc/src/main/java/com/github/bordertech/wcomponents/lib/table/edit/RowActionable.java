package com.github.bordertech.wcomponents.lib.table.edit;

import java.io.Serializable;

/**
 * Marks the column used to hold the row actions.
 *
 * @author jonathan
 */
public interface RowActionable extends Serializable {

	/**
	 * @param rowKey the row key to check
	 * @return the row mode
	 */
	RowMode getRowMode(final Object rowKey);

	/**
	 * @param rowKey add the following key to the row modes
	 * @param mode the row mode
	 */
	void addRowModeKey(final Object rowKey, final RowMode mode);

	/**
	 * @param rowKey remove the following key to the row modes
	 */
	void removeRowModeKey(final Object rowKey);

}
