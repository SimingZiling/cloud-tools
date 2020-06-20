package org.yiming.smstools.tencent.sdk;


import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.yiming.smstools.exception.SMSToolsException;
import org.yiming.smstools.tencent.sdk.config.TencentSMSConfig;

import java.io.IOException;

/**
 * @author ：紫灵
 * @date ：Created in 2020/1/2 上午9:49
 * @modified ：腾讯短信
 */
public class TencentSMS {

    private TencentSMSConfig tencentSMSConfig;
    public TencentSMS(TencentSMSConfig tencentSMSConfig) {
        this.tencentSMSConfig = tencentSMSConfig;
    }

    /**
     * 自定义短信内容发送
     * @param msg 短信内容
     * @param number 用户手机号
     * @return OK 成功  null 失败
     */
    public boolean sendMess(String msg , String number) throws SMSToolsException , HTTPException, IOException {
        SmsSingleSender ssender = new SmsSingleSender(tencentSMSConfig.getAppId(), tencentSMSConfig.getAppKey());
        SmsSingleSenderResult result = ssender.send(0, "86", number, msg, "", "");
        if(result.result != 0){
            throw new SMSToolsException(result.errMsg);
        }
        return true;
    }

}
