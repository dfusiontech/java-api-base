package com.dfusiontech.server.model.dto.role;

import com.dfusiontech.server.model.dto.DTOBase;
import com.dfusiontech.server.model.jpa.entity.Roles;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Role List Entity Definition
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  1.11.1
 * @since    2018-10-27
 */
@Setter
@Getter
@ToString(of = {"id", "name"})
@EqualsAndHashCode(of = {"id", "name"})
public class RoleListDTO extends DTOBase<Roles> {
	private Long id;
	private String name;
	private String description;
}
