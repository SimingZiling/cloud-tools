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

    public static void main(String[] args) throws IOException, SMSToolsException, HTTPException {
//        TencentSMSConfig smsConfig = new TencentSMSConfig.Builder().addAppId(1400115727).addAppKey("7563d25bef6579609a14cf02e67c7393").addSign("管培生").build();
        TencentSMSConfig smsConfig = new TencentSMSConfig.Builder().addAppId(1400338085).addAppKey("20ae36572e2668f7a65722b44f5a22a9").addSign("众医美咖").build();
//        TencentSMSConfig smsConfig = new TencentSMSConfig.Builder().addAppId(1400199525).addAppKey("8ac7ae555a38575b4b4f09410fc942e6").addSign("马上就搬").build();

//        new TencentSMS(smsConfig).sendMess("尊敬的客户，您的验证码为:154865。您于"+new SimpleDateFormat("yyyy年MM月dd日 hh时mm:分ss秒").format(new Date())+",发起短信随机码验证。请注意查收保管。","13438990245");
//        new TencentSMS(smsConfig).sendMess("尊敬的客户，您的验证码为:{1}。您于{2},发起短信随机码验证。请注意查收保管。","13438990245");
        new TencentSMS(smsConfig).sendMess("【众医美咖】您的验证码为：123456，请在5分钟内输入验证码，逾期后您需要重新获取一个验证码--美咖拼","13438990245");
    }

}
