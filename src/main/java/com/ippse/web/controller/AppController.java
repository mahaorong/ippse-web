package com.ippse.web.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ippse.web.dao.AppRepository;
import com.ippse.web.domain.App;
import com.ippse.web.service.AppService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/u")
public class AppController extends BaseController {
	@Autowired
	private AppRepository appRepository;
	@Autowired
	private AppService appService;

	@GetMapping("/apps")
	public String apps(@PageableDefault(page = 0, size = 4) Pageable pageable, Model model) {
		model.addAttribute("apps", appRepository.findByUserid(sessuserid, pageable));
		return "user_apps";
	}

	@GetMapping("/app/input")
	public String input(Model model) {
		return "user_app_input";
	}

	@PostMapping("/app")
	@ResponseBody
	public void create(@RequestParam(name = "head", required = false) MultipartFile logo,
			@RequestParam(name = "images", required = false) MultipartFile[] images, @Valid App app) {
		try {
			appService.save(logo, images, app, sessuserid);
		} catch (Exception e) {
			log.info("erroe={}", e);
		}
	}

	@GetMapping("/app/{id}/delete")
	@ResponseBody
	public void delete(@PathVariable(name = "id") String id) {
		appRepository.findById(id).orElseThrow(AppNotFoundException::new);
		try {
			appService.delete(id);
		} catch (IOException e) {
			log.info("erroe={}", e);
		}
	}

	@GetMapping("/app/{id}/edit")
	public String edit(@PathVariable(name = "id") String id, Model model) {
		App app = appRepository.findById(id).get();
		model.addAttribute("app", app);
		return "user_app_edit";
	}

	@PostMapping("/app/{id}")
	@ResponseBody
	public void update(@PathVariable(name = "id") String id,
			@RequestParam(name = "logo", required = false) MultipartFile logo,
			@RequestParam(name = "images", required = false) MultipartFile[] images, App app) {
		/*
		 * if (app.getId() != aid) { throw new AppNotFoundException(); }
		 * appRepository.findById(aid).orElseThrow(AppNotFoundException::new);
		 */
		appService.update(id, logo, app, images);
	}

}
