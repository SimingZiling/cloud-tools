package org.yiming.imtools.tencent.comfig;

import org.yiming.imtools.exception.IMToolsException;

/**
 * 腾讯即时通讯配置
 */
public class TencentIMConfig {

    public TencentIMConfig() {
    }

    public TencentIMConfig(TencentIMConfig tencentIMConfig) {
        this.sdkAppId = tencentIMConfig.sdkAppId;
        this.key = tencentIMConfig.key;
        this.expire = tencentIMConfig.expire;
    }

    private Long sdkAppId;// 应用时控制台分配的 SDKAppID
    private String key;// 密钥
    private Long expire;// 过期时长 单位秒

    public Long getSdkAppId() {
        return sdkAppId;
    }

    public String getKey() {
        return key;
    }

    public Long getExpire() {
        return expire;
    }

    public static class Builder{
        private TencentIMConfig tencentIMConfig;
        public Builder(){
            tencentIMConfig = new TencentIMConfig();
        }
        public Builder addSdkAppId(Long sdkAppId){
            tencentIMConfig.sdkAppId = sdkAppId;
            return this;
        }
        public Builder addKey(String key){
            tencentIMConfig.key = key;
            return this;
        }
        public Builder addExpire(Long expire){
            tencentIMConfig.expire = expire;
            return this;
        }
        public TencentIMConfig build() throws IMToolsException {
            if(tencentIMConfig.sdkAppId == null || tencentIMConfig.sdkAppId.equals(0L)){
                throw new IMToolsException("SdkAppId不存在！");
            }
            if(tencentIMConfig.key == null || tencentIMConfig.key.equals("")){
                throw new IMToolsException("key不存在！");
            }
            if(tencentIMConfig.expire == null || tencentIMConfig.expire.equals(0L)){
                throw new IMToolsException("过期时长不存在！");
            }
            return new TencentIMConfig(tencentIMConfig);
        }
    }
}
