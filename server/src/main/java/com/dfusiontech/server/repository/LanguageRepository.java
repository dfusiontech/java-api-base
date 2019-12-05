package com.dfusiontech.server.repository;

import com.dfusiontech.server.model.jpa.entity.Language;
import com.dfusiontech.server.repository.jpa.core.CoreRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LanguageRepository extends CoreRepository<Language, Long> {

	Optional<Language> findById(Long id);

	Optional<Language> findFirstByName(String name);

	@Query("SELECT l FROM Language l WHERE UPPER(l.name) LIKE (CONCAT(UPPER(:name), '%'))")
	List<Language> getListByName(@Param("name") String name, Pageable pageable);

	@Query("SELECT count(l) FROM Language l WHERE UPPER(l.name) LIKE (CONCAT(UPPER(:name), '%'))")
	Long getCountByName(@Param("name") String name);

}
