package Teachers;

public class TuDiemDanhJava {
    private String idclass;
    private String idstudent;
    private String namestudent;
    private boolean check;
    private int num;

    public TuDiemDanhJava(String idclass, String idstudent, String namestudent, boolean check, int num) {
        this.idclass = idclass;
        this.idstudent = idstudent;
        this.namestudent = namestudent;
        this.check = check;
        this.num = num;
    }

    public String getIdclass() {
        return idclass;
    }

    public void setIdclass(String idclass) {
        this.idclass = idclass;
    }

    public String getIdstudent() {
        return idstudent;
    }

    public void setIdstudent(String idstudent) {
        this.idstudent = idstudent;
    }

    public String getNamestudent() {
        return namestudent;
    }

    public void setNamestudent(String namestudent) {
        this.namestudent = namestudent;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
