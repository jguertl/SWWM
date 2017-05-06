package at.sw2017.todo4u;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import at.sw2017.todo4u.R;
import at.sw2017.todo4u.database.TaskCategoriesDataSource;
import at.sw2017.todo4u.model.TaskCategory;


public class CategoryListActivity extends AppCompatActivity implements View.OnClickListener {


    private ListView category_list_view;
    private ArrayAdapter adapter;
    private TaskCategoriesDataSource tcds;
    public String test[] = {"Homework", "Training", "get present for mum"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        tcds = new TaskCategoriesDataSource(this);
        tcds.open();
        List<TaskCategory> categories = new ArrayList<>();
        categories = tcds.getAll();
        tcds.close();

        List<String> categoriesAsString = new ArrayList<>();
        for (TaskCategory taskCategory : categories) {
            categoriesAsString.add(taskCategory.getName());
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_category);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Categories");

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categoriesAsString);
        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, test);
        category_list_view = (ListView) findViewById(R.id.category_list_view);
        category_list_view.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.categorylist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.bt_add_category) {
            Intent homeIntent = new Intent(CategoryListActivity.this, CategoryAddPopup.class);
            startActivity(homeIntent);
        } else if(id == R.id.bt_search_category) {
            return true;
        } else if(id == android.R.id.home) {
            Intent homeIntent = new Intent(CategoryListActivity.this, DashboardActivity.class);
            startActivity(homeIntent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(CategoryListActivity.this, DashboardActivity.class);
        startActivity(homeIntent);
        finish();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateData();
    }

    public void updateData() {
        List<String> categoriesAsStrings = new ArrayList<>();
        adapter.clear();
        tcds.open();
        List<TaskCategory> taskCategories = tcds.getAll();
        tcds.close();
        for (TaskCategory taskCategory : taskCategories) {
            categoriesAsStrings.add(taskCategory.getName());
        }
        adapter.addAll(categoriesAsStrings);
        adapter.notifyDataSetChanged();
    }
}
