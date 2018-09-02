package com.syntaxerror.doctorshub;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateDoctorFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private EditText updateName;
    private EditText updateDetails;
    private EditText updateNumber;
    private EditText updateEmail;
    private EditText updateAppointmentDate;

    private Button mUpdateButton;

    private Calendar calendar;

    private DatePickerDialog datePickerDialog;

    private Context mContext;

    private String updateCreationTime;

    private int doctorId;

    public UpdateDoctorFragment() {
        // Required empty public constructor
    }

    public static UpdateDoctorFragment newInstance(String param1, String param2) {
        UpdateDoctorFragment fragment = new UpdateDoctorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_update_doctor, container, false);

        updateName = view.findViewById(R.id.updateNameField);
        updateDetails = view.findViewById(R.id.updateDetailsField);
        updateAppointmentDate = view.findViewById(R.id.updateAppointmentField);
        updateNumber = view.findViewById(R.id.updateNumberField);
        updateEmail = view.findViewById(R.id.updateEmailField);

        mUpdateButton = view.findViewById(R.id.updateDoctor);

        mUpdateButton.setOnClickListener(this);
        updateAppointmentDate.setOnClickListener(this);

        Bundle bundle = getArguments();

        if (bundle != null) {

            doctorId = bundle.getInt("ID");

            updateName.setText(bundle.getString("Name"));
            updateDetails.setText(bundle.getString("Details"));
            updateAppointmentDate.setText(bundle.getString("Appointment"));
            updateNumber.setText(bundle.getString("Number"));
            updateEmail.setText(bundle.getString("Email"));
        }

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.updateDoctor:

                updateButtonClicked();
                break;

            case R.id.updateAppointmentField:

                appointmentDateSelection();
                break;
        }
    }

    private void appointmentDateSelection() {

        calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                updateCreationTime = day + "-" + (month + 1) + "-" + year;

                updateAppointmentDate.setText(updateCreationTime);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void updateButtonClicked() {

        String name = updateName.getText().toString().trim();
        String number = updateNumber.getText().toString().trim();
        String details = updateDetails.getText().toString().trim();
        String email = updateEmail.getText().toString().trim();

        String appointmentDate;

        if (TextUtils.isEmpty(updateCreationTime))

            appointmentDate = updateAppointmentDate.getText().toString();

        else

            appointmentDate = updateCreationTime;

        Doctor doctor = new Doctor(doctorId, name, number, details, appointmentDate, email);

        mListener.updateFields(doctor);
    }

    public void onButtonPressed(Uri uri) {

        if (mListener != null) {

            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {

            mListener = (OnFragmentInteractionListener) context;
        }

        else {

            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }

        mContext = context;
    }

    @Override
    public void onDetach() {

        super.onDetach();
        mListener = null;
        mContext = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
        void updateFields(Doctor doctor);
    }
}
