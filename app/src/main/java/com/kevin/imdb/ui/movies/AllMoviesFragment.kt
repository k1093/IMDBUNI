package com.kevin.imdb.ui.movies

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.kevin.imdb.BaseFragment

import com.kevin.imdb.R
import com.kevin.imdb.api.OnResponseListener
import com.kevin.imdb.api.response.ErrorResponse
import com.kevin.imdb.api.response.GetMoviesResponse
import com.kevin.imdb.api.response.MovieResponse
import com.kevin.imdb.database.MovieViewModel
import com.kevin.imdb.database.entities.Movie
import com.kevin.imdb.movies.MoviesPresenter
import com.kevin.imdb.utils.InternetUtils
import kotlinx.android.synthetic.main.fragment_all_movies.*
import android.widget.TextView
import android.graphics.Color
import androidx.databinding.adapters.SearchViewBindingAdapter
import android.text.InputFilter
import android.widget.EditText
import androidx.core.content.ContextCompat


class AllMoviesFragment : BaseFragment(), OnResponseListener{

    var moviesPresenter : MoviesPresenter? = null
    var getMoviesResponse : GetMoviesResponse? = null
    var currentPage = 1
    private var movieAdapter : MoviesAdapter? = null
    private lateinit var movieViewModel: MovieViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        moviesPresenter = MoviesPresenter(activity!!, context!!)
        moviesPresenter!!.setOnResponseListener(this)

        movieViewModel = run{
            ViewModelProviders.of(this).get(MovieViewModel::class.java)
        }

        return inflater.inflate(R.layout.fragment_all_movies, container, false)
    }

    private fun getMovies(){
        if(InternetUtils.isInternetAvailable(activity!!)) {
            showProgress()
            moviesPresenter!!.getMovies(currentPage)
        }else{
            var thread = Thread {
                var movies = ArrayList<MovieResponse>()
                if (movieViewModel.getStoredMovies() != null) {
                    var movies1 = movieViewModel.getStoredMovies()
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
                        movies.add(movieResponse)

                    }
                    getMoviesResponse = GetMoviesResponse(1, 1, 1, movies)
                    verifyFavorites(getMoviesResponse!!.results)
                    initReyclerMovies()
                }
            }

            thread.start()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linear_load_more.setOnClickListener(View.OnClickListener {
            showProgress()
            moviesPresenter!!.getMovies(currentPage)
        })

        addObserver()

        getMovies()

        search_movies.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText.isNullOrEmpty()){
                    linear_load_more.visibility = View.VISIBLE
                }else{
                    linear_load_more.visibility = View.GONE
                }
                movieAdapter!!.filter.filter(newText)

                return false
            }

        })

        val editSearchId = resources.getIdentifier("android:id/search_src_text", null, null)
        var editSearch = search_movies.findViewById<EditText>(editSearchId)
        editSearch!!.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(30))
        editSearch!!.setTextColor(ContextCompat.getColor(context!!, com.kevin.imdb.R.color.color_white))

        val id = resources.getIdentifier("android:id/search_src_text", null, null)
        val textView = search_movies.findViewById(id) as TextView
        textView.setTextColor(ContextCompat.getColor(context!!, com.kevin.imdb.R.color.color_white))


    }


    override fun onResponseSuccess(code: Int, response: Any) {
        dismissProgress()
        if(response is GetMoviesResponse){
            currentPage++
            if(movieAdapter == null) {
                getMoviesResponse = response
                verifyFavorites(getMoviesResponse!!.results!!)
                initReyclerMovies()
            }else{
                var newItems = (response as GetMoviesResponse).results
                verifyFavorites(newItems)
                movieAdapter!!.setNewMovies(newItems)
            }

            addAllMovies()
        }
    }

    override fun onResponseHasErrors(code: Int, errorResponse: ErrorResponse) {
        dismissProgress()
        this.httpCodeHandler.showHttpMessage(code,null)
    }

    override fun onResponseFailure() {
        dismissProgress()
        this.httpCodeHandler.showGenerarlFailureMessage(null)
    }

    private fun initReyclerMovies(){
        recycler_all_movies.layoutManager = GridLayoutManager(context, 3)
        recycler_all_movies.setHasFixedSize(true)
        movieAdapter = MoviesAdapter(context!!, getMoviesResponse!!.results, Glide.with(this) , object : MoviesAdapter.MovieSelected{

            override fun onSelected(movieResponse: MovieResponse) {
                startActivity(Intent(activity, MovieDetailActivity::class.java).apply {
                    putExtra("id", movieResponse.id)
                })
            }

            override fun onFavorite(movieResponse: MovieResponse, position: Int) {
                movieAdapter!!.setFavorite(position)
                if(movieResponse.favorite == 1) {
                    movieViewModel.updateMovie(createMovie(movieResponse,"0"))
                }else{
                    movieViewModel.updateMovie(createMovie(movieResponse,"1"))
                }
            }

        })
        recycler_all_movies.adapter = movieAdapter



    }

    private fun createMovie(movieResponse: MovieResponse, isFav : String) : Movie{
        var movie = Movie(  movieResponse.id,
            movieResponse.original_title,
            movieResponse.overview,
            movieResponse.vote_count,
            movieResponse.poster_path, isFav,0)

        return movie
    }

    private fun verifyFavorites(items : ArrayList<MovieResponse>){
        var movies = movieViewModel.favoriteMovies.value

        if(movies != null) {
            for (result in items) {
                movies!!.forEach { movie ->
                    if (movie.id == result.id) {
                        result.favorite = movie.favorite.toInt()
                    }
                }
            }
        }
    }


    private fun addAllMovies(){

        getMoviesResponse!!.results.forEach {
            movieResponse ->
                if(!movieViewModel.saveIfNotExists(movieResponse.id)) {
                    addMovie(movieResponse)
                }
        }
    }

    private fun addObserver() {
        val observer = Observer<List<Movie>> { movies ->
            if (movies != null) {
                var text = ""
                for (movie in movies) {
                    text += movie.id.toString() + " " + movie.title + "---" + movie.favorite.toString() +  "\n"
                }
                print("Movies : ${text}")
            }
        }
        movieViewModel.movies.observe(this, observer)

        val observer2 = Observer<List<Movie>>{

        }

        movieViewModel.favoriteMovies.observe(this, observer2)

    }
    private fun addMovie(movieResponse: MovieResponse){
        var movie = Movie(  movieResponse.id,
                            movieResponse.original_title,
                            movieResponse.overview,
                            movieResponse.vote_count,
                            movieResponse.poster_path,
                            "0",0)

        movieViewModel.saveMovie(movie)
    }



    companion object {
        @JvmStatic
        fun newInstance() =
            AllMoviesFragment().apply {

            }
    }
}
