package com.aston.myapplication.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.aston.myapplication.R
import com.aston.myapplication.databinding.FragmentCBinding
import com.aston.myapplication.databinding.NextFrameBinding

class FragmentC : Fragment() {
    private var message: String? = null

    private var _binding: FragmentCBinding? = null
    private var _mergeBinding: NextFrameBinding? = null

    private val binding: FragmentCBinding
        get() = _binding ?: throw RuntimeException("FragmentCBinding == null")

    private val mergeBinding: NextFrameBinding
        get() = _mergeBinding ?: throw RuntimeException("MergeBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            message = it.getString(ARG_MSG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCBinding.inflate(inflater)
        _mergeBinding = NextFrameBinding.bind(binding.root)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showFragmentC()
    }

    private fun showFragmentC() {
        with(mergeBinding) {
            message?.let {
                tvResult.text = it
            }

            tvFragmentName.text = String.format(
                resources.getString(R.string.fragment_name),
                CURRENT_FRAGMENT_NAME
            )

            btnNext.apply {
                text = String.format(resources.getString(R.string.next), NEXT_FRAGMENT_NAME)
                setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fc_screen, FragmentD.instanceFragmentC())
                        .addToBackStack(FRAGMENT_BACKSTACK_NAME)
                        .commit()
                }
            }

            btnPrev.apply {
                text = String.format(resources.getString(R.string.previous), PREVIOUS_FRAGMENT_NAME)
                setOnClickListener {
                    requireActivity().supportFragmentManager.popBackStack(
                        PREVIOUS_FRAGMENT_BACKSTACK_NAME,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
        _mergeBinding = null
    }

    companion object {
        private const val FRAGMENT_BACKSTACK_NAME = "Fragment_C"
        private const val CURRENT_FRAGMENT_NAME = "C"
        private const val NEXT_FRAGMENT_NAME = "D"
        private const val PREVIOUS_FRAGMENT_NAME = "A"
        private const val PREVIOUS_FRAGMENT_BACKSTACK_NAME = "Fragment_A"

        private const val ARG_MSG = "message"

        fun instanceFragmentC(str: String) = FragmentC().apply {
            arguments = Bundle().apply {
                putString(ARG_MSG, str)
            }
        }
    }
}