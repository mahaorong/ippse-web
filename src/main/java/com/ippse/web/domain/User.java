package com.ippse.web.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements OAuth2User,Serializable {
	private static final long serialVersionUID = 1767052910033405847L;
	private List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
	private Map<String, Object> attributes;

	public static final class Views {
		public interface Public {
		}

		public interface Friend extends Public {
		}

		public interface Profile extends Friend {
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public Map<String, Object> getAttributes() {
		if (this.attributes == null) {
			this.attributes = new HashMap<>();
			this.attributes.put("id", this.getId());
			this.attributes.put("name", this.getName());
			this.attributes.put("username", this.getUsername());
			this.attributes.put("email", this.getEmail());
		}
		return attributes;
	}

	@JsonView(User.Views.Public.class)
	private String id;

	@JsonView(User.Views.Public.class)
	private String username;
	private String password;

	@JsonView(User.Views.Public.class)
	private String firstName;

	@JsonView(User.Views.Public.class)
	private String lastName;

	@JsonView(User.Views.Profile.class)
	private String email;

	@JsonView(User.Views.Profile.class)
	private String code;
	@JsonView(User.Views.Profile.class)
	private String mobile;
	@JsonView(User.Views.Profile.class)
	private Date lastPasswordResetDate;
	@JsonView(User.Views.Profile.class)
	private String type;

	@JsonView(User.Views.Public.class)
	private String status;
	@JsonView(User.Views.Profile.class)
	private String certcode;

	@JsonView(User.Views.Public.class)
	private Integer rank;
	@JsonView(User.Views.Public.class)
	private Integer follow;
	@JsonView(User.Views.Public.class)
	private Integer fans;
	@JsonView(User.Views.Public.class)
	private Integer posts;

	@JsonView(User.Views.Public.class)
	private String name;
	@JsonView(User.Views.Public.class)
	private String image;
	@JsonView(User.Views.Public.class)
	private String nickname;

	@JsonView(User.Views.Profile.class)
	private String homepage;
	@JsonView(User.Views.Profile.class)
	private String intro;
	@JsonView(User.Views.Profile.class)
	private String sex;
	@JsonView(User.Views.Profile.class)
	private Date birthday;
	@JsonView(User.Views.Profile.class)
	private String marriage;
	@JsonView(User.Views.Profile.class)
	private String bloodtype;
	@JsonView(User.Views.Profile.class)
	private String hometown;
	@JsonView(User.Views.Profile.class)
	private String edulevel;
	@JsonView(User.Views.Profile.class)
	private String religion;
	@JsonView(User.Views.Profile.class)
	private String hobby;

	@JsonView(User.Views.Public.class)
	private String signature;

	@JsonView(User.Views.Profile.class)
	private Timestamp ctime;

	@JsonView(User.Views.Profile.class)
	private Set<String> roles = new HashSet<String>();

	@JsonView(User.Views.Profile.class)
	private Set<Config> configs = new HashSet<Config>(0);

	// 不用于json输出，仅业务层需要
	public Set<String> getConfigValue(String property) {
		for (Config config : this.getConfigs()) {
			if (StringUtils.equals(config.getProperty(), property))
				return config.getVals();
		} // TODO 增加默认值
		return new HashSet<String>();
	}

}
