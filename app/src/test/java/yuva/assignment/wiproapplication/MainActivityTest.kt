package yuva.assignment.wiproapplication

import android.os.Build
import com.nhaarman.mockitokotlin2.spy
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import yuva.assignment.wiproapplication.fragment.FactsFragment
import org.robolectric.Shadows
import yuva.assignment.wiproapplication.model.Country
import org.mockito.Mock
import yuva.assignment.wiproapplication.model.Facts
import butterknife.internal.ListenerClass.NONE
import io.reactivex.Observable
import org.mockito.Mockito.*
import yuva.assignment.wiproapplication.network.Api
import org.mockito.Mockito.`when`
import retrofit2.Call


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [27])
class MainActivityTest {

    lateinit var factsFragment: FactsFragment
    lateinit var mainActivity: MainActivity

    @Mock
    lateinit var apiService: Api

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainActivity = Robolectric.buildActivity(MainActivity::class.java).create().resume().get()
        factsFragment = mainActivity.supportFragmentManager.findFragmentById(R.id.fragmentContainer) as FactsFragment
    }

    @Test
    fun activityShouldNotBeNull(){
        assertNotNull(mainActivity)
    }

    @Test
    @Throws(Exception::class)
    fun shouldHaveFactsFragment() {
        assertNotNull(factsFragment)
    }

    @Test
    fun serviceDataSetup() {
        val countryModel = Country(null, null)
        `when`(apiService.countryFacts)
                .thenReturn(Observable.just(countryModel))
    }

    @Test
    fun actionBarTitleCheck() {
        val countryModel = Country("India", null)
        `when`(apiService.countryFacts)
                .thenReturn(Observable.just(countryModel))
        factsFragment.updateAdapter(countryModel)
        assertEquals(mainActivity.actionBar?.title, countryModel.title)
    }

//    @Test
//    fun actionBarRecyclerDataCheck() {
//        val facts  = Facts("Hyderabad", "charminar", "http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg")
//        val factsList = ArrayList<Facts>()
//
//        factsList.add(facts)
//
//        val countryModel = Country("India", factsList)
//        `when`(apiService.countryFacts)
//                .thenReturn(Observable.just(countryModel))
//        factsFragment.updateAdapter(countryModel)
////        factsFragment.view.findViewById()
//        assertEquals(mainActivity.actionBar?.title, countryModel.title)
//    }

    @After
    fun tearDown() {
    }
}