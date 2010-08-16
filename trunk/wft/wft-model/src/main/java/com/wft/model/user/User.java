package com.wft.model.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import net.sf.gilead.pojo.java5.LightEntity;

@Entity
@Table(name = "USER")
public class User extends LightEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6107680353319244730L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "LOGIN")
	private String login;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "FIRSTNAME")
	private String firstName;

	@Column(name = "LASTNAME")
	private String lastName;

	@Column(name = "BIRTHDATE")
	private Date birthDate;

	@Column(name = "MAILADRESS")
	private String mailAdress;

	@Column(name = "ROLE")
	protected Role getRole() {
		return Role.ROLE_USER;
	}

	public User() {
	}

	public User(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}

}
