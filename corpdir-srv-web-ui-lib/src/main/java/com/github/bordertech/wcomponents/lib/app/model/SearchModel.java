package com.github.bordertech.wcomponents.lib.app.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author jonathan
 */
public interface SearchModel<S, T> extends Serializable {

	List<T> search(final S criteria);

}
