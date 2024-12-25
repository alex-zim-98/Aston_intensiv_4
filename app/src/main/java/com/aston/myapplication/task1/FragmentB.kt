package com.aston.myapplication.task1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aston.myapplication.R
import com.aston.myapplication.databinding.FragmentBBinding
import com.aston.myapplication.databinding.NextFrameBinding

class FragmentB : Fragment() {

    private var _binding: FragmentBBinding? = null
    private var _mergeBinding: NextFrameBinding? = null

    private val binding: FragmentBBinding
        get() = _binding ?: throw RuntimeException("FragmentBBinding == null")

    private val mergeBinding: NextFrameBinding
        get() = _mergeBinding ?: throw RuntimeException("MergeBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBBinding.inflate(inflater)
        _mergeBinding = NextFrameBinding.bind(binding.root)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showFragmentB()
    }

    private fun showFragmentB() {
        with(mergeBinding) {
            tvFragmentName.text = String.format(
                resources.getString(R.string.fragment_name),
                CURRENT_FRAGMENT_NAME
            )

            btnNext.apply {
                text = String.format(resources.getString(R.string.next), NEXT_FRAGMENT_NAME)
                setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fc_screen, FragmentC.instanceFragmentC(MESSAGE_TO_FRAGMENT_C))
                        .addToBackStack(FRAGMENT_BACKSTACK_NAME)
                        .commit()
                }
            }

            btnPrev.apply {
                text = String.format(resources.getString(R.string.previous), PREVIOUS_FRAGMENT_NAME)
                setOnClickListener {
                    requireActivity().onBackPressedDispatcher.onBackPressed()
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
        private const val CURRENT_FRAGMENT_NAME = "B"
        private const val NEXT_FRAGMENT_NAME = "C"
        private const val PREVIOUS_FRAGMENT_NAME = "A"

        private const val MESSAGE_TO_FRAGMENT_C = "Hello Fragment C"

        fun instanceFragmentB() = FragmentB()
    }
}