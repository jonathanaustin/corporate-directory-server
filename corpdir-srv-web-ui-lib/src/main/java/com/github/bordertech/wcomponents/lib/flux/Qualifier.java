package com.github.bordertech.wcomponents.lib.flux;

import java.io.Serializable;

/**
 *
 * @author jonathan
 */
public interface Qualifier extends Serializable {

	EventType getEventType();

	String getQualifier();

}
