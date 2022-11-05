package com.example.comp1424_coursework;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ObservationsAdapter extends ArrayAdapter<Observations> {
    // View lookup cache
    DatabaseHelper helper;
    SQLiteDatabase db;
    Context context;

    private static class ViewHolder {
        TextView id;
        TextView name;
        TextView time;
        TextView comments;

    }
    public ObservationsAdapter(Context context, ArrayList<Observations> observations) {
        super(context, R.layout.row_observations, observations);
        helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Observations observations = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ObservationsAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ObservationsAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_observations, parent, false);
            viewHolder.id = (TextView) convertView.findViewById(R.id.textViewHike_ID);
            viewHolder.name = (TextView) convertView.findViewById(R.id.textViewOBSName);
            viewHolder.time = (TextView) convertView.findViewById(R.id.textViewObservTime);
            viewHolder.comments = (TextView) convertView.findViewById(R.id.textViewOBSComments);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ObservationsAdapter.ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.id.setText("Id:" + observations.get_id());
        viewHolder.name.setText("Name: " + observations.get_obsname());
        viewHolder.time.setText("Time: " + observations.get_obstime());
        viewHolder.comments.setText("Comments: " + observations.get_obscomment());
        // Return the completed view to render on screen
        return convertView;
    }
}

