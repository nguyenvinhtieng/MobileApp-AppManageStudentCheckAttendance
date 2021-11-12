package Students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quanlyhoctap.ChangePasswordAccount;
import com.example.quanlyhoctap.Login;
import com.example.quanlyhoctap.MyApplication;
import com.example.quanlyhoctap.R;
import com.example.quanlyhoctap.Subject;
import com.example.quanlyhoctap.SubjectAdapter;

import java.util.Date;
import java.util.List;

import Database.Database;

public class List_My_Class extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private String UserName;
    private ListView list;
    private List<Subject> subjects ;
    private Database db;
    SubjectAdapter listSj;
    private ImageView homepage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index__student);
        // ẨN ACTIONBAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //END
        Intent it = getIntent();
        UserName = it.getStringExtra("username");
        db = new Database(this);
        list = findViewById(R.id.list);
        homepage = findViewById(R.id.homepage);
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(List_My_Class.this, Home_Page_Student.class);
                it.putExtra("username",UserName);
                startActivity(it);
                finish();
            }
        });
        subjects = db.ListSubjectStudent(UserName);
        if(subjects == null){
            String temp = " ";
            subjects.add(new Subject(temp, temp, temp,temp));
        }
        listSj = new SubjectAdapter(this,
                R.layout.row_item_subject, subjects);
        list.setAdapter(listSj);
        listSj.notifyDataSetChanged();
        list.setOnItemClickListener(this);

        //check xem có nên hiển thị thông báo không ?
        boolean hasnotify = db.checkHasNotify(UserName);
        if(hasnotify == true){
            //showDialogNotify();
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
            Notification notification = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                    .setContentTitle("Thông báo từ APP Quản Lý Học Tập")
                    .setContentText(db.contentNotify(UserName))
                    .setSmallIcon(R.drawable.ic_notifycation)
                    .setLargeIcon(bitmap)
                    .build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(getNotificationId(), notification);
        }
    }

    private int getNotificationId(){
        return (int) new Date().getTime();
    }

    private void showDialogNotify() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.notify_student);
        TextView messNotify = dialog.findViewById(R.id.notify);
        Button btnClose = dialog.findViewById(R.id.ButtonClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        String mess = db.contentNotify(UserName);
        messNotify.setText(mess);
        dialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listSj.notifyDataSetChanged();

        Subject subject = subjects.get(position);
        String idclass = subject.getId();
        Intent it = new Intent(this, MyClass.class);
        it.putExtra("id",idclass);
        it.putExtra("username",UserName);
        startActivity(it);
    }

    //menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuindexstudent,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                Intent itentlogout = new Intent(List_My_Class.this, Login.class);
                startActivity(itentlogout);
                finish();
                break;
            case R.id.changepassword:
                Intent itentchangepass = new Intent(List_My_Class.this, ChangePasswordAccount.class);
                startActivity(itentchangepass);
                break;
            case R.id.changeinfor:
                Intent itentchangeinfor = new Intent(List_My_Class.this, ChangeInformationStudent.class);
                itentchangeinfor.putExtra("username",UserName);
                startActivity(itentchangeinfor);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}