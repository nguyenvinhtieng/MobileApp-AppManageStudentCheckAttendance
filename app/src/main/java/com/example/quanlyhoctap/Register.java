package com.example.quanlyhoctap;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

import Database.Database;

public class Register extends AppCompatActivity {
    private Database db;
    private EditText UserName, PassWord,confirmPassWord;
    private TextView btnLogin;
    private Button btnRegister;
    private ImageView iconout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // ẨN ACTIONBAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //END
        db = new Database(this);
        //set data
        UserName = findViewById(R.id.UserName);
        PassWord = findViewById(R.id.PassWord);
        confirmPassWord = findViewById(R.id.confirmPassWord);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        iconout = findViewById(R.id.iconout);
        iconout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Register.this, Login.class);
                startActivity(it);
                finish();
            }
        });
        //btn login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Register.this, Login.class);
                startActivity(it);
                finish();
            }
        });
        //btn Register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = UserName.getText().toString().trim();
                String password = PassWord.getText().toString().trim();
                String confirmpass = confirmPassWord.getText().toString().trim();
                if(username.equals("") || password.equals("") || confirmpass.equals("")){
                    // CUSTOM TOAST
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_custom_red,(ViewGroup) findViewById(R.id.toast));
                    TextView toastText = layout.findViewById(R.id.toasttext);
                    ImageView imageView = layout.findViewById(R.id.imageview);
                    toastText.setText("Vui lòng nhập đầy đủ thông tin");
                    imageView.setImageResource(R.drawable.ic_sad);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    /// END TOAST
                }
                else if(password.length() < 8){
                    // CUSTOM TOAST
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_custom_red,(ViewGroup) findViewById(R.id.toast));
                    TextView toastText = layout.findViewById(R.id.toasttext);
                    ImageView imageView = layout.findViewById(R.id.imageview);
                    toastText.setText("Mật khẩu phải lớn hơn 8 kí tự");
                    imageView.setImageResource(R.drawable.ic_sad);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    /// END TOAST
                }
                else{
                    if(password.equals(confirmpass)){
                        boolean checkUsername = db.checkUsername(username);
                        if(checkUsername == true){
                            if(username.equals("admin")){
                                boolean a = db.insertDataAccountAdmin(username,password);
                                // CUSTOM TOAST
                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                                TextView toastText = layout.findViewById(R.id.toasttext);
                                ImageView imageView = layout.findViewById(R.id.imageview);
                                toastText.setText("Đăng kí thành công tài khoản Admin");
                                imageView.setImageResource(R.drawable.ic_admin);
                                Toast toast = new Toast(getApplicationContext());
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                                /// END TOAST
                            }
                            else{
                                boolean insert = db.insertDataAccountHS(username,password);
                                if(insert == true){
                                    // CUSTOM TOAST
                                    LayoutInflater inflater = getLayoutInflater();
                                    View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                                    TextView toastText = layout.findViewById(R.id.toasttext);
                                    ImageView imageView = layout.findViewById(R.id.imageview);
                                    toastText.setText("Đăng kí thành công");
                                    imageView.setImageResource(R.drawable.ic_notifycation);
                                    Toast toast = new Toast(getApplicationContext());
                                    toast.setGravity(Gravity.CENTER,0,0);
                                    toast.setDuration(Toast.LENGTH_SHORT);
                                    toast.setView(layout);
                                    toast.show();
                                    /// END TOAST
                                }
                                else{
                                    // CUSTOM TOAST
                                    LayoutInflater inflater = getLayoutInflater();
                                    View layout = inflater.inflate(R.layout.toast_custom_red,(ViewGroup) findViewById(R.id.toast));
                                    TextView toastText = layout.findViewById(R.id.toasttext);
                                    ImageView imageView = layout.findViewById(R.id.imageview);
                                    toastText.setText("Đăng kí thất bại");
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
                        else{
                            // CUSTOM TOAST
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast_custom_red,(ViewGroup) findViewById(R.id.toast));
                            TextView toastText = layout.findViewById(R.id.toasttext);
                            ImageView imageView = layout.findViewById(R.id.imageview);
                            toastText.setText("Tên tài khoản đã tồn tại");
                            imageView.setImageResource(R.drawable.ic_sad);
                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.CENTER,0,0);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout);
                            toast.show();
                            /// END TOAST
                        }
                    }
                    else{
                        // CUSTOM TOAST
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_custom_red,(ViewGroup) findViewById(R.id.toast));
                        TextView toastText = layout.findViewById(R.id.toasttext);
                        ImageView imageView = layout.findViewById(R.id.imageview);
                        toastText.setText("Password và Confirm Password không trùng khớp");
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