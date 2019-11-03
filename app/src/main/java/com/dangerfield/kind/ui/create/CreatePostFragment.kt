package com.dangerfield.kind.ui.create


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide

import com.dangerfield.kind.R
import com.dangerfield.kind.api.CurrentUser
import com.dangerfield.kind.api.Resource
import com.dangerfield.kind.util.addCharacterMax
import com.dangerfield.kind.util.hideKeyBoardOnPressAway
import kotlinx.android.synthetic.main.fragment_create_post.*

class CreatePostFragment : Fragment() {

    private val viewModel : CreatePostViewModel by lazy {
        ViewModelProviders.of(this).get(CreatePostViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setProfilePic()

        tv_post_text.addCharacterMax(tv_char_count, 200)

        btn_cancel.setOnClickListener { navigateBack() }

        btn_post.setOnClickListener { post() }

        tv_post_text.hideKeyBoardOnPressAway()
    }

    private fun post() {
        viewModel.post(tv_post_text.text.toString()).observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> navigateBack()
                is Resource.Error -> Toast.makeText(context!!, it.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun navigateBack() = NavHostFragment.findNavController(this).popBackStack()

    private fun setProfilePic() {
        viewModel.getProfilePic().observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    Glide.with(context!!).load(it.data).centerCrop().into(circle_image)
                }
                is Resource.Error -> Log.d("ERROR", "COULD NOT LOAD PROFILE PIC")
            }
        })
    }
}
