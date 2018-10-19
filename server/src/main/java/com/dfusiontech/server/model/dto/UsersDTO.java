package com.dfusiontech.server.model.dto;

import com.dfusiontech.server.model.jpa.entity.UsersEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * User Entity Definition
 *
 * @author Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  1.11.1
 * @since    2018-10-17
 */
@Setter
@Getter
@ToString(of = {"id", "email"})
@EqualsAndHashCode(of = {"id", "email"})
public class UsersDTO extends DTOBase<UsersEntity> {

	private Integer id;
	private String email;
	private String password;
	private Date createdAt;
	private Date updatedAt;

}