package at.sw2017.todo4u;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

import at.sw2017.todo4u.database.TaskCategoriesDataSource;
import at.sw2017.todo4u.database.TasksDataSource;
import at.sw2017.todo4u.model.Task;
import at.sw2017.todo4u.model.TaskCategory;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class DatabaseTest {
    private Context instrumentationCtx;

    @Before
    public void setup() {
        instrumentationCtx = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void simpleInsertAndRead() {
        String categoryName = "My Test Category";
        String taskTitle = "My First TEST TASK";
        String taskDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam et tempus magna. Fusce dui odio, cursus quis nisi ac, luctus iaculis nulla. In mollis eget nibh et mattis. Morbi quis sapien quam. Nullam a ullamcorper magna. Integer pellentesque ipsum facilisis eros hendrerit posuere sed in ante. Donec sodales massa non neque vulputate, sagittis ullamcorper eros vehicula.";
        Calendar dueCal = Calendar.getInstance();
        dueCal.set(2017, 5, 10, 9, 30);
        Date creationDate = new Date();

        TaskCategory cat = new TaskCategory(categoryName);
        Task task = new Task();
        task.setCategory(cat);
        task.setTitle(taskTitle);
        task.setDescription(taskDescription);
        task.setDueDate(dueCal.getTime());
        task.setCreationDate(creationDate);

        TaskCategoriesDataSource tcDs = new TaskCategoriesDataSource(instrumentationCtx);
        tcDs.open();
        assertTrue(tcDs.insertOrUpdate(cat));
        //tcDs.insertOrUpdate(cat);
        tcDs.close();


        TasksDataSource tDs = new TasksDataSource(instrumentationCtx);
        tDs.open();
        assertTrue(tDs.insertOrUpdate(task));

        Task dbTask = tDs.getById(task.getId());
        assertNotNull(dbTask);
        tDs.close();

        assertEquals(task.getId(), dbTask.getId());
        assertEquals(cat.getId(), dbTask.getCategory().getId());
        assertEquals(categoryName, dbTask.getCategory().getName());
        assertEquals(taskTitle, dbTask.getTitle());
        assertEquals(taskDescription, dbTask.getDescription());
        assertEquals(dueCal.getTime(), dbTask.getDueDate());
        assertEquals(creationDate, dbTask.getCreationDate());
        assertNull(dbTask.getReminderDate());


    }
}
