package com.dangerfield.kind.ui.profile.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.dangerfield.kind.R
import com.dangerfield.kind.api.Resource

import com.dangerfield.kind.util.hideKeyBoardOnPressAway
import com.dangerfield.kind.util.showIFF
import com.dangerfield.kind.util.visibleContingency
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.tv_email
import kotlinx.android.synthetic.main.fragment_login.tv_password

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_submit_log_in.setOnClickListener {
            it.isClickable = false
            signIn(
                    tv_email.text.toString().trim(),
                    tv_password.text.toString().trim()
            )
        }

        listOf<View>(tv_email,tv_password).forEach{it.hideKeyBoardOnPressAway()}

        viewModel.loginStatus.observe(viewLifecycleOwner, Observer {
            pb_auth_login.showIFF(it is Resource.Loading)
            pb_auth_login.visibleContingency(
                    {btn_submit_log_in.text = ""} ,
                    {btn_submit_log_in.text = getString(R.string.sign_in)})

            when(it){
                is Resource.Success ->
                    NavHostFragment.findNavController(this).popBackStack()
                is Resource.Error ->{
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    btn_submit_log_in.isClickable = true
                }
            }
        })
    }


    private fun signIn(email: String, password: String) { viewModel.login(email,password) }
}
