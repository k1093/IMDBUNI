package com.kevin.imdb.ui.movies

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MoviesPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {


    var allMoviewFragment =  AllMoviesFragment.newInstance()
    var favoritesFragment = FavoritesFragment.newInstance()



    override fun getItem(position: Int): Fragment {
        when(position){
            0->{
                return allMoviewFragment
            }
            1->{
                return favoritesFragment
            }
            else->{
                return allMoviewFragment
            }
        }
    }

    override fun getCount(): Int {

        return 2
    }

    fun getFragment(position: Int) : Fragment?{
        when(position){
            0->{
                return allMoviewFragment
            }
            1->{
                return favoritesFragment
            }
            else->{
                return null
            }
        }
    }


    override fun getPageTitle(position: Int): CharSequence {
        when(position){
            0->{
                return "Todos"
            }
            1->{
                return "Favoritas"
            }
            else->{
                return ""
            }
        }
    }
}