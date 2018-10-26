package com.dfusiontech.server.model.jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * User Entity Definition
 *
 * @author Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  1.11.1
 * @since    2018-10-17
 */
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString(of = {"id", "email"})
@EqualsAndHashCode(of = {"id", "email"})
public class UsersEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "password")
	private String password;

	/* enabled boolean default true,
				expired boolean default true,
				credentials_expired boolean default true,
				locked boolean default true,
				created_at timestamp without time zone NOT NULL DEFAULT now(),
				updated_at timestamp without time zone NOT NULL DEFAULT now(),
				expiration_date timestamp without time zone DEFAULT NULL,
				credentials_expiration_date timestamp without time zone DEFAULT NULL, */


	@Column(name = "enabled")
	private Boolean enabled;

	@Column(name = "expired")
	private Boolean expired;

	@Column(name = "credentials_expired")
	private Boolean credentialsExpired;

	@Column(name = "locked")
	private Boolean locked;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expiration_date")
	private Date expirationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "credentials_expiration_date")
	private Date credentialsExpirationDate;

}
