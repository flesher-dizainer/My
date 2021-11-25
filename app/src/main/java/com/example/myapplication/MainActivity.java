package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
//import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.adapter.btConst;

public class MainActivity extends AppCompatActivity {

    private MenuItem menuItem;
    private BluetoothAdapter btAdapter;
    private final int ENABLE_REQUEST = 15;
    //private SharedPreferences pref;


    @Override//создание формы
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();//вызов инициализации
    }

    @Override//создание меню
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        menuItem = menu.findItem(R.id.id_bt_button);
        setBtIcon();
        return super.onCreateOptionsMenu(menu);
    }

    @Override//обработка нажатий на пункты меню
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.id_bt_button ){//проверка нажатия кнопки включения/отключения блютуз
            if(!btAdapter.isEnabled()){
                enableBt();

            }else{
                btAdapter.disable();
                menuItem.setIcon(R.drawable.ic_bt_enable);
            }

        } else
            if(item.getItemId() == R.id.id_menu ){//выбор включения списка, новое окошко
                if(btAdapter.isEnabled()) {
                    Intent i = new Intent(MainActivity.this, BtListActivity.class);
                    startActivity(i);//запуск нового окошка со списком
                }else {
                    Toast.makeText(this, "Bt отключен, включите!", Toast.LENGTH_SHORT).show();
                }

            }
        return super.onOptionsItemSelected(item);
    }

    @Override//обработка выбора да/нет на запросы
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ENABLE_REQUEST){
            if(resultCode == RESULT_OK){
                setBtIcon();


            }

        }
    }

    private void setBtIcon(){//установка иконок
        if(btAdapter.isEnabled())
        {
            menuItem.setIcon(R.drawable.ic_bt_disabled);
        }
        else {
            menuItem.setIcon(R.drawable.ic_bt_enable);
        }
    }

    private void init(){//создаётся экземпляр адаптера
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        getSharedPreferences(btConst.MY_PREF, Context.MODE_PRIVATE);

    }

    private void enableBt(){//включение адаптера

        Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(i, ENABLE_REQUEST);

    }
}