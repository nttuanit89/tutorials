package com.nttuanit.aws.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.google.common.io.Resources;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class S3Application {
  private static final AWSCredentials credentials;

  static {
    // put your accesskey and secretkey here
    credentials = new BasicAWSCredentials("<AWS accesskey>", "<AWS secretkey>");
  }

  public static void main(String[] args) throws IOException {
    // set-up the client
    AmazonS3 s3Client =
        AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.AP_SOUTHEAST_1)
            .build();
    AWSS3Service awsService = new AWSS3Service(s3Client);

    String bucketName = "nttuan-bucket";

    createBucket(awsService, "nttuan-bucket");
    listAllBuckets(awsService);
    deleteBucket(awsService, "nttuan-demo-bucket-test2");
    listAllBuckets(awsService);
    uploadObject(awsService, bucketName, "documents/welcome.html");
    listObjects(awsService, bucketName);
    downloadObject(awsService, bucketName, "documents/hello.txt");
  }

  private static void createBucket(AWSS3Service awss3Service, String bucketName) {
    if (awss3Service.doesBucketExist(bucketName)) {
      System.out.println(
          "Bucket name is not available." + " Try again with a different Bucket name.");
      return;
    }
    awss3Service.createBucket(bucketName);
  }

  private static void listAllBuckets(AWSS3Service awss3Service) {
    awss3Service.listBuckets().stream().map(Bucket::getName).forEach(System.out::println);
  }

  private static void deleteBucket(AWSS3Service awss3Service, String bucketName) {
    awss3Service.deleteBucket(bucketName);
  }

  private static void uploadObject(AWSS3Service awss3Service, String bucketName, String url) {
    String file = Resources.getResource(url).getFile();
    awss3Service.putObject(bucketName, url, new File(file));
  }

  private static void listObjects(AWSS3Service awss3Service, String bucketName) {
    awss3Service.listObjects(bucketName).getObjectSummaries().stream()
        .map(S3ObjectSummary::getKey)
        .forEach(System.out::println);
  }

  private static void downloadObject(AWSS3Service awss3Service, String bucketName, String file)
      throws IOException {
    S3Object s3Object = awss3Service.getObject(bucketName, file);
    S3ObjectInputStream inputStream = s3Object.getObjectContent();
    FileUtils.copyInputStreamToFile(inputStream, new File("/home/user/Documents/hello.txt"));
  }
}
