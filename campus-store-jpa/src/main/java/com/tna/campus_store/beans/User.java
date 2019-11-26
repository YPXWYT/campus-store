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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="sys_user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "student_id")
	private String studentId;
	private String name;
	private String nick;
	private Character sex;
	private String account;
	private String password;
	private String email;
	@Column(scale = 2)
	private Double money;
	@Column(name = "head_img")
	private String headImg;
	@Column(name = "phone_number")
	private String phoneNumber;
	private String address;
	private Integer reputation;
	@Column(name = "confirm_status")
	private Integer confirmStatus;
	@Column(name = "inform_count")
	private Integer informCount;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
	private Date createTime;
	@Column(name = "modify_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
	private Date modifyTime;
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Product> products = new HashSet<Product>();
	
	@JoinTable(name = "sys_user_role",
			joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
	@ManyToMany(targetEntity = Role.class,fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Role> roles = new HashSet<Role>();
	
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Order> orders = new HashSet<Order>();

	public User(String name, Date createTime, Date modifyTime) {
		super();
		this.name = name;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
	}

	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", studentId=" + studentId + ", name=" + name + ", nick=" + nick + ", sex=" + sex
				+ ", account=" + account + ", password=" + password + ", email=" + email + ", money=" + money
				+ ", headImg=" + headImg + ", phoneNumber=" + phoneNumber + ", address=" + address + ", reputation="
				+ reputation + ", confirmStatus=" + confirmStatus + ", informCount=" + informCount + ", createTime="
				+ createTime + ", modifyTime=" + modifyTime + "]";
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getStudentId() {
		return studentId;
	}


	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Integer getReputation() {
		return reputation;
	}


	public void setReputation(Integer reputation) {
		this.reputation = reputation;
	}


	public Integer getConfirmStatus() {
		return confirmStatus;
	}


	public void setConfirmStatus(Integer confirmStatus) {
		this.confirmStatus = confirmStatus;
	}


	public Integer getInformCount() {
		return informCount;
	}


	public void setInformCount(Integer informCount) {
		this.informCount = informCount;
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
