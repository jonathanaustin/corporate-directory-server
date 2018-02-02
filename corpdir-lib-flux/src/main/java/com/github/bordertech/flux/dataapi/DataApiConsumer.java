package com.github.bordertech.flux.dataapi;

import com.github.bordertech.flux.DataApi;
import java.io.Serializable;

/**
 * Data API for the application persistence.
 *
 * @param <D> the data API type.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface DataApiConsumer<D extends DataApi> extends Serializable {

	/**
	 * @return the data API
	 */
	D getDataApi();

}
