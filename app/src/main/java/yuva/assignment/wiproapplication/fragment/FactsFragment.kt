package yuva.assignment.wiproapplication.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import yuva.assignment.wiproapplication.adapter.CountryFactsAdapter
import yuva.assignment.wiproapplication.app.MyApplication
import yuva.assignment.wiproapplication.model.Country
import yuva.assignment.wiproapplication.utils.MyDividerItemDecoration
import yuva.assignment.wiproapplication.viewmodel.FactsViewModel
import yuva.assignment.wiproapplication.R
import yuva.assignment.wiproapplication.utils.NetworkUtils
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */

class FactsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var adapter: CountryFactsAdapter? = null
    private var mCallback: CountrySelectedListener? = null

    @BindView(R.id.rvFacts)
    lateinit var rvFacts: RecyclerView

    @BindView(R.id.imgnointernet)
    lateinit var imgNoInternet: ImageView

    @BindView(R.id.swipe_container)
    lateinit var swipeContainer: SwipeRefreshLayout

    private var unbinder: Unbinder? = null
//    var model: FactsViewModel? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val model: FactsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(FactsViewModel::class.java)
    }

    // Container Activity must implement this interface
    interface CountrySelectedListener {
        fun onCountrySelected(title: String?)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_factlist, container, false)
        // bind view using butter knife
        unbinder = ButterKnife.bind(this, view);
//        model = ViewModelProviders.of(this).get(FactsViewModel::class.java)
        rvFacts.layoutManager = LinearLayoutManager(activity)
        rvFacts.addItemDecoration(MyDividerItemDecoration(activity!!, LinearLayoutManager.VERTICAL, 16))
        // SwipeRefreshLayout
        // swipeContainer = view.findViewById<View>(R.id.swipe_container) as SwipeRefreshLayout
        swipeContainer.setOnRefreshListener(this)
        swipeContainer.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark)

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeContainer.post {
            // Fetching data from server
            loadDataToViewModel()
        }
        return view
    }


    override fun onAttach(context: Context?) {
//        AndroidSupportInjection.inject(this)
        MyApplication.netComponent?.inject(this)
        super.onAttach(context)
        var activity: Activity? = null

        if (context is Activity) {
            activity = context
        }
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = activity as CountrySelectedListener?
        } catch (e: ClassCastException) {
            throw ClassCastException(activity!!.toString() + " must implement OnHeadlineSelectedListener")
        }
    }

    public fun loadDataToViewModel() {
        val networkUtils = context?.let { NetworkUtils(it) }

        if (!networkUtils!!.isNetworkAvailable()) {
            swipeContainer.isRefreshing = false
            showNoInternet()
            return;
        }
        if (model.isDataAvailableViewModel) {
            showRecyclerView()
            model.data?.observe(this, Observer { country -> updateAdapter(country) })
        } else {
            getUpdatedData()
        }
    }

    private fun getUpdatedData() {
        showRecyclerView()
        swipeContainer.isRefreshing = true
        model.loadFacts().observe(this, Observer { country ->
            updateAdapter(country)
        })
    }


    private fun showRecyclerView() {
        imgNoInternet.visibility = View.GONE
        rvFacts.visibility = View.VISIBLE
    }

    private fun showNoInternet() {
        imgNoInternet.visibility = View.VISIBLE
        rvFacts.visibility = View.GONE
    }


    public fun updateAdapter(country: Country?) {
        adapter = CountryFactsAdapter(country?.rows, activity!!.applicationContext)
        rvFacts.adapter = adapter
        mCallback!!.onCountrySelected(country?.title)
        swipeContainer.isRefreshing = false
    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    override fun onRefresh() {
        // Fetching data from server
        loadDataToViewModel()
    }

    override fun onResume() {
        super.onResume()
        MyApplication.instance
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder!!.unbind()
    }

}