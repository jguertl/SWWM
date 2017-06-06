package at.sw2017.todo4u.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import at.sw2017.todo4u.R;
import at.sw2017.todo4u.database.TasksDataSource;
import at.sw2017.todo4u.model.TaskCategory;


public class CategoryAdapter extends ArrayAdapter<TaskCategory> {
    private static LayoutInflater inflater = null;
    private Activity activity;
    private List<TaskCategory> items;

    public CategoryAdapter(Activity activity, int textViewResourceId, List<TaskCategory> items) {
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

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.category_list_item, null);
                holder = new ViewHolder();

                holder.display_name = (TextView) vi.findViewById(R.id.categorylistitem_name);
                holder.display_count = (TextView) vi.findViewById(R.id.categorylistitem_count);


                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }


            TaskCategory cat = items.get(position);
            TasksDataSource tds = new TasksDataSource(getContext());
            tds.openReadonly();
            int count = tds.getNotFinishedTasksInCategory(cat).size();
            tds.close();


            holder.display_name.setText(cat.getName());
            holder.display_count.setText(String.format("%d", count));

            // 0 = none, 1 = red, 2 = green, 3 = yellow, 4 = blue, 5 = cyan
            int color = 0;
            switch (cat.getColor())
            {
                case 0: break;
                case 1: color = Color.argb(30, 200, 20, 30); break;
                case 2: color = Color.argb(30, 30, 200, 20); break;
                case 3: color = Color.argb(30, 220, 255, 0); break;
                case 4: color = Color.argb(30, 20, 30, 200); break;
                case 5: color = Color.argb(30, 0, 183, 235); break;
                default: break;
            }
            vi.setBackgroundColor(color);

//            if(position % 2 == 0)
//                vi.setBackgroundColor(Color.LTGRAY);
//            else
//                vi.setBackgroundColor(Color.WHITE);

        } catch (Exception e) {
        }

        return vi;
    }

    public static class ViewHolder {
        public TextView display_name;
        public TextView display_count;

    }
}
