package com.dfusiontech.server.repository.jpa;

import com.dfusiontech.server.model.jpa.entity.Users;
import com.dfusiontech.server.repository.jpa.core.CoreRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CoreRepository<Users, Long> {

	Optional<Users> findById(Long id);

	Users findByEmail(String email);

	Optional<Users> findFirstByEmail(String email);

	List<Users> findByEmailIsLike(String email);

	Optional<Users> findFirstByEmailAndIdIsNotIn(String email, Collection<Long> excludeIds);

}
