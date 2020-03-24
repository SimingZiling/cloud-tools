package org.yiming.costools.qiniu.sdk;

//import org.yiming.costools.qiniu.sdk.config.QiniuCOSConfig;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.yiming.costools.exception.COSToolsException;
import org.yiming.costools.qiniu.sdk.config.QiniuCOSConfig;

import javax.swing.plaf.synth.SynthScrollBarUI;
import java.io.InputStream;
import java.nio.file.Paths;

public class QiniuCOS {

    private QiniuCOSConfig qiniuCOSConfig;


    public QiniuCOS(QiniuCOSConfig qiniuCOSConfig) {
        this.qiniuCOSConfig = qiniuCOSConfig;
    }

    /**
     * 上传文件通过地址
     * @param filePath 文件地址
     * @param key 文件key（自己设置）
     * @return 文件key
     */
    public String uploudFilePath(String filePath,String key) throws COSToolsException {
        //构造一个带指定 Region 对象的配置类
        UploadManager uploadManager = new UploadManager(new Configuration(qiniuCOSConfig.getRegion()));

        //...生成上传凭证，然后准备上传
        Auth auth = Auth.create(qiniuCOSConfig.getAccessKey(), qiniuCOSConfig.getSecretKey());
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String upToken = auth.uploadToken(qiniuCOSConfig.getBucket());
        try {
            //默认不指定key的情况下，以文件内容的hash值作为文件名
            Response response = uploadManager.put(filePath, key, upToken);
            DefaultPutRet defaultPutRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return defaultPutRet.key;
        } catch (QiniuException e) {
            throw new COSToolsException(e.response.error);
        }
    }

    /**
     * 删除文件
     * @param key 文件key
     * @throws COSToolsException
     */
    public void deleteFile(String key) throws COSToolsException {
        if(key == null || key.equals("")){
            throw new COSToolsException("文件Key不能为空！");
        }
        Auth auth = Auth.create(qiniuCOSConfig.getAccessKey(), qiniuCOSConfig.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, new Configuration(qiniuCOSConfig.getRegion()));
        try {
            bucketManager.delete(qiniuCOSConfig.getBucket(),key);
        } catch (QiniuException e) {
            throw new COSToolsException(e.response.error);
        }
    }


    /**
     * 上传文件通过输入流
     * @param inputStream 输入流
     * @param key 健
     * @return 地址
     */
    public String uploudInputStream(InputStream inputStream, String key) throws COSToolsException {
        UploadManager uploadManager = new UploadManager(new Configuration(qiniuCOSConfig.getRegion()));

        Auth auth = Auth.create(qiniuCOSConfig.getAccessKey(), qiniuCOSConfig.getSecretKey());

        String upToken = auth.uploadToken(qiniuCOSConfig.getBucket());

        try {
            Response response = uploadManager.put(inputStream,key,upToken,null, null);

            DefaultPutRet defaultPutRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return defaultPutRet.key;
        } catch (QiniuException e) {
            throw new COSToolsException(e.response.error);
        }

    }
}
