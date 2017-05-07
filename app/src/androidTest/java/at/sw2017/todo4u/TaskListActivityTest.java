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
import static org.hamcrest.Matchers.*;

public class TaskListActivityTest {
    private Context context;
    private TaskCategoriesDataSource tcDs;
    private TasksDataSource tDs;


    @Rule
    public ActivityTestRule<CategoryListActivity> mActivityRule = new ActivityTestRule(CategoryListActivity.class);


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
        Task t1 = new Task("TestTask 1");
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


        //workaround to call onResume methode
        onView(withId(R.id.bt_add_category)).perform(click());
        onView(withId(R.id.category_add_btCancel)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.category_list_view)).atPosition(0).perform(click());
        onView(withId(R.id.task_list_view)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.task_list_view)).atPosition(0)
                .onChildView(withText("TestTask 1")).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.task_list_view)).atPosition(1)
                .onChildView(withText("My second Task")).check(matches(isDisplayed())).perform(click());

        onView(withText(allOf(startsWith("Selected task "), endsWith("My second Task"))))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

    }

    @Test
    public void testSearch() {
        TaskCategory cat = new TaskCategory("MyTaskCategory");
        tcDs.open();
        tcDs.insertOrUpdate(cat);
        tcDs.close();
        Task t1 = new Task("test");
        t1.setCategory(cat);
        Task t2 = new Task("tes");
        t2.setCategory(cat);
        Task t3 = new Task("te");
        t3.setCategory(cat);
        Task t4 = new Task("t");
        t4.setCategory(cat);

        tDs.open();
        tDs.insertOrUpdate(t1);
        tDs.insertOrUpdate(t2);
        tDs.insertOrUpdate(t3);
        tDs.insertOrUpdate(t4);
        tDs.close();


        //workaround to call onResume methode
        onView(withId(R.id.bt_add_category)).perform(click());
        onView(withId(R.id.category_add_btCancel)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.category_list_view)).atPosition(0).perform(click());
        onView(withId(R.id.task_list_view)).check(matches(isDisplayed()));



        onView(withId(R.id.bt_search_task)).perform(click());
        onView(withId(android.support.design.R.id.search_src_text))
                .perform(typeText("t"), closeSoftKeyboard());
        onData(anything()).inAdapterView(withId(R.id.task_list_view)).atPosition(0).onChildView(withText("test")).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.task_list_view)).atPosition(1).onChildView(withText("tes")).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.task_list_view)).atPosition(2).onChildView(withText("te")).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.task_list_view)).atPosition(3).onChildView(withText("t")).check(matches(isDisplayed()));

        onView(withId(android.support.design.R.id.search_src_text))
                .perform(typeText("e"), closeSoftKeyboard());
        onData(anything()).inAdapterView(withId(R.id.task_list_view)).atPosition(0).onChildView(withText("test")).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.task_list_view)).atPosition(1).onChildView(withText("tes")).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.task_list_view)).atPosition(2).onChildView(withText("te")).check(matches(isDisplayed()));

        onView(withId(android.support.design.R.id.search_src_text))
                .perform(typeText("s\n"), closeSoftKeyboard());
        onData(anything()).inAdapterView(withId(R.id.task_list_view)).atPosition(0).onChildView(withText("test")).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.task_list_view)).atPosition(1).onChildView(withText("tes")).check(matches(isDisplayed()));

        onView(withId(android.support.design.R.id.search_src_text))
                .perform(typeText("t"), closeSoftKeyboard());
        onData(anything()).inAdapterView(withId(R.id.task_list_view)).atPosition(0).onChildView(withText("test")).check(matches(isDisplayed()));
    }


}