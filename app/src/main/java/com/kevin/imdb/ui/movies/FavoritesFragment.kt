package com.kevin.imdb.ui.movies

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.kevin.imdb.BaseFragment

import com.kevin.imdb.R
import com.kevin.imdb.api.response.GetMoviesResponse
import com.kevin.imdb.api.response.MovieResponse
import com.kevin.imdb.database.MovieViewModel
import com.kevin.imdb.database.entities.Movie
import com.kevin.imdb.utils.InternetUtils
import kotlinx.android.synthetic.main.fragment_all_movies.*
import kotlinx.android.synthetic.main.fragment_favorites.*


class FavoritesFragment : BaseFragment() {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieAdapter : MoviesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewModel = run{
            ViewModelProviders.of(this).get(MovieViewModel::class.java)
        }
        addObserver()
        getMovies()
    }

    fun addObserver(){
        val observer2 = Observer<List<Movie>>{movies ->

            for(movie in movies){
                var movieResponse = MovieResponse(
                    0.0,
                    movie.votes,
                    movie.poster,
                    movie.poster,
                    movie.id,
                    movie.title,
                    movie.title,
                    movie.description,
                    ""
                )
                movieResponse.favorite = 1
                movieAdapter.addMovie(movieResponse)
            }


        }

        movieViewModel.favoriteMovies.observe(this, observer2)
    }

    private fun getMovies(){

            var thread = Thread {
                var movies = ArrayList<MovieResponse>()
                if (movieViewModel.getStoredFavoritesMovies() != null) {
                    var movies1 = movieViewModel.getStoredFavoritesMovies()
                    movies1!!.forEach { movie ->
                        var movieResponse = MovieResponse(
                            0.0,
                            movie.votes,
                            movie.poster,
                            movie.poster,
                            movie.id,
                            movie.title,
                            movie.title,
                            movie.description,
                            ""
                        )
                        movieResponse.favorite = 1
                        movies.add(movieResponse)

                    }
                    initRecyclerMovies(movies)
                }
            }

            thread.start()

    }

    private fun initRecyclerMovies(items : ArrayList<MovieResponse>){
        recycler_favorites_movies.layoutManager = GridLayoutManager(context, 3)
        recycler_favorites_movies.setHasFixedSize(true)
        movieAdapter = MoviesAdapter(context!!, items, Glide.with(this) , object : MoviesAdapter.MovieSelected{

            override fun onSelected(movieResponse: MovieResponse) {
                startActivity(Intent(activity, MovieDetailActivity::class.java).apply {
                    putExtra("id", movieResponse.id)
                })
            }

            override fun onFavorite(movieResponse: MovieResponse, position: Int) {
                movieAdapter!!.setFavorite(position)
                if(movieResponse.favorite == 1) {
                    movieViewModel.updateMovie(createMovie(movieResponse,"0"))
                    movieAdapter.deleteMovie(position)
                }else{
                    movieViewModel.updateMovie(createMovie(movieResponse,"1"))
                }
            }

        })
        recycler_favorites_movies.adapter = movieAdapter
    }

    private fun createMovie(movieResponse: MovieResponse, isFav : String) : Movie {
        var movie = Movie(  movieResponse.id,
            movieResponse.original_title,
            movieResponse.overview,
            movieResponse.vote_count,
            movieResponse.poster_path,
            isFav,0)

        return movie
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            FavoritesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
