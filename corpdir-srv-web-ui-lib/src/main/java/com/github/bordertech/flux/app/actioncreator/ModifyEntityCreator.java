package com.github.bordertech.flux.app.actioncreator;

import com.github.bordertech.flux.app.dataapi.modify.ModifyEntityApi;

/**
 * Provides the action (event) creator interface to handle change store events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ModifyEntityCreator<T> extends ModifyCreator, ModifyEntityApi<T> {

}
