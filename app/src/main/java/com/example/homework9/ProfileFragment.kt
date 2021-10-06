package com.example.homework9

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.homework9.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.backArrowTxtView?.setOnClickListener {
            findNavController().popBackStack()
        }

        setFragmentResultListener("username_key_result") { _, bundle ->
            val name = bundle.getString("username_key")
            binding?.userNameTxtView?.text = name
        }

        setFragmentResultListener("result_email_key") { _, bundle ->
            val name = bundle.getString("email_key")
            binding?.userNameTxtView?.text = name
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}