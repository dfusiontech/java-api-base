package com.dfusiontech.server.repository.jpa;

import com.dfusiontech.server.model.jpa.entity.RolesEntity;
import com.dfusiontech.server.model.jpa.entity.UsersEntity;
import com.dfusiontech.server.repository.jpa.core.CoreRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends CoreRepository<RolesEntity, Integer> {

	@Query(value = "SELECT r.* FROM roles r INNER JOIN user_roles ur ON ur.role_id=r.id " +
		"INNER JOIN users u ON ur.user_id = u.id WHERE u.email = ?1", nativeQuery = true)
	List<RolesEntity> getUserRolesByEmail(String email);

}
