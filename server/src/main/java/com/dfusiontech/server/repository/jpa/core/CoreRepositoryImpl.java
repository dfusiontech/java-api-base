package com.dfusiontech.server.repository.jpa.core;

import org.hibernate.Session;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Base implementation of JPA repositories, based on current EntityManager instance.
 *
 * @author Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @param <T>
 * @param <ID>
 */
public class CoreRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements CoreRepository<T, ID> {

	private final EntityManager entityManager;

	private final Class<T> typeParameterClass = null;

	public CoreRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	public void detach(T object) {
		entityManager.detach(object);
	}

	@Override
	public void detach(Iterable<T> objects) {
		if (objects != null) {
			objects.forEach(entityManager::detach);
		}
	}

	@Override
	public T saveAndRefresh(T object) {
		T t = save(object);
		refresh(t);
		return t;
	}

	@Override
	public List<T> saveAndRefresh(Iterable<T> objects) {
		List<T> list = saveAll(objects);
		refresh(list);
		return list;
	}

	@Override
	public void refresh(T object) {
		entityManager.refresh(object);
	}

	@Override
	public void refresh(Iterable<T> objects) {
		objects.forEach(this::refresh);
	}

	@Override
	public Session revealSession() {
		return entityManager.unwrap(Session.class);
	}

	@Override
	public void executeStoredProcedure(String procedureName, Map<String, Object> parameters) {
		Query query = entityManager.createNativeQuery(procedureName);
		if (parameters != null) {
			parameters.forEach(query::setParameter);
		}
		query.executeUpdate();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <M> List<M> executeStoredProcedure(String procedureName, Map<String, Object> parameters, Class<M> mappedClass) {
		StoredProcedureQuery storedProcedure = entityManager.createNamedStoredProcedureQuery(procedureName);
		if (parameters != null) {
			parameters.forEach(storedProcedure::setParameter);
		}
		return (List<M>) storedProcedure.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <M> List<M> executeQuery(String queryString, Map<String, Object> parameters, Class<M> mappedClass) {
		TypedQuery<M> typedQuery = entityManager.createQuery(queryString, mappedClass);
		if (parameters != null) {
			parameters.forEach(typedQuery::setParameter);
		}
		return typedQuery.getResultList();
	}

	@Override
	public T getReference(ID id) {
		return entityManager.getReference(typeParameterClass, id);
	}

	@Override
	public void flush() {
		entityManager.flush();
	}

	@Override
	public <M> List<M> executeNativeQuery(String sqlQuery, Map<String, Object> parameters, Pageable pageable, Class<M> mappedClass) {
		Query query = entityManager.createNativeQuery(sqlQuery, mappedClass);
		if (parameters != null) {
			parameters.forEach(query::setParameter);
		}
		if (pageable != null) {
			query.setFirstResult((int) pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
		}
		return (List<M>) query.getResultList();
	}

	@Override
	public BigInteger executeNativeCountQuery(String sqlQuery, Map<String, Object> parameters) {
		Query query = entityManager.createNativeQuery(sqlQuery);
		if (parameters != null) {
			parameters.forEach(query::setParameter);
		}
		return (BigInteger) query.getSingleResult();
	}
}
