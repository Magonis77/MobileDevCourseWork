package com.example.comp1424_coursework;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchAdapter extends ArrayAdapter<Hikes> {
    // View lookup cache
    DatabaseHelper helper;
    SQLiteDatabase db;
    Context context;
    private static class ViewHolder {
        TextView id;
        TextView name;
        TextView location;
        TextView date;
        TextView parking;
        TextView lenght;
        TextView difficulty;
        TextView weather;
        TextView heartrate;
        TextView description;
    }

    public SearchAdapter(Context context, ArrayList<Hikes> hikes) {
        super(context, R.layout.row_search, hikes);
        helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Hikes hikes = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_search, parent, false);
            viewHolder.id = (TextView) convertView.findViewById(R.id.textview_id3);
            viewHolder.name = (TextView) convertView.findViewById(R.id.textview_name3);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.id.setText("Id:" + hikes.get_id());
        viewHolder.name.setText("Name: " + hikes.get_hikename());
        // Return the completed view to render on screen
        return convertView;
    }

}
