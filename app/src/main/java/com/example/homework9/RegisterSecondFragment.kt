package com.example.homework9

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.homework9.databinding.FragmentRegisterSecondBinding
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterSecondFragment : Fragment() {

    private var binding: FragmentRegisterSecondBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterSecondBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.signUpBtn?.setOnClickListener {
            checkUserAndSignUp()
        }

        binding?.usernameEditTxtView?.doOnTextChanged { text, _, _, _ ->
            if (text?.length ?: 0 < 2)
                binding?.usernameEditTxtView?.error = getString(R.string.enter_username)
        }

        binding?.backArrowTxtView?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun checkUserAndSignUp() {
        if (!binding?.usernameEditTxtView?.text.isNullOrEmpty())
            getDataSignUp()
        else
            Toast.makeText(context, getString(R.string.enter_username), Toast.LENGTH_SHORT)
                .show()
    }


    private fun getDataSignUp() {
        setFragmentResultListener(RegisterFirstFragment.EMAIL_PASSWORD) { _, bundle ->
            val email = bundle.getString(RegisterFirstFragment.EMAIL_KEY) ?: "null"
            val password = bundle.getString(RegisterFirstFragment.PASSWORD_KEY) ?: "null"
            signUp(email, password)
        }
    }

    private fun navigateToProfile() {
        val name = binding?.usernameEditTxtView?.text.toString()
        setFragmentResult(
            "username_key_result",
            bundleOf("username_key" to name)
        )
        findNavController().navigate(R.id.action_global_profileFragment)
    }

    private fun signUp(email: String, password: String) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                   // Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    navigateToProfile()
                } else {
                    Toast.makeText(context, task.exception?.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}