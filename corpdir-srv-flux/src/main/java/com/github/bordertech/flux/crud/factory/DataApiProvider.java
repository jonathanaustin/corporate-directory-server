package com.github.bordertech.flux.crud.factory;

import com.github.bordertech.flux.DataApi;
import java.io.Serializable;

/**
 * Data API provider.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface DataApiProvider extends Serializable {

	DataApi getDataApi(final String key);

}
