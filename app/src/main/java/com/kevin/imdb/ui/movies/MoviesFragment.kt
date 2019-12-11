package com.kevin.imdb.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.kevin.imdb.R
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment() {

    private var moviesPagerAdapter : MoviesPagerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTabLayout()
        initViewPager()
        setTabChangeListener()


    }

    private fun initViewPager() {

        moviesPagerAdapter = MoviesPagerAdapter(childFragmentManager)
        val limit = if (moviesPagerAdapter!!.getCount() > 1) moviesPagerAdapter!!.getCount() - 1 else 1
        viewPager.adapter = moviesPagerAdapter
        viewPager.offscreenPageLimit = limit
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))


    }

    private fun initTabLayout() {

        tablayout.addTab(tablayout.newTab().setText("Todas"))
        tablayout.addTab(tablayout.newTab().setText("Favoritos"))
        tablayout.tabGravity = TabLayout.GRAVITY_FILL
        tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
        tablayout.setTabTextColors(ContextCompat.getColor(context!!, R.color.colorPrimary), ContextCompat.getColor(context!!, R.color.colorPrimaryDark))
    }

    private fun setTabChangeListener() {

        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.setCurrentItem(tab!!.position)

            }

        })
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            MoviesFragment().apply {

            }
    }
}
