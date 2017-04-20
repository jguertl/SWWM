package at.sw2017.todo4u.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import at.sw2017.todo4u.model.TaskCategory;

public class TaskCategoriesDataSource extends AbstractDataSource<TaskCategory> {

    public TaskCategoriesDataSource(Context context) {
        super(context, new String[]{Todo4uContract.TaskCategory._ID, Todo4uContract.TaskCategory.NAME}, Todo4uContract.TaskCategory._TABLE_NAME);
    }

    @Override
    protected ContentValues getContentValues(TaskCategory object) {
		return null;
    }

    @Override
    protected TaskCategory cursorToObject(Cursor cursor) {
        return null;
    }

}
