package com.dfusiontech.server.repository;

import com.dfusiontech.server.model.jpa.entity.Country;
import com.dfusiontech.server.model.jpa.entity.State;
import com.dfusiontech.server.repository.jpa.core.CoreRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StateRepository extends CoreRepository<State, Long> {

	Optional<State> findById(Long id);

	Optional<State> findFirstByNameAndCountry(String name, Country country);

	@Query("SELECT s FROM State s JOIN s.country c WHERE c.id = :countryId AND UPPER(s.name) LIKE (CONCAT(UPPER(:name), '%'))")
	List<State> getListByName(@Param("countryId") Long countryId, @Param("name") String name, Pageable pageable);

	@Query("SELECT count(s) FROM State s JOIN s.country c WHERE c.id = :countryId AND UPPER(s.name) LIKE (CONCAT(UPPER(:name), '%'))")
	Long getCountByName(@Param("countryId") Long countryId, @Param("name") String name);

}
