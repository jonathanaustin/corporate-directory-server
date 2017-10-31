package com.github.bordertech.flux;

/**
 * Smart View.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ViewContainer extends View {

	/**
	 * @return true if this view is a qualifier context
	 */
	boolean isQualifierContext();

	/**
	 *
	 * @param qualifierContext true if this view is a qualifier context
	 */
	void setQualifierContext(final boolean qualifierContext);

}
