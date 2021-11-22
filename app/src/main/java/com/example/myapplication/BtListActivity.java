package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.adapter.BtAdapter;
import com.example.myapplication.adapter.ListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BtListActivity extends AppCompatActivity {
    private ListView listView;
    private BtAdapter adapter;
    private BluetoothAdapter btAdapter;
    private List<ListItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt_list);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    private void init(){
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        list = new ArrayList<>();
        ActionBar ab = getSupportActionBar();
        if(ab==null) return;

            ab.setDisplayHomeAsUpEnabled(true);
            listView = findViewById(R.id.listView);
            adapter = new BtAdapter(this, R.layout.bt_list_item,list);
            listView.setAdapter(adapter);
            getPairedDevices();


    }
    private void getPairedDevices(){
        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
        Toast.makeText(this, "getPairedDevices OK", Toast.LENGTH_SHORT).show();
        if (pairedDevices.size() > 0) {
            list.clear();
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                ListItem item = new ListItem();
                item.setBtName(device.getName());
                item.setBtMac(device.getAddress());
                list.add(item);
            }
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "pairedDevices.size <=0", Toast.LENGTH_SHORT).show();
        }
    }

}