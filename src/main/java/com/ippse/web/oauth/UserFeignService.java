package com.ippse.web.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ippse.web.domain.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserFeignService {

	@Autowired
	protected UserFeignClient userFeignClient;

	public User findById(String userid) {
		log.info("UserFeignService findById:" + userid);
		User user = new User();
		try {
			user = userFeignClient.findById(userid);
			log.info("UserFeignService user:" + user.toString());
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return user;
	}

}
