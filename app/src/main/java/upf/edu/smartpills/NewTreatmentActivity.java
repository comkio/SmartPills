package upf.edu.smartpills;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.transitionseverywhere.ChangeText;
import com.transitionseverywhere.TransitionManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import static upf.edu.smartpills.FirstActivity.db;

//import android.support.transition.TransitionManager;

public class NewTreatmentActivity extends AppCompatActivity {

    private static Context mContext;
    private static final String CERO = "0";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int month = c.get(Calendar.MONTH);
    final int day = c.get(Calendar.DAY_OF_MONTH);
    final int year = c.get(Calendar.YEAR);

    ImageButton fromB;
    ImageButton toB;
    EditText pillName, quantity, tname;
    Spinner repetition;
    TextView from, to;
    Button confirmBtn;
    Button cancelBtn;
    ListView pillNamesList;
    List<Pill> pills = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_treatment);

        pillName = findViewById(R.id.editText2);
        from = findViewById(R.id.editText3);
        to = findViewById(R.id.editText4);
        repetition = findViewById(R.id.repetition_spinner);
        quantity = findViewById(R.id.editText);
        fromB = findViewById(R.id.from);
        toB = findViewById(R.id.to);
        tname = findViewById(R.id.editText8);


        //AddpillButton
        //Animation
        final ViewGroup transitionsContainer = findViewById(R.id.transitions_container);
        final Button button = transitionsContainer.findViewById(R.id.button);
        final ScrollView linearLayoutCompat = findViewById(R.id.layout2);


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (pillName.getText().toString().length() < 25 && quantity.getText().toString().length() < 5
                        && isNumeric(quantity.getText().toString())) {
                    Pill newPill = new Pill(pillName.getText().toString(), quantity.getText().toString());
                    pills.add(newPill);
                } else {
                    CharSequence text = "Input's too big!";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }
                pillName.setText(null);
                from.setText(null);
                to.setText(null);
                repetition.setSelection(0);
                quantity.setText(null);

            }
        });

        //Confirm and Cancel buttons
        confirmBtn = findViewById(R.id.confirmButton);
        cancelBtn = findViewById(R.id.cancelButton);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pills.isEmpty()) {
                    confirmBtn.setError("Treatment with no pills");
                    Toast.makeText(getApplicationContext(), "All fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    db.myDao().insertPills(pills);
                    db.myDao().insertTreatments(new Treatment(tname.getText().toString()));
                    List<Pill> allpills = db.myDao().getAllPills();
                    List<Treatment> alltreatments = db.myDao().getAllTreatments();
                    int addedPills = pills.size();
                    DateFormat df = new SimpleDateFormat("HH:mm");
                    String date = df.format(Calendar.getInstance().getTime());
                    for (int i = 0; i < addedPills; i++) {
                        db.myDao().insertTreatmentPills(new TreatmentPill(allpills.get(allpills.size()-1-i).id,
                                alltreatments.get(alltreatments.size()-1).id,from.getText().toString(),
                                date, to.getText().toString(), repetition.getSelectedItem().toString()));
                    }
                    Intent homeIntent = new Intent(NewTreatmentActivity.this, CalendarActivity.class);
                    startActivity(homeIntent);
                    //Transition Animation
                    overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
                    finish();
                }

            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(NewTreatmentActivity.this, TreatmentsActivity.class);
                startActivity(homeIntent);
                //Transition Animation
                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
                finish();
            }
        });
        fromB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha();
            }
        });
        toB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha2();
            }
        });


    }

    private void obtenerFecha() {
        DatePickerDialog recogerFecha = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;

                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10) ? CERO + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);

                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10) ? CERO + String.valueOf(mesActual) : String.valueOf(mesActual);

                //Muestro la fecha con el formato deseado
                from.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        }, year, month, day);
        //Muestro el widget
        recogerFecha.show();

    }

    private void obtenerFecha2() {
        DatePickerDialog recogerFecha = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;

                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10) ? CERO + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);

                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10) ? CERO + String.valueOf(mesActual) : String.valueOf(mesActual);

                //Muestro la fecha con el formato deseado
                to.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        }, year, month, day);
        //Muestro el widget
        recogerFecha.show();

    }

    public static boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }

    @Override
    public void onBackPressed() {
        Intent profile = new Intent(NewTreatmentActivity.this, CalendarActivity.class);
        startActivity(profile);
        //Transition Animation
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
        finish();
    }
}