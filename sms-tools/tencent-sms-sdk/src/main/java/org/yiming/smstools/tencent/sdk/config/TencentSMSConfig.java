package org.yiming.smstools.tencent.sdk.config;


import org.yiming.smstools.exception.SMSToolsException;

/**
 * @author ：紫灵
 * @date ：Created in 2020/1/2 上午9:38
 * @modified ：腾讯短信配置
 */
public class TencentSMSConfig {

    private TencentSMSConfig() {
    }

    private TencentSMSConfig(TencentSMSConfig tencentSMSConfig) {
        this.appId = tencentSMSConfig.appId;
        this.appKey = tencentSMSConfig.appKey;
        this.sign = tencentSMSConfig.sign;
    }

    private Integer appId;// 短信应用SDK AppID 1400开头
    private String appKey ;// 短信应用SDK AppKey
    private String sign ;// 签名

    public Integer getAppId() {
        return appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getSign() {
        return sign;
    }

    public static class Builder{
        private TencentSMSConfig tencentSMSConfig;
        public Builder() {
            tencentSMSConfig = new TencentSMSConfig();
        }

        public Builder addAppId(Integer appId){
            tencentSMSConfig.appId = appId;
            return this;
        }

        public Builder addAppKey(String appKey){
            tencentSMSConfig.appKey = appKey;
            return this;
        }

        public Builder addSign(String sign){
            tencentSMSConfig.sign = sign;
            return this;
        }

        /**
         * 构建腾讯云短信配置
         * @return 腾讯短信配置
         * @throws SMSToolsException
         */
        public TencentSMSConfig build() throws SMSToolsException {
            if(null == tencentSMSConfig.appId){
                throw new SMSToolsException("APPID不能为空！");
            }
            if(tencentSMSConfig.appKey == null){
                throw new SMSToolsException("APPKEY不能为空！");
            }
            return new TencentSMSConfig(tencentSMSConfig);
        }

    }

}
