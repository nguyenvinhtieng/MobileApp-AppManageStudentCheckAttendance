package Teachers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlyhoctap.ChangePasswordAccount;
import com.example.quanlyhoctap.Login;
import com.example.quanlyhoctap.R;
import com.example.quanlyhoctap.RandomString;
import com.example.quanlyhoctap.Subject;
import com.example.quanlyhoctap.SubjectAdapter;

import java.security.SecureRandom;
import java.util.List;

import Database.Database;

public class ManagerClass extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView listView;
    private String username;
    private List<Subject> subjects ;
    private  Database db;
    private ArrayAdapter<Subject> adapterlist;
    SubjectAdapter listSj;
    private ImageView homepage,createclass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index__teacher);
        // ẨN ACTIONBAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //END
        Intent it = getIntent();
        username = it.getStringExtra("username");
        db = new Database(this);
        listView = findViewById(R.id.list_subject);
        homepage = findViewById(R.id.homepage);
        createclass = findViewById(R.id.createclass);
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        createclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogcreateclass();
            }
        });

        getdataSubject();
        listSj = new SubjectAdapter(this,
                R.layout.row_item_subject_teacher, subjects);
        listView.setAdapter(listSj);
        listSj.notifyDataSetChanged();

        listView.setOnItemClickListener(this);
    }

    private void getdataSubject() {
        subjects = db.ListSubjectTeacher(username);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Subject subject = subjects.get(position);
        String idclass = subject.getId();
        Intent it = new Intent(this,ClassSubject.class);
        it.putExtra("id",idclass);
        it.putExtra("username",username);
        startActivity(it);
    }
    // DIalog tạo lớp học
    private void dialogcreateclass() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_create_class);

        //Tạo id random
        RandomString a;
        String easy = RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx";
        RandomString tickets = new RandomString(30, new SecureRandom(), easy);
        char[] random = new char[30];
        tickets.toString().getChars(39,tickets.toString().length(),random,0);
        // ánh xạ
        final EditText classname = dialog.findViewById(R.id.classname);
        final EditText classtotal = dialog.findViewById(R.id.classtotal);
        final EditText classnote = dialog.findViewById(R.id.classnote);
        final Button btncreate = dialog.findViewById(R.id.btncreate);
        final ImageView iconclose = dialog.findViewById(R.id.iconclose);
        iconclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        String chuoirandom = random.toString();

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameclass = classname.getText().toString().trim();
                String total = classtotal.getText().toString().trim();
                String note = classnote.getText().toString().trim();
                int totals = 0;
                if(! total.isEmpty()){
                    totals = Integer.parseInt(total);
                }
                //Toast.makeText(CreateClass.this,totals,Toast.LENGTH_SHORT).show();
                if(nameclass.equals("") || note.equals("") || total.equals("")){
                    // TOAST RED
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_custom_red,(ViewGroup) findViewById(R.id.toast));
                    TextView toastText = layout.findViewById(R.id.toasttext);
                    ImageView imageView = layout.findViewById(R.id.imageview);
                    toastText.setText("Vui lòng nhập đủ thông tin của lớp học");
                    imageView.setImageResource(R.drawable.ic_sad);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    //END
                }
                else{
                    boolean check =db.insertClass(chuoirandom,nameclass,note,username,totals);
                    if(check == true){
                        // TOAST BLUE
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                        TextView toastText = layout.findViewById(R.id.toasttext);
                        ImageView imageView = layout.findViewById(R.id.imageview);
                        toastText.setText("Tạo lớp học thành công !\nMôn : " + nameclass + "\nChú thích : " + note);
                        imageView.setImageResource(R.drawable.ic_done);
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                        //END
                        String nameGiaoVien = db.getNameTeacher(username);
                        subjects.add(new Subject(chuoirandom,"Môn : " + nameclass,"Chú thích : " + note,"Giáo viên : " + nameGiaoVien));
                        listSj.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                    else{
                        // TOAST RED
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_custom_red,(ViewGroup) findViewById(R.id.toast));
                        TextView toastText = layout.findViewById(R.id.toasttext);
                        ImageView imageView = layout.findViewById(R.id.imageview);
                        toastText.setText("Tạo lớp học thất bại !");
                        imageView.setImageResource(R.drawable.ic_sad);
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                        //END

                    }
                }
            }
        });
        dialog.show();
    }


}