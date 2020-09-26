package com.example.craterzoneassignment.view

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.craterzoneassignment.R
import com.example.craterzoneassignment.adapter.GalleryAdapter
import com.example.craterzoneassignment.data.Resource
import com.example.craterzoneassignment.models.Photo
import com.example.craterzoneassignment.utils.*
import com.example.craterzoneassignment.viewmodel.ImagesViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var galleryAdapter: GalleryAdapter
    private var currentPage: Int = 1
    private var previousPage = 1
    private var isLastPageLeft = false
    private var totalPage = 10
    private var isLoad = false
    private var mQuery = ""

    private val viewModel: ImagesViewModel by lazy {
        ViewModelProviders.of(this, InjectorUtils.provideImageViewModelFactory(this)).get(ImagesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()
        observeImages()
    }

    private fun observeImages() {
        viewModel.results.observe(this, Observer<Resource<List<Photo>>?> { t ->
            Log.e("crater", "observe")
            t?.data?.let {
                setData(it, 1)
            }
        })
    }

    private fun initRecycler() {
        galleryAdapter = GalleryAdapter(object : GalleryAdapter.IImageLoadCallback {
            override fun onVisible(visible: Boolean) {
                progress_circular.visibility = View.GONE
            }
        })

        var layoutManager = GridLayoutManager(this, 2)
        recycler_view.layoutManager = layoutManager

        val spacing = resources.getDimensionPixelSize(R.dimen.recycler_spacing) / 2
        recycler_view.adapter = galleryAdapter
        addScrollListener(layoutManager)

        recycler_view.setPadding(spacing, spacing, spacing, spacing)
        recycler_view.clipToPadding = false
        recycler_view.clipChildren = false

        recycler_view.addItemDecoration(object : ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                outRect.set(spacing, spacing, spacing, spacing)
            }
        })
    }

    private fun setData(listPhoto: List<Photo>, currentPage: Int) {
        Log.e("crater", "data" + listPhoto.size)

        if (currentPage > previousPage) {
            galleryAdapter.addAll(listPhoto)
        } else {
            galleryAdapter.setData(listPhoto)
        }

        isLoad = false
        previousPage = currentPage

        if (currentPage >= totalPage) {
            isLastPageLeft = true
        }
    }

    private fun addScrollListener(layoutManager: GridLayoutManager) {
        recycler_view.addOnScrollListener(object : PaginationListener(layoutManager) {

            override fun loadMoreItems() {
                isLoad = true
                currentPage++
                searchImages(mQuery, currentPage)
            }

            override val isLastPage: Boolean
                get() = isLastPageLeft

            override val isLoading: Boolean
                get() = isLoad
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchItem = menu.findItem(R.id.menu_search)
        val mSearchView = searchItem.actionView as SearchView
        mSearchView.queryHint = resources.getString(R.string.search)
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mQuery = query
                currentPage = 1
                isLastPageLeft = false
                searchImages(query, currentPage)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_column -> {
                onColumnClicked()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun onColumnClicked() {
        openDialog(this, object : IDialogCallback {
            override fun onButtonClick(value: Int) {
                recycler_view.layoutManager = GridLayoutManager(this@MainActivity, value)

                if (this@MainActivity::galleryAdapter.isInitialized) {
                    galleryAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    fun searchImages(query: String, page: Int) {
        progress_circular.visibility = View.VISIBLE
        hideKeyboard(this)

        viewModel.setQuery(query)
    }
}