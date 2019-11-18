package com.dangerfield.kind.ui.find.search


import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dangerfield.kind.R
import com.dangerfield.kind.ui.feed.PostAdapter
import com.dangerfield.kind.api.Resource.*
import com.dangerfield.kind.model.Post
import com.dangerfield.kind.util.*
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    private val postAdapter : PostAdapter by lazy { PostAdapter(context!!, viewModel) }
    private val viewModel : SearchViewModel
            by lazy { ViewModelProviders.of(this).get(SearchViewModel::class.java)}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        btn_back.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        ib_clear.setOnClickListener { tv_search.text.clear() }

        tv_search.hideKeyBoardOnPressAway()
        tv_search.addClearButton(ib_clear)
        tv_search.onSearch {
            viewModel.currentSearchTerm.value = tv_search.text.toString().trim() }

        viewModel.searchResult.observe(viewLifecycleOwner, Observer { searchResquest ->
            pb_search.showIFF(searchResquest is Loading)
            when(searchResquest){
                is Success -> showResults( searchResquest.data ?: listOf())

                is Error -> Toast.makeText(context, searchResquest.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        tv_search.openKeyboard()
    }

    @SuppressLint("SetTextI18n")
    private fun showResults(list: List<Post>) {
        tv_empty.text = "No Search Results Found For: \"${viewModel.currentSearchTerm.value.toString()}\""
        tv_empty.showIFF(list.isEmpty())
        postAdapter.posts = list
    }

    private fun setUpRecyclerView() {
        rv_search_post.layoutManager = LinearLayoutManager(activity)
        rv_search_post.adapter = postAdapter
    }
}
