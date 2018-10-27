package com.dfusiontech.server.model.jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
@AllArgsConstructor
@Setter
@Getter
@ToString(of = {"id", "email"})
@EqualsAndHashCode(of = {"id", "email"})
public class Users {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "password")
	private String password;

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

	@ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.LAZY)
	@JoinTable(
		name = "user_roles",
		joinColumns = {@JoinColumn(name = "user_id")},
		inverseJoinColumns = {@JoinColumn(name = "role_id")}
	)
	Set<Roles> roles = new HashSet<>();

	/**
	 * Default constructor
	 */
	public Users() {
		enabled = true;
		expired = false;
		locked = false;
		credentialsExpired = false;
	}

}
