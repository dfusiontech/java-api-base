package com.dfusiontech.server.repository.jpa.core;

import org.hibernate.Session;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Base Repository Definition
 *
 * @param <T>
 * @param <ID>
 * @author Eugene A. Kalosha <ekalosha@dfusiontech.com>
 */
@NoRepositoryBean
public interface CoreRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID>, JpaSpecificationExecutor<T> {

	void detach(T object);

	void detach(Iterable<T> objects);

	T saveAndRefresh(T object);

	List<T> saveAndRefresh(Iterable<T> objects);

	void refresh(T object);

	void refresh(Iterable<T> objects);

	Session revealSession();

	void executeStoredProcedure(String procedureName, Map<String, Object> parameters);

	<M> List<M> executeStoredProcedure(String procedureName, Map<String, Object> parameters, Class<M> mappedClass);

	<M> List<M> executeQuery(String queryString, Map<String, Object> parameters, Class<M> mappedClass);

	T getReference(ID id);

	void flush();

	List<T> findAll();

	<M> List<M> executeNativeQuery(String sqlQuery, Map<String, Object> parameters, Pageable pageable, Class<M> mappedClass);

	BigInteger executeNativeCountQuery(String sqlQuery, Map<String, Object> parameters);
}
