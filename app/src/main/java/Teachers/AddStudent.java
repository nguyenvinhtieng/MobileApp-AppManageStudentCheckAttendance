/*package Teachers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quanlyhoctap.R;

import Database.Database;

public class AddStudent extends AppCompatActivity {
    private EditText studentid;
    private TextView Errmess;
    private Button btnaddstudent;
    private String idclass;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        db = new Database(this);
        Intent it = getIntent();
        idclass = it.getStringExtra("id");

        studentid = findViewById(R.id.studentid);
        Errmess = findViewById(R.id.Errmess);
        btnaddstudent = findViewById(R.id.btnaddstudent);

        btnaddstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernamestudent = studentid.getText().toString();
                if(usernamestudent.equals("")){
                    Errmess.setText("Vui lòng nhập mã số học sinh");
                }
                else if(db.checkUsername(usernamestudent) == true){
                    Errmess.setText("Mã số học sinh không hợp lệ");
                }
                else{
                    //insert
                    boolean check_has = db.checkstudentinclass(idclass,usernamestudent);
                    if(check_has == true){
                        Errmess.setText("Học sinh đã tham gia lớp học này rồi");
                    }
                    else{
                        boolean check = db.insertStudentClass(idclass,usernamestudent);
                        if(check == false){
                            Errmess.setText("Thêm thất bại");
                        }
                        else{
                            Errmess.setText("Thêm học sinh thành công");

                        }
                    }

                }


            }
        });

    }
}*/