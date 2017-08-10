package com.github.bordertech.wcomponents.lib.pub;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface DataEvent<T> extends Event {

	T getEventData();

}
