package com.ippse.web.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ippse.web.utils.KeyUtil;
import com.ippse.web.utils.Time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements java.io.Serializable {
	private static final long serialVersionUID = -3265231236807002419L;

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appid")
	@NotFound(action = NotFoundAction.IGNORE)
	private App app;

	@Column(name = "userid", length = 32)
	private String userid;

	@Transient
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pid")
	private Comment comment;

	@OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comment> comments;

	@OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Praise> praise;

	@NotNull(message = "comment.content.notnull")
	@Size(min = 3, message = "comment.content.too_little")
	@Size(max = 200, message = "comment.content.too_long") // 限制200字以内
	private String content;

	private Integer praises; // 点赞数量

	private String status; // 权限

	@CreationTimestamp // 由数据库自动创建时间
	private Timestamp ctime;

	public Comment(String userid, App app, String content) {
		this.id = KeyUtil.UUID();
		this.userid = userid;
		this.app = app;
		this.content = content;
		this.ctime = Time.timestamp();
	}

}
