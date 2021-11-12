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

public class ChangePasswordAccount extends AppCompatActivity {
    private EditText UserName, OPassWord, NPassWord, CNPassWord;
    private Button btnchage;
    private Database db;
    private TextView login;
    private ImageView iconout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_account);
        // ẨN ACTIONBAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //END
        db = new Database(this);
        UserName = findViewById(R.id.UserName);
        OPassWord = findViewById(R.id.OPassWord);
        NPassWord = findViewById(R.id.NPassWord);
        CNPassWord = findViewById(R.id.CNPassWord);
        btnchage = findViewById(R.id.btnchage);
        login = findViewById(R.id.login);
        iconout = (ImageView) findViewById(R.id.iconout);

        iconout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ChangePasswordAccount.this, Login.class);
                startActivity(it);
                finish();
            }
        });
        btnchage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = UserName.getText().toString();
                String opassword = OPassWord.getText().toString();
                String npassword = NPassWord.getText().toString();
                String cnpassword = CNPassWord.getText().toString();
                if(username.equals("") || opassword.equals("") || npassword.equals("") || cnpassword.equals("")){
                    // CUSTOM TOAST RED
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_custom_red,(ViewGroup) findViewById(R.id.toast));
                    TextView toastText = layout.findViewById(R.id.toasttext);
                    ImageView imageView = layout.findViewById(R.id.imageview);
                    toastText.setText("Vui lòng nhập đủ thông tin");
                    imageView.setImageResource(R.drawable.ic_sad);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    /// END TOAST
                }
                else {
                    if (db.checkAccount(username, opassword) == 0) {
                        // CUSTOM TOAST RED
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_custom_red, (ViewGroup) findViewById(R.id.toast));
                        TextView toastText = layout.findViewById(R.id.toasttext);
                        ImageView imageView = layout.findViewById(R.id.imageview);
                        toastText.setText("Tên tài khoản hoặc mật khẩu không chính xác");
                        imageView.setImageResource(R.drawable.ic_sad);
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                        /// END TOAST
                    } else {
                        if (npassword.length() < 8) {
                            // CUSTOM TOAST RED
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast_custom_red, (ViewGroup) findViewById(R.id.toast));
                            TextView toastText = layout.findViewById(R.id.toasttext);
                            ImageView imageView = layout.findViewById(R.id.imageview);
                            toastText.setText("Mật khẩu phải lớn hơn 8 kí tự");
                            imageView.setImageResource(R.drawable.ic_sad);
                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout);
                            toast.show();
                            /// END TOAST
                        } else if (cnpassword.equals(npassword) == false) {
                            // CUSTOM TOAST RED
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast_custom_red, (ViewGroup) findViewById(R.id.toast));
                            TextView toastText = layout.findViewById(R.id.toasttext);
                            ImageView imageView = layout.findViewById(R.id.imageview);
                            toastText.setText("Xác nhận mật khẩu không chính xác");
                            imageView.setImageResource(R.drawable.ic_sad);
                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout);
                            toast.show();
                            /// END TOAST
                        } else {
                            db.updatePasswordAccount(username, opassword, npassword);
                            // CUSTOM TOAST RED
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast_custom_blue, (ViewGroup) findViewById(R.id.toast));
                            TextView toastText = layout.findViewById(R.id.toasttext);
                            ImageView imageView = layout.findViewById(R.id.imageview);
                            toastText.setText("Đổi mật khẩu thành công");
                            imageView.setImageResource(R.drawable.ic_done);
                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout);
                            toast.show();
                            /// END TOAST
                        }
                    }
                }
            }
        });


    }
}