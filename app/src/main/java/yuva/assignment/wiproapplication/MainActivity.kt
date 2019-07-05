package yuva.assignment.wiproapplication

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import android.os.Bundle
import android.widget.FrameLayout

import butterknife.BindView
import butterknife.ButterKnife
import yuva.assignment.wiproapplication.fragment.FactsFragment


class MainActivity : AppCompatActivity(), FactsFragment.CountrySelectedListener {
    var actionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        actionBar = supportActionBar
        val fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.fragmentContainer)
        if (fragment == null) {
            fragment = FactsFragment()
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit()
        }
    }

    override fun onCountrySelected(title: String?) {
        actionBar!!.title = title
    }



}
