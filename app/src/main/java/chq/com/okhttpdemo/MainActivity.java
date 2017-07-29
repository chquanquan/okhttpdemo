package chq.com.okhttpdemo;

import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

import chq.com.okhttpdemo.config.Constants;
import chq.com.okhttpdemo.model.User;
import chq.com.okhttpdemo.okhttp.CommonOkHttpClient;
import chq.com.okhttpdemo.config.UrlConstants;
import chq.com.okhttpdemo.okhttp.listerner.DisposeDataHandle;
import chq.com.okhttpdemo.okhttp.listerner.DisposeDataListener;
import chq.com.okhttpdemo.okhttp.request.CommonRequest;
import chq.com.okhttpdemo.okhttp.request.RequestParams;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private ImageView mImageView;
    private Button mLoginView;
    private Button mGetView;
    private Button mFileDownloadView;
    private TextView mCookieTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    //初始化所有的view
    private void initView() {
        mImageView = (ImageView) findViewById(R.id.four_view);
        mLoginView = (Button) findViewById(R.id.post_view);
        mGetView = (Button) findViewById(R.id.get_view);
        mFileDownloadView = (Button) findViewById(R.id.down_load_file);
        mCookieTextView = (TextView) findViewById(R.id.cookie_show_view);
        mLoginView.setOnClickListener(this);
        mGetView.setOnClickListener(this);
        mFileDownloadView.setOnClickListener(this);

    }


    //一般这些请求都会被放到requestCenter里面.
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_view:
                getRequest();
                break;
            case R.id.post_view:
                postRequest();
                break;
            case R.id.down_load_file:
                downloadFile();
                break;
            //上传文件测试
        }

    }

    //改了请求地址,返回一个位置数据
    private void getRequest() {
        CommonOkHttpClient.get(CommonRequest.createGetRequest("http://gc.ditu.aliyun.com/regeocoding?l=39.938133,116.395739&type=001", null), new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                mCookieTextView.setText("");
                mCookieTextView.setText(responseObj.toString());
            }

            @Override
            public void onFailure(Object reasonObj) {
                Log.d(TAG, "onFailure: get请求,网络请求失败");
            }
        }));
    }


    //我改了请求,变成查快递数据
    private void postRequest() {
        RequestParams params = new RequestParams();

        //原来的
//        params.put("mb", "19811230100");
//        params.put("pwd", "999999q");

        params.put("type", "yuantong");
        params.put("postid", "11111111111");

        CommonOkHttpClient.post(CommonRequest.createPostRequest(UrlConstants.USER_LOGIN, params),
                new DisposeDataHandle(new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        mCookieTextView.setText("");
                        mCookieTextView.setText(responseObj.toString());
                    }

                    @Override
                    public void onFailure(Object reasonObj) {
                        Log.d(TAG, "onFailure: post请求,网络请求失败");
                    }
                }, User.class));
    }

    private void downloadFile() {
        if (hasPermission(Constants.WRITE_READ_EXTERNAL_PERMISSION)) {
            doSDCardPermission();
        } else {
            requestPermission(Constants.WRITE_READ_EXTERNAL_CODE, Constants.WRITE_READ_EXTERNAL_PERMISSION);
        }
    }

    //这个接口,我没有调用.文件路径对,接口对应该是没问题的.
    private void uploadFile() throws FileNotFoundException {
        RequestParams params = new RequestParams();
        params.put("test", new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "test2.jpg"));
        CommonOkHttpClient.post(CommonRequest.createMultiPostRequest("https://api.imgur.com/3/image", params), new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.d(TAG, "onSuccess: 上传文件成功");
            }

            @Override
            public void onFailure(Object reasonObj) {
                Log.d(TAG, "onFailure: 上传文件,网络请求失败");
            }
        }));
    }

    private void doSDCardPermission() {

        Log.d(TAG, "doSDCardPermission: 准备发送下载请求");

        CommonOkHttpClient.downloadFile(CommonRequest.createGetRequest("http://upload.shunwang.com/2014/0612/1402539871763.jpg", null), new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                mImageView.setImageBitmap(BitmapFactory.decodeFile((String)responseObj));
            }

            @Override
            public void onFailure(Object reasonObj) {
                Log.d(TAG, "onFailure: 下载文件,网络请求失败");
            }
        }, Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg"));
    }

    private boolean hasPermission(String[] permissions) {
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]
            ) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestPermission(int requestCode, String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.WRITE_READ_EXTERNAL_CODE:

                Log.d(TAG, "onRequestPermissionsResult: " + grantResults.toString());

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //重启下载任务
                    doSDCardPermission();
                } else {
                    Toast.makeText(this, "需要文件访问授权才可以下载", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
