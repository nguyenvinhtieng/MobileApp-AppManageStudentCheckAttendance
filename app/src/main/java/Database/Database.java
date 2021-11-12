package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.quanlyhoctap.RoomChat;
import com.example.quanlyhoctap.Subject;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Admin.TaiKhoan;
import Teachers.StudentInSubject;
import Teachers.TuDiemDanhJava;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_NONE;

public class Database extends SQLiteOpenHelper {


    public Database(@Nullable Context context) {
        super(context, "QuanLyHocSinh.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Account (UserName TEXT PRIMARY KEY, PassWord TEXT, Lever INTEGER)");
        db.execSQL("CREATE TABLE InforStudents (UserName TEXT PRIMARY KEY, Name TEXT, Class TEXT)");
        db.execSQL("CREATE TABLE InforTeachers (UserName TEXT PRIMARY KEY, Name TEXT)");
        db.execSQL("CREATE TABLE List_Subject (Id TEXT PRIMARY KEY UNIQUE, Name TEXT, Note TEXT, UserCreate TEXT,Total INTEGER,  NumStudied INTEGER)");
        db.execSQL("CREATE TABLE Subject (Id TEXT, UserName TEXT)");
        db.execSQL("CREATE TABLE CheckStatusStudentInClass (IdClass TEXT , IdStudent TEXT, Total INTEGER, NumAbsent INTEGER)");
        db.execSQL("CREATE TABLE ChatRoom (IdRoom TEXT , IdSender TEXT, Content TEXT,Time int,ThoiGian TEXT)");
        db.execSQL("CREATE TABLE DiemDanh (IdClass TEXT , Code TEXT)");
        db.execSQL("CREATE TABLE CheckDiemDanh (IdClass TEXT , IdStudent TEXT, HasDiemDanh INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Account");
        db.execSQL("DROP TABLE IF EXISTS InforStudents");
        db.execSQL("DROP TABLE IF EXISTS InforTeachers");
        db.execSQL("DROP TABLE IF EXISTS List_Subject");
        db.execSQL("DROP TABLE IF EXISTS Subject");
        db.execSQL("DROP TABLE IF EXISTS CheckStatusStudentInClass");
        db.execSQL("DROP TABLE IF EXISTS ChatRoom");
        db.execSQL("DROP TABLE IF EXISTS DiemDanh");
        db.execSQL("DROP TABLE IF EXISTS CheckDiemDanh");
        //this.onCreate(db);
    }
    //-----------------------------------------------------------------------------------------------------
    ///// DATABASE ACCOUNT + INSERT INFOR------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    //insert
    public boolean insertDataAccountAdmin(String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        //chen vao bang Account
        ContentValues values = new ContentValues();
        values.put("UserName",username);
        values.put("PassWord",password);
        values.put("Lever",3);
        long ins = db.insert("Account",null,values);
        if(ins == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean insertDataAccountHS(String username,String password){
         SQLiteDatabase db = this.getWritableDatabase();
         //chen vao bang Account
         ContentValues values = new ContentValues();
         values.put("UserName",username);
         values.put("PassWord",password);
         values.put("Lever",1);
         long ins = db.insert("Account",null,values);
         //chen vao bang InforStudents
        ContentValues valuesInfor = new ContentValues();
        valuesInfor.put("UserName",username);
        valuesInfor.put("Name","");
        valuesInfor.put("Class","");
        db.insert("InforStudents",null,valuesInfor);
         if(ins == -1){
             return false;
         }
         else{
             return true;
         }
    }
    public boolean insertDataAccountGV(String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        //chen vao bang Account
        ContentValues values = new ContentValues();
        values.put("UserName",username);
        values.put("PassWord",password);
        values.put("Lever",2);
        long ins = db.insert("Account",null,values);
        //chen vao bang InforTeachers
        ContentValues valuesInfor = new ContentValues();
        valuesInfor.put("UserName",username);
        valuesInfor.put("Name","");
        db.insert("InforTeachers",null,valuesInfor);
        if(ins == -1){
            return false;
        }
        else{
            return true;
        }
    }
    //get lever
    public int getLeverAccount(String username){
        int lv = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from Account", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String us = cursor.getString(0);
            if( username.equals(us)){
                return  cursor.getInt(2);
            }
            // Đến dòng tiếp theo
            cursor.moveToNext();
        }
        cursor.close();
        return lv;
    }
    // check username
    public boolean checkUsername(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Account where UserName=?",new String[]{username});
        if(cursor.getCount() > 0) {
            db.close();
            return false;
        }
        else {
            db.close();
            return true;
        }
    }
    //update pass
    public void updatePasswordAccount(String username,String opassword,String npassword){
        SQLiteDatabase db = this.getWritableDatabase();
        int lv = checkAccount(username,opassword);
        ContentValues values = new ContentValues();
        values.put("UserName",username);
        values.put("PassWord",npassword);
        values.put("Lever",lv);
        db.update("Account",values,"UserName = ? and PassWord=?",new String[]{username,opassword});
        db.close();
    }
    //check account
    public int checkAccount(String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from Account", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            //Đọc dữ liệu dòng hiện tại
            String User = cursor.getString(0);
            String Pass = cursor.getString(1);
            int Lv = cursor.getInt(2);

            if(User.equals(username) && Pass.equals(password)){
                return Lv;
            }
            // Đến dòng tiếp theo
            cursor.moveToNext();
        }

        cursor.close();
        return 0;
    }
    //XÓa tài khoản
    public void deleteAccount(String idaccount){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("CheckStatusStudentInClass", "IdStudent = ? ", new String[]{idaccount});
        db.delete("Subject", "UserName = ? ", new String[]{idaccount});
        db.delete("List_Subject", "UserCreate = ? ", new String[]{idaccount});
        db.delete("InforTeachers", "UserName = ? ", new String[]{idaccount});
        db.delete("InforStudents", "UserName = ? ", new String[]{idaccount});
        db.delete("Account", "UserName = ? ", new String[]{idaccount});
        db.close();
    }
    // trả về danh sách tài khoản
    public List<TaiKhoan> getAccountlist(){

        List<TaiKhoan> taikhoans = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from Account", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Đọc dữ liệu dòng hiện tại
            String maso = cursor.getString(0);
            int chucvuint = cursor.getInt(2);
            if(chucvuint == 3){
                cursor.moveToNext();
            }
            else{
                String chucvu = "";
                if(chucvuint == 1){
                    chucvu = "Học Sinh";
                }
                else if(chucvuint == 2){
                    chucvu = "Giáo Viên";
                }
                else{
                    chucvu = "Admin";
                }

                taikhoans.add(new TaiKhoan(maso,chucvu));
                // Đến dòng tiếp theo
                cursor.moveToNext();
            }
        }
        cursor.close();
        return taikhoans;
    }
    //-----------------------------------------------------------------------------------------------------
    ///// DATABASE STUDENT -----------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    //Laays thong tin infor
    public String getNameStudent(String username){
        String name ="";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from InforStudents", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String User = cursor.getString(0);
            String nameStudent = cursor.getString(1);
            if(User.equals(username) ){
                return nameStudent;
            }
            // Đến dòng tiếp theo
            cursor.moveToNext();
        }
        cursor.close();
        return name;
    }
    //get class
    public String getClassStudent(String username){
        String classname ="";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from InforStudents", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String User = cursor.getString(0);
            String classStudent = cursor.getString(2);
            if(User.equals(username) ){
                return classStudent;
            }
            // Đến dòng tiếp theo
            cursor.moveToNext();
        }
        cursor.close();
        return classname;
    }
    //update
    public void updateInforStudent(String username,String name,String classname){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("UserName",username);
        values.put("Name",name);
        values.put("Class",classname);
        db.update("InforStudents",values,"UserName = ?",new String[]{username});
        db.close();
    }
    //-----------------------------------------------------------------------------------------------------
    ///// DATABASE TEACHER -----------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    public String getNameTeacher(String username){
        String name ="";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from InforTeachers", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String User = cursor.getString(0);
            String nameTeacher = cursor.getString(1);
            if(User.equals(username) ){
                return nameTeacher;
            }
            // Đến dòng tiếp theo
            cursor.moveToNext();
        }
        cursor.close();
        return name;
    }
    //update
    public void updateInforTeacher(String username,String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("UserName",username);
        values.put("Name",name);
        db.update("InforTeachers",values,"UserName = ?",new String[]{username});
        db.close();
    }
    //-----------------------------------------------------------------------------------------------------
    ///// LIST_SUBJECT ------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    //insert
    public boolean insertClass(String id,String name, String note, String usercreate, int total){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valuesInforClass = new ContentValues();
        valuesInforClass.put("Id",id);
        valuesInforClass.put("Name",name);
        valuesInforClass.put("Note",note);
        valuesInforClass.put("Total",total);
        valuesInforClass.put("UserCreate",usercreate);
        valuesInforClass.put("NumStudied",0);
        long ins = db.insert("List_Subject",null,valuesInforClass);
        if(ins == -1){
            return false;
        }
        else{
            return true;
        }
    }
    //tra ve danh sach mon hoc cua giao vien
    public List<Subject> ListSubjectTeacher(String username){

        List<Subject> subjects = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from List_Subject", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Đọc dữ liệu dòng hiện tại
            String User = cursor.getString(3);

            if(User.equals(username)){
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String note = cursor.getString(2);
                String ten = getNameTeacher(User);
                subjects.add(new Subject(id,name,note,ten));
            }
            // Đến dòng tiếp theo
            cursor.moveToNext();
        }
        cursor.close();
        return subjects;
    }
    //tra ve danh sach mon hoc cua hoc sinh
    public List<Subject> ListSubjectStudent(String username){

        List<Subject> subjectsstd = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from Subject", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Đọc dữ liệu dòng hiện tại
            String idclass = cursor.getString(0);
            String usernamestd = cursor.getString(1);
            if(username.equals(usernamestd)){
                String name = getnamesubject(idclass);
                String note = getnotesubject(idclass);
                String ten = getUCsubject(idclass);
                subjectsstd.add(new Subject(idclass,name,note,getNameTeacher(ten)));
            }
            // Đến dòng tiếp theo
            cursor.moveToNext();
        }
        cursor.close();
        return subjectsstd;
    }
    //tra ve id mon học
    public List<String> getlistIdsubject(String username){
        List<String> ids = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from List_Subject", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Đọc dữ liệu dòng hiện tại
            String User = cursor.getString(3);
            if(User.equals(username)){
                String id = cursor.getString(0);
                ids.add(id);
            }
            // Đến dòng tiếp theo
            cursor.moveToNext();
        }
        cursor.close();
        return ids;
    }
    //get ten môn
    public String getnamesubject(String id){
        String Name = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from List_Subject", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Đọc dữ liệu dòng hiện tại
            String Id = cursor.getString(0);
            if(id.equals(Id)){
                Name = cursor.getString(1);
                return Name;
            }
            // Đến dòng tiếp theo
            cursor.moveToNext();
        }
        cursor.close();
        return Name;
    }
    //get chu thich
    public String getnotesubject(String id){
        String Note = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from List_Subject", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Đọc dữ liệu dòng hiện tại
            String Id = cursor.getString(0);
            if(id.equals(Id)){
                Note = cursor.getString(2);
                return Note;
            }
            // Đến dòng tiếp theo
            cursor.moveToNext();
        }
        cursor.close();
        return Note;
    }
    //get tong so buoi
    public int gettotalsubject(String id){
        int Note = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from List_Subject", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Đọc dữ liệu dòng hiện tại
            String Id = cursor.getString(0);
            if(id.equals(Id)){
                Note = cursor.getInt(4);
                return Note;
            }
            // Đến dòng tiếp theo
            cursor.moveToNext();
        }
        cursor.close();
        return Note;
    }
    public String getUCsubject(String id){
        String UC = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from List_Subject", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Đọc dữ liệu dòng hiện tại
            String Id = cursor.getString(0);
            if(id.equals(Id)){
                UC = cursor.getString(3);
                return UC;
            }
            // Đến dòng tiếp theo
            cursor.moveToNext();
        }
        cursor.close();
        return UC;
    }
    public void updateInforSubject(String id,String name, String note, int total){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Id",id);
        values.put("Name",name);
        values.put("Note",note);
        values.put("UserCreate",getUCsubject(id));
        values.put("Total",total);
        values.put("NumStudied",0);
        db.update("List_Subject",values,"Id = ? ",new String[]{id});
        ////
        Cursor cursor = db.rawQuery("SELECT * from CheckStatusStudentInClass", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String IdOfClass = cursor.getString(0);
            if(id.equals(IdOfClass)){
                String IdOfStudent = cursor.getString(1);
                ContentValues values1 = new ContentValues();
                values1.put("IdClass",id);
                values1.put("IdStudent",IdOfStudent);
                values1.put("Total",total);
                values1.put("NumAbsent",getNumabsent(IdOfStudent,id));
                db.update("CheckStatusStudentInClass",values1,"IdClass = ? and IdStudent= ?",new String[]{id,IdOfStudent});
            }
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
    }

    //-----------------------------------------------------------------------------------------------------
    ///// DATABASE     SUBJECT ------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    //insert
    public boolean insertStudentClass(String id,String username){
        SQLiteDatabase db = this.getWritableDatabase();
        //Insert vaof bang Subject
        ContentValues values = new ContentValues();
        values.put("Id",id);
        values.put("UserName",username);
        long ins = db.insert("Subject",null,values);

        //Insert vao bang CheckStatusSVInClass
        ContentValues valuestatus = new ContentValues();
        valuestatus.put("IdStudent",username);
        valuestatus.put("IdClass",id);
        valuestatus.put("Total",gettotalsubject(id));
        valuestatus.put("NumAbsent",0);
        db.insert("CheckStatusStudentInClass",null,valuestatus);
        if(ins == -1 ){
            return false;
        }
        else{
            return true;
        }

    }
    //check có hs trong lớp này chưa
    public boolean checkstudentinclass(String id,String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Subject where Id=? and UserName = ?",new String[]{id,username});
        if(cursor.getCount() > 0) {
            db.close();
            return true;
        }
        else {
            db.close();
            return false;
        }
    }
    // tra ve danh sach sinh vien trong lớp
    public List<StudentInSubject> ListStudentInClass(String idclass){

        List<StudentInSubject> students = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from CheckStatusStudentInClass", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Đọc dữ liệu dòng hiện tại
            String IdOfClass = cursor.getString(0);

            if(idclass.equals(IdOfClass)){
                String id_student = cursor.getString(1);
                String nametsudent = getNameStudent(id_student);
                int Total = cursor.getInt(2);
                int absent = cursor.getInt(3);
                boolean ban = false;
                if((double)(absent *1.0 / Total ) > 0.2 ){
                    ban = true;
                }
                else{
                    ban = false;
                }
                students.add(new StudentInSubject(idclass,id_student,absent,Total,ban,nametsudent));
            }
            // Đến dòng tiếp theo
            cursor.moveToNext();
        }
        cursor.close();
        return students;
    }
    // Trả về số buổi đã học của lớp
    public int getNumStudied(String idclass){
        int num = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from List_Subject where Id =?", new String[]{idclass});
        cursor.moveToFirst();
        num = cursor.getInt(5);
        cursor.close();
        db.close();
        return num;
    }
    //trả về danh sách bị cấm thi
    public List<StudentInSubject> ListStudentInClassBan(String idclass){

        List<StudentInSubject> students = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from CheckStatusStudentInClass", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Đọc dữ liệu dòng hiện tại
            String IdOfClass = cursor.getString(0);

            if(idclass.equals(IdOfClass)){
                String id_student = cursor.getString(1);
                String nametsudent = getNameStudent(id_student);
                int Total = cursor.getInt(2);
                int absent = cursor.getInt(3);
                boolean ban = false;
                if((double)(absent *1.0 / Total ) > 0.2 ){
                    ban = true;
                }
                else{
                    ban = false;
                }
                if(ban == true){
                    students.add(new StudentInSubject(idclass,id_student,absent,Total,ban,nametsudent));
                }
            }
            // Đến dòng tiếp theo
            cursor.moveToNext();
        }
        cursor.close();
        return students;
    }
    //trả về danh sách bị cấm thi chỉ lấy id hoc sinh
    public List<String> ListStudentInClassBanId(String idclass){
        List<String> students = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from CheckStatusStudentInClass", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Đọc dữ liệu dòng hiện tại
            String IdOfClass = cursor.getString(0);

            if(idclass.equals(IdOfClass)){
                String id_student = cursor.getString(1);
                String nametsudent = getNameStudent(id_student);
                int Total = cursor.getInt(2);
                int absent = cursor.getInt(3);
                boolean ban = false;
                if((double)(absent *1.0 / Total ) > 0.2 ){
                    ban = true;
                }
                else{
                    ban = false;
                }
                if(ban == true){
                    students.add(id_student);
                }
            }
            // Đến dòng tiếp theo
            cursor.moveToNext();
        }
        cursor.close();
        return students;
    }
    // XÓA HỌC SINH KHỎI LỚP HỌC => Xóa trong bảng Count luôn
    public void deleteStudentClass(String idclass, String idstd){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Subject", "Id = ? and UserName = ?", new String[]{idclass,idstd});
        db.delete("CheckStatusStudentInClass", "IdClass = ? and IdStudent = ?", new String[]{idclass,idstd});
        db.close();
    }
    // XÓA LỚP HỌC
    public void deleteClass(String idclass){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("ChatRoom", "IdRoom = ? ", new String[]{idclass});
        db.delete("CheckStatusStudentInClass", "IdClass = ?", new String[]{idclass});
        db.delete("Subject", "Id = ?", new String[]{idclass});
        db.delete("List_Subject", "Id = ?", new String[]{idclass});
        db.close();
    }
    ////////////////////////////////////////////
    ////////////////COUNT////////////////////////
    public int getNumabsent(String idstudent, String idclass){
        int absent = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from CheckStatusStudentInClass", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Đọc dữ liệu dòng hiện tại
            String IdOfClass = cursor.getString(0);
            String IdOfStudent = cursor.getString(1);
            if(idclass.equals(IdOfClass) && idstudent.equals(IdOfStudent)){
                absent = cursor.getInt(3);
            }
            // Đến dòng tiếp theo
            cursor.moveToNext();
        }
        cursor.close();

        return absent;
    }
    public int getNumstudied(String idclass){
        int studied = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from list_subject", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Đọc dữ liệu dòng hiện tại
            String IdOfClass = cursor.getString(0);
            if(idclass.equals(IdOfClass)){
                studied = cursor.getInt(5);
                cursor.close();
                return studied;
            }
            // Đến dòng tiếp theo
            cursor.moveToNext();
        }
        cursor.close();

        return studied;
    }
        ////////////////////////////////////////////
    ////////////////CHAT ROOM////////////////////////
    // tra ve noi dung cua phong chat
        public List<RoomChat> contentChats(String idclass){

            List<RoomChat> chats = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * from ChatRoom ", null);
//ORDER BY Time DESC
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                //Đọc dữ liệu dòng hiện tại
                String IdOfClass = cursor.getString(0);

                if(idclass.equals(IdOfClass)){
                    String sender = cursor.getString(1);
                    String namesender = "";
                    String thoigian = cursor.getString(4);
                    int lvsender = getLeverAccount(sender);
                    if(lvsender == 1){
                        namesender = getNameStudent(sender);
                    }
                    else{
                        namesender = getNameTeacher(sender);
                    }
                    String content = cursor.getString(2);
                    int time = getmaxtime() + 1;
                    chats.add(new RoomChat(IdOfClass,namesender,content,time,thoigian,sender));
                }
                // Đến dòng tiếp theo
                cursor.moveToNext();
            }
            cursor.close();
            return chats;
        }
        //lay max time

    public int getmaxtime(){
        int time = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from ChatRoom ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            time = cursor.getInt(3);
            cursor.moveToNext();
        }
        cursor.close();
        return time;
    }
        //insert content chatroom
    public boolean insertContentChatroom(String idclass,String idsender,String content){
        SQLiteDatabase db = this.getWritableDatabase();
        //chen vao bang ChatRoom
        //lay ngay hien tai chuyen thanh string
        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);

        int time = 0;
        time = getmaxtime() + 1;
        ContentValues values = new ContentValues();
        values.put("IdRoom",idclass);
        values.put("IdSender",idsender);
        values.put("Content",content);
        values.put("Time",time);
        values.put("ThoiGian",todayAsString);
        long ins = db.insert("ChatRoom",null,values);

        if(ins == -1){
            return false;
        }
        else{
            return true;
        }
    }

    ////// XUAT RA DANH SACH CAM THI
    public String exportDSCamThi(String idclass){
        ///// DỌC RA DANH SACH CAM THI THANH CHUỖI GÁN VÀO BIẾN danhsachcamthi
        SQLiteDatabase db = this.getWritableDatabase();
        String danhsachcamthi = "Danh sách cấm thi môn " + getnamesubject(idclass) + "\n";
        int i = 1;
        Cursor cursor = db.rawQuery("SELECT * from CheckStatusStudentInClass", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String IdOfClass = cursor.getString(0);
            if(idclass.equals(IdOfClass)){
                String id_student = cursor.getString(1);
                String nametsudent = getNameStudent(id_student);
                int Total = cursor.getInt(2);
                int absent = cursor.getInt(3);
                boolean ban = false;
                if((double)(absent *1.0 / Total ) > 0.2 ){
                    ban = true;
                }
                else{
                    ban = false;
                }

                if(ban == true){
                    danhsachcamthi = danhsachcamthi + String.valueOf(i) + "    Tên : " + nametsudent + "\n\t ID : "+ id_student +"\n\t Lớp : "+getClassStudent(id_student)+"\n";
                    i = i + 1;
                }
            }
            // Đến dòng tiếp theo
            cursor.moveToNext();
        }
        cursor.close();
        return danhsachcamthi;
        // HOÀN TẤT ĐỌC RA DANH SÁCH
    }

    ////////////////////////////////////////////////
    //CHECK XEM CÓ NÊN HIỂN THỊ THÔNG BÁO KHÔNG
    public boolean checkHasNotify(String idstudent){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from CheckStatusStudentInClass", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String IdOfStudent = cursor.getString(1);
            if(idstudent.equals(IdOfStudent)){
                int Total = cursor.getInt(2);
                int absent = cursor.getInt(3);
                boolean hasnotify = false;
                if((double)(absent *1.0 / Total ) >= 0.2 ){
                    hasnotify = true;
                }
                else{
                    hasnotify = false;
                }

                if(hasnotify == true){
                    return true;
                }
            }
            // Đến dòng tiếp theo
            cursor.moveToNext();
        }
        cursor.close();
        return false;
    }
    public String contentNotify(String idstudent){
        String mess = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from CheckStatusStudentInClass", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String IdOfStudent = cursor.getString(1);
            if(idstudent.equals(IdOfStudent)){
                String idOfClass = cursor.getString(0);
                int Total = cursor.getInt(2);
                int absent = cursor.getInt(3);
                boolean hasnotify = false;
                if((double)(absent *1.0 / Total ) > 0.2 ){
                    mess = mess + "- Bạn bị cấm thi môn " + getnamesubject(idOfClass) + " Giáo Viên :" + getNameTeacher(getUCsubject(idOfClass))+"\n";
                }
                else if((double)(absent *1.0 / Total ) == 0.2 ){
                    mess = mess + "- Bạn sắp bị cấm thi môn " + getnamesubject(idOfClass) + "\n";
                }
                else{
                    mess = mess;
                }
            }
            cursor.moveToNext();
        }
        cursor.close();
        return mess;
    }

    ////// DIỂM DANH HỌC SINH
    // Hiển thị danh sách để điểm danh
    public List<TuDiemDanhJava> DSHocSinhDiemDanh(String idclass){

        List<TuDiemDanhJava> students = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from Subject", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Đọc dữ liệu dòng hiện tại
            String IdOfClass = cursor.getString(0);
            if(idclass.equals(IdOfClass)){
                String id_student = cursor.getString(1);

                students.add(new TuDiemDanhJava(idclass,id_student,getNameStudent(id_student),false,getNumStudied(idclass)));
            }
            // Đến dòng tiếp theo
            cursor.moveToNext();
        }
        cursor.close();
        return students;
    }
    // Hiển thị danh sách để điểm danh
    public List<String> DSHocSinhDiemDanhString(String idclass){

        List<String> students = new ArrayList<>();
        int i = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from Subject", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Đọc dữ liệu dòng hiện tại
            String IdOfClass = cursor.getString(0);
            if(idclass.equals(IdOfClass)){
                String id_student = cursor.getString(1);
                String NS = getNameStudent(id_student);
                students.add("Tên : " + NS +"\nID : " + id_student);
            }
            // Đến dòng tiếp theo
            cursor.moveToNext();
        }
        cursor.close();
        return students;
    }
    //diem danh
    public void diemdanh(String idclass, List<String> danhsachhocsinhvang){
        SQLiteDatabase db = this.getWritableDatabase();
        for(String idstudent : danhsachhocsinhvang){
            int k = getNumabsent(idstudent,idclass) + 1;
            ContentValues values = new ContentValues();
            values.put("IdClass",idclass);
            values.put("IdStudent",idstudent);
            values.put("Total",gettotalsubject(idclass));
            values.put("NumAbsent",k);
            db.update("CheckStatusStudentInClass",values,"IDClass = ? and IdStudent = ?",new String[]{idclass,idstudent});
        }
        db.close();
        enddiemdanh(idclass);
    }

    //-----------------------------------------------------------------------------------------------------
    ///// DIEM DANH ------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------
    public String checkCode(String idclass){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from DiemDanh", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String IdOfClass = cursor.getString(0);
            if(IdOfClass.equals(idclass)){
                String codeClass = cursor.getString(1);
                cursor.close();
                return codeClass;
            }
            cursor.moveToNext();
        }

        cursor.close();
        return "";
    }
    public void createCode(String idclass, String code){
        SQLiteDatabase db = this.getWritableDatabase();

        String hasCode = checkCode(idclass);
        if(hasCode.equals("")){ //LỚP CHƯA TẠO CODE
            // TẠO CODE
            ContentValues values = new ContentValues();
            values.put("IdClass",idclass);
            values.put("Code",code);
            long ins = db.insert("DiemDanh",null,values);
            // ĐÁNH TẤT CẢ LÀ VẮNG
            Cursor cursor = db.rawQuery("SELECT * from CheckStatusStudentInClass", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String IdOfClass = cursor.getString(0);
                if(idclass.equals(IdOfClass)){
                    String IdOfStudent = cursor.getString(1);
                    ContentValues values1 = new ContentValues();
                    values1.put("IdClass",idclass);
                    values1.put("IdStudent",IdOfStudent);
                    values1.put("Total",gettotalsubject(idclass));
                    values1.put("NumAbsent",getNumabsent(IdOfStudent,idclass) + 1);
                    db.update("CheckStatusStudentInClass",values1,"IdClass = ? and IdStudent= ?",new String[]{idclass,IdOfStudent});
                }
                cursor.moveToNext();
            }
            cursor.close();
            // THÊM VÀO BẢNG CHECK ĐỂ KIỂM TRA HỌC SINH
            Cursor cursorcheck = db.rawQuery("SELECT * from Subject", null);
            cursorcheck.moveToFirst();
            while (!cursorcheck.isAfterLast()) {
                String IdOfClass = cursorcheck.getString(0);
                if(IdOfClass.equals(idclass)){
                    String IdOfStudent = cursorcheck.getString(1);
                    ContentValues valuesInsert = new ContentValues();
                    valuesInsert.put("IdClass",idclass);
                    valuesInsert.put("IdStudent",IdOfStudent);
                    valuesInsert.put("HasDiemDanh",0);
                    db.insert("CheckDiemDanh",null,valuesInsert);
                }

                cursorcheck.moveToNext();
            }
            cursorcheck.close();

        }
        else{ //LỚP ĐÃ TẠO CODE ==> CẦN UPDATE CODE
            ContentValues valuesupdate = new ContentValues();
            valuesupdate.put("IdClass",idclass);
            valuesupdate.put("Code",code);
            db.update("DiemDanh",valuesupdate,"IdClass = ? ",new String[]{idclass});
        }
        db.close();
    }
    public void enddiemdanh(String idclass){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("DiemDanh", "IdClass = ? ", new String[]{idclass});
        db.delete("CheckDiemDanh", "IdClass = ? ", new String[]{idclass});
        db.close();
        // UPDATE
        int t = getNumStudied(idclass) + 1;
        int total = gettotalsubject(idclass);
        ContentValues updateclass = new ContentValues();
        String usercreate = getUCsubject(idclass);
        String nameclass = getnamesubject(idclass);
        String noteclass = getnotesubject(idclass);
        SQLiteDatabase data = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Id",idclass);
        values.put("Name",nameclass);
        values.put("Note",noteclass);
        values.put("UserCreate",getUCsubject(idclass));
        values.put("Total",total);
        values.put("NumStudied",t);
        data.update("List_Subject",values,"Id = ? ",new String[]{idclass});
        data.close();
    }

    // CHECK HAS DIEM DANH
    public boolean HasTODiemDanh(String idclass){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorcheck = db.rawQuery("SELECT * from CheckDiemDanh where IdClass=?", new String[]{idclass});
        cursorcheck.moveToFirst();
        while (!cursorcheck.isAfterLast()) {
            String IdOfClass = cursorcheck.getString(0);
            if(idclass.equals(IdOfClass)){
                return true;
            }
            cursorcheck.moveToNext();
        }
        db.close();
        return false;
    }
    //GET NUM DIEM DANH HSS
    public int getNumDiemDanh(String idclass,String idstudent){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorcheck = db.rawQuery("SELECT * from CheckDiemDanh Where IdClass = ? and IdStudent = ?", new String[]{idclass,idstudent});
        cursorcheck.moveToFirst();
        db.close();
        return cursorcheck.getInt(2);

    }
    public String getCodeDiemDanh(String idclass){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorcheck = db.rawQuery("SELECT * from DiemDanh Where IdClass = ?", new String[]{idclass});
        cursorcheck.moveToFirst();
        db.close();
        return cursorcheck.getString(1);
    }
    // TIEN HÀNH  DIEM DANH
    public String DDHocSinh(String idclass,String idstudent,String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        String result = "";
        Cursor cursorcheck = db.rawQuery("SELECT * from CheckDiemDanh where IdClass = ? and IdStudent = ?", new String[]{idclass, idstudent});
        cursorcheck.moveToFirst();
        String CODE_CLASS = getCodeDiemDanh(idclass);
        int num = cursorcheck.getInt(2);
        db.close();
        if (num > 3 && num < 9) {
            result = "Bạn đã điểm danh quá số lần quy định";
        } else if (num == 10) {
            result = "Bạn đã điểm danh thành công ca học này rồi";
        } else {
            if(code.equals(CODE_CLASS)){
                try{
                    result = newFunctionDD(idclass,idstudent,10);
                }
                catch (Exception e){
                    result = "Ok But ERRR DATABASE";
                }
            }
            else{
                try{
                    int t = (int) getNumDiemDanh(idclass,idstudent) + 1;
                    result = newFunctionDD(idclass,idstudent,t);
                }
                catch (Exception e){
                    result = "NOT CORRECT AND ERR DATABASE";
                }
            }

        }

        return result;
    }
    public String newFunctionDD(String idclass,String idstudent ,int t){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("IdClass",idclass);
        values.put("IdStudent",idstudent);
        values.put("HasDiemDanh",t);
        db.update("CheckDiemDanh",values,"IdClass = ? and IdStudent=?",new String[]{idclass,idstudent});
        // UPDATE NUMABSENT
        if(t == 10){
            ContentValues contentValues = new ContentValues();
            contentValues.put("IdClass",idclass);
            contentValues.put("IdStudent",idstudent);
            contentValues.put("Total",gettotalsubject(idclass));
            contentValues.put("NumAbsent",getNumabsent(idstudent,idclass) - 1);
            db.update("CheckStatusStudentInClass",contentValues,"IdClass = ? and IdStudent=?",new String[]{idclass,idstudent});
            db.close();
            return "OK, Điểm danh thành công";
        }
        db.close();
        return "Sai Mã Rồi !\nBạn còn " + String.valueOf(4-t) + " lần nhập";
    }

    public void DeleteChat(String idclass, String sender, String thoigian){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("ChatRoom", "IdRoom=? and IdSender=? and ThoiGian = ?", new String[]{idclass,sender,thoigian});
        db.close();
    }

}
///values.put("IdClass",idclass);
//        values.put("IdStudent",idstudent);
//        values.put("Total",gettotalsubject(idclass));
//        values.put("NumAbsent",getNumabsent(idstudent,idclass) - 1);
//        db.update("Account",values,"IdClass = ? and IdStudent=?",new String[]{idclass,idstudent});
//        db.close();
//        return "OK";


///SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("Id",id);
//        values.put("Name",name);
//        values.put("Note",note);
//        values.put("UserCreate",getUCsubject(id));
//        values.put("Total",total);
//        values.put("NumStudied",0);
//        db.update("List_Subject",values,"Id = ? ",new String[]{id});

/////        db.execSQL("CREATE TABLE CheckStatusStudentInClass (IdClass TEXT , IdStudent TEXT, Total INTEGER, NumAbsent INTEGER)");
//        db.execSQL("CREATE TABLE ChatRoom (IdRoom TEXT , IdSender TEXT, Content TEXT,Time int,ThoiGian TEXT)");
//        db.execSQL("CREATE TABLE DiemDanh (IdClass TEXT , Code TEXT)");
//        db.execSQL("CREATE TABLE CheckDiemDanh (IdClass TEXT , IdStudent TEXT, HasDiemDanh INTEGER)");



