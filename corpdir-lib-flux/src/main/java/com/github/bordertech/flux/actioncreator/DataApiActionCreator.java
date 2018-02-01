package com.github.bordertech.flux.actioncreator;

import com.github.bordertech.flux.ActionCreator;
import com.github.bordertech.flux.DataApi;
import com.github.bordertech.flux.dataapi.DataApiConsumer;

/**
 * Action creator that uses a data API to perform actions.
 *
 * @param <D> the data API type for actions
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface DataApiActionCreator<D extends DataApi> extends ActionCreator, DataApiConsumer<D> {

}
