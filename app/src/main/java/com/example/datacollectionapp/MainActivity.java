package com.example.datacollectionapp;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private AtomPayListAdapter adapter;
    private View itemView;
    private ImageButton button_edit;
    private static final int REQUEST_CODE = 10;
    private AtomPayment gAtomPayment;

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
//                setupAddPaymentButton();
                intialize_new_entry();
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

    private void intialize_new_entry() {
        itemView = null;
        setupAddPaymentButton();
        open_edit_page();
    }

    public void edit_configuration(View v)
    {
        //add a confirmation dialogue here: Entering edit mode will toggle off data labelling. Are you sure you would like to continue?
        itemView = v; // pass the reference to a global view variable because View v can not be accessed in the inner loop
//        Intent intent = new Intent("com.example.datacollectionapp.config_activity" );
//        startActivityForResult(intent,REQUEST_CODE);
        open_edit_page();
    }

    public void open_edit_page() {
        Intent intent = new Intent("com.example.datacollectionapp.config_activity_improved" );
//        Intent intent = new Intent("com.example.datacollectionapp.config_activity" );
        startActivityForResult(intent,REQUEST_CODE);
    }

    public void removeAtomPayOnClickHandler(View v) { // remove a list item
        //add a remove confirmation here before actually removing the entry
        itemView = v; // pass the reference to a global view variable because View v can not be accessed in the inner loop
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to remove this item: " + ((AtomPayment)itemView.getTag()).getName()+" ?")
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

    public void removeButton() {
        adapter.remove(gAtomPayment);
    }

    private void setupListViewAdapter() {
        adapter = new AtomPayListAdapter(MainActivity.this, R.layout.list_item, new ArrayList<AtomPayment>());
        ListView atomPaysListView = (ListView)findViewById(R.id.EnterPays_atomPaysList);
        atomPaysListView.setAdapter(adapter);
    }

    private void setupAddPaymentButton() { // add a list item
        AtomPayment mAtomPayment = new AtomPayment("",0);
     //   adapter.insert(mAtomPayment, 0);// add an entry to the specified index of the array
        adapter.add(mAtomPayment);// add an entry to the end of the array adapter
        gAtomPayment = mAtomPayment; //assign the value to a global variable;

//        adapter.insert(new AtomPayment("", 0), 0);// create an entry after pushing the button
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("act_name")) {
                //set the text to string
                if(itemView!=null) { // triggering by clicking the edit button
                    AtomPayment itemToEdit = (AtomPayment) itemView.getTag(); //find that particular view
                    itemToEdit.setName(data.getExtras().getString("act_name"));
                } else { //triggering by clicking the fab button
                    gAtomPayment.setName(data.getExtras().getString("act_name"));
                }
                adapter.notifyDataSetChanged();//notify the data set has changed and nay view reflecing the data set should referesh itself
            }
        }

        if (resultCode == RESULT_CANCELED) {
            //Log.d("TAG", "RESULT_CANCELED");
            removeButton();
        }
    }
}
