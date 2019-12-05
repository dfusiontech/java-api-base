package com.dfusiontech.server.repository;

import com.dfusiontech.server.model.jpa.entity.Country;
import com.dfusiontech.server.repository.jpa.core.CoreRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends CoreRepository<Country, Long> {

	Optional<Country> findById(Long id);

	Optional<Country> findFirstByName(String name);

	@Query("SELECT c FROM Country c WHERE UPPER(c.name) LIKE (CONCAT(UPPER(:name), '%'))")
	List<Country> getListByName(@Param("name") String name, Pageable pageable);

	@Query("SELECT count(c) FROM Country c WHERE UPPER(c.name) LIKE (CONCAT(UPPER(:name), '%'))")
	Long getCountByName(@Param("name") String name);

}
