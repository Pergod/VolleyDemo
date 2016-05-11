package com.anttech.volleydemo;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ImageView imageview;
    private NetworkImageView netImageView;
    private String url = "https://www.baidu.com/img/bdlogo.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageview = (ImageView) findViewById(R.id.image_view);
        netImageView= (NetworkImageView) findViewById(R.id.network_image_view);

        volleyNetImageView();
//        volleyImageLoader();
//        volleyImageRequest();
//        volleyGet();
//        volleyPost();

    }


    /*
    1. 创建一个RequestQueue对象。

    2. 创建一个ImageLoader对象。

    3. 在布局文件中添加一个NetworkImageView控件。
    4. 在代码中获取该控件的实例。

    5. 设置要加载的图片地址。
     */
    public void volleyNetImageView() {
        ImageLoader imageLoader = new ImageLoader(MyApplication.getHttpQueue(), new BitmapCache());
        netImageView.setImageUrl(url,imageLoader);
        netImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        netImageView.setErrorImageResId(R.mipmap.ic_launcher);

    }

    /*

        1. 创建一个RequestQueue对象。

        2. 创建一个ImageLoader对象。

        3. 获取一个ImageListener对象。

        4. 调用ImageLoader的get()方法加载网络上的图片。
     */
    public void volleyImageLoader() {

        ImageLoader imageLoader = new ImageLoader(MyApplication.getHttpQueue(), new BitmapCache());

        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageview, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        imageLoader.get(url, imageListener);
    }


    /*
    1. 创建一个RequestQueue对象。

    2. 创建一个Request对象。

    3. 将Request对象添加到RequestQueue里面。
     */
    public void volleyImageRequest() {

        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {

            @Override
            public void onResponse(Bitmap bitmap) {
                Toast.makeText(MainActivity.this, "Succeed", Toast.LENGTH_LONG).show();
                imageview.setImageBitmap(bitmap);

            }
        }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                imageview.setBackgroundColor(Color.BLACK);
            }
        });
        MyApplication.getHttpQueue().add(request);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getHttpQueue().cancelAll("post");
    }

    public void volleyPost() {
        String url = "http://apis.juhe.cn/mobile/get?";
//        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("phone", "18370035337");
//                map.put("key", "335adcc4e891ba4e4be6d7534fd54c5d");
//                return map;
//            }
//        };

        Map<String, String> map = new HashMap<String, String>();
        map.put("phone", "18370035337");
        map.put("key", "335adcc4e891ba4e4be6d7534fd54c5d");
        JSONObject jsonObject = new JSONObject(map);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(MainActivity.this, jsonObject.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        });
        request.setTag("post");
        MyApplication.getHttpQueue().add(request);
    }

    public void volleyGet() {
        String url = "http://www.baidu.com";

       /*   1. 创建一个RequestQueue对象。

            2. 创建一个StringRequest对象。

            3. 将StringRequest对象添加到RequestQueue里面。
        */
//        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(MainActivity.this,"failed",Toast.LENGTH_SHORT).show();
//            }
//        });



        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.i("onResponse=", "succeed");
                Toast.makeText(MainActivity.this, jsonObject.toString() + "OK", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("onErrorResponse=", "failed");
                Toast.makeText(MainActivity.this, volleyError.toString() + "\n" + "bad!!!!", Toast.LENGTH_LONG).show();
            }
        });
        request.setTag("get");
        MyApplication.getHttpQueue().add(request);
    }
}
