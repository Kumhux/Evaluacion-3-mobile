package com.example.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<UserModel> userModelArrayList;

    public CustomAdapter(Context context, ArrayList<UserModel> userModelArrayList) {

        this.context = context;
        this.userModelArrayList = userModelArrayList;
    }


    @Override
    public int getCount() {
        return userModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return userModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_item, null, true);

            holder.lblab = (TextView) convertView.findViewById(R.id.lab);
            holder.lbrut = (TextView) convertView.findViewById(R.id.rut);
            holder.lbname = (TextView) convertView.findViewById(R.id.name);
            holder.lbdescr = (TextView) convertView.findViewById(R.id.descr);
            holder.lbdate = (TextView) convertView.findViewById(R.id.date);



            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.lblab.setText("Laboratorio: "+userModelArrayList.get(position).getLab());
        holder.lbrut.setText("RUT: "+userModelArrayList.get(position).getRut());
        holder.lbname.setText("Nombre: "+userModelArrayList.get(position).getName());
        holder.lbdescr.setText("Descripcion: "+userModelArrayList.get(position).getDescr());
        holder.lbdate.setText("Fecha: " + userModelArrayList.get(position).getDate());


        return convertView;
    }

    private class ViewHolder {

        protected TextView lblab, lbrut, lbname, lbdescr, lbdate;
    }

}