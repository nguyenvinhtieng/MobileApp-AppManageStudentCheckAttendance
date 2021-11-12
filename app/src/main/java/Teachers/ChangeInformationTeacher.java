package Teachers;

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

import com.example.quanlyhoctap.R;

import Database.Database;

public class ChangeInformationTeacher extends AppCompatActivity {
    private TextView username;
    private String UserName;
    private EditText name;
    private Button btnchangeinformation;
    private Database db;
    private  ImageView iconout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_information_teacher);
        // ẨN ACTIONBAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //END
        Intent it = getIntent();
        UserName = it.getStringExtra("username");
        db = new Database(this);
        username = findViewById(R.id.username);
        name = findViewById(R.id.name);
        btnchangeinformation = findViewById(R.id.btnchangeinformation);
        iconout = findViewById(R.id.iconout);
        iconout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        String nameTeacher = db.getNameTeacher(UserName);
        name.setText(nameTeacher);
        username.setText(UserName);

        btnchangeinformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTeacher = name.getText().toString().trim();
                if(nameTeacher.equals("")){
                    // CUSTOM TOAST RED
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
                    //Toast.makeText(ChangeInformationTeacher.this,"Vui lòng nhập thông tin",Toast.LENGTH_SHORT).show();
                }
                else{
                    db.updateInforTeacher(UserName,nameTeacher);
                    // TOAST BLUE
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                    TextView toastText = layout.findViewById(R.id.toasttext);
                    ImageView imageView = layout.findViewById(R.id.imageview);
                    toastText.setText("Cập nhật thông tin thành công");
                    imageView.setImageResource(R.drawable.ic_done);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    //END
                    //Toast.makeText(ChangeInformationTeacher.this,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        });

    }
}