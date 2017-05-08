package at.sw2017.todo4u;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

import at.sw2017.todo4u.database.TaskCategoriesDataSource;
import at.sw2017.todo4u.database.TasksDataSource;
import at.sw2017.todo4u.model.Task;
import at.sw2017.todo4u.model.TaskCategory;

public class TaskAddActivity extends AppCompatActivity {

    private EditText tx_title;
    private EditText tx_description;
    private EditText tx_dueDate;
    private EditText tx_reminderDate;
    private TasksDataSource tds;
    private long categoryId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        categoryId = getIntent().getLongExtra("id", 0);
        tds = new TasksDataSource(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkEmptyFields())
                    updateData();
            }
        });
    }

    private void updateData() {
        tds.open();
        TaskCategory cat = null;
        if(categoryId != 0) {
            TaskCategoriesDataSource tcDs = new TaskCategoriesDataSource(this);
            tcDs.openReadonly();
            cat = tcDs.getById(categoryId);
            tcDs.close();
        }
        Task t1 = new Task(tx_title.getText().toString());
        t1.setDescription(tx_description.getText().toString());
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            t1.setDueDate(format.parse(tx_dueDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            t1.setCreationDate(format.parse(String.valueOf(Calendar.getInstance().getTime())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            t1.setReminderDate(format.parse(tx_reminderDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        t1.setDescription(tx_description.getText().toString());
        t1.setCategory(cat);
        t1.setState(Task.State.OPEN);

        tds.insertOrUpdate(t1);
        tds.close();

        finish();
    }

    private boolean checkEmptyFields() {
        tx_title = (EditText) findViewById(R.id.Title);
        tx_description = (EditText) findViewById(R.id.Description);
        tx_dueDate = (EditText) findViewById(R.id.Date);
        tx_reminderDate = (EditText) findViewById(R.id.ReminderDate);

        if(tx_title.getText().toString().isEmpty() || tx_description.getText().toString().isEmpty()
                || tx_dueDate.getText().toString().isEmpty() || tx_reminderDate.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
