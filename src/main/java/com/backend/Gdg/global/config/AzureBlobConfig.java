package com.backend.Gdg.global.config;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AzureBlobConfig {

    /** application.yml 에 정의한 값이 주입됩니다 */
    @Value("${azure.storage.account-name}")
    private String accountName;

    @Value("${azure.storage.account-key}")
    private String accountKey;

    @Value("${azure.storage.endpoint}")
    private String endpoint;

    @Value("${azure.storage.container-name}")
    private String containerName;

    /**
     * BlobServiceClient 를 빈으로 등록합니다.
     */
    @Bean
    public BlobServiceClient blobServiceClient() {
        String conn = String.format(
                "DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows.net",
                accountName, accountKey
        );
        return new BlobServiceClientBuilder()
                .connectionString(conn)
                .buildClient();
    }

    /**
     * BlobContainerClient 를 빈으로 등록합니다.
     */
    @Bean
    public BlobContainerClient blobContainerClient(BlobServiceClient svc) {
        // 컨테이너가 없으면 자동 생성하도록 되어 있으면 좋습니다만,
        // 미리 Portal 에서 컨테이너를 만들어 두었다면 단순 getClient 로 OK
        BlobContainerClient c = svc.getBlobContainerClient(containerName);
        if (!c.exists()) {
            c.create();
        }
        return c;
    }
}