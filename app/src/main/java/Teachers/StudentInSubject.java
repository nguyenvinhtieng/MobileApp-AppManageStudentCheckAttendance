package Teachers;

public class StudentInSubject {
    private String id; //Id của lớp học
    private String UserName; //Mã số của sinh viên
    private int numabsent; //Số buổi vắng học
    private boolean isBan; //Có cấm thi hay không
    private int numtotal; //Số buổi của môn học
    private String ten; // ten cua hoc sinh

    public StudentInSubject(String id, String userName, int numabsent, int numtotal, boolean ban, String Ten) {
        this.id = id;
        UserName = userName;
        this.numabsent = numabsent;
        this.numtotal = numtotal;
        this.isBan = ban;
        this.ten = Ten;

    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public boolean isBan(){
        if((double)(this.numabsent *1.0 / this.numtotal ) > 0.2 ){
            this.isBan = true;
        }
        else{
            this.isBan = false;
        }
        return false;
    }
    public void plus1absent(){
        this.numabsent = this.numabsent + 1;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getNumabsent() {
        return numabsent;
    }

    public void setNumabsent(int numabsent) {
        this.numabsent = numabsent;
    }

    public void setBan(boolean ban) {
        isBan = ban;
    }

    public int getNumtotal() {
        return numtotal;
    }

    public void setNumtotal(int numtotal) {
        this.numtotal = numtotal;
    }
}
