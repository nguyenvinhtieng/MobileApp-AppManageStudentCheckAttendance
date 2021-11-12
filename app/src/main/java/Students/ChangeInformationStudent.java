package Students;

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

public class ChangeInformationStudent extends AppCompatActivity {
    private TextView username;
    private String UserName;
    private EditText name,classname;
    private Button btnchangeinformation;
    private Database db;
    private ImageView iconout;
    String nameStudent,classStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_information_student);
        // ẨN ACTIONBAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //END
        Intent it = getIntent();
        UserName = it.getStringExtra("username");
        db = new Database(this);
        username = findViewById(R.id.username);
        name = findViewById(R.id.name);
        classname = findViewById(R.id.classname);
        btnchangeinformation = findViewById(R.id.btnchangeinformation);
        iconout = findViewById(R.id.iconout);

        nameStudent = db.getNameStudent(UserName);
        classStudent = db.getClassStudent(UserName);
        name.setText(nameStudent);
        classname.setText(classStudent);
        username.setText(UserName);
        iconout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnchangeinformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStudentAfter = name.getText().toString().trim();
                String classStudentAfter = classname.getText().toString().trim();
                if(nameStudentAfter.equals("") && classStudentAfter.equals("")){
                    // CUSTOM TOAST
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_custom_red,(ViewGroup) findViewById(R.id.toast));
                    TextView toastText = layout.findViewById(R.id.toasttext);
                    ImageView imageView = layout.findViewById(R.id.imageview);
                    toastText.setText("Vui lòng nhập đầy đủ các thông tin");
                    imageView.setImageResource(R.drawable.ic_sad);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    /// END TOAST
                }
                else{
                    if(nameStudent.equals(nameStudentAfter) && classStudent.equals(classStudentAfter)){
                        //START
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_custom_red,(ViewGroup) findViewById(R.id.toast));
                        TextView toastText = layout.findViewById(R.id.toasttext);
                        ImageView imageView = layout.findViewById(R.id.imageview);
                        toastText.setText("Không có thông tin cần thay đổi");
                        imageView.setImageResource(R.drawable.ic_sad);
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                        /// END TOAST
                    }
                    else{
                        db.updateInforStudent(UserName,nameStudentAfter,classStudentAfter);
                        nameStudent = nameStudentAfter;
                        classStudent = classStudentAfter;
                        //START
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
                        /// END TOAST
                    }
                }
            }
        });


    }
}