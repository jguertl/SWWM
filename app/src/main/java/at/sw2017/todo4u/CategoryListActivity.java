package at.sw2017.todo4u;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import at.sw2017.todo4u.R;
import at.sw2017.todo4u.database.TaskCategoriesDataSource;
import at.sw2017.todo4u.model.TaskCategory;

public class CategoryListActivity extends AppCompatActivity {

    private ListView category_list_view;
    private String test[] = {"Homework", "Training", "get present for mum"};;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        TaskCategoriesDataSource tcds = new TaskCategoriesDataSource(this);
        tcds.open();
        List<TaskCategory> categories = new ArrayList<>();
        categories = tcds.getAll();
        tcds.close();

        List<String> categoriesAsString = new ArrayList<>();
        for (TaskCategory taskCategory : categories) {
            categoriesAsString.add(taskCategory.getName());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categoriesAsString);
        category_list_view = (ListView) findViewById(R.id.category_list_view);
        category_list_view.setAdapter(adapter);

        setTitle("Categories");



    }
}
