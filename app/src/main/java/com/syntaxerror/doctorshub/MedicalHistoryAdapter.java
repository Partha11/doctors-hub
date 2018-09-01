package com.syntaxerror.doctorshub;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MedicalHistoryAdapter extends ArrayAdapter<MedicalHistory> {

    private Context mContext;
    private List<MedicalHistory> historyList;
    private int mResource;

    public MedicalHistoryAdapter(@NonNull Context context, int resource, @NonNull List<MedicalHistory> objects) {

        super(context, resource, objects);

        mContext = context;
        mResource = resource;
        historyList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        convertView = inflater.inflate(mResource, parent, false);

        ImageView imageView = convertView.findViewById(R.id.historyImageView);
        TextView nameView = convertView.findViewById(R.id.historyNameView);
        TextView dateView = convertView.findViewById(R.id.historyDateView);
        TextView detailsView = convertView.findViewById(R.id.historyDetailsView);

        Bitmap imageBitmap = decodeBase64(historyList.get(position).getPrescription());

        imageView.setImageBitmap(imageBitmap);
        nameView.setText(historyList.get(position).getDoctorName());
        dateView.setText(historyList.get(position).getAppointmentDate());
        detailsView.setText(historyList.get(position).getDoctorDetails());

        return convertView;
    }

    public static Bitmap decodeBase64(String input) {

        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
