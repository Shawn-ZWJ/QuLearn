package com.viaviapp.hdvideo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {

    private EditText login_input_name;
    private EditText login_input_password;
    private TextView login_register;

    private String strResult="";//http结果
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_input_name = (EditText)findViewById(R.id.login_input_name);
        login_input_password = (EditText)findViewById(R.id.login_input_password);
        login_register = (TextView)findViewById(R.id.login_register);

        //view层的控件和业务层的控件，靠id关联和映射  给btn1赋值，即设置布局文件中的Button按钮id进行关联
        Button btn1=(Button)findViewById(R.id.login_btn);

        //给btn1绑定监听事件
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                username = login_input_name.getText().toString().trim();
                password = login_input_password.getText().toString().trim();

                //在新线程里发送请求并获得返回结果字符串，把值赋给strResult
                new Thread(new RequestThread()).start();

                if(username.equals("admin") && password.equals("admin")){
                    // 给bnt1添加点击响应事件
                    Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                    //启动
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this, "Login failed! please input your username and password.", Toast.LENGTH_SHORT).show();
                }


            }
        });


        login_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    Intent intent =new Intent(LoginActivity.this,registerActivity.class);
                    //启动
                    startActivity(intent);
            }
        });


    }


    private class RequestThread implements Runnable {
        @SuppressWarnings("unchecked")
        public void run() {
            //因为选择POST方法，所以new HttpPost对象，构造方法传入处理请求php文件的url
            HttpPost httpRequest = new HttpPost("http://47.94.20.69/login_db.php");
            //POST方法的参数列表
            ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
            //添加参数，值
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
            try {
                //设置请求实体，设定了参数列表
                httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                //执行请求,等待服务器返回结果
                HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
                //log出http返回报文头
                Log.e("status",httpResponse.getStatusLine().toString());
                //判断返回码是否为200，200表示请求成功
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    //获取返回字符串
                    strResult = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
                    if(strResult.equals("y")){
                        // 给bnt1添加点击响应事件
                        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                        //启动
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
