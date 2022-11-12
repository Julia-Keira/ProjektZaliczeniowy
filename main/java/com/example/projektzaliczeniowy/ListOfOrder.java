package com.example.projektzaliczeniowy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

public class ListOfOrder extends AppCompatActivity {

    ListView listView;
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
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
                return true;
            case R.id.zamowienia:
                return true;
            case R.id.autor:
                Intent intent = new Intent(this, Autor.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_zamowien);

        listView = findViewById(R.id.list_view);
        OrderAdapter orderAdapter = new OrderAdapter(getApplicationContext(), Order.orderArrayList);
        listView.setAdapter(orderAdapter);
        loadFromDBToMemory();
    }

    public void loadFromDBToMemory(){
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateOrderListArray();
    }

}