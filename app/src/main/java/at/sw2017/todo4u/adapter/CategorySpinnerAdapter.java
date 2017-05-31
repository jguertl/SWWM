package at.sw2017.todo4u.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import at.sw2017.todo4u.R;
import at.sw2017.todo4u.model.TaskCategory;


public class CategorySpinnerAdapter extends ArrayAdapter<TaskCategory> {
    private static LayoutInflater inflater = null;
    private Activity activity;
    private List<TaskCategory> items;

    public CategorySpinnerAdapter(Activity activity, int textViewResourceId, List<TaskCategory> items) {
        super(activity, textViewResourceId, items);
        try {
            this.activity = activity;
            this.items = items;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return items.size();
    }

    public TaskCategory getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View vi = convertView;
        try {
            vi = inflater.inflate(R.layout.category_spinner_item, parent, false);
            TextView name = (TextView) vi.findViewById(R.id.categoryspinneritem_name);
            name.setText(items.get(position).getName());

        } catch (Exception e) {


        }
        return vi;
    }

    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        View vi = convertView;

        try {
            vi = inflater.inflate(R.layout.category_spinner_item, parent, false);
            TextView name = (TextView) vi.findViewById(R.id.categoryspinneritem_name);
            name.setText(items.get(position).getName());
        } catch (Exception e) {

        }
        return  vi;
    }
}
