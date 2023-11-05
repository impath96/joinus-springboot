package com.joinus.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

	public static String uploadFile(String uploadPath, MultipartFile file) throws IOException {
		
		String savedFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		File target = new File(uploadPath, savedFileName);
		FileCopyUtils.copy(file.getBytes(), target);
		
		return savedFileName;
	}

//  현재 내가 업로드해야할 경로 반환
//	public static String getCurrentUploadPath(String uploadRootPath) {
//		
//		return "";
//	}
//
//	private static String makeDir(String uploadRootPath, String... paths) {
//		
//		return "";
//		
//	}
	
}
