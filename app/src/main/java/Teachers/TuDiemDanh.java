package Teachers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlyhoctap.R;

import java.util.ArrayList;
import java.util.List;

import Database.Database;

public class TuDiemDanh extends AppCompatActivity{

    private TextView Textnameclass;
    private ListView liststudent;
    private String idclass,username;
    private Database db;
    private Button btndiemdanh;
    private List<TuDiemDanhJava> students;
    TuDiemDanhAdapter listStudentInClass;
    private LinearLayout finish;
    private ImageView imgclose;
    ArrayAdapter<String> adapter;
    List<String> arr = new ArrayList<String>();
    private CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tu_diem_danh);
        // ẨN ACTIONBAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //END
        Intent it = getIntent();
        idclass = it.getStringExtra("id");
        username = it.getStringExtra("username");
        db = new Database(this);
        //
        checkBox = findViewById(R.id.cb_check_all);
        finish = findViewById(R.id.finish);
        imgclose = findViewById(R.id.imgclose);
        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoantatdiemdanh();
            }
        });
        arr = db.DSHocSinhDiemDanhString(idclass);
        liststudent = findViewById(R.id.list);
        adapter = new ArrayAdapter<>(this,R.layout.row_check,arr);
        liststudent.setAdapter(adapter);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for(int i = 0 ; i < adapter.getCount(); i++){
                    liststudent.setItemChecked(i, isChecked);
                }
            }
        });
    }

    private void hoantatdiemdanh() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.are_you_sure_dialog);
        ImageView imgclose = dialog.findViewById(R.id.imageViewclose);
        Button btnYes = dialog.findViewById(R.id.btnYes);
        Button btnNo = dialog.findViewById(R.id.btnNo);
        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemnotselect = "";
                List<String> DSStudentsNOT = new ArrayList<>();
                for(int i = 0; i < liststudent.getCount();i++){
                    if(!liststudent.isItemChecked(i)){
                        String idOfStudent = (String) liststudent.getItemAtPosition(i);
                        int in = idOfStudent.indexOf("ID : ");
                        String[] words=idOfStudent.split("ID : ");
                        boolean isGet = false;
                        for(String w:words){
                            if(isGet){
                                DSStudentsNOT.add(w);
                            }
                            isGet = true;
                        }
                    }
                }
                // xu li database
                db.diemdanh(idclass,DSStudentsNOT);
                // TOAST BLUE
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                TextView toastText = layout.findViewById(R.id.toasttext);
                ImageView imageView = layout.findViewById(R.id.imageview);
                toastText.setText("Điểm danh thành công");
                imageView.setImageResource(R.drawable.ic_done);
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER,0,0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
                //END
                Intent it = new Intent(TuDiemDanh.this,ClassSubject.class);
                it.putExtra("id",idclass);
                it.putExtra("username",username);
                startActivity(it);
            }
        });
        dialog.show();
/*
        AlertDialog.Builder builder = new AlertDialog.Builder(TuDiemDanh.this);
        builder.setMessage("ARE YOU SURE ").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String itemnotselect = "";
                        List<String> DSStudentsNOT = new ArrayList<>();
                        for(int i = 0; i < liststudent.getCount();i++){
                            if(!liststudent.isItemChecked(i)){
                                String idOfStudent = (String) liststudent.getItemAtPosition(i);
                                int in = idOfStudent.indexOf("ID : ");
                                String[] words=idOfStudent.split("ID : ");
                                boolean isGet = false;
                                for(String w:words){
                                    if(isGet){
                                        DSStudentsNOT.add(w);
                                    }
                                    isGet = true;
                                }
                            }
                        }
                        // xu li database
                        db.diemdanh(idclass,DSStudentsNOT);
                        // TOAST BLUE
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                        TextView toastText = layout.findViewById(R.id.toasttext);
                        ImageView imageView = layout.findViewById(R.id.imageview);
                        toastText.setText("Điểm danh thành công");
                        imageView.setImageResource(R.drawable.ic_done);
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                        //END
                        Intent it = new Intent(TuDiemDanh.this,ClassSubject.class);
                        it.putExtra("id",idclass);
                        it.putExtra("username",username);
                        startActivity(it);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
*/
    }

}