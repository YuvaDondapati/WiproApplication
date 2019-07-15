package yuva.assignment.wiproapplication.adapter

import android.content.Context
import org.junit.After
import org.junit.Before
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import yuva.assignment.wiproapplication.model.Facts
import org.junit.Test
import org.mockito.MockitoAnnotations
import org.robolectric.RuntimeEnvironment.application
import android.widget.LinearLayout
import yuva.assignment.wiproapplication.R.*

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [27])
class CountryFactsAdapterTest {

    lateinit var context: Context
    var factsList = ArrayList<Facts>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val application = application
        assertNotNull(application)
        context = application
    }

    @Test
    fun test_getItemCount() {
        val facts1 = Facts("Beavers", "description", "http://1.bp.blogspot.com/_VZVOmYVm68Q/SMkzZzkGXKI/AAAAAAAAADQ/U89miaCkcyo/s400/the_golden_compass_still.jpg")
        val facts2 = Facts("Transportation", "description", "http://1.bp.blogspot.com/_VZVOmYVm68Q/SMkzZzkGXKI/AAAAAAAAADQ/U89miaCkcyo/s400/the_golden_compass_still.jpg")
      factsList.add(facts1)
        factsList.add(facts2)
        val adapter = CountryFactsAdapter(factsList, context)
        //initial state
        val initialExpected = 2
        val initialActual = adapter.getItemCount()
        assertEquals(initialExpected, initialActual)
    }
    @Test
    fun testData(){
        val facts = Facts("Beavers", "description", "http://1.bp.blogspot.com/_VZVOmYVm68Q/SMkzZzkGXKI/AAAAAAAAADQ/U89miaCkcyo/s400/the_golden_compass_still.jpg")
        factsList.add(facts)
        assertTrue(true)
    }

    @Test
    fun testRecyclerViewTitle() {
        val facts1 = Facts("Beavers", "description", "http://1.bp.blogspot.com/_VZVOmYVm68Q/SMkzZzkGXKI/AAAAAAAAADQ/U89miaCkcyo/s400/the_golden_compass_still.jpg")

        factsList.add(facts1)

        val adapter = CountryFactsAdapter(factsList, context)
        val parent = LinearLayout(context)
        val childViewHolder = adapter.onCreateViewHolder(parent, 0)
        adapter.onBindViewHolder(childViewHolder, 0)

        assertEquals(childViewHolder.movieTitle.text, "Beavers")
    }


    @Test
    fun testImageviewNullCheck() {
        val facts1 = Facts("Beavers", "description", null)
        val placeholderDrawable = context.resources.getDrawable(drawable.placeholder)
        factsList.add(facts1)

        val adapter = CountryFactsAdapter(factsList, context)
        val parent = LinearLayout(context)
        val childViewHolder = adapter.onCreateViewHolder(parent, 0)
        adapter.onBindViewHolder(childViewHolder, 0)

        assertEquals(childViewHolder.imageView.drawable.constantState,placeholderDrawable.constantState)
    }
}