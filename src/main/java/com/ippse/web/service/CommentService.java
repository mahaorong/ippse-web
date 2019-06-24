package com.ippse.web.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ippse.web.dao.AppRepository;
import com.ippse.web.dao.CommentRepository;
import com.ippse.web.dao.PraiseRepository;
import com.ippse.web.domain.App;
import com.ippse.web.domain.Comment;
import com.ippse.web.domain.Praise;
import com.ippse.web.utils.CommentEnum;
import com.ippse.web.utils.CommentException;
import com.ippse.web.utils.KeyUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class CommentService {
	@Autowired
	private AppRepository appRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PraiseRepository praiseRepository;

	/**
	 * 回复
	 * 
	 * @param appid
	 * @param pid
	 * @param userid
	 * @param comment
	 */
	public void replyComment(String appid, String pid, String userid, Comment comment) {
		Comment p = commentRepository.findById(pid)
				.orElseThrow(() -> new CommentException(CommentEnum.COMMENT_NOT_FOUND));
		comment.setComment(p);
		save(appid, userid, comment);
	}

	/**
	 * 评论回复
	 * 
	 * @param appid
	 * @param userid
	 * @param comment
	 */
	public void save(String appid, String userid, Comment comment) {
		App app = appRepository.findById(appid).orElseThrow(() -> new CommentException(CommentEnum.APP_NOT_FOUND));
		comment.setApp(app);
		comment.setId(KeyUtil.UUID());
		comment.setContent(comment.getContent());
		comment.setPraises(0);
		commentRepository.save(comment);
	}

	/**
	 * 删除评论
	 * 
	 * @param id
	 */
	public void delete(String id) {
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new CommentException(CommentEnum.COMMENT_NOT_FOUND));
		commentRepository.delete(comment);
	}

	/**
	 * 判断用户是否点赞，没有就保存点赞，点赞了就取消
	 * 
	 * @param id
	 * @param userid
	 */
	public void praise(String id, String userid) {
		Optional<Praise> praises = praiseRepository.findByCommentidAndUserid(id, userid);
		if (praises.isPresent()) {
			praiseRepository.delete(praises.get());
			// 减去评论点赞数量1
			Optional<Comment> comment = commentRepository.findById(id);
			int sum = comment.get().getPraises() - 1;
			comment.get().setPraises(sum);
			commentRepository.save(comment.get());
		} else {
			Praise praise = new Praise();
			Optional<Comment> comment = commentRepository.findById(id);
			// 根据userId查询点赞所属用户
			praise.setUserid(userid);
			// 设置点赞表主键
			praise.setId(KeyUtil.UUID());
			try {
				praiseRepository.save(praise);
				log.info("点赞成功");
			} catch (Exception e) {
				log.error("点赞失败:{}", e);
			}
			// 修改评论点赞数量
			comment.get().setPraises(comment.get().getPraises() + 1);
			commentRepository.save(comment.get());
		}
	}
}
