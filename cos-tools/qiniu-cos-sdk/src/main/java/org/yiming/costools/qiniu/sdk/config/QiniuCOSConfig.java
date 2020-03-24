package org.yiming.costools.qiniu.sdk.config;

import com.qiniu.storage.Region;
import org.yiming.costools.exception.COSToolsException;

/**
 * 七牛云对象存储配置
 */
public class QiniuCOSConfig {

    public QiniuCOSConfig(){}

    public QiniuCOSConfig(QiniuCOSConfig qiniuCOSConfig) {
        this.accessKey = qiniuCOSConfig.accessKey;
        this.secretKey = qiniuCOSConfig.secretKey;
        this.bucket = qiniuCOSConfig.bucket;
    }

    private String accessKey;
    private String secretKey;
    private String bucket;
    private Region region;

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public Region getRegion() {
        return region;
    }

    public static class Builder {
        private QiniuCOSConfig qiniuCOSConfig;
        public Builder() {
            qiniuCOSConfig = new QiniuCOSConfig();
        }

        public Builder addAccessKey(String accessKey){
            qiniuCOSConfig.accessKey = accessKey;
            return this;
        }

        public Builder addSecretKey(String secretKey){
            qiniuCOSConfig.secretKey = secretKey;
            return this;
        }

        public Builder addBucket(String bucket){
            qiniuCOSConfig.bucket = bucket;
            return this;
        }

        public Builder addZone(String zone){
            if(zone == null || zone.equals("")){
                qiniuCOSConfig.region = Region.autoRegion();
            }else if(zone.equals("华东")){
                qiniuCOSConfig.region = Region.huadong();
            }else if(zone.equals("华北")){
                qiniuCOSConfig.region = Region.huabei();
            }else if(zone.equals("华南")){
                qiniuCOSConfig.region = Region.huanan();
            }else if(zone.equals("北美")){
                qiniuCOSConfig.region = Region.beimei();
            }else if(zone.equals("东南亚")){
                qiniuCOSConfig.region = Region.xinjiapo();
            }else {
                qiniuCOSConfig.region = null;
            }
            return this;
        }

        /**
         * 构建七牛云配置
         * @return 七牛云配置对象
         * @throws COSToolsException
         */
        public QiniuCOSConfig build() throws COSToolsException {
            if(qiniuCOSConfig.accessKey == null || qiniuCOSConfig.accessKey.equals("")){
                throw new COSToolsException("accessKey不能为空！");
            }
            if(qiniuCOSConfig.secretKey == null || qiniuCOSConfig.secretKey.equals("")){
                throw new COSToolsException("secretKey不能为空！");
            }
            if(qiniuCOSConfig.bucket == null || qiniuCOSConfig.bucket.equals("")){
                throw new COSToolsException("bucket不能为空！");
            }
            if(qiniuCOSConfig.region == null){
                qiniuCOSConfig.region = Region.autoRegion();
            }
            return new QiniuCOSConfig(qiniuCOSConfig);
        }

    }

}
