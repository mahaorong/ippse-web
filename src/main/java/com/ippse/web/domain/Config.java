package com.ippse.web.domain;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Config implements java.io.Serializable {
	private static final long serialVersionUID = 1801219483748426473L;

	@JsonView(User.Views.Profile.class)
	private String property;

	@JsonView(User.Views.Profile.class)
	private String name;

	@JsonView(User.Views.Profile.class)
	private Set<String> vals = new HashSet<String>();

	@JsonView(User.Views.Profile.class)
	private Set<String> options = new HashSet<String>();

	@JsonView(User.Views.Profile.class)
	private Set<String> defvals = new HashSet<String>();

	@JsonView(User.Views.Profile.class)
	private String type;

	public Config(String property, String name, Set<String> options, Set<String> defvals, String type) {
		this.property = property;
		this.name = name;
		this.options = options;
		this.defvals = defvals;
		this.type = type;
	}

	public boolean check(String option) {
		for (String val : this.getVals()) {
			if (StringUtils.equals(option, val)) {
				return true;
			}
		}
		return false;
	}
}