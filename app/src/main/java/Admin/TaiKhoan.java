package Admin;

public class TaiKhoan {
    private String Maso ;
    private String ChucVu;

    public TaiKhoan(String maso, String chucVu) {
        Maso = maso;
        ChucVu = chucVu;
    }

    public String getMaso() {
        return Maso;
    }

    public void setMaso(String maso) {
        Maso = maso;
    }

    public String getChucVu() {
        return ChucVu;
    }

    public void setChucVu(String chucVu) {
        ChucVu = chucVu;
    }
}
