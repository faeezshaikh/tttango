package com.faeez.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface AwsService {
	public String upload(MultipartFile file, Long profile_id, String keyName);

	public void deleteObject(String keyName,String replacement) throws IOException;

	public void makeImageMain(Long profile_id, String fileName);
	
	public void listObects(String bucketName);
}
