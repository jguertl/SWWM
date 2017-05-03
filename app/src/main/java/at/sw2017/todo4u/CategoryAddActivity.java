package at.sw2017.todo4u;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import at.sw2017.todo4u.database.TaskCategoriesDataSource;
import at.sw2017.todo4u.model.TaskCategory;

public class CategoryAddActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);
    }

    public void onSaveClick(View v) {
        EditText tfName = (EditText) findViewById(R.id.category_add_tfname);
        String name = tfName.getText().toString().trim();
        if(name.length() == 0) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.category_add_alert_name_title)
                    .setMessage(R.string.category_add_alert_name_msg)
                    .show();
            tfName.requestFocus();
        } else {
            TaskCategory tc = new TaskCategory(name);
            TaskCategoriesDataSource tcDs = new TaskCategoriesDataSource(this);
            tcDs.open();
            if(!tcDs.insertOrUpdate(tc)) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.category_add_alert_database_title)
                        .setMessage(R.string.category_add_alert_database_msg)
                        .show();
            } else {
                finish();
            }
            tcDs.close();
        }
    }
}
