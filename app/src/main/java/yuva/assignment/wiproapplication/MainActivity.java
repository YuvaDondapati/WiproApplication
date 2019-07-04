package yuva.assignment.wiproapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import yuva.assignment.wiproapplication.fragment.FactsFragment;


public class MainActivity extends AppCompatActivity implements FactsFragment.CountrySelectedListener {
//    private ActionBar actionBar;
    @BindView(R.id.fragmentContainer)
    FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        actionBar = getSupportActionBar();
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = new FactsFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }

    }

    public void onCountrySelected(String title) {
//        actionBar.setTitle(title);
        System.out.println("*********** title "+ title);
    }


}
