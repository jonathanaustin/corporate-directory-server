package com.github.bordertech.flux;

import java.io.Serializable;

/**
 * Provides the application data API interface.
 * <p>
 * A Store uses the data API to retrieve data. ActionCreators use the data API to update application data and then let
 * the Store know the data has changed via an Action.
 * </p>
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface DataApi extends Serializable {

}
