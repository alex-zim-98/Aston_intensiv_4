package com.aston.myapplication.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.aston.myapplication.R
import com.aston.myapplication.databinding.FragmentDBinding
import com.aston.myapplication.databinding.NextFrameBinding

class FragmentD : Fragment() {

    private var _binding: FragmentDBinding? = null
    private var _mergeBinding: NextFrameBinding? = null

    private val binding: FragmentDBinding
        get() = _binding ?: throw RuntimeException("FragmentDBinding == null")

    private val mergeBinding: NextFrameBinding
        get() = _mergeBinding ?: throw RuntimeException("MergeBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDBinding.inflate(inflater)
        _mergeBinding = NextFrameBinding.bind(binding.root)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showFragmentD()
    }

    private fun showFragmentD() {
        with(mergeBinding) {

            btnNext.visibility = View.INVISIBLE

            tvFragmentName.text = String.format(
                resources.getString(R.string.fragment_name),
                CURRENT_FRAGMENT_NAME
            )

            btnPrev.apply {
                text = String.format(
                    resources.getString(R.string.previous),
                    PREVIOUS_FRAGMENT_NAME
                )
                setOnClickListener {
                    requireActivity().supportFragmentManager.popBackStack(
                        FRAGMENT_BACKSTACK_NAME,
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
        private const val FRAGMENT_BACKSTACK_NAME = "Fragment_B"
        private const val CURRENT_FRAGMENT_NAME = "D"
        private const val PREVIOUS_FRAGMENT_NAME = "B"

        fun instanceFragmentC() = FragmentD()
    }
}