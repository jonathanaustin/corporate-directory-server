package com.github.bordertech.wcomponents.lib.table;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator needs to be serializable so can be stored on the user context.
 *
 * @author jonathan
 */
public interface ComparatorSerializable<T> extends Comparator<T>, Serializable {

}
