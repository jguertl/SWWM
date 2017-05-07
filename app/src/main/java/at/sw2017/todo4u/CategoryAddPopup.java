package at.sw2017.todo4u;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.WindowDecorActionBar;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import at.sw2017.todo4u.database.TaskCategoriesDataSource;
import at.sw2017.todo4u.model.TaskCategory;

public class CategoryAddPopup extends AppCompatActivity implements View.OnClickListener{

    private EditText tx_new_category;
    TaskCategoriesDataSource tcds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tcds = new TaskCategoriesDataSource(this);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_category_add_popup);
        this.setFinishOnTouchOutside(false);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int Width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (Width * .8), (int) (height * .50));
    }


    public void saveNewCategory(View v){

        tx_new_category = (EditText) findViewById(R.id.tx_new_category);

        String help_str = tx_new_category.getText().toString();

        if(help_str.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "At least one character, please", Toast.LENGTH_SHORT).show();
        } else {
            tcds.open();
            List<TaskCategory> taskCategories = tcds.getAll();
            for (TaskCategory taskCategory : taskCategories) {
                if (taskCategory.getName().equals(help_str)) {
                    Toast.makeText(getApplicationContext(), "Category already exist", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
            }

            TaskCategory taskCategory = new TaskCategory(help_str);
            tcds.insertOrUpdate(taskCategory);
            tcds.close();
            Toast.makeText(getApplicationContext(), "Name of caegory: " + help_str, Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    public void cancelPopup(View v) {
        finish();
    }

    @Override
    public void onClick(View v) {

    }

}
