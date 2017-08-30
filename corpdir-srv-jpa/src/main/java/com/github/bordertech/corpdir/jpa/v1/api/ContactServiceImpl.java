package com.github.bordertech.corpdir.jpa.v1.api;

import com.github.bordertech.corpdir.api.exception.NotFoundException;
import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.ContactService;
import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.jpa.common.map.MapperApiEntity;
import com.github.bordertech.corpdir.jpa.common.svc.AbstractJpaKeyIdService;
import com.github.bordertech.corpdir.jpa.entity.ContactEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import com.github.bordertech.corpdir.jpa.entity.links.ContactLinks;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import com.github.bordertech.corpdir.jpa.v1.mapper.ContactMapper;
import com.github.bordertech.corpdir.jpa.v1.mapper.PositionMapper;
import java.util.List;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

/**
 * Contact JPA service implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public class ContactServiceImpl extends AbstractJpaKeyIdService<Contact, ContactEntity> implements ContactService {

	private static final ContactMapper CONTACT_MAPPER = new ContactMapper();
	private static final PositionMapper POSITION_MAPPER = new PositionMapper();

	@Override
	protected Class<ContactEntity> getEntityClass() {
		return ContactEntity.class;
	}

	@Override
	protected MapperApiEntity<Contact, ContactEntity> getMapper() {
		return CONTACT_MAPPER;
	}

	@Override
	public DataResponse<byte[]> getImage(final String keyId) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public BasicResponse deleteImage(final String keyId) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public BasicResponse setImage(final String keyId, final byte[] image) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public DataResponse<byte[]> getThumbnail(final String keyId) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public BasicResponse deleteThumbnail(final String keyId) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public BasicResponse setThumbnail(final String keyId, final byte[] image) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	protected PositionEntity getPositionEntity(final EntityManager em, final String keyId) {
		PositionEntity entity = MapperUtil.getEntity(em, keyId, PositionEntity.class);
		if (entity == null) {
			throw new NotFoundException("Position [" + keyId + "] not found.");
		}
		return entity;
	}

	@Override
	public DataResponse<List<Position>> getPositions(final String keyId) {
		return getPositions(getCurrentVersionId(), keyId);
	}

	@Override
	public DataResponse<Contact> addPosition(final String keyId, final String positionKeyId) {
		return addPosition(getCurrentVersionId(), keyId, positionKeyId);
	}

	@Override
	public DataResponse<Contact> removePosition(final String keyId, final String positionKeyId) {
		return removePosition(getCurrentVersionId(), keyId, positionKeyId);
	}

	@Override
	public DataResponse<List<Position>> getPositions(final Integer versionId, final String keyId) {
		EntityManager em = getEntityManager();
		try {
			ContactEntity entity = getEntity(em, keyId);
			ContactLinks data = entity.getDataVersion(versionId);
			List<Position> list = POSITION_MAPPER.convertEntitiesToApis(em, data.getPositions());
			return new DataResponse<>(list);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<Contact> addPosition(final Integer versionId, final String keyId, final String positionKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the contact
			ContactEntity contact = getEntity(em, keyId);
			// Get the position
			PositionEntity position = getPositionEntity(em, positionKeyId);
			// Add the position
			ContactLinks data = contact.getDataVersion(versionId);
			data.addPosition(position);
			em.getTransaction().commit();
			return buildResponse(em, contact);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<Contact> removePosition(final Integer versionId, final String keyId, final String positionKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the contact
			ContactEntity contact = getEntity(em, keyId);
			// Get the position
			PositionEntity position = getPositionEntity(em, positionKeyId);
			// Remove the position
			ContactLinks data = contact.getDataVersion(versionId);
			data.removePosition(position);
			em.getTransaction().commit();
			return buildResponse(em, contact);
		} finally {
			em.close();
		}
	}

}
