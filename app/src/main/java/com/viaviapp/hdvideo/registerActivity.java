package com.viaviapp.hdvideo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class registerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //view层的控件和业务层的控件，靠id关联和映射  给btn1赋值，即设置布局文件中的Button按钮id进行关联
        Button btn2=(Button)findViewById(R.id.register_btn);

        //给btn1绑定监听事件
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // 给bnt1添加点击响应事件
                Intent intent =new Intent(registerActivity.this,LoginActivity.class);
                //启动
                startActivity(intent);
            }
        });


    }
}
