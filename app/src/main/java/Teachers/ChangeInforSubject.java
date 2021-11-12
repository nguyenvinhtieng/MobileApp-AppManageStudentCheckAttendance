package Teachers;

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
import com.example.quanlyhoctap.RandomString;

import Database.Database;

public class ChangeInforSubject extends AppCompatActivity {
    private EditText classname,classtotal,classnote;
    private TextView numshift;
    private Button btnchange;
    private Database db;
    String idclass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_infor_subject);

        db = new Database(this);
        classname = findViewById(R.id.classnamec);
        classtotal = findViewById(R.id.classtotalc);
        classnote = findViewById(R.id.classnotec);
        btnchange = findViewById(R.id.btnchangeifsubject);
        numshift= findViewById(R.id.numshift);

        Intent it = getIntent();
        idclass = it.getStringExtra("id");

        String tenmonhoc = db.getnamesubject(idclass);
        String chuthich = db.getnotesubject(idclass);
        int sobuoi = db.gettotalsubject(idclass);
        classname.setText(tenmonhoc);
        numshift.setText(String.valueOf(sobuoi));
        classnote.setText(chuthich);



        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameclass = classname.getText().toString().trim();
                String noteclass = classnote.getText().toString().trim();
                String sumtotal = classtotal.getText().toString().trim();
                int total = 0;
                if(! sumtotal.isEmpty()){
                    total = Integer.parseInt(sumtotal);
                }
                if(nameclass.equals("") ||  noteclass.equals("") || sumtotal.equals("")){
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
                }
                else{
                    db.updateInforSubject(idclass,nameclass,noteclass,total);
                    // CUSTOM TOAST RED
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                    TextView toastText = layout.findViewById(R.id.toasttext);
                    ImageView imageView = layout.findViewById(R.id.imageview);
                    toastText.setText("Cập nhật thông tin lớp học thành công");
                    imageView.setImageResource(R.drawable.ic_done);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    /// END TOAST
                }
            }
        });

    }
}