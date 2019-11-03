package com.dangerfield.kind.ui.create


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide

import com.dangerfield.kind.R
import com.dangerfield.kind.api.CurrentUser
import com.dangerfield.kind.api.Resource
import kotlinx.android.synthetic.main.fragment_create_post.*

class CreatePostFragment : Fragment() {

    val viewModel : CreatePostViewModel by lazy {
        ViewModelProviders.of(this).get(CreatePostViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()

        btn_cancel.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        btn_post.setOnClickListener {
            post()
        }
    }

    private fun setupView() {
        setProfilePic()
    }

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

    private fun post() {
    }
}
