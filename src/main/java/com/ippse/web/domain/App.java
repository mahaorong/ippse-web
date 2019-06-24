package com.ippse.web.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonStringType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "comments" })
@TypeDef(name = "json", typeClass = JsonStringType.class)
@Entity
@Table(name = "app")
public class App implements java.io.Serializable {
	private static final long serialVersionUID = -3265224646807002419L;

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String description;

	private BigDecimal price;

	@Column(nullable = false)
	private String icon;

	@Type(type = "json")
	@Column(columnDefinition = "json")
	private List<String> images = new ArrayList<>();

	@Column(name = "userid", nullable = false, length = 32)
	private String userid;

	@Transient
	private User user;

	@Column(name = "developerid", nullable = false, length = 32)
	private String developerid;
	@Transient
	private User developer;

	private BigDecimal size;

	private String copyright;

	@Column(name = "pubtime", length = 19)
	private Timestamp pubtime;

	@Column(nullable = false)
	private String status;

	@Column(name = "ctime", nullable = false, length = 19)
	private Timestamp ctime;

	@OneToMany(mappedBy = "app", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Comment> comments = new HashSet<Comment>();

	@NotEmpty(message = "标签")
	@Type(type = "json")
	@Column(columnDefinition = "json")
	private List<String> tags = new ArrayList<String>();

}
