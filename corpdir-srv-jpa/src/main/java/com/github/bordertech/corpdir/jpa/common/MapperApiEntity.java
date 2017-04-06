package com.github.bordertech.corpdir.jpa.common;

import com.github.bordertech.corpdir.api.common.ApiObject;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Map {@link ApiObject} and {@link PersistentKeyIdObject}.
 *
 * @author jonathan
 * @param <A> the API object
 * @param <P> the Entity object
 */
public interface MapperApiEntity<A extends ApiObject, P extends PersistentObject> {

	/**
	 *
	 * @param em the entity manager
	 * @param from the API item
	 * @return the entity item
	 */
	P convertApiToEntity(final EntityManager em, final A from);

	/**
	 *
	 * @param em the entity manager
	 * @param from the API item
	 * @param to the entity
	 */
	void copyApiToEntity(final EntityManager em, final A from, final P to);

	/**
	 * @param em the entity manager
	 * @param from the entity item
	 * @return the API item
	 */
	A convertEntityToApi(final EntityManager em, final P from);

	/**
	 * @param em the entity manager
	 * @param from the entity item
	 * @param to the API item
	 */
	void copyEntityToApi(final EntityManager em, final P from, final A to);

	/**
	 *
	 * @param em the entity manager
	 * @param rows the list of entity items
	 * @return the list of converted API items
	 */
	List<A> convertEntitiesToApis(final EntityManager em, final Collection<P> rows);

	/**
	 *
	 * @param em the entity manager
	 * @param rows the list of API items
	 * @return the list of converted Entity items
	 */
	List<P> convertApisToEntities(final EntityManager em, final Collection<A> rows);

}
