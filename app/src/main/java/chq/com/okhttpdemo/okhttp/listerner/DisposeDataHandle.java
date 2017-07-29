package chq.com.okhttpdemo.okhttp.listerner;

/**
 * Created by quan on 2017/7/28.
 */

public class DisposeDataHandle {

    public DisposeDataListener mListener = null;
    public Class<?> mClass = null;
    public String mFilePath;

    public DisposeDataHandle(DisposeDataListener listener) {
        mListener = listener;
    }

    public DisposeDataHandle(DisposeDataListener listener, Class<?> clazz) {
        mListener = listener;
        mClass = clazz;
    }

    public DisposeDataHandle(DisposeDataListener listener, String filePath) {
        mListener = listener;
        mFilePath = filePath;
    }


}
