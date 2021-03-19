package com.spring.w3m.upload.user;

import java.io.File;
import java.io.InputStream;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class AwsS3 {
	// amazon-s3-sdk
	private AmazonS3 s3Client;
	final private String accessKey = "AKIART7SB7DNWUHH3SNH";
	final private String secretKey = "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";
	private Regions clientRegion = Regions.AP_NORTHEAST_2;
	private String bucket = "imageup";
	
	private AwsS3() {
		createS3Client();
	}
	
	// singleton pattern
	static private AwsS3 instance = null;
	
	public static AwsS3 getInstance() {
		if(instance==null) {
			return new AwsS3();
		} else {
			return instance;
		}
	}
	
	// aws S3 client 생성
	private void createS3Client() {
		
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		this.s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(clientRegion).build();
	}
	
	public void upload(File file, String key) {
		uploadToS3(new PutObjectRequest(this.bucket, key, file));
	}
	
	public void upload(InputStream is, String key, String contentType, long contentLength) {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(contentType);
		objectMetadata.setContentLength(contentLength);
		
		uploadToS3(new PutObjectRequest(this.bucket, key, is, objectMetadata));
	}
	
	// PutObjectRequest는 Aws S3 버킷에 업로드 할 객체 메타 데이터와 파일 데이터로 이루어져있음
	private void uploadToS3(PutObjectRequest putObjectRequest) {
		try {
			this.s3Client.putObject(putObjectRequest);
			System.out.println(String.format("[%s] upload complete", putObjectRequest.getKey()));
		} catch(AmazonServiceException e) {
			e.printStackTrace();
		} catch(SdkClientException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}