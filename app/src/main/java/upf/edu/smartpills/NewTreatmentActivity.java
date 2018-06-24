package upf.edu.smartpills;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import static upf.edu.smartpills.FirstActivity.db;

public class NewTreatmentActivity extends AppCompatActivity {

    private static final String CERO = "0";
    private static final String BARRA = "/";

    //Calendar to obtain date
    public final Calendar c = Calendar.getInstance();

    //Variables used to obtain date
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
    List<Pill> pills = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_treatment);

        //Obtain each interactable view from the layout
        pillName = findViewById(R.id.editText2);
        from = findViewById(R.id.editText3);
        to = findViewById(R.id.editText4);
        repetition = findViewById(R.id.repetition_spinner);
        quantity = findViewById(R.id.editText);
        fromB = findViewById(R.id.from);
        toB = findViewById(R.id.to);
        tname = findViewById(R.id.editText8);


        final ViewGroup transitionsContainer = findViewById(R.id.transitions_container);
        final Button button = transitionsContainer.findViewById(R.id.button);

        //Add pill button
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Check that quantity is not abnormal and save the data in a pill list
                if (pillName.getText().toString().length() < 25 && quantity.getText().toString().length() < 5
                        && isNumeric(quantity.getText().toString())) {
                    Pill newPill = new Pill(pillName.getText().toString(), quantity.getText().toString());
                    pills.add(newPill);
                } else {
                    CharSequence text = "Input's too big!";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }
                //Reset the editable view fields to add new pills
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
                //Error in case of no that in pills list
                if (pills.isEmpty()) {
                    confirmBtn.setError("Treatment with no pills");
                    Toast.makeText(getApplicationContext(), "All fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    //Add all the items to the DataBase to its respective tables here we hace to be
                    //careful of the relation between keys
                    db.myDao().insertPills(pills);
                    db.myDao().insertTreatments(new Treatment(tname.getText().toString()));
                    List<Pill> allpills = db.myDao().getAllPills();
                    List<Treatment> alltreatments = db.myDao().getAllTreatments();
                    int addedPills = pills.size();
                    DateFormat df = new SimpleDateFormat("HH:mm");
                    String date = df.format(Calendar.getInstance().getTime());
                    //Create a new insert in TreatmentPill relating each pill with their treatment
                    for (int i = 0; i < addedPills; i++) {
                        db.myDao().insertTreatmentPills(new TreatmentPill(allpills.get(allpills.size() - 1 - i).getId(),
                                alltreatments.get(alltreatments.size() - 1).getId(), from.getText().toString(),
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
        //GO back doing nothing in case of cancel
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
        //Button to add the necessary date to start and finish the treatment
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

    //Methods to create the calendar and save the date picked by the user
    private void obtenerFecha() {
        DatePickerDialog recogerFecha = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                final int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10) ? CERO + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10) ? CERO + String.valueOf(mesActual) : String.valueOf(mesActual);
                from.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);

            }
        }, year, month, day);

        recogerFecha.show();

    }

    private void obtenerFecha2() {
        DatePickerDialog recogerFecha = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                final int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10) ? CERO + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10) ? CERO + String.valueOf(mesActual) : String.valueOf(mesActual);
                to.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);

            }
        }, year, month, day);
        recogerFecha.show();

    }

    //Method to check if the input is a number
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

    //On back go to Calendar Activity
    @Override
    public void onBackPressed() {
        Intent profile = new Intent(NewTreatmentActivity.this, CalendarActivity.class);
        startActivity(profile);
        //Transition Animation
        overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
        finish();
    }
}