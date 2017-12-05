package com.github.bordertech.corpdir.jpa.v1.api;

import com.github.bordertech.corpdir.api.exception.NotFoundException;
import com.github.bordertech.corpdir.api.response.BasicResponse;
import com.github.bordertech.corpdir.api.response.DataResponse;
import com.github.bordertech.corpdir.api.v1.ContactService;
import com.github.bordertech.corpdir.api.v1.model.Channel;
import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.jpa.common.map.MapperApiVersion;
import com.github.bordertech.corpdir.jpa.common.svc.JpaBasicVersionKeyIdService;
import com.github.bordertech.corpdir.jpa.entity.ChannelEntity;
import com.github.bordertech.corpdir.jpa.entity.ContactEntity;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import com.github.bordertech.corpdir.jpa.entity.links.ContactLinksEntity;
import com.github.bordertech.corpdir.jpa.util.MapperUtil;
import com.github.bordertech.corpdir.jpa.v1.mapper.ChannelMapper;
import com.github.bordertech.corpdir.jpa.v1.mapper.ContactMapper;
import com.github.bordertech.corpdir.jpa.v1.mapper.PositionMapper;
import java.util.ArrayList;
import java.util.Collections;
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
public class ContactServiceImpl extends JpaBasicVersionKeyIdService<Contact, ContactLinksEntity, ContactEntity> implements ContactService {

	private static final ContactMapper CONTACT_MAPPER = new ContactMapper();
	private static final PositionMapper POSITION_MAPPER = new PositionMapper();
	private static final ChannelMapper CHANNEL_MAPPER = new ChannelMapper();

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
		PositionEntity entity = MapperUtil.getEntityByKeyId(em, keyId, PositionEntity.class);
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
	public DataResponse<List<Position>> getPositions(final Long versionId, final String keyId) {
		EntityManager em = getEntityManager();
		try {
			ContactEntity entity = getEntity(em, keyId);
			ContactLinksEntity links = entity.getDataVersion(versionId);
			List<Position> list;
			if (links == null) {
				list = Collections.EMPTY_LIST;
			} else {
				list = POSITION_MAPPER.convertEntitiesToApis(em, links.getPositions(), versionId);
			}
			return new DataResponse<>(list);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<Contact> addPosition(final Long versionId, final String keyId, final String positionKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the contact
			ContactEntity contact = getEntity(em, keyId);
			// Get the position
			PositionEntity position = getPositionEntity(em, positionKeyId);
			// Get Version
			VersionCtrlEntity ctrl = getVersionCtrl(em, versionId);
			// Add the position to Contact
			contact.getOrCreateDataVersion(ctrl).addPosition(position);
			em.getTransaction().commit();
			return buildResponse(em, contact, versionId);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<Contact> removePosition(final Long versionId, final String keyId, final String positionKeyId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the contact
			ContactEntity contact = getEntity(em, keyId);
			// Get the position
			PositionEntity position = getPositionEntity(em, positionKeyId);
			// Get Version
			VersionCtrlEntity ctrl = getVersionCtrl(em, versionId);
			// Remove the position
			contact.getOrCreateDataVersion(ctrl).removePosition(position);
			em.getTransaction().commit();
			return buildResponse(em, contact, versionId);
		} finally {
			em.close();
		}
	}

	@Override
	protected Class<ContactEntity> getEntityClass() {
		return ContactEntity.class;
	}

	@Override
	protected MapperApiVersion<Contact, ContactLinksEntity, ContactEntity> getMapper() {
		return CONTACT_MAPPER;
	}

	@Override
	public DataResponse<Contact> create(final Contact apiContact) {
		EntityManager em = getEntityManager();
		try {
			Long versionId = apiContact.getVersionId();
			// Add the current version id (if not set)
			if (versionId == null) {
				versionId = getCurrentVersionId();
				apiContact.setVersionId(versionId);
			}
			handleCreateVerify(em, apiContact);
			em.getTransaction().begin();
			// Handle the channels first
			List<Channel> apiChannels = apiContact.getChannels();
			List<ChannelEntity> entityChannels = new ArrayList<>();
			for (Channel channel : apiChannels) {
				ChannelEntity entityChannel = CHANNEL_MAPPER.convertApiToEntity(em, channel);
				em.persist(entityChannel);
				entityChannels.add(entityChannel);
			}
			// Handle the Contact Entity
			apiContact.setChannels(null);
			ContactEntity entityContact = getMapper().convertApiToEntity(em, apiContact, versionId);
			// Add the Channel Entities to the Contact Entity
			for (ChannelEntity channel : entityChannels) {
				entityContact.addChannel(channel);
			}
			em.persist(entityContact);
			em.getTransaction().commit();
			return buildResponse(em, entityContact, versionId);
		} finally {
			em.close();
		}
	}

	@Override
	public DataResponse<Contact> update(final String keyId, final Contact apiContact) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			ContactEntity entityContact = getEntity(em, keyId);
			handleUpdateVerify(em, apiContact, entityContact);
			Long versionId = apiContact.getVersionId();

			// Handle the channels first (Delete Channel should be handled by CASCADES)
			List<Channel> apiChannels = apiContact.getChannels();
			List<ChannelEntity> entityChannels = new ArrayList<>();
			for (Channel channel : apiChannels) {
				String channelId = channel.getId();
				if (MapperUtil.isTempId(channelId)) {
					// Persist
					ChannelEntity entityChannel = CHANNEL_MAPPER.convertApiToEntity(em, channel);
					em.persist(entityChannel);
					entityChannels.add(entityChannel);
				} else {
					// Merge
					ChannelEntity entityChannel = getChannelEntity(em, channelId);
					CHANNEL_MAPPER.copyApiToEntity(em, channel, entityChannel);
					em.merge(entityChannel);
					entityChannels.add(entityChannel);
				}
			}
			// Handle the Contact Entity
			apiContact.setChannels(null);
			getMapper().copyApiToEntity(em, apiContact, entityContact, versionId);
			// Add the Channel Entities to the Contact Entity
			for (ChannelEntity channel : entityChannels) {
				entityContact.addChannel(channel);
			}
			em.merge(entityContact);
			em.getTransaction().commit();
			return buildResponse(em, entityContact, versionId);
		} finally {
			em.close();
		}
	}

	protected ChannelEntity getChannelEntity(final EntityManager em, final String keyId) {
		ChannelEntity entity = MapperUtil.getEntityByApiId(em, keyId, ChannelEntity.class);
		if (entity == null) {
			throw new NotFoundException("Entity [" + keyId + "] not found.");
		}
		return entity;
	}

}
