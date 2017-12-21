package com.github.bordertech.corpdir.jpa.v1.api;

import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.PositionTypeService;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.api.v1.model.PositionType;
import com.github.bordertech.corpdir.jpa.common.map.MapperApi;
import com.github.bordertech.corpdir.jpa.common.svc.JpaBasicKeyIdService;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionTypeEntity;
import com.github.bordertech.corpdir.jpa.util.CriteriaUtil;
import com.github.bordertech.corpdir.jpa.v1.mapper.PositionMapper;
import com.github.bordertech.corpdir.jpa.v1.mapper.PositionTypeMapper;
import java.util.List;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Position type JPA service implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public class PositionTypeServiceImpl extends JpaBasicKeyIdService<PositionType, PositionTypeEntity> implements PositionTypeService {

	private static final PositionTypeMapper POSITIONTYPE_MAPPER = new PositionTypeMapper();
	private static final PositionMapper POSITION_MAPPER = new PositionMapper();

	@Override
	public DataResponse<List<Position>> getPositions(final String keyId) {
		EntityManager em = getEntityManager();
		try {
			PositionTypeEntity type = getEntity(em, keyId);
			CriteriaBuilder cb = em.getCriteriaBuilder();

			CriteriaQuery<PositionEntity> qry = cb.createQuery(PositionEntity.class);
			Root<PositionEntity> from = qry.from(PositionEntity.class);
			qry.select(from);
			qry.where(cb.equal(from.get("type"), type));

			// Order by
			qry.orderBy(CriteriaUtil.getDefaultOrderBy(cb, from));

			List<PositionEntity> rows = em.createQuery(qry).getResultList();
			List<Position> list = POSITION_MAPPER.convertEntitiesToApis(em, rows, getCurrentVersionId());
			return new DataResponse<>(list);
		} finally {
			em.close();
		}
	}

	@Override
	protected Class<PositionTypeEntity> getEntityClass() {
		return PositionTypeEntity.class;
	}

	@Override
	protected MapperApi<PositionType, PositionTypeEntity> getMapper() {
		return POSITIONTYPE_MAPPER;
	}

}
