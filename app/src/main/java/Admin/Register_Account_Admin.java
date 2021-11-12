package Admin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlyhoctap.R;

import Database.Database;

public class Register_Account_Admin extends AppCompatActivity {
    private RadioGroup rbCVu;
    private Database db;
    private EditText UserName, PassWord;
    private TextView MessErr;
    private Button btnRegister;
    private ImageView iconout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__account__admin);
        // ẨN ACTIONBAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //END
        iconout = findViewById(R.id.iconout);
        iconout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Register_Account_Admin.this,Index_Admin.class);
                startActivity(it);
                finish();
            }
        });
        db = new Database(this);
        //set data
        rbCVu = findViewById(R.id.rbCVu);
        UserName = findViewById(R.id.UserName);
        PassWord = findViewById(R.id.PassWord);
        PassWord.setText("12345678");
        btnRegister = findViewById(R.id.btnRegister);
        //btn Register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = UserName.getText().toString().trim();
                String password = PassWord.getText().toString().trim();
                boolean isGV = rbCVu.getCheckedRadioButtonId() == R.id.rbGV; //nếu là giáo viên thì isGV = true ngược lại là học sinh thì false
                if(username.equals("") || password.equals("")){
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
                    boolean checkUsername = db.checkUsername(username);
                    if(checkUsername == false){
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
                    else{
                        if(isGV == true){
                            boolean insert = db.insertDataAccountGV(username,password);
                            if(insert == true){
                                // TOAST BLUE
                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                                TextView toastText = layout.findViewById(R.id.toasttext);
                                ImageView imageView = layout.findViewById(R.id.imageview);
                                toastText.setText("Đăng kí tài khoản Giáo Viên thành công");
                                imageView.setImageResource(R.drawable.ic_notifycation);
                                Toast toast = new Toast(getApplicationContext());
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                            }
                            else{
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
                        else{
                            boolean insert = db.insertDataAccountHS(username,password);
                            if(insert == true){
                                // TOAST BLUE
                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                                TextView toastText = layout.findViewById(R.id.toasttext);
                                ImageView imageView = layout.findViewById(R.id.imageview);
                                toastText.setText("Đăng kí tài khoản Học Sinh thành công");
                                imageView.setImageResource(R.drawable.ic_notifycation);
                                Toast toast = new Toast(getApplicationContext());
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                            }
                            else{
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

                }
            }
        });


    }
}