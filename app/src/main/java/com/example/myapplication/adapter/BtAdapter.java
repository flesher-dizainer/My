package com.example.myapplication.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class BtAdapter extends ArrayAdapter<ListItem> {
    private List<ListItem> mainList;
    private List<ViewHolder> listViewHolders;
    private SharedPreferences pref;

    public BtAdapter(@NonNull Context context, int resource, List<ListItem> btList) {
        super(context, resource, btList);
        mainList = btList;
        listViewHolders = new ArrayList<>();
        pref = context.getSharedPreferences(btConst.MY_PREF, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
           convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bt_list_item, null,false);
           viewHolder.tvBtName = convertView.findViewById(R.id.tvBtName);
           viewHolder.chBtSelected = convertView.findViewById(R.id.checkBox);
           convertView.setTag(viewHolder);
           listViewHolders.add(viewHolder);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvBtName.setText(mainList.get(position).getBtName());
        viewHolder.chBtSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(ViewHolder holder:listViewHolders){
                    holder.chBtSelected.setChecked(false);
                }
                viewHolder.chBtSelected.setChecked(true);
                savePref(position);

            }
        });
        if(pref.getString(btConst.MAC_KEY,"no bt select").equals(mainList.get(position).getBtMac()))
            viewHolder.chBtSelected.setChecked(true);


        return convertView;
    }

    private void savePref(int pos){//сохранение МАС адреса в память

        SharedPreferences.Editor editor = pref.edit();
        editor.putString(btConst.MAC_KEY,mainList.get(pos).getBtMac());
        editor.apply();

    }

    static class ViewHolder{
        TextView tvBtName;
        CheckBox chBtSelected;
    }
}
