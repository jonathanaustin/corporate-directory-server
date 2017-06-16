package com.github.bordertech.corpdir.web.ui.pub;

import com.github.bordertech.wcomponents.WComponent;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Publisher extends WComponent {

	List<Class<? extends Event>> getPublisherEvents();

	List<Subscriber> getSubscribers();

	void addSubscriber(final Subscriber subscriber);

	void removeSubscriber(final Subscriber subscriber);

}
