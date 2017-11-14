package app.cedipro.android;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class TSLFragment extends Fragment implements View.OnClickListener {
    private EditText fechaEntrada, fechaDeseada, fechaReal;
    private Button btnEnviar, btnCancelar;
    private View view;
    private int year, month, day;
    static final int  DIALOG_ID = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tsl, container, false);
        mostrarCalendario();
        return view;
    }

    public void mostrarCalendario(){
        fechaEntrada =  (EditText) view.findViewById(R.id.fechaEntrada);
        fechaEntrada.setKeyListener(null);
        //fechaEntrada.setOnTouchListener(this);
        fechaEntrada.setOnClickListener(this);

        fechaDeseada =  (EditText) view.findViewById(R.id.fechaDeseada);
        fechaDeseada.setKeyListener(null);
        fechaDeseada.setOnClickListener(this);

        fechaReal =  (EditText) view.findViewById(R.id.fechaReal);
        fechaReal.setKeyListener(null);
        fechaReal.setOnClickListener(this);

        btnEnviar = (Button) view.findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(this);
        btnCancelar = (Button) view.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fechaEntrada:
                showDatePickerDialog(fechaEntrada);
                break;
            case R.id.fechaDeseada:
                showDatePickerDialog(fechaDeseada);
                break;
            case R.id.fechaReal:
                showDatePickerDialog(fechaReal);
                break;
            case R.id.btnEnviar:
                Toast.makeText(getContext(), "Registro enviado con éxito", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnCancelar:
                fechaEntrada.getText().clear();
                fechaReal.getText().clear();
                fechaDeseada.getText().clear();
                /*ViewGroup group = (ViewGroup)view.findViewById(R.id.layout_formulario_tsp);
                for (int i = 0, count = group.getChildCount(); i < count; ++i) {
                    View view2 = group.getChildAt(i);
                    if (view2 instanceof EditText) {
                        ((EditText)view2).setText("");
                    }
                }*/
                Toast.makeText(getContext(), "Registro cancelado", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /*@Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        showDatePickerDialog(fechaEntrada);
        return false;
    }*/

    private void showDatePickerDialog(final EditText editText) {
        TSPFragment.DatePickerFragment newFragment = TSPFragment.DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = twoDigits(day) + "/" + twoDigits(month+1) + "/" + year;

                editText.setText(selectedDate);
                //se deseada cambiar la anchura del edittext despues de ingresar la fecha
                /*
                ViewGroup.LayoutParams params = editText.getLayoutParams();
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                editText.setLayoutParams(params);*/
                Toast.makeText(getContext(), "Fecha seleccionada: "+selectedDate, Toast.LENGTH_LONG).show();
            }
        });
        newFragment.show(getActivity().getFragmentManager(), "datePicker");
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }
/*
    public void showDialogOnClickButton(){
        btnFechaEntrada =  (Button) view.findViewById(R.id.btn_fechaEntrada);
        btnFechaEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "fecha: "+year+"/"+month+"/"+day, Toast.LENGTH_SHORT).show();

            }
        });
    }

    protected Dialog onCreateDialog(int id){
        if (id == DIALOG_ID){
            return new DatePickerDialog(getContext(), datePickerListener ,year, month, day );
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            year = i;
            month = i1;
            day =  i2;
            Toast.makeText(getActivity(), "fecha: "+year+"/"+month+"/"+day, Toast.LENGTH_SHORT).show();
        }
    };*/

    public static class DatePickerFragment extends DialogFragment {

        private DatePickerDialog.OnDateSetListener listener;

        public static TSPFragment.DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
            TSPFragment.DatePickerFragment fragment = new TSPFragment.DatePickerFragment();
            fragment.setListener(listener);
            return fragment;
        }

        public void setListener(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            Context context =  getActivity();
            if (isBrokenSamsungDevice()) {
                context = new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_Dialog);
            }
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(context, listener, year, month, day);
        }

        private static boolean isBrokenSamsungDevice() {
            return (Build.MANUFACTURER.equalsIgnoreCase("samsung")
                    && isBetweenAndroidVersions(
                    Build.VERSION_CODES.LOLLIPOP,
                    Build.VERSION_CODES.LOLLIPOP_MR1));
        }

        private static boolean isBetweenAndroidVersions(int min, int max) {
            return Build.VERSION.SDK_INT >= min && Build.VERSION.SDK_INT <= max;
        }
    }
}
