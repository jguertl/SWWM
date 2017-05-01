package at.sw2017.todo4u;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import at.sw2017.todo4u.database.TaskCategoriesDataSource;

public class CategoryAddPopup extends AppCompatActivity {

    private EditText tx_new_category;
    TaskCategoriesDataSource tcds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void saveNewCategory(View v) {
    }


    public void cancelPopup(View v) {
    }

}