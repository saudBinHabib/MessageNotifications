package com.example.saadi.message;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saadi on 23-Dec-17.
 */

public class ListDataAdapter extends ArrayAdapter {
    List list =  new ArrayList();
    public ListDataAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }
        static class LayoutHandler
        {
            TextView NAME,MOB,CAT;

        }

    @Override
    public void add( Object object) {
        super.add(object);
        list.add(object);
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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        LayoutHandler layoutHandler;
        if (row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout,parent,false);
            layoutHandler = new LayoutHandler();
            layoutHandler.NAME =(TextView) row.findViewById(R.id.txtusername);
            layoutHandler.MOB = (TextView) row.findViewById(R.id.txtusernum);
            layoutHandler.CAT = (TextView) row.findViewById(R.id.txtusercat);
            row.setTag(layoutHandler);

        }
            else {
            layoutHandler =(LayoutHandler) row.getTag();

            }
        DataProvider dataProvider = (DataProvider) this.getItem(position);
        layoutHandler.NAME.setText(dataProvider.getName());
        layoutHandler.MOB.setText(dataProvider.getMob());
        layoutHandler.CAT.setText(dataProvider.getCategroy());
        return row;
    }
}
