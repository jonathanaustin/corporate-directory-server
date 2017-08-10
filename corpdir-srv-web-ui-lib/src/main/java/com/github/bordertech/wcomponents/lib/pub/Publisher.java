package com.github.bordertech.wcomponents.lib.pub;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Publisher extends Serializable {

	List<Subscriber> getSubscribers();

	void addSubscriber(final Subscriber subscriber);

	void removeSubscriber(final Subscriber subscriber);

	void notifySubscribers(final Event event);

}
