package at.sw2017.todo4u;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import at.sw2017.todo4u.database.TaskCategoriesDataSource;
import at.sw2017.todo4u.model.TaskCategory;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CategoryAddTest {

    private Context instrumentationCtx;

    @Before
    public void setup() {
        instrumentationCtx = InstrumentationRegistry.getTargetContext();
    }

    @Rule
    public ActivityTestRule<CategoryAddActivity> mActivityRule = new ActivityTestRule<>(CategoryAddActivity.class);

    @Test
    public void categoryAddSuccess() {

        TaskCategoriesDataSource tcDs = new TaskCategoriesDataSource(instrumentationCtx);
        tcDs.open();
        int sizeBefore = tcDs.getAll().size();
        tcDs.close();

        String name = "A new category for my test.";

        onView(withId(R.id.category_add_tfname))
                .check(matches(isDisplayed()))
                .perform(typeText(name), closeSoftKeyboard());

        onView(withId(R.id.category_add_btSave))
                .perform(click());

        tcDs.open();
        List<TaskCategory> taskCategories = tcDs.getAll();
        assertEquals(sizeBefore + 1, taskCategories.size());
        assertEquals(name, taskCategories.get(taskCategories.size()-1).getName());
        tcDs.close();

    }

    @Test
    public void categoryAddNameEmpty() {
        TaskCategoriesDataSource tcDs = new TaskCategoriesDataSource(instrumentationCtx);
        tcDs.open();
        int sizeBefore = tcDs.getAll().size();
        tcDs.close();

        onView(withId(R.id.category_add_btSave)).perform(click());

        onView(withText(R.string.category_add_alert_name_msg)).check(matches(isDisplayed()));

        tcDs.open();
        assertEquals(sizeBefore, tcDs.getAll().size());
        tcDs.close();
    }

}
