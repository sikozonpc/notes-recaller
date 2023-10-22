package com.tiagotaquelim.kindlerecaller.gcp;

import com.google.cloud.spring.storage.GoogleStorageResource;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

@Service
public class GcpFileStorage {
    @Value("${gcp.bucket.name}")
    private String bucketName;
    private final StorageOptions storage;

    public GcpFileStorage() {
        storage = StorageOptions.getDefaultInstance();
    }

    public ByteArrayResource downloadFile(String fileName) {
        Blob blob = storage.getService().get(bucketName, fileName);

        return new ByteArrayResource(
                blob.getContent());
    }

    public GoogleStorageResource fetchResource(String filename) {
        return new GoogleStorageResource(
                storage.getService(),
                String.format("gs://%s/%s", this.bucketName, filename)
        );
    }

    public String readGcsFile(String filename) throws IOException {
        return StreamUtils.copyToString(
                fetchResource(filename).getInputStream(),
                Charset.defaultCharset())
                + "\n";
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public StorageOptions getStorage() {
        return storage;
    }
}
