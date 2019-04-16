package com.dfusiontech.server.model.dao;

import com.dfusiontech.server.model.data.BaseSort;
import com.dfusiontech.server.model.data.UsersFilter;
import com.dfusiontech.server.model.jpa.entity.Users;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * User DAO Model
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @since    2019-04-16
 */
@Service
public class UserModelDAO implements PageableModelDAO<Users, UsersFilter> {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public PagedResult<Users> getItemsPageable(UsersFilter filter, Pageable pageable, BaseSort sort) {

		String nameFilter = Optional.ofNullable(filter.getName()).orElse("");
		List<Long> excludeIds = null;
		if (filter.getExcludeIds() != null && filter.getExcludeIds().size() > 0) {
			excludeIds = filter.getExcludeIds();
		}
		List<String> roles = null;
		if (filter.getRoles() != null && filter.getRoles().size() > 0) {
			roles = filter.getRoles();
		}

		// Define base hql data Query
		String hqlQuery = "SELECT u FROM Users u JOIN u.organization o JOIN u.roles r " +
			"LEFT JOIN FETCH u.createdBy cb LEFT JOIN FETCH u.updatedBy ub ";

		// Define base count Query
		String hqlQueryCount = "SELECT count(u) FROM Users u JOIN u.organization o JOIN u.roles r ";

		// Build Query String
		String whereString = " WHERE o.id = :organizationId AND u.deleted = false";
		if (StringUtils.isNotEmpty(nameFilter)) {
			whereString += " AND UPPER(u.fullName) LIKE (CONCAT(UPPER(:name), '%'))";
		}
		if (excludeIds != null) {
			whereString += " AND u.id NOT IN :excludeIds";
		}
		if (roles != null) {
			whereString += " AND r.name IN :roles";
		}

		// Build Sort based on the mapping
		String searchQueryString = hqlQuery + whereString;
		Map<String, String> sortMapping = Map.ofEntries(
			Map.entry("id", "u.id"),
			Map.entry("firstName", "u.firstName"),
			Map.entry("lastName", "u.lastName"),
			Map.entry("email", "u.email"),
			Map.entry("enabled", "u.enabled")
		);
		if (sort != null) searchQueryString += sort.toOrderString(sortMapping);

		// Build Query data
		TypedQuery<Users> typedQuery = entityManager.createQuery(searchQueryString, Users.class);
		applySearchFilterValues(nameFilter, excludeIds, roles, typedQuery);
		typedQuery.setMaxResults(pageable.getPageSize());
		typedQuery.setFirstResult((int) pageable.getOffset());
		List<Users> resultList = typedQuery.getResultList();

		// Calculate count query
		Query queryCount = entityManager.createQuery(hqlQueryCount + whereString);
		applySearchFilterValues(nameFilter, excludeIds, roles, queryCount);
		Long resultsCount = (Long) queryCount.getSingleResult();

		return new PagedResult<Users>(resultList, resultsCount);
	}

	/**
	 * Apply query data
	 *
	 * @param nameFilter
	 * @param excludeIds
	 * @param roles
	 * @param query
	 */
	private void applySearchFilterValues(String nameFilter, List<Long> excludeIds, List<String> roles, Query query) {
		if (StringUtils.isNotEmpty(nameFilter)) query.setParameter("name", nameFilter);
		if (excludeIds != null) query.setParameter("excludeIds", excludeIds);
		if (roles != null) query.setParameter("roles", roles);
	}

}
