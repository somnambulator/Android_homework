package com.bytedance.practice5;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bytedance.practice5.model.Message;
import com.bytedance.practice5.model.MessageListResponse;
import com.bytedance.practice5.model.UploadResponse;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadActivity extends AppCompatActivity {
    private static final String TAG = "chapter5";
    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024;
    private static final int REQUEST_CODE_COVER_IMAGE = 101;
    private static final String COVER_IMAGE_TYPE = "image/*";
    private IApi api;
    private Uri coverImageUri;
    private SimpleDraweeView coverSD;
    private EditText toEditText;
    private EditText contentEditText ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNetwork();
        setContentView(R.layout.activity_upload);
        coverSD = findViewById(R.id.sd_cover);
        toEditText = findViewById(R.id.et_to);
        contentEditText = findViewById(R.id.et_content);
        findViewById(R.id.btn_cover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFile(REQUEST_CODE_COVER_IMAGE, COVER_IMAGE_TYPE, "选择图片");
            }
        });


        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        submitMessageWithURLConnection();
                    }
                }).start();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_COVER_IMAGE == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                coverImageUri = data.getData();
                coverSD.setImageURI(coverImageUri);

                if (coverImageUri != null) {
                    Log.d(TAG, "pick cover image " + coverImageUri.toString());
                } else {
                    Log.d(TAG, "uri2File fail " + data.getData());
                }

            } else {
                Log.d(TAG, "file pick fail");
            }
        }
    }

    private void initNetwork() {
        //TODO 3
        // 创建Retrofit实例
        // 生成api对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-android-camp.bytedance.com/zju/invoke/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(IApi.class);
    }

    private void getFile(int requestCode, String type, String title) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(type);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.putExtra(Intent.EXTRA_TITLE, title);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, requestCode);
    }

    private void submit() {
        byte[] coverImageData = readDataFromUri(coverImageUri);
        if (coverImageData == null || coverImageData.length == 0) {
            Toast.makeText(this, "封面不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        String to = toEditText.getText().toString();
        if (TextUtils.isEmpty(to)) {
            Toast.makeText(this, "请输入TA的名字", Toast.LENGTH_SHORT).show();
            return;
        }
        String content = contentEditText.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "请输入想要对TA说的话", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( coverImageData.length >= MAX_FILE_SIZE) {
            Toast.makeText(this, "文件过大", Toast.LENGTH_SHORT).show();
            return;
        }
        //TODO 5
        // 使用api.submitMessage()方法提交留言
        // 如果提交成功则关闭activity，否则弹出toast
        MultipartBody.Part coverPart = MultipartBody.Part.createFormData("image",
                "cover.png",
                RequestBody.create(MediaType.parse("multipart/form-data"),
                        coverImageData));
        MultipartBody.Part from_part = MultipartBody.Part.createFormData("from",Constants.USER_NAME);
        MultipartBody.Part to_part = MultipartBody.Part.createFormData("to",to);
        MultipartBody.Part content_part = MultipartBody.Part.createFormData("content",content);
        Call<UploadResponse> result = api.submitMessage(Constants.STUDENT_ID,"",from_part,to_part,content_part,coverPart,Constants.token);
        result.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                if(response.isSuccessful()){
                    //Log.d(TAG,"upload successfully!");
                    Intent intent = new Intent(UploadActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(UploadActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    // TODO 7 选做 用URLConnection的方式实现提交
    private void submitMessageWithURLConnection(){
        byte[] coverImageData=readDataFromUri(coverImageUri);
        //System.out.println(Arrays.toString(coverImageData));

        if (coverImageData == null || coverImageData.length == 0) {
            Toast.makeText(this, "封面不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        String to = toEditText.getText().toString();
        if (TextUtils.isEmpty(to)) {
            Toast.makeText(this, "请输入TA的名字", Toast.LENGTH_SHORT).show();
            return;
        }
        String content = contentEditText.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "请输入想要对TA说的话", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( coverImageData.length >= MAX_FILE_SIZE) {
            Toast.makeText(this, "文件过大", Toast.LENGTH_SHORT).show();
            return;
        }

        HttpURLConnection conn = null;
        String response;
        String charset = "UTF-8";
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ContentResolver resolver = getContentResolver();
        Bitmap bm;
        File file;
        try {
//            file = getFileStreamPath(String.valueOf(coverImageUri));
            bm = MediaStore.Images.Media.getBitmap(resolver, coverImageUri);
            //System.out.println(bm);
            conn = (HttpURLConnection) new URL("https://api-android-camp.bytedance.com/zju/invoke/messages/"+"?"+"student_id="+Constants.STUDENT_ID+"&extra_value=").openConnection();

            conn.setDoInput(true);// 允许输入 是否从httpUrlConnection读入，默认情况下是true;
            conn.setDoOutput(true);// 允许输出 post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
            conn.setRequestMethod("POST");// POST请求 要在获取输入输出流之前设置 否则报错
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset","utf-8");
            //conn.addRequestProperty("Content-Language","en-US, zh");

            conn.setConnectTimeout(6000);// 设置超时时间
            conn.setUseCaches(false);// Post 请求不能使用缓存
            //header
            conn.setRequestProperty("token",Constants.token);
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=****");

            //body
            DataOutputStream request = new DataOutputStream(conn.getOutputStream());
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));

            request.writeBytes("--****\r\n");
            request.writeBytes("Content-Disposition: form-data; name=\"from\"\r\n\r\n");
            request.writeUTF(Constants.USER_NAME);
            request.writeBytes("\r\n");
            request.writeBytes("--****\r\n");
            request.writeBytes("Content-Disposition: form-data; name=\"to\"\r\n\r\n");
            request.writeUTF(to);
            request.writeBytes("\r\n");
            request.writeBytes("--****\r\n");
            request.writeBytes("Content-Disposition: form-data; name=\"content\"\r\n\r\n");
            request.writeUTF(content);
            request.writeBytes("\r\n");
            request.writeBytes("--****\r\n");
            request.writeBytes("Content-Disposition: form-data; name=\"" +
                    "image" + "\"; filename=\"" +
                    "cover.png" +"\""+ "\r\n");
            request.writeBytes("Content-Type: image/*"+"\r\n");
            //request.writeBytes("Content-Transfer-Encoding: binary"+"\r\n");
            request.writeBytes("\r\n");

            request.write(coverImageData);
            request.writeBytes("\r\n");
            request.writeBytes("--****--\r\n");

//            bw.write("--****\r\n");
//            bw.write("Content-Disposition: form-data; name=\"from\"\r\n"+
//                    "\r\n\r\n"+Constants.USER_NAME+"\r\n");
//            bw.write("--****\r\n");
//            bw.write("Content-Disposition: form-data; name=\"to\"\r\n" +
//                    "\r\n\r\n"+to+"\r\n");
//            bw.write("--****\r\n");
//            bw.write("Content-Disposition: form-data; name=\"content\"\r\n" +
//                    "\r\n\r\n"+content+"\r\n");
//            bw.write("--****\r\n");
//            bw.write("Content-Disposition: form-data; name=\"" +
//                    "image" + "\"; filename=\"" +
//                    "cover.png" +"\""+ "\r\n");
//            bw.write("Content-Type: image/*"+"\r\n");
//            //request.writeBytes("Content-Transfer-Encoding: binary"+"\r\n");
//            bw.write("\r\n");
//
//            bw.write(Arrays.toString(coverImageData));
//            bw.write("\r\n");
//            bw.write("--****--\r\n");

            //System.out.println(request.toString());
            if (conn.getResponseCode() == 200) {
                request.flush();
                request.close();

                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

                byte[] data = new byte[1024];
                int len = 0;
                while ((len = in.read(data, 0, data.length)) != -1) {
                    outStream.write(data, 0, len);
                }
                in.close();
                response = outStream.toString("UTF-8");
                System.out.println(response);
//                System.out.println(in);
                reader.close();
                in.close();
                Intent intent = new Intent(UploadActivity.this,MainActivity.class);
                startActivity(intent);
            } else {
                // 错误处理
                Toast.makeText(UploadActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
            }
            conn.disconnect();
        }catch(Exception e){
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(UploadActivity.this, "网络异常" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private byte[] readDataFromUri(Uri uri) {
        byte[] data = null;
        try {
            InputStream is = getContentResolver().openInputStream(uri);
            data = Util.inputStream2bytes(is);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


}
