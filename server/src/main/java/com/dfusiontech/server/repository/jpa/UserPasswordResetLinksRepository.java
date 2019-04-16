package com.dfusiontech.server.repository.jpa;

import com.dfusiontech.server.model.jpa.entity.UserPasswordResetLinks;
import com.dfusiontech.server.repository.jpa.core.CoreRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPasswordResetLinksRepository extends CoreRepository<UserPasswordResetLinks, Long> {

	Optional<UserPasswordResetLinks> findById(Long id);

	Optional<UserPasswordResetLinks> findFirstByCode(String code);

}
