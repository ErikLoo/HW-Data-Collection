package com.example.datacollectionapp;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private AtomPayListAdapter adapter;
    private View itemView;
    private ImageButton button_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        button_edit = (ImageButton) findViewById(R.id.edit_button);

        fab.setOnClickListener(new View.OnClickListener() { //set up the onclick listern for the floating action button
            @Override
            public void onClick(View view) {
                setupAddPaymentButton();
            }
        });


        setupListViewAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void edit_configuration(View v)
    {
        Intent intent = new Intent("com.example.datacollectionapp.config_activity" );
        startActivity(intent);
    }

    public void removeAtomPayOnClickHandler(View v) { // remove a list item
        //add a remove confirmation here before actually removing the entry
        itemView = v; // pass the reference to a global view variable because View v can not be accessed in the inner loop
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to remove this item?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AtomPayment itemToRemove = (AtomPayment)itemView.getTag();
                        adapter.remove(itemToRemove);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel(); //do nothing if "no" is pressed
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setupListViewAdapter() {
        adapter = new AtomPayListAdapter(MainActivity.this, R.layout.list_item, new ArrayList<AtomPayment>());
        ListView atomPaysListView = (ListView)findViewById(R.id.EnterPays_atomPaysList);
        atomPaysListView.setAdapter(adapter);
    }

    private void setupAddPaymentButton() { // add a list item
        adapter.insert(new AtomPayment("", 0), 0);// create an entry after pushing the button
    }


}
