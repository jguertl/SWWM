package at.sw2017.todo4u.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import at.sw2017.todo4u.model.BaseModel;

abstract class AbstractDataSource<T extends BaseModel> {
    // Database fields
    protected SQLiteDatabase database;
    private Todo4uDbHelper dbHelper;
    protected String[] allColumns;
    protected String tableName;

    public AbstractDataSource(Context context, String[] allColumns, String tableName) {
        dbHelper = new Todo4uDbHelper(context);
        this.allColumns = allColumns;
        this.tableName = tableName;
    }

    public void open() throws SQLException {
    }

    public void openReadonly() throws SQLException {
    }

    public boolean isDatabaseOpen() {
		return false;
    }

    public boolean isDatabaseWriteable() {
		return false;
    }

    public void close() {
    }

    public boolean insertOrUpdate(T object) {
		return false;
    }

    protected abstract ContentValues getContentValues(T object);

    public T getById(long id) {
        return null;
    }

    public boolean delete(T obj) {
        return false;
    }

    public boolean delete(long id) {
        return false;
    }

    public List<T> getAll() {
        return null;
    }

    protected abstract T cursorToObject(Cursor cursor);

    protected Context getContext() {
        return dbHelper.getContext();
    }
}
