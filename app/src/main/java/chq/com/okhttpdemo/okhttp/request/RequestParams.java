package chq.com.okhttpdemo.okhttp.request;


import java.io.File;
import java.util.HashMap;

/**
 * Created by quan on 2017/7/28.
 */

public class RequestParams {

    public HashMap<String, String> urlParams;
    public HashMap<String, Object> fileParams;

    public void put(String key, String value) {
        if (urlParams == null) {
            urlParams = new HashMap<>();
        }
        urlParams.put(key, value);
    }

    public void put(String fileName, File file) {
        if (fileParams == null) {
            fileParams = new HashMap<>();
        }
        fileParams.put(fileName, file);
    }


}
