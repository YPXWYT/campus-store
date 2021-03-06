package com.tna.campus_store.beans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="sys_user")
@JsonIgnoreProperties(value = {"createTime","modifyTime","products",
		"orders","addresses"})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nick;
	private Character sex;
	@Column(unique = true)
	private String account;
	private String password;
	@Column(unique = true)
	private String email;
	@Column(scale = 2)
	private Double money=0.0;
	@Column(name = "head_img")
	private String headImg;
	@Column(name = "phone_number")
	private String phoneNumber;
	private Integer reputation;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
	private Date createTime = new Date();
	@Column(name = "modify_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
	private Date modifyTime = new Date();
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	private Set<Product> products = new HashSet<Product>();
	
	@JoinTable(name = "sys_user_role",
			joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
	@ManyToMany(targetEntity = Role.class,fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<Role>();
	
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	private Set<Order> orders = new HashSet<Order>();
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	private Set<Address> addresses = new HashSet<Address>(); 
	@OneToOne(mappedBy = "user",fetch = FetchType.LAZY)
	private Identification identification;		

	

	@Override
	public String toString() {
		return "User [id=" + id + ", account=" + account + ", password=" + password + ", email=" + email + "]";
	}

	public Identification getIdentification() {
		return identification;
	}

	public void setIdentification(Identification identification) {
		this.identification = identification;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}


	public void setNick(String nick) {
		this.nick = nick;
	}


	public Character getSex() {
		return sex;
	}


	public void setSex(Character sex) {
		this.sex = sex;
	}


	public String getAccount() {
		return account;
	}


	public void setAccount(String account) {
		this.account = account;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Double getMoney() {
		return money;
	}


	public void setMoney(Double money) {
		this.money = money;
	}


	public String getHeadImg() {
		return headImg;
	}


	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getReputation() {
		return reputation;
	}


	public void setReputation(Integer reputation) {
		this.reputation = reputation;
	}

	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getModifyTime() {
		return modifyTime;
	}


	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}


	public Set<Product> getProducts() {
		return products;
	}


	public void setProducts(Set<Product> products) {
		this.products = products;
	}


	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	public Set<Order> getOrders() {
		return orders;
	}


	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
}
