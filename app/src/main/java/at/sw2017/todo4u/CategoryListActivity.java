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

import java.util.List;

import at.sw2017.todo4u.adapter.CategoryAdapter;
import at.sw2017.todo4u.database.TaskCategoriesDataSource;
import at.sw2017.todo4u.model.TaskCategory;


public class CategoryListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private ListView category_list_view;
    private ArrayAdapter<TaskCategory> adapter;
    private TaskCategoriesDataSource tcds;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        tcds = new TaskCategoriesDataSource(this);
/*
        //insert Test Data
        TaskCategory testCategory1 = new TaskCategory("test");
        TaskCategory testCategory2 = new TaskCategory("awesome test");
        tcds.open();
        tcds.insertOrUpdate(testCategory1);
        tcds.insertOrUpdate(testCategory2);
        tcds.close();
        //++++++++++++++++++++++
*/
        tcds.open();
        List<TaskCategory> categories = tcds.getAll();
        tcds.close();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_category);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Categories");

        adapter = new CategoryAdapter(this, android.R.layout.simple_list_item_1, categories);
        category_list_view = (ListView) findViewById(R.id.category_list_view);
        category_list_view.setAdapter(adapter);
        category_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = parent.getItemAtPosition(position);
                if(o instanceof TaskCategory) {
                    long tcId = ((TaskCategory)o).getId();
                    Intent intent = new Intent(CategoryListActivity.this, TaskListActivity.class);
                    intent.putExtra("id", tcId);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.categorylist, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.bt_search_category).getActionView();
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
    protected void onResume() {
        super.onResume();
        updateData();
    }

    public void updateData() {
        adapter.clear();
        tcds.open();
        adapter.addAll(tcds.getAll());
        tcds.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        tcds.open();
        adapter.clear();
        adapter.addAll(tcds.getCategoriesWithName(newText));
        tcds.close();
        adapter.notifyDataSetChanged();
        return false;
    }
}
