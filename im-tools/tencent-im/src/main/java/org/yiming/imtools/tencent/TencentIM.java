package org.yiming.imtools.tencent;

import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.yiming.imtools.exception.IMToolsException;
import org.yiming.imtools.tencent.comfig.TencentIMConfig;
import org.yiming.imtools.tencent.model.IMResponse;
import org.yiming.imtools.tencent.model.MsgRequestBody;
import org.yiming.localtools.basicinfo.Charact;
import org.yiming.localtools.random.RandomValue;
import org.yiming.networktools.exception.NetworkToolsException;
import org.yiming.networktools.request.NetworkRequest;
import org.yiming.networktools.request.RequestMethod;
import org.yiming.networktools.request.SSLProtocol;
//import pers.mytools.cloud.im.exception.IMToolsException;
//import pers.mytools.cloud.im.tencent.config.TencentIMConfig;
//import pers.mytools.cloud.im.tencent.model.IMResponse;
//import pers.mytools.cloud.im.tencent.model.MsgRequestBody;
//import pers.mytools.local.basicinfo.Charact;
//import pers.mytools.local.random.RandomValue;
//import pers.mytools.network.request.NetworkRequest;
//import pers.mytools.network.request.RequestMethod;
//import pers.mytools.network.request.SSLProtocol;
//import pers.mytools.network.util.exception.NetworkToolsException;

import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 腾讯即时通讯
 */
public class TencentIM {

    private TencentIMConfig tencentIMConfig;

    private TLSSigAPIv2 tlsSigAPIv2;

    public TencentIM(TencentIMConfig tencentIMConfig){
        this.tencentIMConfig = tencentIMConfig;
        this.tlsSigAPIv2 = new TLSSigAPIv2(tencentIMConfig);
    }

    public void openLoginAccountImport(String identifier,String nick,String faceUrl) throws MalformedURLException, NetworkToolsException, IMToolsException, InvalidKeyException, NoSuchAlgorithmException {

//        TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(tencentIMConfig);

        JSONObject body = new JSONObject();
        body.put("Identifier",identifier);
        if(nick != null && !nick.equals("")) {
            body.put("Nick", nick);
        }
        if(faceUrl != null && !faceUrl.equals("")) {
            body.put("FaceUrl", faceUrl);
        }
        String sig = tlsSigAPIv2.getUserSig("administrator",null);
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Content-Type","application/json");
        Map<String,Object> param = new HashMap<>();
        param.put("sdkappid",tencentIMConfig.getSdkAppId());
        param.put("identifier","administrator");
        param.put("usersig",sig);
        param.put("random", RandomValue.randomLong(32));
        param.put("contenttype","json");
        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addRequestUrl("https://console.tim.qq.com/v4/im_open_login_svc/account_import?sdkappid="+tencentIMConfig.getSdkAppId()+"&identifier=administrator&usersig="+sig+"&random="+RandomValue.randomLong(32)+"&contenttype=json")
//                .addRequestParam(param)
//                .addRequestUrl("https://console.tim.qq.com/v4/im_open_login_svc/account_import")
                .addHeaderMap(headerMap)
                .addRequestMethod(RequestMethod.POST)
                .addSslProtocol(SSLProtocol.SSL)
                .addCharact(Charact.UTF8)
                .addRequestBody(body.toJSONString())
                .build();
        JSONObject request = JSONObject.parseObject(networkRequest.sendRequest());
        if(request.getInteger("ErrorCode") != 0){
            throw new IMToolsException("错误代码："+request.getString("ErrorCode")+" 错误信息："+request.getString("ErrorInfo"));
        }
        System.out.println(request);
    }

    public static void openLoginMultiaccountImport() throws IMToolsException, NetworkToolsException, MalformedURLException, InvalidKeyException, NoSuchAlgorithmException {
        TencentIMConfig tencentIMConfig = new TencentIMConfig.Builder()
                .addKey("40e89221c0c1efee5501d792eba98fe83fc3ccc04983f976d4afe0c9afd98730")
//                .addSdkAppId("1400292489")
                .addSdkAppId(1400292489L)
                .build();
        TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(tencentIMConfig);

        JSONObject body = new JSONObject();
        List<String> list = new ArrayList<>();
        list.add("31");
        list.add("32");
        list.add("33");
        list.add("34");
        list.add("35");
        list.add("36");
        body.put("Accounts",list);
//        body.put("Identifier",identifier);
//        if(nick != null && nick.equals("")) {
//            body.put("Nick", nick);
//        }
//        if(faceUrl != null && faceUrl.equals("")) {
//            body.put("FaceUrl", faceUrl);
//        }
        String sig = tlsSigAPIv2.getUserSig("administrator",null);
        Map<String,String> map = new HashMap<>();
        map.put("Content-Type","application/json");
//        Map<String,Object> param = new HashMap<>();
//        param.put("sdkappid",tencentIMConfig.getSdkAppId());
//        param.put("identifier","administrator");
//        param.put("usersig",sig);
//        param.put("random",RandomValue.randomLong(32));
//        param.put("contenttype","json");
        NetworkRequest networkRequest = new NetworkRequest.Builder()
//                .addRequestUrl("https://console.tim.qq.com/v4/im_open_login_svc/account_import?sdkappid="+tencentIMConfig.getSdkAppId()+"&identifier=administrator&usersig="+sig+"&random="+RandomValue.randomLong(32)+"&contenttype=json")
                .addRequestUrl("https://console.tim.qq.com/v4/im_open_login_svc/multiaccount_import?sdkappid="+tencentIMConfig.getSdkAppId()+"&identifier=administrator&usersig="+sig+"&random="+RandomValue.randomLong(32)+"&contenttype=json")
//                .addRequestParam(param)
//                .addRequestUrl("https://console.tim.qq.com/v4/im_open_login_svc/account_import")
                .addHeaderMap(map)
                .addRequestMethod(RequestMethod.POST)
                .addSslProtocol(SSLProtocol.SSL)
                .addCharact(Charact.UTF8)
                .addRequestBody(body.toJSONString())
                .build();
        JSONObject request = JSONObject.parseObject(networkRequest.sendRequest());
        if(request.getInteger("ErrorCode") != 0){
            throw new IMToolsException("错误代码："+request.getString("ErrorCode")+" 错误信息："+request.getString("ErrorInfo"));
        }
        System.out.println(request);
    }


    /**
     * 单发单聊消息
     * @param adminIdentifier 管理员
     */
    public void openImSendMsg(String adminIdentifier, MsgRequestBody msgRequestBody) throws IMToolsException, NoSuchAlgorithmException, InvalidKeyException, MalformedURLException, NetworkToolsException {

//        TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(tencentIMConfig);
        String sig = tlsSigAPIv2.getUserSig(adminIdentifier,null);

        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Content-Type","application/json");

        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addRequestUrl("https://console.tim.qq.com/v4/openim/sendmsg?sdkappid="+tencentIMConfig.getSdkAppId()+"&identifier="+adminIdentifier+"&usersig="+sig+"&random="+RandomValue.randomLong(32)+"&contenttype=json")
                .addHeaderMap(headerMap)
                .addRequestMethod(RequestMethod.POST)
                .addSslProtocol(SSLProtocol.SSL)
                .addCharact(Charact.UTF8)
                .addRequestBody(JSONObject.toJSONString(msgRequestBody))
                .build();

        System.out.println(JSONObject.toJSONString(msgRequestBody));
        JSONObject request = JSONObject.parseObject(networkRequest.sendRequest());
        if(request.getInteger("ErrorCode") != 0){
            throw new IMToolsException("错误代码："+request.getString("ErrorCode")+" 错误信息："+request.getString("ErrorInfo"));
        }
        System.out.println(request);
    }

    /**
     * 判断用户是否在线
     * @param adminIdentifier 管理元用户
     * @param toAccountList 请求用户列表
     * @return IM返回信息
     */
    public IMResponse openImQueryState(String adminIdentifier, List<String> toAccountList) throws IMToolsException, NoSuchAlgorithmException, InvalidKeyException, MalformedURLException, NetworkToolsException {
        TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(tencentIMConfig);
        String sig = tlsSigAPIv2.getUserSig(adminIdentifier,null);

        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Content-Type","application/json");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("To_Account",toAccountList);


        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addRequestUrl("https://console.tim.qq.com/v4/openim/querystate?sdkappid="+tencentIMConfig.getSdkAppId()+"&identifier="+adminIdentifier+"&usersig="+sig+"&random="+RandomValue.randomLong(32)+"&contenttype=json")
                .addHeaderMap(headerMap)
                .addRequestMethod(RequestMethod.POST)
                .addSslProtocol(SSLProtocol.SSL)
                .addCharact(Charact.UTF8)
                .addRequestBody(jsonObject.toJSONString())
                .build();

        JSONObject request = JSONObject.parseObject(networkRequest.sendRequest());
        System.out.println(request);
        return JSONObject.toJavaObject(request,IMResponse.class);
    }

    /**
     * 群法消息
     * @param adminIdentifier
     * @param msgRequestBody
     * @return
     */
    public IMResponse openImBatchSendMsg(String adminIdentifier,MsgRequestBody msgRequestBody) throws IMToolsException, NoSuchAlgorithmException, InvalidKeyException, NetworkToolsException, MalformedURLException {
        String sig = tlsSigAPIv2.getUserSig(adminIdentifier,null);

        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Content-Type","application/json");
        JSONObject msgRequestBodyJson = (JSONObject) JSON.toJSON(msgRequestBody);
        msgRequestBodyJson.remove("To_Account_List");
        msgRequestBodyJson.put("To_Account",msgRequestBody.getToAccountList());
        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addRequestUrl("https://console.tim.qq.com/v4/openim/batchsendmsg?sdkappid="+tencentIMConfig.getSdkAppId()+"&identifier="+adminIdentifier+"&usersig="+sig+"&random="+RandomValue.randomLong(32)+"&contenttype=json")
                .addHeaderMap(headerMap)
                .addRequestMethod(RequestMethod.POST)
                .addSslProtocol(SSLProtocol.SSL)
                .addCharact(Charact.UTF8)
                .addRequestBody(msgRequestBodyJson.toJSONString())
                .build();

        JSONObject request = JSONObject.parseObject(networkRequest.sendRequest());
        return JSONObject.toJavaObject(request,IMResponse.class);
    }
}
