package com.example.projektzaliczeniowy;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    final List<String> hooks = Arrays.asList("Tulip Red cena: 40zł", "Clover Amour cena: 30zł", "Pony cena: 15zł");
    final List<Integer> hooksImg = Arrays.asList(R.drawable.tulip, R.drawable.clover, R.drawable.pony);
    final List<String> colours = Arrays.asList("Fioletowy cena: 7zł", "Czerwony cena: 7zł", "Beżowy cena: 7zł", "Niebieski cena: 7zł", "Zielony cena: 7zł", "Koralowy cena: 7zł");
    final List<Integer> coloursImg = Arrays.asList(R.drawable.fioletowa, R.drawable.czerwona, R.drawable.bezowa, R.drawable.niebieska, R.drawable.zielona, R.drawable.koralowy);
    final List<String> patterns = Arrays.asList("Jelonek cena: 20zł", "Kotojednorożec: 25zł", "Chomik: 18zł");
    final List<Integer> patternsImg = Arrays.asList(R.drawable.jelonek, R.drawable.kotojednorozec, R.drawable.chomik);


    Spinner hooksSpinner;
    Spinner firstYarnSpinner;
    Spinner secondyarnSpinner;
    Spinner patternSpinner;
    TextView textViewcost;
    EditText editText;
    EditText editTextphone;
    Button button;
    CheckBox hookCheck;
    CheckBox yarnFirstcheck;
    CheckBox yarnSecondcheck;
    CheckBox patternCheck;
    int priceHook = 0;
    int priceFirstyarn = 0;
    int priceSecondyarn = 0;
    int pricePattern = 0;

//menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.zamawianie:
                return true;
            case R.id.zamowienia:
                Intent intent2 = new Intent(this, ListOfOrder.class);
                startActivity(intent2);
                return true;
            case R.id.autor:
                Intent intent = new Intent(this, Autor.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 0);

        textViewcost = findViewById(R.id.price);
        editTextphone = findViewById(R.id.phone);
        editText = findViewById(R.id.personal_data);
        button = findViewById(R.id.button);
        hookCheck = findViewById(R.id.checkbox_hooks);
        yarnFirstcheck = findViewById(R.id.checkbox_first_yarn);
        yarnSecondcheck = findViewById(R.id.checkbox_second_yarn);
        patternCheck = findViewById(R.id.checkbox_pattern);

        spinnersSpin();
        substractOnUnchecked();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonOrderClick();
            }

        });
    }

    private void substractOnUnchecked() {
        hookCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!hookCheck.isChecked()){
                    priceHook = 0;
                    textViewcost.setText(String.valueOf(priceHook+priceFirstyarn+priceSecondyarn+pricePattern)+"zł");
                }else{
                    int position = hooksSpinner.getSelectedItemPosition();
                    addToPrice(hooks.get(position), "hook");
                }
            }
        });

        yarnFirstcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!yarnFirstcheck.isChecked()){
                    priceFirstyarn = 0;
                    textViewcost.setText(String.valueOf(priceHook+priceFirstyarn+priceSecondyarn+pricePattern)+"zł");
                }else{
                    int position = firstYarnSpinner.getSelectedItemPosition();
                    addToPrice(colours.get(position), "yarn1");
                }
            }
        });

        yarnSecondcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!yarnSecondcheck.isChecked()){
                    priceSecondyarn = 0;
                    textViewcost.setText(String.valueOf(priceHook+priceFirstyarn+priceSecondyarn+pricePattern)+"zł");
                }else{
                    int position = secondyarnSpinner.getSelectedItemPosition();
                    addToPrice(colours.get(position), "yarn2");
                }
            }
        });

        patternCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!patternCheck.isChecked()){
                    pricePattern = 0;
                    textViewcost.setText(String.valueOf(priceHook+priceFirstyarn+priceSecondyarn+pricePattern)+"zł");
                }else{
                    int position = patternSpinner.getSelectedItemPosition();
                    addToPrice(patterns.get(position), "pattern");
                }
            }
        });
    }

    private void spinnersSpin(){
        //ustawianie SpinnerAdapterów do Spinnerów
        hooksSpinner = findViewById(R.id.spinner_hooks);
        MySpinnerAdapter adapterszydelka = new MySpinnerAdapter(getApplicationContext(), hooks, hooksImg);
        adapterszydelka.setDropDownViewResource(R.layout.dropdown_item);
        hooksSpinner.setAdapter(adapterszydelka);

        hooksSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(hookCheck.isChecked()) {
                    addToPrice(hooks.get(i), "hook");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {return;}
        });

        firstYarnSpinner = findViewById(R.id.spinner_first_yarn);
        secondyarnSpinner = findViewById(R.id.spinner_second_yarn);
        MySpinnerAdapter wloczkiAdapter = new MySpinnerAdapter(getApplicationContext(), colours, coloursImg);
        wloczkiAdapter.setDropDownViewResource(R.layout.dropdown_item);
        firstYarnSpinner.setAdapter(wloczkiAdapter);
        secondyarnSpinner.setAdapter(wloczkiAdapter);

        firstYarnSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(yarnFirstcheck.isChecked()){
                    addToPrice(colours.get(i), "yarn1");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        secondyarnSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(yarnSecondcheck.isChecked()){
                    addToPrice(colours.get(i), "yarn2");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        patternSpinner = findViewById(R.id.spinner_pattern);
        MySpinnerAdapter wzorAdapter = new MySpinnerAdapter(getApplicationContext(), patterns, patternsImg);
        wzorAdapter.setDropDownViewResource(R.layout.dropdown_item);
        patternSpinner.setAdapter(wzorAdapter);
        patternSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(patternCheck.isChecked()){
                    addToPrice(patterns.get(i), "pattern");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void addToPrice(String information, String type){
        Log.v("CENA", "addToPrice");
        String[] itemPriceTable = information.split(" ");
        String itemPriceTemp = itemPriceTable[itemPriceTable.length-1];
        int itemPrice = Integer.parseInt(itemPriceTemp.substring(0, itemPriceTemp.length()-2));

        switch (type){
            case "hook":
                priceHook = itemPrice;
                break;
            case "yarn1":
                priceFirstyarn = itemPrice;
                break;
            case "yarn2":
                priceSecondyarn = itemPrice;
                break;
            case "pattern":
                pricePattern = itemPrice;
                break;
        }
        textViewcost.setText(String.valueOf(priceHook+priceFirstyarn+priceSecondyarn+pricePattern)+"zł");
    }

    public void buttonOrderClick(){
            String name = editText.getText().toString();
            String summerisePrice = (String) textViewcost.getText();
            if(!name.isEmpty()){
                List<String> itemsSelected = new ArrayList<>();
                if(hookCheck.isChecked()){
                    itemsSelected.add((String) hooksSpinner.getSelectedItem());
                    hookCheck.setChecked(false);
                }
                if(yarnFirstcheck.isChecked()){
                    itemsSelected.add((String) firstYarnSpinner.getSelectedItem());
                    yarnFirstcheck.setChecked(false);
                }
                if(yarnSecondcheck.isChecked()){
                    itemsSelected.add((String) secondyarnSpinner.getSelectedItem());
                    yarnSecondcheck.setChecked(false);
                }
                if(patternCheck.isChecked()){
                    itemsSelected.add((String) patternSpinner.getSelectedItem());
                    patternCheck.setChecked(false);
                }

                String personalData = String.valueOf(editText.getText());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                String number = editTextphone.getText().toString();
                Order order = new Order(itemsSelected, personalData, summerisePrice, currentDateandTime, number, SmsManager.getDefault());
                order.send(getApplicationContext());
                Toast.makeText(getApplicationContext(), "Zamówienie wysłane", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "Proszę wpisać imię i nazwisko", Toast.LENGTH_LONG).show();
            }
        }
    }
