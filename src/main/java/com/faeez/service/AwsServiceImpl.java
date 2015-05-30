package com.faeez.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.faeez.controller.TangoController;

@Service
public class AwsServiceImpl implements AwsService {
	private static final Logger logger = LoggerFactory.getLogger(TangoController.class);
	private static String bucketName = "mbuds";
	@Autowired
	private AmazonS3 s3Client;

	@Autowired
	private TransferManager transferManager;
	
	@Autowired
	private MemberService memberService;


	@Override
	public void listObects(String bucketName) {
		ObjectListing listObjects = s3Client.listObjects(bucketName);
		List<S3ObjectSummary> objectSummaries = listObjects.getObjectSummaries();
		for (S3ObjectSummary s3ObjectSummary : objectSummaries) {
			logger.debug("File name = " + s3ObjectSummary.getKey());
		}
		logger.debug(" ----------Size = " + objectSummaries.size());
	}
	
	@Override
	public void makeImageMain(Long profile_id,String keyName) {
		
        try {
   
            CopyObjectRequest copyFirstImageToTemp = new CopyObjectRequest(bucketName, profile_id.toString()+"_1.jpg", bucketName, profile_id.toString()+"_temp.jpg");
            s3Client.copyObject(copyFirstImageToTemp);
            
            CopyObjectRequest copyTargetToFirstImage = new CopyObjectRequest(bucketName, keyName, bucketName, profile_id.toString()+"_1.jpg");
            s3Client.copyObject(copyTargetToFirstImage);
            
            CopyObjectRequest copyTempToTargetImage = new CopyObjectRequest(bucketName, profile_id.toString()+"_temp.jpg", bucketName, keyName);
            s3Client.copyObject(copyTempToTargetImage);
            
            
         } catch (AmazonServiceException ase) {
            logger.error("Caught an AmazonServiceException, " +
            		"which means your request made it " +
                    "to Amazon S3, but was rejected with an error response " +
                    "for some reason.");
            logger.error("Error Message:    " + ase.getMessage());
            logger.error("HTTP Status Code: " + ase.getStatusCode());
            logger.error("AWS Error Code:   " + ase.getErrorCode());
            logger.error("Error Type:       " + ase.getErrorType());
            logger.error("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.error("Caught an AmazonClientException, " +
            		"which means the client encountered " +
                    "an internal error while trying to communicate" +
                    " with S3, " +
                    "such as not being able to access the network.");
            logger.error("Error Message: " + ace.getMessage());
        }
    }
		
	
	
	@Override
	public void deleteObject(String keyName,String replacement) throws IOException {
		try {
			if(keyName.equalsIgnoreCase(replacement)) { // the last image itself is being deleted.
				s3Client.deleteObject(new DeleteObjectRequest(bucketName, keyName));
			} else {
				   CopyObjectRequest replaceTargetWithLastImage = new CopyObjectRequest(bucketName,replacement, bucketName, keyName);
		            s3Client.copyObject(replaceTargetWithLastImage);
		            
		            s3Client.deleteObject(new DeleteObjectRequest(bucketName, replacement));
			}
		} catch (AmazonServiceException ase) {
			logger.error("Caught an AmazonServiceException.");
			logger.error("Error Message:    " + ase.getMessage());
			logger.error("HTTP Status Code: " + ase.getStatusCode());
			logger.error("AWS Error Code:   " + ase.getErrorCode());
			logger.error("Error Type:       " + ase.getErrorType());
			logger.error("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			logger.error("Caught an AmazonClientException.");
			logger.error("Error Message: " + ace.getMessage());
		}
	}


	@Override
	public String upload(MultipartFile file, Long profile_id,String keyName) {

		/**
		 * Future updates. Allow multiple files upload at same time. Small issue
		 * on server side. After updating the no_of_pics in database, the
		 * subsequent read of the count is not picking the latest count. The
		 * write is taking time, because of which the subsequent read is not
		 * correct. Other approach would be to have filename prefixed by profile
		 * id, but then the logic for displaying random pics wld have to change.
		 * Right now its easy. "ProfileId_1.jpg". You would have to get file
		 * names from bucket which cld be anything eg "profileId-xyh.jpg" or
		 * "profileId-abc.jpg" and then modify display logic accordingly.
		 */
		if (!file.isEmpty()) {
			try {

				File convFile = new File(file.getOriginalFilename());
				file.transferTo(convFile);

				Upload upload = transferManager.upload(bucketName, keyName, convFile);
				logger.debug("Uploading " + keyName);

				try {
					// Or you can block and wait for the upload to finish
					upload.waitForCompletion();
					logger.debug("Upload complete " + keyName);
				} catch (AmazonClientException amazonClientException) {
					logger.error("Unable to upload file, upload was aborted." + keyName);
					amazonClientException.printStackTrace();
				}

				return keyName;
			} catch (Exception e) {
				return "You failed to upload " + keyName + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + keyName + " because the file was empty.";
		}

	}

}
