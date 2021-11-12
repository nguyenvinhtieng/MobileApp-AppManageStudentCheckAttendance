package Admin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlyhoctap.R;
import com.example.quanlyhoctap.RoomChat;
import com.example.quanlyhoctap.RoomChatAdapter;

import java.util.List;

public class TaiKhoanAdapter extends ArrayAdapter<TaiKhoan> {

    private Context context;
    private int layoutToBeInflated;
    private List<TaiKhoan> taikhoans;

    public TaiKhoanAdapter(@NonNull Context context, int resource, @NonNull List<TaiKhoan> taikhoans) {
        super(context, resource, taikhoans);

        this.taikhoans = taikhoans;
        this.layoutToBeInflated = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TaiKhoanViewHolder holder;
        View row = convertView;

        if(row == null){
            LayoutInflater inflater =((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutToBeInflated, null);
            holder = new TaiKhoanViewHolder();
            holder.maso = row.findViewById(R.id.maso);
            holder.chucvu = row.findViewById(R.id.chucvu);
            row.setTag(holder);
        }
        else{
            holder =(TaiKhoanViewHolder) row.getTag();
        }
        TaiKhoan taikhoan = taikhoans.get(position);
        holder.maso.setText("Mã số : " + taikhoan.getMaso());
        holder.chucvu.setText("Chức Vụ : " + taikhoan.getChucVu());

        // animation

        //Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale_list);
        //row.startAnimation(animation);
        return row;
    }


    private class TaiKhoanViewHolder{
        TextView maso,chucvu;
    }
}
