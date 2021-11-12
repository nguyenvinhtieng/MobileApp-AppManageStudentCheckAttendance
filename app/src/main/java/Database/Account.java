package Database;

public class Account {
    private String Username;
    private String PassWord;
    private int Lever;

    public Account(){
        this.Username = "";
        this.PassWord = "";
        this.Lever = 0;
    }
    public Account(String username, String passWord, int lever) {
        this.Username = username;
        this.PassWord = passWord;
        this.Lever = lever;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public int getLever() {
        return Lever;
    }

    public void setLever(int lever) {
        Lever = lever;
    }
}
