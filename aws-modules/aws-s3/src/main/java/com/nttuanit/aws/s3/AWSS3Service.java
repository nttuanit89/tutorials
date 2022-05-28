package com.nttuanit.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

import java.io.File;
import java.util.List;

public class AWSS3Service {
  private final AmazonS3 s3Client;

  public AWSS3Service(AmazonS3 s3Client) {
    this.s3Client = s3Client;
  }

  public boolean doesBucketExist(String bucketName) {
    return s3Client.doesBucketExistV2(bucketName);
  }

  public Bucket createBucket(String bucketName) {
    return s3Client.createBucket(bucketName);
  }

  public List<Bucket> listBuckets() {
    return s3Client.listBuckets();
  }

  public void deleteBucket(String bucketName) {
    s3Client.deleteBucket(bucketName);
  }

  public PutObjectResult putObject(String bucketName, String key, File file) {
    return s3Client.putObject(bucketName, key, file);
  }

  public ObjectListing listObjects(String bucketName) {
    return s3Client.listObjects(bucketName);
  }

  public S3Object getObject(String bucketName, String objectKey) {
    return s3Client.getObject(bucketName, objectKey);
  }

  public CopyObjectResult copyObject(
      String sourceBucketName,
      String sourceKey,
      String destinationBucketName,
      String destinationKey) {
    return s3Client.copyObject(sourceBucketName, sourceKey, destinationBucketName, destinationKey);
  }

  public void deleteObject(String bucketName, String objectKey) {
    s3Client.deleteObject(bucketName, objectKey);
  }

  public DeleteObjectsResult deleteObjects(DeleteObjectsRequest delObjReq) {
    return s3Client.deleteObjects(delObjReq);
  }
}
