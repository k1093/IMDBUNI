package com.kevin.imdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kevin.imdb.api.OnResponseListener
import com.kevin.imdb.api.response.ErrorResponse
import com.kevin.imdb.api.dataSources.AuthenticateDataSource
import com.kevin.imdb.movies.MoviesDataSource
import com.kevin.imdb.movies.MoviesPresenter
import javax.inject.Inject

class MainActivity : AppCompatActivity(), OnResponseListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var moviesPresenter = MoviesPresenter(this, this)
        moviesPresenter.setOnResponseListener(this)

    }



    override fun onResponseSuccess(code: Int, response: Any) {

    }

    override fun onResponseHasErrors(code: Int, errorResponse: ErrorResponse) {

    }

    override fun onResponseFailure() {

    }
}
