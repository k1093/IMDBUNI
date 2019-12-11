package com.kevin.imdb.ui.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.kevin.imdb.BaseActivity
import com.kevin.imdb.R
import com.kevin.imdb.api.OnResponseListener
import com.kevin.imdb.api.response.ErrorResponse
import com.kevin.imdb.api.response.GetMovieDetail
import com.kevin.imdb.movies.MoviesPresenter
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : BaseActivity(), OnResponseListener {


    private var moviesPresenter = MoviesPresenter(this, this)
    private var movieId : Int = 0
    private lateinit var getMovieDetail : GetMovieDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        moviesPresenter.setOnResponseListener(this)
        getExtras()
    }

    private fun getMovieDetail(){
        showProgress()
        moviesPresenter.getMovieDetail(movieId)
    }


    private fun getExtras(){
        if(intent.extras != null && intent.extras!!.containsKey("id")){
            movieId = intent.extras!!.getInt("id")
            getMovieDetail()
        }
    }


    override fun onResponseSuccess(code: Int, response: Any) {
        dismissProgress()
        if(response is GetMovieDetail){
            text_original_title.text = response.original_title
            text_overview.text = response.overview
            text_average.text = response.vote_count.toString()
            setImagePoster(response.poster_path)
        }
    }

    fun setImagePoster(url : String){
        Glide.with(this).load("https://image.tmdb.org/t/p/w200$url")
            .centerCrop()
            //.format(DecodeFormat.PREFER_RGB_565)
            .into(this.image_poster)
    }

    override fun onResponseHasErrors(code: Int, errorResponse: ErrorResponse) {
        dismissProgress()
        this.httpCodeHandler.showHttpMessage(code, null)
    }

    override fun onResponseFailure() {
        dismissProgress()
        this.httpCodeHandler.showGenerarlFailureMessage(null)
    }
}
