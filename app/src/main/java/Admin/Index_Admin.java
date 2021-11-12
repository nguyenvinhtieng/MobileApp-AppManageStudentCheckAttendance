package Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlyhoctap.ChangePasswordAccount;
import com.example.quanlyhoctap.Login;
import com.example.quanlyhoctap.R;

import java.util.List;

import Database.Database;
import Students.ChangeInformationStudent;
import Teachers.ChangeInformationTeacher;
import Teachers.ClassSubject;

public class Index_Admin extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView list;
    private Database db;
    private List<TaiKhoan> taikhoans;
    private TaiKhoanAdapter taiKhoanAdapter;
    private ImageView imgclose, imgcreateaccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index__admin);
        // ẨN ACTIONBAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //END
        db = new Database(this);
        list = findViewById(R.id.danhsachtaikhoan);
        imgcreateaccount = findViewById(R.id.imgcreateaccount);
        imgclose = findViewById(R.id.imgclose);
        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Animation animation = AnimationUtils.loadAnimation(Index_Admin.this, R.anim.scale_list);
        imgcreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgcreateaccount.startAnimation(animation);
                Intent itentcreate = new Intent(Index_Admin.this, Register_Account_Admin.class);
                startActivity(itentcreate);
            }
        });
        //getdata
        taikhoans = db.getAccountlist();
        taiKhoanAdapter = new TaiKhoanAdapter(this,R.layout.row_taikhoan,taikhoans);
        list.setAdapter(taiKhoanAdapter);
        taiKhoanAdapter.notifyDataSetChanged();
        //list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.scale_list);
                view.startAnimation(animation);

                TaiKhoan tk = taikhoans.get(position);
                String maso = tk.getMaso();
                String chucvu = tk.getChucVu();
                PopupMenu popupMenu = new PopupMenu(Index_Admin.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_event_account,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete:
                                String mess = "Bạn có chắc muốn xóa tài khoản có mã số :" + maso;
                                Dialog dialog1 = new Dialog(Index_Admin.this);
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
                                        db.deleteAccount(maso);
                                        // TOAST BLUE
                                        LayoutInflater inflater = getLayoutInflater();
                                        View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                                        TextView toastText = layout.findViewById(R.id.toasttext);
                                        ImageView imageView = layout.findViewById(R.id.imageview);
                                        toastText.setText("Xóa tài khoản thành công");
                                        imageView.setImageResource(R.drawable.ic_notifycation);
                                        Toast toast = new Toast(getApplicationContext());
                                        toast.setGravity(Gravity.CENTER,0,0);
                                        toast.setDuration(Toast.LENGTH_SHORT);
                                        toast.setView(layout);
                                        toast.show();
                                        //END
                                        // Toast.makeText(Index_Admin.this,"Xóa tài khoản thành công",Toast.LENGTH_SHORT).show();
                                        taiKhoanAdapter.remove(tk);
                                        taiKhoanAdapter.notifyDataSetChanged();
                                        dialog1.dismiss();
                                    }
                                });
                                //
                                dialog1.show();
                                //////////////////

                                break;
                            case R.id.edit:
                                if(chucvu.equals("Học Sinh")){
                                    Intent edithocsinh = new Intent(Index_Admin.this, ChangeInformationStudent.class);
                                    edithocsinh.putExtra("username",maso);
                                    startActivity(edithocsinh);
                                }
                                else if(chucvu.equals("Giáo Viên")){
                                    Intent editgiaovien = new Intent(Index_Admin.this, ChangeInformationTeacher.class);
                                    editgiaovien.putExtra("username",maso);
                                    startActivity(editgiaovien);
                                }
                                break;
                        }

                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
    }
    //click event
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TaiKhoan tk = taikhoans.get(position);
        String maso = tk.getMaso();
        String chucvu = tk.getChucVu();
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_event_account,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.delete:
                        String mess = "Bạn có chắc muốn xóa tài khoản có mã số :" + maso;
                        Dialog dialog1 = new Dialog(Index_Admin.this);
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
                                db.deleteAccount(maso);
                                // TOAST BLUE
                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                                TextView toastText = layout.findViewById(R.id.toasttext);
                                ImageView imageView = layout.findViewById(R.id.imageview);
                                toastText.setText("Xóa tài khoản thành công");
                                imageView.setImageResource(R.drawable.ic_notifycation);
                                Toast toast = new Toast(getApplicationContext());
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                                //END
                               // Toast.makeText(Index_Admin.this,"Xóa tài khoản thành công",Toast.LENGTH_SHORT).show();
                                taiKhoanAdapter.remove(tk);
                                taiKhoanAdapter.notifyDataSetChanged();
                                dialog1.dismiss();
                            }
                        });
                        //
                        dialog1.show();
                        //////////////////

                        break;
                    case R.id.edit:
                        if(chucvu.equals("Học Sinh")){
                            Intent edithocsinh = new Intent(Index_Admin.this, ChangeInformationStudent.class);
                            edithocsinh.putExtra("username",maso);
                            startActivity(edithocsinh);
                        }
                        else if(chucvu.equals("Giáo Viên")){
                            Intent editgiaovien = new Intent(Index_Admin.this, ChangeInformationTeacher.class);
                            editgiaovien.putExtra("username",maso);
                            startActivity(editgiaovien);
                        }
                        break;
                }

                return false;
            }
        });
        popupMenu.show();
    }



}