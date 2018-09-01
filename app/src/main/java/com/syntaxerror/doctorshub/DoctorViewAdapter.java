package com.syntaxerror.doctorshub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DoctorViewAdapter extends ArrayAdapter<Doctor> {

    private Context mContext;
    private List<Doctor> doctorList;
    private int mResource;

    public DoctorViewAdapter(@NonNull Context context, int resource, @NonNull List<Doctor> objects) {

        super(context, resource, objects);

        mContext = context;
        mResource = resource;
        doctorList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        convertView = inflater.inflate(mResource, parent, false);

        TextView nameView = convertView.findViewById(R.id.nameView);
        TextView numberView = convertView.findViewById(R.id.numberView);
        TextView detailsView = convertView.findViewById(R.id.detailsView);

        nameView.setText(doctorList.get(position).getDoctorName());
        numberView.setText(doctorList.get(position).getDoctorNumber());
        detailsView.setText(doctorList.get(position).getDoctorDetails());

        return convertView;
    }
}
