package Teachers;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyhoctap.R;

import java.util.List;

import Database.Database;

public class TuDiemDanhAdapter extends ArrayAdapter<TuDiemDanhJava> {

    private Context context;
    private int layoutToBeInflated;
    private List<TuDiemDanhJava> dshocsinhdediemdanh;
    Database db ;

    public TuDiemDanhAdapter(@NonNull Context context, int resource, @NonNull List<TuDiemDanhJava> dshocsinhdediemdanh) {
        super(context, resource, dshocsinhdediemdanh);
        this.dshocsinhdediemdanh = dshocsinhdediemdanh;
        this.layoutToBeInflated = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TuDiemDanhHolder holder;
        View row = convertView;

        if(row == null){
            LayoutInflater inflater =((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutToBeInflated, null);
            holder = new TuDiemDanhAdapter.TuDiemDanhHolder();
            holder.idstudent = row.findViewById(R.id.idstudent);
            holder.namestudent = row.findViewById(R.id.namestudent);
     //       holder.checkbox = row.findViewById(R.id.diemdanh);
            row.setTag(holder);
        }
        else{
            holder =(TuDiemDanhHolder) row.getTag();
        }
        TuDiemDanhJava tuDiemDanhJava = dshocsinhdediemdanh.get(position);
        holder.namestudent.setText("Tên : " + tuDiemDanhJava.getNamestudent());
        holder.idstudent.setText("ID : " + tuDiemDanhJava.getIdstudent());
      //  holder.checkbox.setChecked(false);
        return row;
    }

    private class TuDiemDanhHolder {
        TextView namestudent, idstudent;
        //CheckBox checkbox;
    }
}
