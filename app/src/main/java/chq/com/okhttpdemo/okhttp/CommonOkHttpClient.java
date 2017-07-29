package chq.com.okhttpdemo.okhttp;

/**
 * Created by quan on 2017/7/28.
 */

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import chq.com.okhttpdemo.okhttp.https.HttpsUtils;
import chq.com.okhttpdemo.okhttp.listerner.DisposeDataHandle;
import chq.com.okhttpdemo.okhttp.response.CommonCallback;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * github地址: https://github.com/chquanquan/okhttpdemo
 * 如果有疑问或建议,欢迎issues
 */

/**
 * 用来发送get,post请求的工具类, 包括设置一些请求的共用参数.
 */

public class CommonOkHttpClient {

    private static final int TIME_OUT = 30;
    private static OkHttpClient mOkHttpClient;

    static {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });

        okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.followRedirects(true);
//        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
//        okHttpBuilder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);//有一定风险的,看自己情况再配置相关参数.其实不执行的话,okhttp会调用默认值.
        mOkHttpClient = okHttpBuilder.build();
    }

    public static void post(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonCallback(handle));
    }

    public static void get(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonCallback(handle));
    }

    public static void downloadFile(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonCallback(handle));
    }




}
