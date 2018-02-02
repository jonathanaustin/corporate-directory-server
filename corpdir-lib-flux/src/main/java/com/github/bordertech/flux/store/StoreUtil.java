package com.github.bordertech.flux.store;

import com.github.bordertech.didums.Didums;
import com.github.bordertech.flux.ActionCreator;
import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.Store;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class StoreUtil {

	private static final Dispatcher DISPATCHER = Didums.getService(Dispatcher.class);

//    @Inject
//    private static Iterable<Store> allStores;
//    public static List<Store> getAllBooks() {
//        List<Store> retVal = new ArrayList<Store>();
//
//        for (Store store : allStores) {
//            retVal.add(store);
//        }
//        return retVal;
//    }
	private StoreUtil() {
	}

	public static Dispatcher getDispatcher() {
		return DISPATCHER;
	}

	public static <T extends ActionCreator> T getActionCreator(final String key) {
		return (T) getDispatcher().getActionCreator(key);
	}

	public static <T extends Store> T getStore(final String key) {
		return (T) getDispatcher().getStore(key);
	}

}
