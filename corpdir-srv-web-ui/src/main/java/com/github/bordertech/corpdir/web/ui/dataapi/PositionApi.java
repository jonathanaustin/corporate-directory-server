package com.github.bordertech.corpdir.web.ui.dataapi;

import com.github.bordertech.corpdir.api.v1.PositionService;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.flux.CorpCrudTreeVersionDataApi;

/**
 * Position CRUD API.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface PositionApi extends CorpCrudTreeVersionDataApi<Position, PositionService> {
}
