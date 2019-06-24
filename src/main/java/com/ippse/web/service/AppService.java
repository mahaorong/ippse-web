package com.ippse.web.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ippse.web.dao.AppRepository;
import com.ippse.web.domain.App;
import com.ippse.web.utils.CommentEnum;
import com.ippse.web.utils.CommentException;
import com.ippse.web.utils.FileUtil;
import com.ippse.web.utils.KeyUtil;
import com.ippse.web.utils.LangUtils;
import com.ippse.web.utils.Time;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class AppService {
	@Autowired
	private AppRepository appRepository;

	public void save(MultipartFile logo, MultipartFile[] images, App app, String userid) throws Exception {
		saveFile(app, logo, images);
		app.setId(KeyUtil.UUID());
		app.setStatus("0");
		app.setDeveloperid(KeyUtil.UUID());
		app.setUserid(userid);
		app.setCtime(Time.timestamp());
		app.setDescription(app.getDescription());
		appRepository.save(app);
	}

	public void delete(String id) throws IOException {
		Optional<App> app = appRepository.findById(id);

		// 判断是否为默认图
		if (!app.get().getIcon().substring(11).equals("1.png")) {
			// 删除本地目录图片
			new File(FileUtil.getPath() + "/upload/" + app.get().getIcon().substring(11)).delete();
		}

		// 删除本地目录的banner图片
		List<String> images = app.get().getImages();
		for (String image : images) {
			new File(FileUtil.getPath() + "/upload/" + image.substring(11)).delete();
		}

		appRepository.delete(app.get());
	}

	public void update(String id, MultipartFile head, App app, MultipartFile[] images) {
		saveFile(app, head, images);
		App editapp = appRepository.findById(id).orElseThrow(() -> new CommentException(CommentEnum.APP_NOT_FOUND));
		BeanUtils.copyProperties(app, editapp, LangUtils.getNullPropertyNames(app));
		appRepository.save(editapp);
	}

	// 保存图片
	private void saveFile(App app, MultipartFile file, MultipartFile[] image) {
		if (image.length > 0) {
			List<String> list = new ArrayList<>();
			for (MultipartFile images : image) {
				try {
					String filename = images.getOriginalFilename(); // 获取文件名：
																	// xxx.jpg
																	// 带文件后缀
					log.info("文件名称：{}", filename);
					String randomFileName = FileUtil.getRandomFileName(filename);// 带uuid的
					String imagePath = FileUtil.getPath() + "/upload/";
					log.info("imagePath=={}", imagePath);
					FileUtil.uploadFile(images.getBytes(), imagePath, randomFileName);

					list.add("/imagePath/" + randomFileName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			app.setImages(list);
		}

		try {
			if (null != file && StringUtils.isNotBlank(file.getOriginalFilename())) {
				String filename = file.getOriginalFilename(); // 获取文件名： xxx.jpg
																// 带文件后缀
				log.info("文件名称：{}", filename);
				String randomFileName = FileUtil.getRandomFileName(filename);// 带uuid的
				String imagePath = FileUtil.getPath() + "/upload/";
				log.info("imagePath=={}", imagePath);
				FileUtil.uploadFile(file.getBytes(), imagePath, randomFileName);
				app.setIcon("/imagePath/" + randomFileName);
			} else {
				app.setIcon("/imagePath/" + "1.png");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
