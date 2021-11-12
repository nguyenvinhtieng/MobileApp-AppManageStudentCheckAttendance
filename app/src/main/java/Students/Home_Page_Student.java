package Students;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlyhoctap.ChangePasswordAccount;
import com.example.quanlyhoctap.Login;
import com.example.quanlyhoctap.R;

import Teachers.ChangeInformationTeacher;
import Teachers.Home_Page_Teacher;
import Teachers.ManagerClass;

public class Home_Page_Student extends AppCompatActivity {
    private ImageView imgclose;
    private Button listclass;
    private Button changeinformation;
    private Button changepassword;
    private Button logout;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__page__student);
        // ẨN ACTIONBAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //END
        Intent it = getIntent();
        username = it.getStringExtra("username");
        //

        imgclose = findViewById(R.id.imgclose);
        listclass = findViewById(R.id.listclass);
        changeinformation = findViewById(R.id.changeinformation);
        changepassword = findViewById(R.id.changepassword);
        logout = findViewById(R.id.logout);
        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logoutsystem();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logoutsystem();
            }
        });
        listclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Home_Page_Student.this, List_My_Class.class);
                it.putExtra("username",username);
                startActivity(it);
            }
        });
        changeinformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Home_Page_Student.this, ChangeInformationStudent.class);
                it.putExtra("username",username);
                startActivity(it);
            }
        });
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Home_Page_Student.this, ChangePasswordAccount.class);
                startActivity(it);
            }
        });
    }

    private void logoutsystem() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_out_system);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView imageclose = (dialog).findViewById(R.id.imageViewclose);
        Button btnclose = (dialog).findViewById(R.id.btnclose);
        Button btnout = (dialog).findViewById(R.id.btnout);

        //Animation scaleup = AnimationUtils.loadAnimation(Home_Page_Student.this,R.anim.scale_button_up);
        //Animation scaledown = AnimationUtils.loadAnimation(Home_Page_Student.this,R.anim.scale_button_down);

        imageclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TOAST BLUE
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                TextView toastText = layout.findViewById(R.id.toasttext);
                ImageView imageView = layout.findViewById(R.id.imageview);
                toastText.setText("Đăng xuất thành công");
                imageView.setImageResource(R.drawable.ic_notifycation);
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER,0,0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
                //END
                Intent it = new Intent(Home_Page_Student.this, Login.class);
                startActivity(it);
                finish();
            }
        });
        dialog.show();
    }
}