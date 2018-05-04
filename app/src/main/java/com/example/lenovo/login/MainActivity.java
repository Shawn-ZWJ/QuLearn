package com.example.lenovo.login;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //view层的控件和业务层的控件，靠id关联和映射  给btn1赋值，即设置布局文件中的Button按钮id进行关联
        Button btn1=(Button)findViewById(R.id.login_btn);

        //给btn1绑定监听事件
        btn1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // 给bnt1添加点击响应事件
                Intent intent =new Intent(MainActivity.this,registerActivity.class);
                //启动
                startActivity(intent);
            }
        });

    }

}
