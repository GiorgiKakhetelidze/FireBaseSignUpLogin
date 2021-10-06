package com.example.homework9

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.homework9.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.loginBtn?.setOnClickListener {

            val email = binding?.emailEditTxtView?.text.toString().trim()
            val password = binding?.passwordEditTxtView?.text.toString().trim()
            if (!(email.isEmpty() || password.isEmpty()))
                login(email, password)
            else
                Toast.makeText(context, getString(R.string.empty_input), Toast.LENGTH_SHORT).show()
        }

        binding?.backArrowTxtView?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun navigateToProfile() {
        val email = binding?.emailEditTxtView?.text.toString().trim()

        setFragmentResult(
            "result_email_key",
            bundleOf("email_key" to email)
        )

        findNavController().navigate(R.id.action_global_profileFragment)
    }

    private fun login(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Log in Success", Toast.LENGTH_SHORT).show()
                    navigateToProfile()
                } else {
                    Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}