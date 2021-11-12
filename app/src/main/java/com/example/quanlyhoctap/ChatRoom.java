package com.example.quanlyhoctap;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Admin.Index_Admin;
import Database.Database;
import Students.ChangeInformationStudent;
import Students.List_My_Class;
import Students.MyClass;
import Teachers.ChangeInformationTeacher;
import Teachers.ClassSubject;

public class ChatRoom extends AppCompatActivity implements View.OnClickListener {
    private EditText content;
    private Button btnsent;
    private ListView list;
    private String idclass,username;
    private Database db;
    private List<RoomChat> chats;
    private RoomChatAdapter chatAdapter;
    private ImageView imgclose;
    private TextView tenphongchat;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        // ẨN ACTIONBAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //END
        Intent it = getIntent();
        idclass = it.getStringExtra("id");
        username = it.getStringExtra("username");
        db = new Database(this);
        content = findViewById(R.id.content);
        btnsent = findViewById(R.id.btnsent);
        list = findViewById(R.id.list);
        tenphongchat = findViewById(R.id.tenphongchat);
        tenphongchat.setText("Phòng chat môn:\n" + db.getnamesubject(idclass));
        imgclose = findViewById(R.id.imgclose);
        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lever = db.getLeverAccount(username);
                if(lever == 1){
                    Intent it = new Intent(ChatRoom.this, MyClass.class);
                    it.putExtra("username", username);
                    it.putExtra("id", idclass);
                    startActivity(it);
                    finish();
                }
                else if(lever == 2){
                    Intent it = new Intent(ChatRoom.this, ClassSubject.class);
                    it.putExtra("username", username);
                    it.putExtra("id", idclass);
                    startActivity(it);
                    finish();
                }
                onBackPressed();
            }
        });

        getdatachat();
        chatAdapter = new RoomChatAdapter(this,
                R.layout.rowchat, chats);

        list.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
        btnsent.setOnClickListener(this);
        list.setSelection(chatAdapter.getCount() - 1);
        list.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        list.setStackFromBottom(true);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                RoomChat roomChat = chats.get(position);
                String masonguoigui = roomChat.getIdsender();
                String thoigiangui = roomChat.getThoigian();
                PopupMenu popupMenu = new PopupMenu(ChatRoom.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_event_student,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete:
                                Dialog dialog1 = new Dialog(ChatRoom.this);
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
                                        int lever = db.getLeverAccount(username);
                                        if(lever == 2 || username.equals(masonguoigui)){ // TH giáo viên hoặc người gửi đoạn chat
                                            db.DeleteChat(idclass,masonguoigui,thoigiangui);
                                            // TOAST BLUE
                                            //Toast.makeText(ChatRoom.this,idclass+"\n"+masonguoigui+"\n"+thoigiangui,Toast.LENGTH_SHORT).show();
                                            LayoutInflater inflater = getLayoutInflater();
                                            View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                                            TextView toastText = layout.findViewById(R.id.toasttext);
                                            ImageView imageView = layout.findViewById(R.id.imageview);
                                            toastText.setText("Xóa thành công");
                                            imageView.setImageResource(R.drawable.ic_notifycation);
                                            Toast toast = new Toast(getApplicationContext());
                                            toast.setGravity(Gravity.CENTER,0,0);
                                            toast.setDuration(Toast.LENGTH_SHORT);
                                            toast.setView(layout);
                                            toast.show();
                                            //END
                                           chatAdapter.remove(roomChat);
                                          chatAdapter.notifyDataSetChanged();
                                        }
                                        else{
                                            // TOAST BLUE
                                            LayoutInflater inflater = getLayoutInflater();
                                            View layout = inflater.inflate(R.layout.toast_custom_red,(ViewGroup) findViewById(R.id.toast));
                                            TextView toastText = layout.findViewById(R.id.toasttext);
                                            ImageView imageView = layout.findViewById(R.id.imageview);
                                            toastText.setText("Bạn không có quyền được xóa đoạn chat này");
                                            imageView.setImageResource(R.drawable.ic_sad);
                                            Toast toast = new Toast(getApplicationContext());
                                            toast.setGravity(Gravity.CENTER,0,0);
                                            toast.setDuration(Toast.LENGTH_SHORT);
                                            toast.setView(layout);
                                            toast.show();
                                            //END
                                        }
                                        dialog1.dismiss();
                                    }
                                });
                                //
                                dialog1.show();
                                //////////////////

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

    private void getdatachat() {
        chats = db.contentChats(idclass);
    }

    @Override
    public void onClick(View v) {
        String noidung = content.getText().toString();
        if(noidung.equals("")){
            // CUSTOM TOAST RED
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_custom_red,(ViewGroup) findViewById(R.id.toast));
            TextView toastText = layout.findViewById(R.id.toasttext);
            ImageView imageView = layout.findViewById(R.id.imageview);
            toastText.setText("Vui lòng nhập nội dung muốn gửi");
            imageView.setImageResource(R.drawable.ic_sad);
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER,0,0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
            /// END TOAST
            //Toast.makeText(ChatRoom.this,"vui lòng nhập nội dung muốn gửi",Toast.LENGTH_SHORT).show();
        }
        else{
            boolean check = db.insertContentChatroom(idclass,username,noidung);
            //ngayhientai
            String pattern = "MM/dd/yyyy HH:mm:ss";
            DateFormat df = new SimpleDateFormat(pattern);
            Date today = Calendar.getInstance().getTime();
            String todayAsString = df.format(today);
            int lv = db.getLeverAccount(username);
            if(lv == 1){
                chats.add(new RoomChat(idclass,db.getNameStudent(username),noidung,1,todayAsString,username));
            }
            else{
                chats.add(new RoomChat(idclass,db.getNameTeacher(username),noidung,1,todayAsString,username));
            }
            list.setSelection(chatAdapter.getCount() -1);
            list.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
            list.setStackFromBottom(true);
            if(check == false){
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_custom_red,(ViewGroup) findViewById(R.id.toast));
                TextView toastText = layout.findViewById(R.id.toasttext);
                ImageView imageView = layout.findViewById(R.id.imageview);
                toastText.setText("Gửi thất bại");
                imageView.setImageResource(R.drawable.ic_sad);
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER,0,0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
                /// END TOAST
            }
            else{
                content.setText("");
                // TOAST BLUE
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_custom_blue,(ViewGroup) findViewById(R.id.toast));
                TextView toastText = layout.findViewById(R.id.toasttext);
                ImageView imageView = layout.findViewById(R.id.imageview);
                toastText.setText("Gửi thành công");
                imageView.setImageResource(R.drawable.ic_notifycation);
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER,0,0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
                //END
                //Toast.makeText(ChatRoom.this,"Gửi thành công",Toast.LENGTH_SHORT).show();

            }
            chatAdapter.notifyDataSetChanged();
        }
    }
}