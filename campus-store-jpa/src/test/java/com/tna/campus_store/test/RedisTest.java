package com.tna.campus_store.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tna.campus_store.utils.RedisUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class RedisTest {

	@Autowired
	private RedisUtils redisUtils;
	
	@Test
	public void testSet() {
		System.out.println(redisUtils.set("ypx", 999,3));
//		System.out.println(redisUtils.get("k1"));
	}
	
	@Test
	public void testHaseKey() {
		System.out.println(redisUtils.hasKey("k1"));
	}
	
	@Test
	public void testDeleteKey() {
		redisUtils.del("ypx");
		System.out.println(redisUtils.hasKey("ypx"));
	}
}
