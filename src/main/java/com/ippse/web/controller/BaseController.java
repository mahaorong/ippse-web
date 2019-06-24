package com.ippse.web.controller;

import java.security.Principal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ippse.web.domain.User;
import com.ippse.web.oauth.UserFeignService;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseController {
	@Autowired
	private OAuth2AuthorizedClientService clientService;

	@Autowired
	protected UserFeignService userFeignService;

	protected String sessuserid = "";
	protected User sessuser;
	protected String accessToken;

	@ModelAttribute
	public void getSessionUser(Model model, Principal principal, Authentication authentication) {
		log.info("BaseController getSessionUser");

		if (principal instanceof OAuth2AuthenticationToken) {
			OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) principal;

			OAuth2AuthorizedClient client = clientService
					.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());
			if (null != client) {
				accessToken = client.getAccessToken().getTokenValue();
				log.info(accessToken);
				log.info(oauthToken.getPrincipal().toString());
				DefaultOAuth2User user = (DefaultOAuth2User) oauthToken.getPrincipal();
				// sessuserid = okuser.getId();
				sessuserid = (String) user.getAttributes().get("id");
				if (StringUtils.isNotBlank(sessuserid)) {
					log.info("BaseController getSessionUser sessuserid:" + sessuserid);
					try {
						sessuser = userFeignService.findById(sessuserid);
					} catch (FeignException e) {
						e.printStackTrace();
					}
				} else {
					log.warn("sessuserid is null in BaseController getSessionUser()");
				}
				model.addAttribute("sessuser", sessuser);
			}
		}
	}

}
