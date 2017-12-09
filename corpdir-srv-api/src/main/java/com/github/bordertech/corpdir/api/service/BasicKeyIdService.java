package com.github.bordertech.corpdir.api.service;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;

/**
 * Basic service for keyed API object.
 *
 * @param <T> the keyed API object
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface BasicKeyIdService<T extends ApiKeyIdObject> extends BasicIdService<T> {

}
