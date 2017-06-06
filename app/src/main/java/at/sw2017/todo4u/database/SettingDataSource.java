package at.sw2017.todo4u.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.List;

import at.sw2017.todo4u.model.Setting;

/**
 * Created by mRozwold on 06.06.2017.
 */

public class SettingDataSource extends AbstractDataSource<Setting> {

    public SettingDataSource(Context context) {
        super(context, new String[]{
                    Todo4uContract.Setting._ID,
                    Todo4uContract.Setting.NAME,
                    Todo4uContract.Setting.BOOLEAN
                },
                Todo4uContract.Setting._TABLE_NAME);
    }

    @Override
    protected ContentValues getContentValues(Setting object) {
        ContentValues values = new ContentValues();
        values.put(Todo4uContract.Setting.NAME, object.getName());
        values.put(Todo4uContract.Setting.BOOLEAN, object.getName());

        return values;
    }

    @Override
    protected Setting cursorToObject(Cursor cursor) {
        Setting obj = new Setting();
        obj.setId(cursor.getLong(0));
        obj.setName(cursor.getString(1));
        obj.setBool(cursor.getInt(2));
        return obj;
    }

    public List<Setting> getSettingsWithName(String name) {
        return getSelection(
                Todo4uContract.Setting.NAME + " LIKE ?",
                new String[]{"%" + name + "%"},
                null, null, null);
    }
}
