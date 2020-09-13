package cn.itcast.test;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.util.UUID;

public class QiniuTest {

    public static void main(String[] args) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());  //代表区域 ,这里需要修改为2 代表华南区
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "3Gp628H7kJH_NtQwaio-xGgMwSNEoKxd1tMr9G2a";  //用户名秘钥
        String secretKey = "M_ZOTpb1qvw-cOy7ms0NCJXxxMabYYSCqcxEBWHT";  //密码
        String bucket = "138proejct";  //空间名称
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "H:/美女/1.jpg";  //上传图片的路径
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = UUID.randomUUID().toString()+"_1.jpg"; // 文件存储在服务器的名字
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            System.out.println("图片访问的路径：http://qg7x9c9pu.hn-bkt.clouddn.com/"+key);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }
}
