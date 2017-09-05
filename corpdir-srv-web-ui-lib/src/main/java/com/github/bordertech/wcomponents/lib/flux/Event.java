package com.github.bordertech.wcomponents.lib.flux;

import java.io.Serializable;

/**
 *
 * @author jonathan
 */
public interface Event extends Serializable {

	Qualifier getQualifier();

	Object getData();

	Exception getException();

}
