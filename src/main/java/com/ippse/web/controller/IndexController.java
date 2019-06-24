package com.ippse.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ippse.web.dao.AppRepository;
import com.ippse.web.dao.CommentRepository;
import com.ippse.web.domain.App;
import com.ippse.web.domain.Comment;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexController extends BaseController {
	@Autowired
	private AppRepository appRepository;
	@Autowired
	private CommentRepository commentRepository;

	@GetMapping("/")
	public String findAll(@PageableDefault(page = 0, size = 6) Pageable pageable, Model model) {
		log.info("第{}页", pageable.getPageNumber());
		log.info("每页{}条数据", pageable.getPageSize());
		model.addAttribute("apps", appRepository.findAll(pageable));
		return "index";
	}

	@GetMapping("/apps")
	public String list(@PageableDefault(page = 0, size = 6) Pageable pageable, Model model) {
		log.info("第{}页", pageable.getPageNumber());
		log.info("每页{}条数据", pageable.getPageSize());
		model.addAttribute("apps", appRepository.findAll(pageable));
		return "apps";
	}

	@GetMapping("/app/{id}")
	public String details(@PageableDefault(page = 0, size = 4) Pageable pageable, @PathVariable(name = "id") String id,
			Model model) {
		Page<Comment> comments = commentRepository.findByAppId(id, pageable);
		model.addAttribute("comments", comments);
		App app = appRepository.findById(id).get();
		model.addAttribute("app", app);
		return "app";
	}

}
