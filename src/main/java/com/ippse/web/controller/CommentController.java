package com.ippse.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ippse.web.domain.Comment;
import com.ippse.web.service.CommentService;

@Controller
public class CommentController extends BaseController {
	@Autowired
	private CommentService commentService;

	// 评论
	@PostMapping("/app/{appid}/comment")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void save(@PathVariable(name = "appid") String appid, @Valid Comment comment) {
		commentService.save(appid, sessuserid, comment);
	}

	// 回复
	@PostMapping("/app/{appid}/comment/{cid}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void reply(@PathVariable(name = "appid") String appid, @PathVariable(name = "cid") String cid,
			@Valid Comment comment) {
		commentService.replyComment(appid, cid, sessuserid, comment);
	}

	// 删除评论
	@GetMapping("/comment/{cid}/delete")
	@ResponseBody
	public void delete(@PathVariable(name = "cid") String cid) {
		commentService.delete(cid);
	}

	// 点赞
	@GetMapping("/comment/{cid}/praise")
	@ResponseBody
	public void praise(@PathVariable(name = "cid") String cid) {
		commentService.praise(cid, sessuserid);
	}
}
