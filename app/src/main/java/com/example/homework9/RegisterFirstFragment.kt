package com.example.homework9

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResult
import com.example.extension.checkEmail
import com.example.homework9.databinding.FragmentRegisterFirstBinding

class RegisterFirstFragment : Fragment() {

    private var binding: FragmentRegisterFirstBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterFirstBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.nextBtn?.setOnClickListener {
            if (checkValidityOfInputs())
                navigateToRegistrationSecondScreen()
        }

        binding?.backArrowTxtView?.setOnClickListener {
            findNavController().popBackStack()
        }

        binding?.passwordEditTxtView?.doOnTextChanged { text, start, before, count ->
            if (text?.length ?: 0 < 3)
                binding?.passwordEditTxtView?.error = getString(R.string.invalid_password)
        }

        binding?.emailEditTxtView?.doOnTextChanged { text, _, _, _ ->
            if (text?.length ?: 0 < 3 || !text.toString().checkEmail())
                binding?.emailEditTxtView?.error = getString(R.string.email_not_valid)
        }

    }

    private fun navigateToRegistrationSecondScreen() {
        val email = binding?.emailEditTxtView?.text.toString()
        val password = binding?.passwordEditTxtView?.text.toString()
        setFragmentResult(EMAIL_PASSWORD, bundleOf(EMAIL_KEY to email, PASSWORD_KEY to password))
        findNavController().navigate(R.id.action_registerFirstFragment_to_registerSecondFragment)
    }

    private fun checkValidityOfInputs(): Boolean {
        return when {
            binding?.emailEditTxtView?.text.isNullOrEmpty() || binding?.passwordEditTxtView?.text.isNullOrEmpty() -> {
                toast(getString(R.string.fill_fields))
                false
            }
            !binding?.emailEditTxtView?.text.toString().checkEmail() -> {
                toast(getString(R.string.email_not_valid))
                false
            }
            else -> {
                toast(getString(R.string.success))
                true
            }
        }
    }

    private fun toast(text: String) = Toast.makeText(context, text, Toast.LENGTH_SHORT).show()

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val EMAIL_PASSWORD = "USER_AUTH"
        const val EMAIL_KEY = "EMAIL"
        const val PASSWORD_KEY = "PASSWORD"
    }
}