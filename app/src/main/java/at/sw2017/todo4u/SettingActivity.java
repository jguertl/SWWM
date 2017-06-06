package at.sw2017.todo4u;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

import at.sw2017.todo4u.database.SettingDataSource;
import at.sw2017.todo4u.model.Setting;

public class SettingActivity extends AppCompatActivity {

    private SettingDataSource sds;
    private Spinner spinnerColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        sds = new SettingDataSource(this);
        sds.open();
        Setting setting = new Setting("None");
        setting.setBool(1);
        sds.insertOrUpdate(setting);
        setting = new Setting("Colorful");
        setting.setBool(0);
        sds.insertOrUpdate(setting);
        setting = new Setting("Gray and White");
        setting.setBool(0);
        sds.insertOrUpdate(setting);
        List<Setting> list = sds.getSettingsWithName("");
        sds.close();

        spinnerColor = (Spinner) findViewById(R.id.spinner);


        spinnerColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                sds.open();
                List<Setting> list = sds.getSettingsWithName("");
                for (Setting s : list) {
                    s.setBool(0);
                    sds.insertOrUpdate(s);
                }

                sds.close();
                sds.open();
                list = sds.getSettingsWithName(item);
                list.get(0).setBool(1);
                sds.insertOrUpdate(list.get(0));
                sds.close();
                sds.open();
                list = sds.getSettingsWithName("");
                sds.close();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeColorSettings() {


        sds.open();
        List<Setting> test = sds.getSettingsWithName("switchColorCategory");
        test.get(0).setBool(0);
//        Setting setting = new Setting(categoryName);
//        if (sds.insertOrUpdate(taskCategory)) {
//            Toast.makeText(
//                    getApplicationContext(),
//                    getString(R.string.category_add_success, categoryName),
//                    Toast.LENGTH_SHORT
//            ).show();
//        } else {
//            Toast.makeText(
//                    getApplicationContext(),
//                    getString(R.string.category_add_error_database, categoryName),
//                    Toast.LENGTH_SHORT
//            ).show();
//        }
        sds.close();
    }
}
