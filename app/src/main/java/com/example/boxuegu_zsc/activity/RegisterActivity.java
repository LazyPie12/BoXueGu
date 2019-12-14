package com.example.boxuegu_zsc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boxuegu_zsc.R;
import com.example.boxuegu_zsc.utils.MD5Utils;

public class RegisterActivity extends AppCompatActivity {
    private TextView tv_main_title;
    private TextView tv_back;
    private Button btn_register;
    private EditText et_user_name,et_psw,et_psw_again;
    private String userName,psw,pswAgain;
    private RelativeLayout rl_title_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }
    private void init(){
        tv_main_title=(TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("注册");
        tv_back=(TextView) findViewById(R.id.tv_back);
        rl_title_bar=(RelativeLayout) findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.TRANSPARENT);
        btn_register=(Button) findViewById(R.id.btn_register);
        et_user_name=(EditText) findViewById(R.id.et_user_name);
        et_psw=(EditText) findViewById(R.id.et_psw);
        et_psw_again=(EditText) findViewById(R.id.et_psw_again);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditString();
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(RegisterActivity.this, "请输入用户名",
                            Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(RegisterActivity.this, "请输入密码",
                            Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(pswAgain)){
                    Toast.makeText(RegisterActivity.this, "请再次输入密码",
                            Toast.LENGTH_SHORT).show();
                    return;
                }else if(!psw.equals(pswAgain)){
                    Toast.makeText(RegisterActivity.this, "输入两次的密码不一样",
                            Toast.LENGTH_SHORT).show();
                    return;
                }else if(isExistUserName(userName)){
                    Toast.makeText(RegisterActivity.this, "此账户名已经存在",
                            Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(RegisterActivity.this, "注册成功",
                            Toast.LENGTH_SHORT).show();
                    saveRegisterInfo(userName, psw);
                    Intent data =new Intent();
                    data.putExtra("userName", userName);
                    setResult(RESULT_OK, data);
                    RegisterActivity.this.finish();
                }
            }
        });
    }

    //获取控件中的字符串
    private void getEditString(){
        userName=et_user_name.getText().toString().trim();
        psw=et_psw.getText().toString().trim();
        pswAgain=et_psw_again.getText().toString().trim();
    }

    //从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名
    private boolean isExistUserName(String userName){
        boolean has_userName=false;
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        String spPsw=sp.getString(userName, "");
        if(!TextUtils.isEmpty(spPsw)) {
            has_userName=true;
        }
        return has_userName;
    }
    /**
     * 保存账号和密码到SharedPreferences中
     */
    private void saveRegisterInfo(String userName,String psw){
        String md5Psw= MD5Utils.md5(psw);
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE); //loginInfo表示文件名
        SharedPreferences.Editor editor=sp.edit(); //获取编辑器
        //以用户名为key，密码为value保存在SharedPreferences中
        editor.putString(userName, md5Psw);
        editor.commit(); //提交修改
    }
}
