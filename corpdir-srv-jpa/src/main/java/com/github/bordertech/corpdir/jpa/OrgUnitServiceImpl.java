package com.github.bordertech.corpdir.jpa;

import com.github.bordertech.corpdir.api.OrgUnitService;
import com.github.bordertech.corpdir.api.data.Contact;
import com.github.bordertech.corpdir.api.data.OrgUnit;
import com.github.bordertech.corpdir.api.data.Position;
import com.github.bordertech.corpdir.jpa.entity.ContactEntity;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity;
import com.github.bordertech.corpdir.jpa.entity.OrgUnitEntity_;
import com.github.bordertech.corpdir.jpa.entity.PositionEntity;
import java.util.List;
import java.util.Objects;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Organization unit JPA service implementation.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Singleton
public class OrgUnitServiceImpl extends AbstractService implements OrgUnitService {

	@Override
	public OrgUnit getOrgUnit(final Long orgUnitId) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitId);
			return MapperUtil.convertOrgUnitEntityToApi(entity);
		} finally {
			em.close();
		}
	}

	@Override
	public OrgUnit getOrgUnit(final String orgUnitAltKey) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitAltKey);
			return MapperUtil.convertOrgUnitEntityToApi(entity);
		} finally {
			em.close();
		}
	}

	@Override
	public List<OrgUnit> getSubOrgUnits(final Long orgUnitId) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitId);
			if (entity == null) {
				return null;
			}
			List<OrgUnitEntity> subUnits = entity.getSubOrgUnits();
			return MapperUtil.convertListOrgUnitEntityToApi(subUnits);
		} finally {
			em.close();
		}
	}

	@Override
	public List<OrgUnit> getSubOrgUnits(final String orgUnitAltKey) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitAltKey);
			if (entity == null) {
				return null;
			}
			List<OrgUnitEntity> subUnits = entity.getSubOrgUnits();
			return MapperUtil.convertListOrgUnitEntityToApi(subUnits);
		} finally {
			em.close();
		}
	}

	@Override
	public List<Position> getAssignedPositions(final Long orgUnitId) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitId);
			if (entity == null) {
				return null;
			}
			List<PositionEntity> positions = entity.getPositions();
			return MapperUtil.convertListPositionEntityToApi(positions);
		} finally {
			em.close();
		}
	}

	@Override
	public List<Position> getAssignedPositions(final String orgUnitAltKey) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitAltKey);
			if (entity == null) {
				return null;
			}
			List<PositionEntity> positions = entity.getPositions();
			return MapperUtil.convertListPositionEntityToApi(positions);
		} finally {
			em.close();
		}
	}

	@Override
	public List<Contact> getLinkedContacts(final Long orgUnitId) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitId);
			if (entity == null) {
				return null;
			}
			List<ContactEntity> contacts = entity.getContacts();
			return MapperUtil.convertListContactEntityToApi(contacts);
		} finally {
			em.close();
		}
	}

	@Override
	public List<Contact> getLinkedContacts(final String orgUnitAltKey) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitAltKey);
			if (entity == null) {
				return null;
			}
			List<ContactEntity> contacts = entity.getContacts();
			return MapperUtil.convertListContactEntityToApi(contacts);
		} finally {
			em.close();
		}
	}

	@Override
	public Long createOrgUnit(final OrgUnit orgUnit) {
		EntityManager em = getEntityManager();
		try {
			OrgUnitEntity entity = MapperUtil.convertOrgUnitApiToEntity(orgUnit);
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
			return entity.getId();
		} finally {
			em.close();
		}
	}

	@Override
	public OrgUnit updateOrgUnit(final Long orgUnitId, final OrgUnit orgUnit) {
		EntityManager em = getEntityManager();
		try {
			if (orgUnitId == null || orgUnit == null || !Objects.equals(orgUnitId, orgUnit.getId())) {
				// TODO throw an exception
				return orgUnit;
			}

			em.getTransaction().begin();
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitId);
			if (entity == null) {
				// TODO throw an exception
			}
			MapperUtil.copyOrgUnitApiToEntity(orgUnit, entity);
			em.merge(entity);
			em.getTransaction().commit();
			OrgUnit updated = MapperUtil.convertOrgUnitEntityToApi(entity);
			return updated;
		} finally {
			em.close();
		}
	}

	@Override
	public void deleteOrgUnit(final Long orgUnitId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			OrgUnitEntity entity = getOrgUnitEntity(em, orgUnitId);
			if (entity != null) {
				em.remove(entity);
			}
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	@Override
	public void assignOrgUnitToOrgUnit(final Long orgUnitId, final Long parentOrgUnitId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the org unit
			OrgUnitEntity orgUnit = getOrgUnitEntity(em, orgUnitId);
			if (orgUnit == null) {
				throw new IllegalStateException("Org unit [" + orgUnitId + "] does not exist.");
			}
			// Get the new parent entity
			OrgUnitEntity parent = getOrgUnitEntity(em, parentOrgUnitId);
			if (orgUnit == null) {
				throw new IllegalStateException("Parent org unit [" + parentOrgUnitId + "] does not exist.");
			}
			// Remove Org Unit from its OLD parent (if it had one)
			OrgUnitEntity oldParent = orgUnit.getParentOrgUnit();
			if (oldParent != null) {
				oldParent.removeSubOrgUnit(orgUnit);
				em.merge(oldParent);
			}
			// Add to the new parent
			parent.addSubOrgUnit(orgUnit);
			em.merge(parent);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	@Override
	public void assignPosition(final Long orgUnitId, final Long positionId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the org unit
			OrgUnitEntity orgUnit = getOrgUnitEntity(em, orgUnitId);
			if (orgUnit == null) {
				throw new IllegalStateException("Org unit [" + orgUnitId + "] does not exist.");
			}
			// Get the position
			PositionEntity position = getPositionEntity(em, positionId);
			if (position == null) {
				throw new IllegalStateException("Position [" + positionId + "] does not exist.");
			}
			// TODO Might need to remove the position from the old OrgUnit
			// TODO Might need to check if the position belongs to another position
			// Add the position to the org unit
			orgUnit.addPosition(position);
			em.merge(orgUnit);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	@Override
	public void unassignPosition(final Long orgUnitId, final Long positionId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the org unit
			OrgUnitEntity orgUnit = getOrgUnitEntity(em, orgUnitId);
			if (orgUnit == null) {
				throw new IllegalStateException("Org unit [" + orgUnitId + "] does not exist.");
			}
			// Get the position
			PositionEntity position = getPositionEntity(em, positionId);
			if (position == null) {
				throw new IllegalStateException("Position [" + positionId + "] does not exist.");
			}
			// Remove the position from the org unit
			orgUnit.removePosition(position);
			em.merge(orgUnit);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	@Override
	public void linkContact(final Long orgUnitId, final Long contactId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the org unit
			OrgUnitEntity orgUnit = getOrgUnitEntity(em, orgUnitId);
			if (orgUnit == null) {
				throw new IllegalStateException("Org unit [" + orgUnitId + "] does not exist.");
			}
			// Get the contact
			ContactEntity contact = getContactEntity(em, contactId);
			if (contact == null) {
				throw new IllegalStateException("Contact [" + contactId + "] does not exist.");
			}
			// TODO Might need to remove the contact from an old OrgUnit
			// TODO Might need to check if the position belongs to another position
			// Add the contact to the org unit
			orgUnit.addContact(contact);
			em.merge(orgUnit);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	@Override
	public void unlinkContact(final Long orgUnitId, final Long contactId) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			// Get the org unit
			OrgUnitEntity orgUnit = getOrgUnitEntity(em, orgUnitId);
			if (orgUnit == null) {
				throw new IllegalStateException("Org unit [" + orgUnitId + "] does not exist.");
			}
			// Get the contact
			ContactEntity contact = getContactEntity(em, contactId);
			if (contact == null) {
				throw new IllegalStateException("Contact [" + contactId + "] does not exist.");
			}
			// Remove the contact from the org unit
			orgUnit.removeContact(contact);
			em.merge(orgUnit);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	/**
	 * Get the org unit.
	 *
	 * @param em the entity manager
	 * @param id the record id
	 * @return the entity
	 */
	private OrgUnitEntity getOrgUnitEntity(final EntityManager em, final Long id) {
		OrgUnitEntity entity = em.find(OrgUnitEntity.class, id);
		return entity;
	}

	/**
	 * Get the org unit via the alternate key.
	 *
	 * @param em the entity manager
	 * @param altKey the record alternate key
	 * @return the entity
	 */
	private OrgUnitEntity getOrgUnitEntity(final EntityManager em, final String altKey) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<OrgUnitEntity> qry = cb.createQuery(OrgUnitEntity.class);
		Root<OrgUnitEntity> from = qry.from(OrgUnitEntity.class);
		qry.select(from);
		qry.where(cb.equal(from.get(OrgUnitEntity_.alternateKey), altKey));

		OrgUnitEntity entity = em.createQuery(qry).getSingleResult();
		return entity;
	}

	/**
	 * Get the contact entity.
	 *
	 * @param em the entity manager
	 * @param id the record id
	 * @return the entity
	 */
	private ContactEntity getContactEntity(final EntityManager em, final Long id) {
		ContactEntity entity = em.find(ContactEntity.class, id);
		return entity;
	}

	/**
	 * Get the position entity.
	 *
	 * @param em the entity manager
	 * @param id the record id
	 * @return the entity
	 */
	private PositionEntity getPositionEntity(final EntityManager em, final Long id) {
		PositionEntity entity = em.find(PositionEntity.class, id);
		return entity;
	}

}
