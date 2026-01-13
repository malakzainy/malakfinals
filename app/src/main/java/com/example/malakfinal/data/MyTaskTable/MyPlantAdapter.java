package com.example.malakfinal.data.MyTaskTable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.malakfinal.R;

import java.util.List;

public class MyPlantAdapter extends ArrayAdapter<Plant> {
    private final int itemLayout;

    public MyPlantAdapter(@NonNull Context context, int resource, @NonNull List<Plant> objects) {
        super(context, resource, objects);
        this.itemLayout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View vitem = convertView;
        if (vitem == null)
                vitem = LayoutInflater.from(getContext()).inflate(itemLayout, parent, false);
            ImageView malak = (ImageView) vitem.findViewById(R.id.child);
            TextView tvTitle = (TextView) vitem.findViewById(R.id.tvTitle);
            TextView tvText = (TextView) vitem.findViewById(R.id.tvText);
            TextView tvImportance = (TextView) vitem.findViewById(R.id.tvImportance);

            Plant current = getItem(position);
            tvTitle.setText(current.getTitle());
            tvText.setText(current.getDescription());
            tvImportance.setText(current.getImportance());


            return vitem;
    }
}
