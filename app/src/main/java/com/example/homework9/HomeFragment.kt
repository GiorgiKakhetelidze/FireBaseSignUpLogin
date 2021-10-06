package com.example.homework9

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.homework9.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.registerBtn?.setOnClickListener {
            navigateToRegisterFirstScreen()
        }

        binding?.loginBtn?.setOnClickListener {
            navigateToLoginScreen()
        }
    }

    private fun navigateToRegisterFirstScreen(){
        findNavController().navigate(R.id.action_homeFragment_to_registerFirstFragment)
    }

    private fun navigateToLoginScreen(){
        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}