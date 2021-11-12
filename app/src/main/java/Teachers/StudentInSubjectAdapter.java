package Teachers;

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

import java.util.List;

import Database.Database;

public class StudentInSubjectAdapter extends ArrayAdapter<StudentInSubject> {

    private Context context;
    private int layoutToBeInflated;
    private List<StudentInSubject> studentinsubject;
    Database db ;

    public StudentInSubjectAdapter(@NonNull Context context, int resource, @NonNull List<StudentInSubject> studentinsubject) {
        super(context, resource, studentinsubject);
        this.studentinsubject = studentinsubject;
        this.layoutToBeInflated = resource;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        StudentInSubjectViewHolder holder;
        View row = convertView;

        if(row == null){
            LayoutInflater inflater =((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutToBeInflated, null);
            holder = new StudentInSubjectAdapter.StudentInSubjectViewHolder();
            holder.idstudent = row.findViewById(R.id.idstudent);
            holder.numabsent = row.findViewById(R.id.numabsent);
            holder.camthi = row.findViewById(R.id.camthi);
            holder.namestudent = row.findViewById(R.id.namestudent);
            row.setTag(holder);
        }
        else{
            holder =(StudentInSubjectViewHolder) row.getTag();
        }

        StudentInSubject std = studentinsubject.get(position);
        holder.idstudent.setText("Mã số : " + std.getUserName());
        holder.numabsent.setText("Số buổi vắng : " + std.getNumabsent());
        holder.camthi.setText("");
        holder.namestudent.setText("Tên : " + std.getTen());
        boolean camthi = std.isBan();
        int absent = std.getNumabsent();
        int Total = std.getNumtotal();
        boolean ban = false;
        if((double)(absent *1.0 / Total ) > 0.2 ){
            holder.camthi.setText("X");
        }
        return row;
    }

    private class StudentInSubjectViewHolder {
        TextView idstudent,numabsent,camthi;
        TextView namestudent;
    }
}
