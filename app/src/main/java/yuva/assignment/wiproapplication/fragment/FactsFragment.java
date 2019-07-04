package yuva.assignment.wiproapplication.fragment;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import yuva.assignment.wiproapplication.R;
import yuva.assignment.wiproapplication.adapter.CountryFactsAdapter;
import yuva.assignment.wiproapplication.app.Constant;
import yuva.assignment.wiproapplication.app.MyApplication;
import yuva.assignment.wiproapplication.model.Country;
import yuva.assignment.wiproapplication.utils.MyDividerItemDecoration;
import yuva.assignment.wiproapplication.viewmodel.FactsViewModel;

/**
 * A simple {@link Fragment} subclass.
 */

public class FactsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private CountryFactsAdapter adapter;
    private CountrySelectedListener mCallback;
    @BindView(R.id.rvFacts)
    RecyclerView rvFacts;
    @BindView(R.id.imgnointernet)
    ImageView imgNoInternet;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    private Unbinder unbinder;
    private FactsViewModel model;


    // Container Activity must implement this interface
    public interface CountrySelectedListener {
        public void onCountrySelected(String Country);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_factlist, container, false);
        rvFacts = view.findViewById(R.id.rvFacts);
        model = ViewModelProviders.of(this).get(FactsViewModel.class);
        rvFacts.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFacts.addItemDecoration(new MyDividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        // SwipeRefreshLayout
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeContainer.post(new Runnable() {

            @Override
            public void run() {
                // Fetching data from server
                loadDataToViewModel();
            }
        });

        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a = null;

        if (context instanceof Activity) {
            a = (Activity) context;
        }
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (CountrySelectedListener) a;
        } catch (ClassCastException e) {
            throw new ClassCastException(a.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    private void loadDataToViewModel() {
        // Showing refresh animation before making http call

        System.out.println("********** model.isDataAvailableViewModel()"+model.isDataAvailableViewModel());
        if(model.isDataAvailableViewModel())
        {
            showRecyclerView();
//            showSnack(false);
            model.getData().observe(this, new Observer<Country>() {
                @Override
                public void onChanged(@Nullable Country country) {
                    updateAdapter(country);
                }
            });
        }
        else {
            getUpdatedData();
        }

    }

    private void getUpdatedData() {
        showRecyclerView();
        swipeContainer.setRefreshing(true);
        model.getFacts().observe(this, new Observer<Country>() {
            @Override
            public void onChanged(@Nullable Country country) {
                System.out.println("******** update country"+country);
                updateAdapter(country);
            }
        });
    }

    private void showNoConnection() {
        imgNoInternet.setVisibility(View.VISIBLE);
        rvFacts.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);
    }
    private void showRecyclerView() {
        imgNoInternet.setVisibility(View.GONE);
        rvFacts.setVisibility(View.VISIBLE);
    }
    private void updateAdapter(@Nullable Country country) {
        adapter = new CountryFactsAdapter(Arrays.asList(country.getRows()), getActivity().getApplicationContext());
        rvFacts.setAdapter(adapter);

        mCallback.onCountrySelected(country.getTitle());
        // Stopping swipe refresh
        swipeContainer.setRefreshing(false);
    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        // Fetching data from server
        loadDataToViewModel();
    }


    // Method to manually check connection status
//    private boolean checkConnection() {
//        return ConnectivityReceiver.isConnected();
//
//    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = Constant.INTERNET_CONNECTED;
            color = Color.WHITE;
        } else {
            message = Constant.INTERNET_NOT_CONNECTED;
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        // register connection status listener
        MyApplication.getInstance();
//        if (!checkConnection()) {
//            showSnack(false);
//        }

    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
//    @Override
//    public void onNetworkConnectionChanged(boolean isConnected) {
//        showSnack(isConnected);
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}