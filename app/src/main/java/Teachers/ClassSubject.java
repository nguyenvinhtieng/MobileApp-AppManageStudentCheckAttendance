package Teachers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlyhoctap.ChatRoom;
import com.example.quanlyhoctap.R;

import java.util.List;

import Database.Database;

public class ClassSubject extends AppCompatActivity implements AdapterView.OnItemClickListener{
    TextView test;
    private ListView liststudent;
    private String idclass;
    private Database db;
    private List<StudentInSubject> students ;
    private String username;
    StudentInSubjectAdapter listStudentInClass;
    private ImageView back;
    private LinearLayout deleteclass;
    private ImageView thaydoithongtin,themhocsinh,chatroom,diemdanh,danhsachban;
    private TextView nameclass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_subject);
        // ẨN ACTIONBAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //END
        test = findViewById(R.id.test);
        Intent it = getIntent();
        idclass = it.getStringExtra("id");
        username = it.getStringExtra("username");
        db = new Database(this);
        liststudent = findViewById(R.id.liststudent);

        //set
        back = findViewById(R.id.back);
        deleteclass = findViewById(R.id.deleteclass);
        thaydoithongtin = findViewById(R.id.thaydoithongtin);
        themhocsinh = findViewById(R.id.themhocsinh);
        chatroom = findViewById(R.id.chatroom);
        diemdanh = findViewById(R.id.diemdanh);
        danhsachban = findViewById(R.id.danhsachban);
        nameclass = findViewById(R.id.nameclass);

        nameclass.setText(db.getnamesubject(idclass));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        deleteclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcDeleteClass();
            }
        });
        thaydoithongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thaydoithongtinlophoc();
            }
        });
        themhocsinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addstudent();
            }
        });
        chatroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentchatroom = new Intent(ClassSubject.this, ChatRoom.class);
                intentchatroom.putExtra("id",idclass);
                intentchatroom.putExtra("username",username);
                startActivity(intentchatroom);
            }
        });
        diemdanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////
                diemdanhdanhchogiaovien();
            }
        });
        danhsachban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentban = new Intent(ClassSubject.this,Ban_taking_finaltest.class);
                intentban.putExtra("id",idclass);
                startActivity(intentban);
            }
        });
        getdataStudentinClass();
        listStudentInClass = new StudentInSubjectAdapter(this,
                R.layout.rowliststudent, students);

        liststudent.setAdapter(listStudentInClass);
        liststudent.setOnItemClickListener(this);
    }
    private void thaydoithongtinlophoc() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_changeinforclass);

        ImageView imgclose = dialog.findViewById(R.id.imgclose);
        EditText classname = dialog.findViewById(R.id.classnamec);
        TextView numshift = dialog.findViewById(R.id.numshift);
        EditText classtotal = dialog.findViewById(R.id.classtotalc);
        EditText classnote = dialog.findViewById(R.id.classnotec);
        Button btnchange = dialog.findViewById(R.id.btnchangeifsubject);

        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // set thông tin vào
        String tenmonhoc = db.getnamesubject(idclass);
        String chuthich = db.getnotesubject(idclass);
        int sobuoi = db.gettotalsubject(idclass);
        classname.setText(tenmonhoc);
        numshift.setText(String.valueOf(sobuoi));
        classnote.setText(chuthich);

        //event

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
                    dialog.dismiss();
                    /// END TOAST
                }
            }
        });
        dialog.show();

    }

    private void diemdanhdanhchogiaovien() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_chosen_diemdanh);

        ImageView imgclose = (dialog).findViewById(R.id.imgclose);
        Button tudiemdanh = (dialog).findViewById(R.id.tudiemdanh);
        Button diemdanhbangma = (dialog).findViewById(R.id.diemdanhbangma);
        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tudiemdanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttudiemdanh = new Intent(ClassSubject.this,TuDiemDanh.class);
                intenttudiemdanh.putExtra("id",idclass);
                intenttudiemdanh.putExtra("username",username);
                startActivity(intenttudiemdanh);
            }
        });
        diemdanhbangma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functiondiemdanhbangma();
            }
        });
        dialog.show();
    }

    // LAY  DU LIEU DANH SACH HOC SINH TRONG LOP
    private void getdataStudentinClass() {
        students = db.ListStudentInClass(idclass);
    }


    //SU KIEN CHO TUNG DONG
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        StudentInSubject std = students.get(position);
        String idstd = std.getUserName();
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_event_student,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.delete:
                        deletestudentclass(idstd,std);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void deletestudentclass(String idstd, StudentInSubject std) {
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
                db.deleteStudentClass(idclass,idstd);
                // TOAST BLUE
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                TextView toastText = layout.findViewById(R.id.toasttext);
                ImageView imageView = layout.findViewById(R.id.imageview);
                toastText.setText("Xóa thành công học sinh : " + idstd);
                imageView.setImageResource(R.drawable.ic_done);
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER,0,0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
                //END
                //Toast.makeText(ClassSubject.this,"Xóa thành công",Toast.LENGTH_SHORT).show();
                listStudentInClass.remove(std);
                listStudentInClass.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


// ĐIỂM DANH BẰNG MÃ
    private void Functiondiemdanhbangma() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_diemdanhbangma);

        EditText code = dialog.findViewById(R.id.code);
        Button btncreate = dialog.findViewById(R.id.btncreatecode);
        Button btnclosecode = dialog.findViewById(R.id.btnclosecode);
        ImageView imgclose = dialog.findViewById(R.id.imgclose);
        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        String codeClass = db.checkCode(idclass);
        code.setText(codeClass);
        if(codeClass.equals("")){
            btnclosecode.setEnabled(false);
        }
        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Scode = code.getText().toString().trim();
                if(Scode.equals("")){
                    // TOAST BLUE
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_custom_red,(ViewGroup) findViewById(R.id.toast));
                    TextView toastText = layout.findViewById(R.id.toasttext);
                    ImageView imageView = layout.findViewById(R.id.imageview);
                    toastText.setText("Mã Code Không Hợp Lệ");
                    imageView.setImageResource(R.drawable.ic_sad);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    //END
                    //Toast.makeText(ClassSubject.this,"Mã Code Không Hợp Lệ",Toast.LENGTH_SHORT).show();
                }
                else{
                    db.createCode(idclass,Scode);
                    // TOAST BLUE
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                    TextView toastText = layout.findViewById(R.id.toasttext);
                    ImageView imageView = layout.findViewById(R.id.imageview);
                    toastText.setText("Cập nhật thành công mã code");
                    imageView.setImageResource(R.drawable.ic_done);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    //END
                    //Toast.makeText(ClassSubject.this,"Cập nhật hành công",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        btnclosecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog1 = new Dialog(ClassSubject.this);
                dialog1.setContentView(R.layout.are_you_sure_dialog);
                ImageView imgclose = dialog1.findViewById(R.id.imageViewclose);
                Button btnYes = dialog1.findViewById(R.id.btnYes);
                Button btnNo = dialog1.findViewById(R.id.btnNo);
                imgclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                });
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                });
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TOAST BLUE
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                        TextView toastText = layout.findViewById(R.id.toasttext);
                        ImageView imageView = layout.findViewById(R.id.imageview);
                        toastText.setText("Hoàn tất điểm danh!");
                        imageView.setImageResource(R.drawable.ic_done);
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                        //END
                        //Toast.makeText(ClassSubject.this,"FINISH !",Toast.LENGTH_SHORT).show();
                        db.enddiemdanh(idclass);
                        dialog1.dismiss();
                        dialog.dismiss();
                    }
                });
                //
                dialog1.show();
            }
        });
        dialog.show();

    }


    // THÊM HỌC SINH VÀO LỚP HỌC
    private void addstudent() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_add_student);

        // ánh xạ
         EditText studentid = dialog.findViewById(R.id.studentid);
         Button btnaddstudent = dialog.findViewById(R.id.btnaddstudent);
         ImageView imgclose = dialog.findViewById(R.id.imgclose);
         imgclose.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 dialog.dismiss();
             }
         });
         //event click
        btnaddstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernamestudent = studentid.getText().toString();
                if(usernamestudent.equals("")){
                    // CUSTOM TOAST RED
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_custom_red,(ViewGroup) findViewById(R.id.toast));
                    TextView toastText = layout.findViewById(R.id.toasttext);
                    ImageView imageView = layout.findViewById(R.id.imageview);
                    toastText.setText("Vui lòng nhập mã số học sinh");
                    imageView.setImageResource(R.drawable.ic_sad);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    /// END TOAST
                }
                else if(db.checkUsername(usernamestudent) == true){
                    // CUSTOM TOAST RED
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_custom_red,(ViewGroup) findViewById(R.id.toast));
                    TextView toastText = layout.findViewById(R.id.toasttext);
                    ImageView imageView = layout.findViewById(R.id.imageview);
                    toastText.setText("Mã số học sinh không hợp lệ");
                    imageView.setImageResource(R.drawable.ic_sad);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    /// END TOAST
                }
                else{
                    //insert
                    boolean check_has = db.checkstudentinclass(idclass,usernamestudent);
                    if(check_has == true){
                        // CUSTOM TOAST RED
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_custom_red,(ViewGroup) findViewById(R.id.toast));
                        TextView toastText = layout.findViewById(R.id.toasttext);
                        ImageView imageView = layout.findViewById(R.id.imageview);
                        toastText.setText("Học sinh đã tham gia lớp học này rồi");
                        imageView.setImageResource(R.drawable.ic_sad);
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                        /// END TOAST
                    }
                    else{
                        boolean check = db.insertStudentClass(idclass,usernamestudent);
                        if(check == false){
                            // CUSTOM TOAST RED
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast_custom_red,(ViewGroup) findViewById(R.id.toast));
                            TextView toastText = layout.findViewById(R.id.toasttext);
                            ImageView imageView = layout.findViewById(R.id.imageview);
                            toastText.setText("Thêm thất bại");
                            imageView.setImageResource(R.drawable.ic_sad);
                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.CENTER,0,0);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout);
                            toast.show();
                            /// END TOAST
                        }
                        else{
                            listStudentInClass.add(new StudentInSubject(idclass,usernamestudent,0,0,false,db.getNameStudent(usernamestudent)));
                            listStudentInClass.notifyDataSetChanged();
                            // CUSTOM TOAST BLUE
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                            TextView toastText = layout.findViewById(R.id.toasttext);
                            ImageView imageView = layout.findViewById(R.id.imageview);
                            toastText.setText("Thêm thành công học sinh \n Mã số : "+ usernamestudent);
                            imageView.setImageResource(R.drawable.ic_done);
                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.CENTER,0,0);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout);
                            toast.show();
                            /// END TOAST
                            dialog.dismiss();
                        }
                    }

                }
            }
        });
        dialog.show();
    }
    // XÓa lớp học
    private void funcDeleteClass() {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_deleteclass);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView imageclose = (dialog).findViewById(R.id.imageViewclose);
        Button btnxoa = (dialog).findViewById(R.id.btnxoa);

        imageclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteClass(idclass);
                // CUSTOM TOAST BLUE
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                TextView toastText = layout.findViewById(R.id.toasttext);
                ImageView imageView = layout.findViewById(R.id.imageview);
                toastText.setText("Xóa Lớp Học Thành Công");
                imageView.setImageResource(R.drawable.ic_done);
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER,0,0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
                /// END TOAST
                Intent it = new Intent(ClassSubject.this, ManagerClass.class);
                it.putExtra("username",username);
                startActivity(it);
            }
        });
        dialog.show();
        /////////////////////////////////////////////////////
        /////////////////////////////////////////////////////
        /////////////////////////////////////////////////////
        /////////////////////////////////////////////////////

    }


}