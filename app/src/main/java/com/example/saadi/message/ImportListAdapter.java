package com.example.saadi.message;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Saadi on 26-Dec-17.
 */

public class ImportListAdapter  extends BaseAdapter {

    List<DataProvider> list;
    Activity context;

    public ImportListAdapter(Activity context, List<DataProvider> list){
        this.list= list;
        this.context = context;
    }

    public void overMethod(List<DataProvider> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.import_contact_row, null, true);

        TextView txtName = (TextView) rowView.findViewById(R.id.contactName);
        txtName.setText(list.get(position).getName());

        TextView txtNumber = (TextView) rowView.findViewById(R.id.contactNumber);
        txtNumber.setText(list.get(position).getMob());


        return  rowView;
    }
}

