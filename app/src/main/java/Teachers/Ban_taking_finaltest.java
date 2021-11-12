package Teachers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlyhoctap.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import Database.Database;

import static androidx.core.content.ContextCompat.startActivity;

public class Ban_taking_finaltest extends AppCompatActivity {
    private static final int STORAGE_CODE = 1000;
    TextView test;
    private ListView liststudent;
    private String idclass;
    private Database db;
    private List<StudentInSubject> students ;
    private String username;
    private Button indanhsach;
    StudentInSubjectAdapter listStudentInClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_taking_finaltest);
        // ẨN ACTIONBAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //END
        Intent it = getIntent();
        idclass = it.getStringExtra("id");
        indanhsach = findViewById(R.id.indanhsach);

        db = new Database(this);
        liststudent = findViewById(R.id.liststudent);
        ImageView imageView = findViewById(R.id.backclass);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getdataStudentinClass();


        listStudentInClass = new StudentInSubjectAdapter(this,
                R.layout.rowliststudent, students);

        liststudent.setAdapter(listStudentInClass);

        indanhsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_method_print();
            }
        });


    }

    private void getdataStudentinClass() {
        students = db.ListStudentInClassBan(idclass);
    }

    //DIALOG CHỌN PHƯƠNG THỨC IN
    private void dialog_method_print(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.select_method_print);
        final Button txtprint = dialog.findViewById(R.id.txtprint);
        final Button pdfprint = dialog.findViewById(R.id.pdfprint);
        final Button docxprint = dialog.findViewById(R.id.docxprint);
        // in txt
        txtprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print_ds_txt();
            }
        });
        // in docx
        docxprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print_ds_docx();
            }
        });
        // in pdf
        pdfprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        dialog.show();
    }



    // In Dinh Dang Txt
    private void print_ds_txt(){
        String chuoidanhsach = db.exportDSCamThi(idclass);
        try {
            String tenfile = "DanhSachCamThiMon" + db.getnamesubject(idclass);
            OutputStream file = openFileOutput(tenfile + ".txt",MODE_PRIVATE);
            PrintWriter writer = new PrintWriter(file);
            writer.println(chuoidanhsach);
            writer.close();
            // TOAST BLUE
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
            TextView toastText = layout.findViewById(R.id.toasttext);
            ImageView imageView = layout.findViewById(R.id.imageview);
            toastText.setText("Xuất danh sách định dạng .txt thành công !");
            imageView.setImageResource(R.drawable.ic_notifycation);
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER,0,0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
            //END
            //Toast.makeText(this,"Xuất danh sách định dạng .txt thành công !",Toast.LENGTH_SHORT).show();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    // In Dinh Dang docx
    private void print_ds_docx(){
        String chuoidanhsach = db.exportDSCamThi(idclass);
        try {
            String tenfile = "DanhSachCamThiMon" + db.getnamesubject(idclass);
            OutputStream file = openFileOutput(tenfile + ".docx",MODE_PRIVATE);
            PrintWriter writer = new PrintWriter(file);
            writer.println(chuoidanhsach);
            writer.close();
            // TOAST BLUE
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
            TextView toastText = layout.findViewById(R.id.toasttext);
            ImageView imageView = layout.findViewById(R.id.imageview);
            toastText.setText("Xuất danh sách định dạng .docx thành công !");
            imageView.setImageResource(R.drawable.ic_notifycation);
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER,0,0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
            //END
            //Toast.makeText(this,"Xuất danh sách định dạng .docx thành công !",Toast.LENGTH_SHORT).show();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    // IN Dinh Dang pdf

}

