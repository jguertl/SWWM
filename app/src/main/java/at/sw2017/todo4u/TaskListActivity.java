package at.sw2017.todo4u;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import at.sw2017.todo4u.adapter.TaskAdapter;
import at.sw2017.todo4u.database.TaskCategoriesDataSource;
import at.sw2017.todo4u.database.TasksDataSource;
import at.sw2017.todo4u.model.Task;
import at.sw2017.todo4u.model.TaskCategory;


public class TaskListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private ListView task_list_view;
    private ArrayAdapter<Task> adapter;
    private TasksDataSource tds;
    private SearchView searchView;

    private long categoryId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);


        categoryId = getIntent().getLongExtra("id", 0);


        tds = new TasksDataSource(this);

        //insert Test Data
//        tds.open();
//        if(tds.getTasksInCategory(categoryId).isEmpty()) {
//            TaskCategory cat = null;
//            if(categoryId != 0) {
//                TaskCategoriesDataSource tcDs = new TaskCategoriesDataSource(this);
//                tcDs.openReadonly();
//                cat = tcDs.getById(categoryId);
//                tcDs.close();
//            }
//            Task t1 = new Task("TestTask 1");
//            t1.setCategory(cat);
//            Task t2 = new Task("TestTask 222");
//            t2.setCategory(cat);
//
//            tds.insertOrUpdate(t1);
//            tds.insertOrUpdate(t2);
//        }
//
//        tds.close();


        tds.open();
        List<Task> tasks = tds.getTasksInCategory(categoryId);
        tds.close();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_task);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Tasks");

        adapter = new TaskAdapter(this, android.R.layout.simple_list_item_1, tasks);
        task_list_view = (ListView) findViewById(R.id.task_list_view);
        task_list_view.setAdapter(adapter);

        task_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = parent.getItemAtPosition(position);
                if (o instanceof Task) {
                    Task t = (Task) o;
                    Toast.makeText(getApplicationContext(), "Selected task " + t.getId() + ": " + t.getTitle(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tasklist, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.bt_search_task).getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.bt_add_task) {
//            Intent intent = new Intent(TaskListActivity.this, TaskAddActivity.class);
//            startActivity(intent);
        } else if (id == R.id.bt_search_task) {
            return true;
        } else if (id == android.R.id.home) {
            Intent intent = new Intent(TaskListActivity.this, CategoryListActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TaskListActivity.this, CategoryListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateData();
    }

    public void updateData() {
        tds.open();
        adapter.clear();
        adapter.addAll(tds.getTasksInCategory(categoryId));
        tds.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.clear();
        tds.open();
        adapter.addAll(tds.getTasksInCategoryWithTitle(categoryId, newText));
        tds.close();
        adapter.notifyDataSetChanged();
        return false;
    }
}
