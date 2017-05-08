package at.sw2017.todo4u;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;

import at.sw2017.todo4u.database.TaskCategoriesDataSource;
import at.sw2017.todo4u.database.TasksDataSource;
import at.sw2017.todo4u.database.Todo4uContract;
import at.sw2017.todo4u.model.Task;
import at.sw2017.todo4u.model.TaskCategory;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.*;

public class AddTaskTest {
    private Context context;
    private TaskCategoriesDataSource tcDs;
    private TasksDataSource tDs;


    @Rule
    public ActivityTestRule<TaskAddActivity> mActivityRule = new ActivityTestRule(TaskAddActivity.class);


    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
        tcDs = new TaskCategoriesDataSource(context);
        tDs = new TasksDataSource(context);

        //clear database before test
        tDs.open();
        tDs.getDatabase().execSQL("DELETE FROM " + Todo4uContract.Task._TABLE_NAME);
        tDs.getDatabase().execSQL("DELETE FROM " + Todo4uContract.TaskCategory._TABLE_NAME);
        tDs.close();
    }

    @After
    public void tearDown() {
        //clear database after test
        tDs.open();
        tDs.getDatabase().execSQL("DELETE FROM " + Todo4uContract.Task._TABLE_NAME);
        tDs.getDatabase().execSQL("DELETE FROM " + Todo4uContract.TaskCategory._TABLE_NAME);
        tDs.close();
    }


    @Test
    public void openTasklistOfCategory() {
        TaskCategory cat = new TaskCategory("MyTaskCategory");
        tcDs.open();
        tcDs.insertOrUpdate(cat);
        tcDs.close();
        Task t1 = new Task("AddTask");
        t1.setState(Task.State.IN_PROGRESS);
        t1.setCategory(cat);
        Calendar cal = Calendar.getInstance();
        cal.set(2017, 7, 2, 10, 30);
        t1.setDueDate(cal.getTime());
        Task t2 = new Task("My second Task");
        t2.setState(Task.State.OPEN);
        t2.setCategory(cat);

        tDs.open();
        tDs.insertOrUpdate(t1);
        tDs.insertOrUpdate(t2);
        tDs.close();

        tDs.open();
        assertEquals(tDs.getAll().size(),2);
        tDs.close();

        //TODO workaround and further testing with activity
    }

}