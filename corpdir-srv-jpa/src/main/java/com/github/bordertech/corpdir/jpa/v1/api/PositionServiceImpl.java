package com.github.bordertech.corpdir.jpa.v1.api;

import com.github.bordertech.corpdir.api.exception.NotFoundException;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.PositionService;
import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.jpa.common.svc.AbstractJpaKeyIdTreeService;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import com.github.bordertech.corpdir.jpa.v1.mapper.OrgUnitMapper;
import com.github.bordertech.corpdir.jpa.v1.mapper.PositionMapper;
import java.util.List;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import com.github.bordertech.corpdir.jpa.common.map.MapperApiEntity;
import com.github.bordertech.corpdir.jpa.entity.ContactEntity;
import com.github.bordertech.corpdir.jpa.v1.mapper.ContactMapper;

/**
 * Position JPA service implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public class PositionServiceImpl extends AbstractJpaKeyIdTreeService<Position, PositionEntity> implements PositionService {

	private static final ContactMapper CONTACT_MAPPER = new ContactMapper();
	private static final OrgUnitMapper ORGUNIT_MAPPER = new OrgUnitMapper();
	private static final PositionMapper POSITION_MAPPER = new PositionMapper();

	@Override
	public DataResponse<List<Contact>> getContacts(final String keyId) {
		EntityManager em = getEntityManager();
		try {
			PositionEntity entity = getEntity(em, keyId);
			List<Contact> list = CONTACT_MAPPER.convertEntitiesToApis(em, entity.getContacts());
			return new DataResponse<>(list);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<Position> addContact(final String keyId, final String contactKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the position
			PositionEntity position = getEntity(em, keyId);
			// Get the contact
			ContactEntity contact = getContactEntity(em, contactKeyId);
			// Add the contact
			position.addContact(contact);
			em.getTransaction().commit();
			return buildResponse(em, position);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<Position> removeContact(final String keyId, final String contactKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the position
			PositionEntity position = getEntity(em, keyId);
			// Get the contact
			ContactEntity contact = getContactEntity(em, contactKeyId);
			// Remove the contact
			position.removeContact(contact);
			em.getTransaction().commit();
			return buildResponse(em, position);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<List<OrgUnit>> getManages(final String keyId) {
		EntityManager em = getEntityManager();
		try {
			PositionEntity entity = getEntity(em, keyId);
			List<OrgUnit> list = ORGUNIT_MAPPER.convertEntitiesToApis(em, entity.getManageOrgUnits());
			return new DataResponse<>(list);
		} finally {
			em.close();
		}
	}

	protected ContactEntity getContactEntity(final EntityManager em, final String keyId) {
		ContactEntity entity = MapperUtil.getEntity(em, keyId, ContactEntity.class);
		if (entity == null) {
			throw new NotFoundException("Contact [" + keyId + "] not found.");
		}
		return entity;
	}

	@Override
	protected Class<PositionEntity> getEntityClass() {
		return PositionEntity.class;
	}

	@Override
	protected MapperApiEntity<Position, PositionEntity> getMapper() {
		return POSITION_MAPPER;
	}

}
