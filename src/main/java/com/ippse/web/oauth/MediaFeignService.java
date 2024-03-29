package com.ippse.web.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MediaFeignService {

	@Autowired
	protected MediaFeignClient mediaFeignClient;

	public File upload(MultipartFile file) {
		return mediaFeignClient.upload(file);
	}

}
