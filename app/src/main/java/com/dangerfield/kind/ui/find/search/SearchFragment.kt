package com.dangerfield.kind.ui.find.search


import android.app.ProgressDialog.show
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
import com.dangerfield.kind.util.hideKeyBoardOnPressAway
import com.dangerfield.kind.util.showIFF
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    private val postAdapter : PostAdapter by lazy { PostAdapter(context!!) }
    private val viewModel : SearchViewModel
            by lazy { ViewModelProviders.of(this).get(SearchViewModel::class.java)}


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_search.requestFocus()

        setUpRecyclerView()

        btn_back.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        ib_clear.setOnClickListener { tv_search.text.clear() }

        tv_search.hideKeyBoardOnPressAway()

        tv_search.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                ib_clear.showIFF((p0?.length ?: 0) > 0)
            }
        })

        tv_search.setOnEditorActionListener { textView, i, keyEvent ->
            if(i == EditorInfo.IME_ACTION_SEARCH){
                viewModel.currentSearchTerm.value = tv_search.text.toString().trim()
                tv_search.clearFocus()
                true
            }else{false}
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.searchResult.observe(viewLifecycleOwner, Observer {
            postAdapter.posts = it
            tv_empty.showIFF(it.isEmpty())
        })
    }



    private fun setUpRecyclerView() {
        rv_search_post.layoutManager = LinearLayoutManager(activity)
        rv_search_post.adapter = postAdapter
    }
}
