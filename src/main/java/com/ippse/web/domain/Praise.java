package com.ippse.web.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Praise implements Serializable {
	private static final long serialVersionUID = 8405645137350798539L;

	@Id
    @Column(name = "id", unique = true, nullable = false, length = 32)
    private String id;

	@Column(name = "userid", length = 32)
    private String userid;
	
	/*@Column(name = "commentid", length = 32)
    private String commentid;*/

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "commentid")
	@NotFound(action = NotFoundAction.IGNORE)
	private Comment comment;

	@Column(nullable = false) // 值不能为空
	@CreationTimestamp // 由数据库自动创建时间
	private Timestamp ctime;

}
