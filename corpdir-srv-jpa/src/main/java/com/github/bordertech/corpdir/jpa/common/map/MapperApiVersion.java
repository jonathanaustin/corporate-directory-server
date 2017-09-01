package com.github.bordertech.corpdir.jpa.common.map;

import com.github.bordertech.corpdir.api.common.ApiObject;
import com.github.bordertech.corpdir.api.common.ApiVersionable;
import com.github.bordertech.corpdir.jpa.common.feature.PersistVersionData;
import com.github.bordertech.corpdir.jpa.common.feature.PersistVersionable;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Map {@link ApiObject} and {@link PersistentKeyIdObject}.
 *
 * @author jonathan
 * @param <A> the API object
 * @param <U> the version data type
 * @param <P> the Entity object
 */
public interface MapperApiVersion<A extends ApiVersionable, U extends PersistVersionable<U, P>, P extends PersistVersionData<U>> {

	/**
	 *
	 * @param em the entity manager
	 * @param from the API item
	 * @param versionId the versionId to map
	 * @return the entity item
	 */
	P convertApiToEntity(final EntityManager em, final A from, final Integer versionId);

	/**
	 *
	 * @param em the entity manager
	 * @param from the API item
	 * @param to the entity
	 * @param versionId the versionId to map
	 */
	void copyApiToEntity(final EntityManager em, final A from, final P to, final Integer versionId);

	/**
	 * @param em the entity manager
	 * @param from the entity item
	 * @param versionId the versionId to map
	 * @return the API item
	 */
	A convertEntityToApi(final EntityManager em, final P from, final Integer versionId);

	/**
	 * @param em the entity manager
	 * @param from the entity item
	 * @param to the API item
	 * @param versionId the versionId to map
	 */
	void copyEntityToApi(final EntityManager em, final P from, final A to, final Integer versionId);

	/**
	 *
	 * @param em the entity manager
	 * @param rows the list of entity items
	 * @param versionId the versionId to map
	 * @return the list of converted API items
	 */
	List<A> convertEntitiesToApis(final EntityManager em, final Collection<P> rows, final Integer versionId);

	/**
	 *
	 * @param em the entity manager
	 * @param rows the list of API items
	 * @param versionId the versionId to map
	 * @return the list of converted Entity items
	 */
	List<P> convertApisToEntities(final EntityManager em, final Collection<A> rows, final Integer versionId);

}
