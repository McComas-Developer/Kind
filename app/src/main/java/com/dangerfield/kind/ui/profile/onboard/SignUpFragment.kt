package com.dangerfield.kind.ui.profile.onboard

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.dangerfield.kind.R
import com.dangerfield.kind.api.Error
import com.dangerfield.kind.api.Status
import com.dangerfield.kind.api.Success
import com.dangerfield.kind.util.hideKeyBoardOnPressAway
import com.dangerfield.kind.util.showIFF
import com.dangerfield.kind.util.visibleContingency
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.btn_sign_up

class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by lazy {
        ViewModelProviders.of(this).get(SignUpViewModel::class.java)
    }
    lateinit var statusObserver: Observer<Status>
    val IMAGE_REQ_CODE = 0
    var profilePicture: Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setStatusObserver()
        btn_sign_up.setOnClickListener {
            it.isClickable = false
            signUp(
                    tv_user_name.text.toString().trim(),
                    tv_email.text.toString().trim(),
                    tv_password.text.toString().trim(),
                    tv_confirm_password.text.toString().trim()
            )
        }

        listOf<View>(circle_image_root,btn_edit_photo)
                .forEach { it.setOnClickListener { launchPhotoSelector() }}
        listOf<View>(tv_email,tv_password, tv_confirm_password, tv_user_name)
                .forEach{it.hideKeyBoardOnPressAway()}
    }

    private fun setStatusObserver() {
        statusObserver = Observer {
            pb_auth.showIFF(it is Status.LOADING)
            pb_auth.visibleContingency({btn_sign_up.text = ""} , {btn_sign_up.text = getString(R.string.sign_up)})

            when(it){
                is Status.LOADING -> {}//no op
                is Status.SUCCESS ->
                    NavHostFragment.findNavController(this).popBackStack()
                is Status.FAILURE ->{
                    btn_sign_up.isClickable = true
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun launchPhotoSelector() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/"
        startActivityForResult(intent, IMAGE_REQ_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //called when photo launcher returns
        if(requestCode == IMAGE_REQ_CODE && resultCode == Activity.RESULT_OK && data != null) {
            profilePicture = data.data!! // banging because null check above
            profilePicture?.let{ loadProfilePic(it) }
        }
    }

    private fun loadProfilePic(uri : Uri) {
        val bitmap = if (android.os.Build.VERSION.SDK_INT >= 29){
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(activity!!.contentResolver, uri))
        } else {
            MediaStore.Images.Media.getBitmap(activity!!.contentResolver, uri)
        }

        circle_image.setImageDrawable(BitmapDrawable(resources, bitmap))
        tv_select_photo.text = ""
        btn_edit_photo.visibility = View.VISIBLE
    }

    private fun signUp(username: String, email: String, password: String, confirmPassword: String) {

        val result
                = viewModel.createUser(
                profilePicture,
                username,
                email,
                password,
                confirmPassword
        )

        when (result) {
            is Success ->{
                result.value.observe(viewLifecycleOwner, statusObserver)
            }
            is Error ->{
                Toast.makeText(context, result.value.message, Toast.LENGTH_LONG).show()
                btn_sign_up.isClickable = true
            }

        }
    }
}

