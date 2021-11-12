package Students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlyhoctap.ChatRoom;
import com.example.quanlyhoctap.R;

import Database.Database;

public class MyClass extends AppCompatActivity {
    private String UserName, idclass;
    private Database db;
    private TextView nameclass, note, ucclass, total, studied, absent, ban, notban;
    private ImageView homepage, chat, diemdanh;
    private ImageView outclass;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class);
        // ẨN ACTIONBAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //END
        Intent it = getIntent();
        UserName = it.getStringExtra("username");
        idclass = it.getStringExtra("id");

        db = new Database(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        outclass = findViewById(R.id.outclass);
        nameclass = findViewById(R.id.nameclass);
        note = findViewById(R.id.note);
        ucclass = findViewById(R.id.ucclass);
        total = findViewById(R.id.total);
        studied = findViewById(R.id.studied);
        absent = findViewById(R.id.absent);
        ban = findViewById(R.id.ban);
        notban = findViewById(R.id.notban);
        homepage = findViewById(R.id.homepage);
        chat = findViewById(R.id.chat);
        diemdanh = findViewById(R.id.diemdanh);
        nameclass.setText("Môn :" + db.getnamesubject(idclass));
        note.setText("Chú thích :" + db.getnotesubject(idclass));
        ucclass.setText("Giáo viên :" + db.getNameTeacher(db.getUCsubject(idclass)));
        total.setText("Số buổi của môn :" + String.valueOf(db.gettotalsubject(idclass)));
        studied.setText("Số buổi đã học của môn :" + String.valueOf(db.getNumstudied(idclass)));
        absent.setText("Số buổi bạn vắng :" + String.valueOf(db.getNumabsent(UserName, idclass)));
        outclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outclass();
            }
        });

        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentchatroom = new Intent(MyClass.this, ChatRoom.class);
                intentchatroom.putExtra("id", idclass);
                intentchatroom.putExtra("username", UserName);
                startActivity(intentchatroom);
            }
        });
        diemdanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diemdanh();
            }
        });
        int totallop = db.gettotalsubject(idclass);
        int absentlop = db.getNumabsent(UserName, idclass);

        if ((double) (absentlop * 1.0 / totallop) > 0.2) {
            ban.setText("Bạn bị Cấm thi");
        }

    }

    private void diemdanh() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.diemdanhhocsinh);

        EditText code = dialog.findViewById(R.id.codediemdanh);
        Button btnfinish = dialog.findViewById(R.id.btnfinish);
        boolean HasTODiemDanh = db.HasTODiemDanh(idclass);
        if (HasTODiemDanh == false) {
            btnfinish.setEnabled(false);
            // CUSTOM TOAST RED
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_custom_red, (ViewGroup) findViewById(R.id.toast));
            TextView toastText = layout.findViewById(R.id.toasttext);
            ImageView imageView = layout.findViewById(R.id.imageview);
            toastText.setText("Không phải lúc điểm danh");
            imageView.setImageResource(R.drawable.ic_sad);
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
            /// END TOAST
        }
        btnfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Scode = code.getText().toString().trim();
                if (Scode.equals("")) {
                    // CUSTOM TOAST RED
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_custom_red, (ViewGroup) findViewById(R.id.toast));
                    TextView toastText = layout.findViewById(R.id.toasttext);
                    ImageView imageView = layout.findViewById(R.id.imageview);
                    toastText.setText("Vui lòng nhập mã");
                    imageView.setImageResource(R.drawable.ic_sad);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    /// END TOAST
                } else {
                    // CUSTOM TOAST RED
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_custom_red, (ViewGroup) findViewById(R.id.toast));
                    TextView toastText = layout.findViewById(R.id.toasttext);
                    ImageView imageView = layout.findViewById(R.id.imageview);
                    toastText.setText(db.DDHocSinh(idclass, UserName, Scode));
                    imageView.setImageResource(R.drawable.ic_sad);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    /// END TOAST
                    // check điểm danh
                }
            }
        });
        dialog.show();
    }

    //Rời lớp học
    private void outclass() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.alert_dialog_outclass);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView imageclose = (dialog).findViewById(R.id.imageViewclose);
        Button btnout = (dialog).findViewById(R.id.btnout);

        //Animation scaleup = AnimationUtils.loadAnimation(MyClass.this,R.anim.scale_button_up);
        //Animation scaledown = AnimationUtils.loadAnimation(MyClass.this,R.anim.scale_button_down);
        imageclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        btnout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteStudentClass(idclass, UserName);
                // TOAST BLUE
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_custom_blue, (ViewGroup) findViewById(R.id.toast));
                TextView toastText = layout.findViewById(R.id.toasttext);
                ImageView imageView = layout.findViewById(R.id.imageview);
                toastText.setText("Rời lớp học thành công");
                imageView.setImageResource(R.drawable.ic_done);
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
                //END
                Intent it = new Intent(MyClass.this, List_My_Class.class);
                it.putExtra("username", UserName);
                startActivity(it);
            }
        });
        dialog.show(); /*
        AlertDialog.Builder builder = new AlertDialog.Builder(MyClass.this);
        builder.setMessage("Bạn có chắc muốn rời lớp học").setCancelable(false)
                .setPositiveButton("Rời lớp học", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteStudentClass(idclass,UserName);
                        // TOAST BLUE
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                        TextView toastText = layout.findViewById(R.id.toasttext);
                        ImageView imageView = layout.findViewById(R.id.imageview);
                        toastText.setText("Rời lớp học thành công");
                        imageView.setImageResource(R.drawable.ic_done);
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                        //END
                        //Toast.makeText(MyClass.this,"Rời lớp học thành công",Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(MyClass.this,Index_Student.class);
                        it.putExtra("username",UserName);
                        startActivity(it);
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show(); */
    }
}