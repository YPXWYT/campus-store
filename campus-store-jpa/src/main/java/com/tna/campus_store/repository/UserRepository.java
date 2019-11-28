package com.tna.campus_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tna.campus_store.beans.User;

/*
 * 注：若在JpaRepository<User, Integer>指定了类型后，就只能存储该类型的对象。
 */
public interface UserRepository extends JpaRepository<User, Integer>
,UserRepositoryDefine
{
	User findByAccountAndPassword(String account,String password);
	
	User findByEmailAndPassword(String email,String password);
	
	User findByPhoneNumberAndPassword(String phoneNumber,String password);
	
	User findByPhoneNumber(String phoneNumber);
	
	User findByEmail(String email);
	
	User findByAccount(String account);
}
