package org.yiming.imtools.tencent;

import com.alibaba.fastjson.JSONObject;
import org.yiming.imtools.exception.IMToolsException;
import org.yiming.imtools.tencent.comfig.TencentIMConfig;
import org.yiming.localtools.crypto.SHA;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.zip.Deflater;


public class TLSSigAPIv2 {

    private TencentIMConfig tencentIMConfig;

    public TLSSigAPIv2(TencentIMConfig tencentIMConfig) {
        this.tencentIMConfig = tencentIMConfig;
    }


    public String getUserSig(String identifier,byte[] userbuf) throws NoSuchAlgorithmException, InvalidKeyException, IMToolsException {
        // 添加签名信息
        JSONObject sigDoc = new JSONObject();
        sigDoc.put("TLS.ver", "2.0");
        sigDoc.put("TLS.identifier", identifier);
        sigDoc.put("TLS.sdkappid", tencentIMConfig.getSdkAppId());
        sigDoc.put("TLS.expire", tencentIMConfig.getExpire());
        sigDoc.put("TLS.time", System.currentTimeMillis()/1000);

        //
        String base64UserBuf = null;
        if(userbuf != null && userbuf.length > 0){
            base64UserBuf = Base64.getEncoder().encodeToString(userbuf);
            sigDoc.put("TLS.userbuf", base64UserBuf);
        }

        // 生成签名
        String contentToBeSigned = "TLS.identifier:" + identifier + "\n"
                + "TLS.sdkappid:" + String.valueOf(tencentIMConfig.getSdkAppId()) + "\n"
                + "TLS.time:" + sigDoc.getString("TLS.time") + "\n"
                + "TLS.expire:" + String.valueOf(tencentIMConfig.getExpire()) + "\n";
        if (null != base64UserBuf) {
            contentToBeSigned += "TLS.userbuf:" + base64UserBuf + "\n";
        }

        //  生成Base64签名
        String sig = SHA.sha256HMAC(contentToBeSigned,tencentIMConfig.getKey(),true);
        if (sig.length() == 0){
            throw new IMToolsException("签名失败！");
        }
        sigDoc.put("TLS.sig", sig);
        Deflater compressor = new Deflater();
        compressor.setInput(sigDoc.toString().getBytes(Charset.forName("UTF-8")));
        compressor.finish();
        byte[] compressedBytes = new byte[2048];
        int compressedBytesLength = compressor.deflate(compressedBytes);
        compressor.end();
        return  Base64.getUrlEncoder().encodeToString(Arrays.copyOfRange(compressedBytes,
                0, compressedBytesLength)).replaceAll("\\s*","").replace("-","*").replaceAll("_","-").replaceAll("=","_");
    }

}

