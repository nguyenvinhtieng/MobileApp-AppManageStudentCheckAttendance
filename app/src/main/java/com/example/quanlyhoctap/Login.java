package com.example.quanlyhoctap;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import Admin.Home_Page_Admin;
import Admin.Index_Admin;
import Database.Database;
import Students.Home_Page_Student;
import Students.List_My_Class;
import Teachers.Home_Page_Teacher;

public class Login extends AppCompatActivity {
    private EditText UserName, PassWord;
    private Button btnLogin;
    private Database db;
    private TextView btnRegister;
    private Account account;
    private TextView changepassword;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ẨN ACTIONBAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //END
        db = new Database(this);
        UserName = findViewById(R.id.UserName);
        PassWord = findViewById(R.id.PassWord);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        changepassword = findViewById(R.id.changepassword);
        ///////////////----------------------------
        //-------- TẠO ACCOUNT ADMIN
        //---------------------------------------
        boolean checkUsername = db.checkUsername("admin");
        if(checkUsername){
            boolean a = db.insertDataAccountAdmin("admin","12345678");
            // CUSTOM TOAST
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
            TextView toastText = layout.findViewById(R.id.toasttext);
            ImageView imageView = layout.findViewById(R.id.imageview);
            toastText.setText("\n\nChào mừng lần đầu đăng nhập hệ thống\n\n");
            imageView.setImageResource(R.drawable.ic_admin);
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER,0,0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
            /// END TOAST
        }
        ///////////////----------------------------
        //-------- END
        //---------------------------------------
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Login.this,ChangePasswordAccount.class);
                startActivity(it);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Login.this,Register.class);
                startActivity(it);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = UserName.getText().toString().trim();
                String password = PassWord.getText().toString().trim();
                //requireInternet();
                ////////////////////////////////////////
                ///////////////////////////////////////////////
                if(username.isEmpty()){
                    // CUSTOM TOAST
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_custom_red,(ViewGroup) findViewById(R.id.toast));
                    TextView toastText = layout.findViewById(R.id.toasttext);
                    ImageView imageView = layout.findViewById(R.id.imageview);
                    toastText.setText("Vui Lòng Nhập UserName!");
                    imageView.setImageResource(R.drawable.ic_sad);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    /// END TOAST
                    UserName.requestFocus();
                }
                else if(password.isEmpty()){
                    // CUSTOM TOAST
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_custom_red,(ViewGroup) findViewById(R.id.toast));
                    TextView toastText = layout.findViewById(R.id.toasttext);
                    ImageView imageView = layout.findViewById(R.id.imageview);
                    toastText.setText("Vui Lòng Nhập PassWord!");
                    imageView.setImageResource(R.drawable.ic_sad);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    /// END TOAST
                    PassWord.requestFocus();
                }
                else{
                    int checkLeverAccount = db.checkAccount(username,password);
                    if(checkLeverAccount == 1){ //TH HOC SINH
                        // TOAST BLUE
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                        TextView toastText = layout.findViewById(R.id.toasttext);
                        ImageView imageView = layout.findViewById(R.id.imageview);
                        toastText.setText("Đăng Nhập Thành Công");
                        imageView.setImageResource(R.drawable.ic_notifycation);
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                        //END
                        Intent it = new Intent(Login.this, Home_Page_Student.class);
                        it.putExtra("username",username);
                        startActivity(it);
                        finish();
                    }
                    else if(checkLeverAccount == 2){//TH GIAO VIEN
                        // TOAST BLUE
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                        TextView toastText = layout.findViewById(R.id.toasttext);
                        ImageView imageView = layout.findViewById(R.id.imageview);
                        toastText.setText("Đăng Nhập Thành Công");
                        imageView.setImageResource(R.drawable.ic_notifycation);
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                        //END
                        Intent it = new Intent(Login.this, Home_Page_Teacher.class);
                        it.putExtra("username",username);
                        startActivity(it);
                        finish();
                    }
                    else if(checkLeverAccount == 3){//TH Admin
                        // TOAST BLUE
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                        TextView toastText = layout.findViewById(R.id.toasttext);
                        ImageView imageView = layout.findViewById(R.id.imageview);
                        toastText.setText("Đăng Nhập Thành Công");
                        imageView.setImageResource(R.drawable.ic_notifycation);
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                        //END
                        Intent it = new Intent(Login.this, Home_Page_Admin.class);
                        it.putExtra("username",username);
                        startActivity(it);
                        finish();
                    }
                    else{
                        // CUSTOM TOAST
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_custom_red,(ViewGroup) findViewById(R.id.toast));
                        TextView toastText = layout.findViewById(R.id.toasttext);
                        ImageView imageView = layout.findViewById(R.id.imageview);
                        toastText.setText("Tên tài khoản hoặc mật khẩu không hợp lệ");
                        imageView.setImageResource(R.drawable.ic_sad);
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                        /// END TOAST
                    }
                }
            }
        });
    }
}