package at.sw2017.todo4u.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import at.sw2017.todo4u.model.Task;
import at.sw2017.todo4u.model.TaskCategory;

public class TasksDataSource extends AbstractDataSource<Task> {
    public TasksDataSource(Context context) {
        super(context,
                new String[]{
                        Todo4uContract.Task._ID, Todo4uContract.Task.TITLE,
                        Todo4uContract.Task.DESCRIPTION, Todo4uContract.Task.DUE_DATE,
                        Todo4uContract.Task.CREATION_DATE, Todo4uContract.Task.REMINDER_DATE,
                        Todo4uContract.Task.CATEGORY_ID, Todo4uContract.Task.STATE
                },
                Todo4uContract.Task._TABLE_NAME
        );
    }

    @Override
    protected ContentValues getContentValues(Task task) {
		return null;
    }

    @Override
    protected Task cursorToObject(Cursor cursor) {
		return null;
    }

    public List<Task> getTasksInCategory(TaskCategory category) {
		return null;
    }

    public List<Task> getTasksInCategory(long categoryId) {
        return null;
    }
}
