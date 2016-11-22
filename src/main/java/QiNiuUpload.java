
/**
 * Created by Jax on 2016/11/20.
 */
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class QiNiuUpload {

    // 密钥配置
    Auth          auth;
    Zone          z;
    Configuration c;

    // 创建上传对象
    UploadManager uploadManager;

    // 设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY;
    String SECRET_KEY;

    // 要上传的空间
    String bucketname;

    QiNiuUpload(String ACCESS_KEY, String SECRET_KEY, String bucketname) {
        this.ACCESS_KEY = ACCESS_KEY;
        this.SECRET_KEY = SECRET_KEY;
        this.bucketname = bucketname;
        System.out.println(this.ACCESS_KEY);
        System.out.println(this.SECRET_KEY);
        System.out.println(this.bucketname);
        this.auth     = Auth.create(this.ACCESS_KEY, this.SECRET_KEY);
        z             = Zone.autoZone();
        c             = new Configuration(z);
        uploadManager = new UploadManager(c);
    }

    public void upload(String key, String FilePath, String wjj) {
        try {
            System.out.println("fileName:" + key + ";path:" + FilePath);

            // 调用put方法上传
            Response res = uploadManager.put(FilePath, wjj + "/" + key, getUpToken());

            // 打印返回的信息
            System.out.println(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;

            // 请求失败时打印的异常的信息
            System.out.println(r.toString());

            try {

                // 响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {

                // ignore
            }
        } catch (Exception e) {}
    }

    // 简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken() {
        return auth.uploadToken(this.bucketname);
    }
}