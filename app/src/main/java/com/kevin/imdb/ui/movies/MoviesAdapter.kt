package com.kevin.imdb.ui.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DecodeFormat
import com.kevin.imdb.BuildConfig
import com.kevin.imdb.R
import com.kevin.imdb.api.ApiClient
import com.kevin.imdb.api.response.GetMoviesResponse
import com.kevin.imdb.api.response.MovieResponse
import kotlinx.android.synthetic.main.item_movie.view.*



class MoviesAdapter(context: Context, items : ArrayList<MovieResponse>, glide : RequestManager, movieSelected : MovieSelected) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() , Filterable{


    private var context = context
    private var items = items
    private var itemsFiltered = items
    private var glide = glide
    private var movieSelected = movieSelected

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {

        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_movie, parent, false)
        return MoviesViewHolder(view)

    }

    override fun getItemCount(): Int {
        return itemsFiltered.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.setTitle(itemsFiltered[position].original_title)
        holder.setOverview(itemsFiltered[position].overview)
        holder.setImagePoster(itemsFiltered[position].poster_path)
        holder.setImageFavorite(itemsFiltered[position].favorite)

    }

    override fun onViewRecycled(holder: MoviesViewHolder) {
        super.onViewRecycled(holder)
        holder.recycled()
    }

    fun setNewMovies(newItems : ArrayList<MovieResponse>){
        this.itemsFiltered.addAll(newItems)
        this.notifyDataSetChanged()
    }

    fun setFavorite(position: Int){
        this.notifyItemChanged(position)
    }

    fun addMovie(movieResponse: MovieResponse){
        var flag = false
        this.itemsFiltered.forEach {
            item ->
                if(item.id == movieResponse.id){
                    flag = true
                }
        }
        if(!flag) {
            this.itemsFiltered.add(movieResponse)
            this.notifyItemInserted(itemsFiltered.size - 1)
        }
    }

    fun deleteMovie(position: Int){
        this.itemsFiltered.removeAt(position)
        this.notifyItemRemoved(position)
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    itemsFiltered = items
                } else {
                    val filteredList = ArrayList<MovieResponse>()
                    for (row in itemsFiltered) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.title.toLowerCase().contains(charString.toLowerCase())
                        ) {
                            filteredList.add(row)
                        }
                    }

                    itemsFiltered = filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = itemsFiltered
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: Filter.FilterResults
            ) {
                itemsFiltered = filterResults.values as ArrayList<MovieResponse>
                notifyDataSetChanged()
            }
        }

    }


    inner class MoviesViewHolder(view : View) : RecyclerView.ViewHolder(view){

        var imagePoster = view.findViewById<ImageView>(R.id.image_movie_poster)
        var imageFavorite = view.findViewById<ImageView>(R.id.image_favorite)
        var textTitle = view.findViewById<TextView>(R.id.text_movie_title)
        var textOverview = view.findViewById<TextView>(R.id.text_movie_overview)


        init {
            view.setOnClickListener(View.OnClickListener {
                movieSelected.onSelected(itemsFiltered.get(layoutPosition))
            })

            imageFavorite.setOnClickListener(
                View.OnClickListener {
                    movieSelected.onFavorite(itemsFiltered.get(layoutPosition), layoutPosition)
                    if(itemsFiltered[layoutPosition].favorite == 1){
                        itemsFiltered[layoutPosition].favorite = 0
                    } else{
                        itemsFiltered[layoutPosition].favorite = 1
                    }


                }
            )
        }

        fun setImagePoster(url : String){

            var layoutParams = imagePoster.layoutParams
            glide.load("https://image.tmdb.org/t/p/w200$url")
                .centerCrop()
                .thumbnail(0.1f)
                .format(DecodeFormat.PREFER_RGB_565)
                .into(this.imagePoster)
        }

        fun setImageFavorite(flag : Int){
                if(flag == 1) {
                    imageFavorite.setImageResource(R.drawable.heart)
                    imageFavorite.imageTintList = ContextCompat.getColorStateList(context, R.color.color_error)
                }else {
                    imageFavorite.imageTintList = ContextCompat.getColorStateList(context, R.color.color_gray)
                }

        }

        fun setTitle(title : String){
            textTitle.text = title
        }

        fun setOverview(overview : String){
            textOverview.text = overview
        }

        fun recycled(){
            glide.clear(imagePoster)
        }


    }


    interface MovieSelected{
        fun onSelected(movieResponse: MovieResponse)
        fun onFavorite(movieResponse: MovieResponse, position: Int)
    }
}