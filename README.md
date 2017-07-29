# okhttpdemo

一个网络封装层..框架是OKHttp.对get,post,downloadFile,uploadFile有范例,其实换了网络请求框架这个层还是有用的.


在慕客网上看到一个关于OkHttp网络框架的封装视频[okhttp框架解析与应用](http://www.imooc.com/learn/732).

一开始看视频,我就想要一个demo了.后来发现评论也有很多人要demo.但是老师没有给出来.
我就按照老师的视频代码写了一遍,把运行不了代码和链接换成可用的....如果有同学看到可以改进的或者错误的地方欢迎在评论区指正.

这个是github地址: [okhttpdemo](https://github.com/chquanquan/okhttpdemo)

目录如下图:
![目录](https://github.com/chquanquan/okhttpdemo/blob/master/menu_shot.png?raw=true)

效果如下图:
![效果图](https://github.com/chquanquan/okhttpdemo/blob/master/screen_shot.png?raw=true)

####1.get方法	
```
private void getRequest() { 
CommonOkHttpClient.get(CommonRequest.createGetRequest("http://gc.ditu.aliyun.com/regeocoding?l=39.938133,116.395739&type=001", null), 
new DisposeDataHandle(new DisposeDataListener() {
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
```
####2.post方法

```
private void postRequest() {
RequestParams params = new RequestParams();
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
```

####3.downloadFile方法

```
private void doSDCardPermission() {

Log.d(TAG, "doSDCardPermission: 准备发送下载请求");

CommonOkHttpClient.downloadFile(CommonRequest.createGetRequest("http://upload.shunwang.com/2014/0612/1402539871763.jpg", null), 
new DisposeDataHandle(new DisposeDataListener() {
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
```

最后别忘了: 
申请权限:

```
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```

如果觉得这份代码对你有帮助的话, 我也算没白费工夫了.

欢迎给个start哦....^_^
