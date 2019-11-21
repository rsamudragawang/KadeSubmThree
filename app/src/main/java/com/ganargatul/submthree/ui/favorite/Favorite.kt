package com.ganargatul.submthree.ui.favorite


import android.app.SearchManager
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log.d
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ganargatul.submthree.R
import com.ganargatul.submthree.adapter.FavoriteAdapter
import com.ganargatul.submthree.db.FavoriteItems
import com.ganargatul.submthree.db.database
import com.ganargatul.submthree.ui.detailfavorite.DetailFavorite
import com.ganargatul.submthree.ui.searchevent.SearchEvent
import org.jetbrains.anko.db.MapRowParser

import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity

class Favorite : Fragment() {
    private var favorites: MutableList<FavoriteItems> = mutableListOf()
    lateinit var recyclerview: RecyclerView
    lateinit var adapter: FavoriteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.favorite_fragment, container, false)
        recyclerview= root.findViewById(R.id.rv_list_fav)
        recyclerview.layoutManager= LinearLayoutManager(context)
        initData()
        setHasOptionsMenu(true)

        return root

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater?.inflate(R.menu.toolbar_menu,menu)
        val searchManager = this.activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_menu).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                startActivity<SearchEvent>("query" to query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })


    }
    private fun initData(){
        favorites.clear()
        try {
            context?.database?.use {
               select(FavoriteItems.TABLE_FAVORITE).parseList(object  : MapRowParser<List<FavoriteItems>>{
                    override fun parseRow(columns: Map<String, Any?>): List<FavoriteItems> {
                        val ID = columns.getValue(FavoriteItems.ID)
                        val EVENT_ID = columns.getValue(FavoriteItems.EVENT_ID)
                        val TEAM_HOME = columns.getValue(FavoriteItems.TEAM_HOME_ID)
                        val TEAM_AWAY = columns.getValue(FavoriteItems.TEAM_AWAY_ID)
                        val EVENT_NAME = columns.getValue(FavoriteItems.EVENT_NAME)
                        val favorite = FavoriteItems(
                            ID.toString().toInt(),
                            EVENT_ID.toString().toInt(),
                            TEAM_HOME.toString().toInt(),
                            TEAM_AWAY.toString().toInt(),
                            EVENT_NAME.toString()
                        )
                        favorites.add(favorite)
                        d("fav", favorites.toString())
                        return favorites
                    }
               })
                showFav()

            }
        }catch (e : SQLiteConstraintException){
            e.printStackTrace()
        }
    }

    private fun showFav() {
        val adapter = context?.let {
            FavoriteAdapter(it,favorites){
                var nowItems = FavoriteItems(it.ID_,it.EVENT_ID_,it.TEAM_HOME_,it.TEAM_AWAY_,it.EVENT_NAME_)
                startActivity<DetailFavorite>("Data" to nowItems)

            }
        }
        recyclerview.adapter = adapter
    }


}
