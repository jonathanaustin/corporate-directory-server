package com.github.bordertech.wcomponents.lib.app.model;

import com.github.bordertech.wcomponents.lib.mvc.Model;
import java.util.List;

/**
 *
 * @author jonathan
 * @param <T> the data type
 */
public interface TreeModel<T> extends Model {

	boolean hasChildren(final T entity);

	List<T> getChildren(final T entity);

	String getItemLabel(final T entity);

}
