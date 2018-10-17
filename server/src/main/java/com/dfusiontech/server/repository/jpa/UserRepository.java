package com.dfusiontech.server.repository.jpa;

import com.dfusiontech.server.model.jpa.entity.UsersEntity;
import com.dfusiontech.server.repository.jpa.core.CoreRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CoreRepository<UsersEntity, Integer> {

	Optional<UsersEntity> findById(Integer id);

	UsersEntity findByEmail(String email);

	Optional<UsersEntity> findFirstByEmail(String email);

	List<UsersEntity> findByEmailIsLike(String email);

}
