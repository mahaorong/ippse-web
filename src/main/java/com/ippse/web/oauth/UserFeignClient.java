package com.ippse.web.oauth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ippse.web.domain.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@FeignClient(value = "kxuser-api"/*, fallback = UserFeignClientFallback.class*/)
public interface UserFeignClient {
	
	//获取id查询用户
	@RequestMapping(value = "/api/user/{userid}", method = RequestMethod.GET)
	public User findById(@PathVariable("userid") String userid);
	
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class UserParam {
	String id;
	String username;
}
/*@Component
class UserFeignClientFallback implements UserFeignClient {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public User findById(String userid) {
		log.info("failed......." + userid);
		return new User();
	}

	@Override
	public User findByUsername(UserParam userParam) {
		log.info("failed......." + userParam.toString());
		return new User();
	}

}*/
