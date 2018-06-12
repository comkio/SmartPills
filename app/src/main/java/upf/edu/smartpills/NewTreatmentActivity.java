package upf.edu.smartpills;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.transition.ChangeTransform;
//import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.transitionseverywhere.ChangeText;
import com.transitionseverywhere.TransitionManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import static upf.edu.smartpills.FirstActivity.db;

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
    EditText from,pillName,repetition,quantity,tname;
    EditText to;
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
        repetition = findViewById(R.id.editText5);
        quantity = findViewById(R.id.editText);
        fromB = findViewById(R.id.from);
        toB = findViewById(R.id.to);
        pillNamesList = findViewById(R.id.newTreatPills);
        tname = findViewById(R.id.editText8);


        //AddpillButton
        //Animation
        final ViewGroup transitionsContainer = findViewById(R.id.transitions_container);
        final Button button = transitionsContainer.findViewById(R.id.button);
        final ScrollView linearLayoutCompat = findViewById(R.id.layout2);



        button.setOnClickListener(new View.OnClickListener() {
            boolean visible;
            boolean isSecondText;
            @Override
            public void onClick(View v) {

                isSecondText = !isSecondText;
                TransitionManager.beginDelayedTransition(transitionsContainer);
                visible = !visible;
                linearLayoutCompat.setVisibility(visible ? View.VISIBLE : View.GONE);

                TransitionManager.beginDelayedTransition(transitionsContainer,
                        new ChangeText().setChangeBehavior(ChangeText.CHANGE_BEHAVIOR_OUT_IN));
                button.setText(isSecondText ? "DONE" : "Add Pill");

                if(button.getText().toString().equals("DONE")){
                    if(pillName.getText().toString().length() < 25 && quantity.getText().toString().length()<5
                            && isNumeric(quantity.getText().toString())) {
                        Pill newPill = new Pill(pillName.getText().toString(), quantity.getText().toString());
                        pills.add(newPill);
                    }else{
                        CharSequence text = "Inputs too big!";
                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Confirm and Cancel buttons
        confirmBtn = findViewById(R.id.confirmButton);
        cancelBtn = findViewById(R.id.cancelButton);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( TextUtils.isEmpty(pillName.getText()) || TextUtils.isEmpty(tname.getText()) || TextUtils.isEmpty(from.getText()) ||
                        TextUtils.isEmpty(to.getText()) || TextUtils.isEmpty(repetition.getText()) || TextUtils.isEmpty(quantity.getText())) {
                    confirmBtn.setError("All fields are required!");
                    Toast.makeText(getApplicationContext(), "All fields are empty", Toast.LENGTH_SHORT).show();
                }else{
                    db.myDao().insertPills(pills);
                    db.myDao().insertTreatments(new Treatment(tname.getText().toString()));
                    //db.myDao().insertTreatmentPills(new TreatmentPill(from.getText().toString(),8,to.getText().toString(),Integer.parseInt(repetition.getText().toString())));
                    //Instancia de TreatmentPill peta //db.myDao().insertTreatmentPills(new TreatmentPill(from.getText().toString(),
                    //        8, to.getText().toString(), Integer.parseInt(repetition.getText().toString())));
                    Intent homeIntent = new Intent(NewTreatmentActivity.this, CalendarActivity.class);
                    DateFormat df = new SimpleDateFormat("h:mm a");
                    String date = df.format(Calendar.getInstance().getTime());
                    homeIntent.putExtra("PlanB",pillName.getText() + "-" + date);
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

        /*
        //List to be shown in the ListView
        //Need List of pill name form DB
        String[] pillNames =null;
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < pillNames.length; ++i) {
            list.add(pillNames[i]);
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        pillNamesList.setAdapter(adapter);
*/


    }

    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, R.style.DialogTheme ,new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;

                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);

                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                //Muestro la fecha con el formato deseado
                from.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },year, month, day);
        //Muestro el widget
        recogerFecha.show();

    }
    private void obtenerFecha2(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, R.style.DialogTheme ,new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;

                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);

                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                //Muestro la fecha con el formato deseado
                to.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },year, month, day);
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