package com.aston.myapplication.task1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aston.myapplication.R
import com.aston.myapplication.databinding.FragmentABinding
import com.aston.myapplication.databinding.NextFrameBinding

class FragmentA : Fragment() {

    private var _binding: FragmentABinding? = null
    private var _mergeBinding: NextFrameBinding? = null

    private val binding: FragmentABinding
        get() = _binding ?: throw RuntimeException("FragmentABinding == null")

    private val mergeBinding: NextFrameBinding
        get() = _mergeBinding ?: throw RuntimeException("MergeBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FragmentA", "FragmentA")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentABinding.inflate(inflater)
        _mergeBinding = NextFrameBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showFragmentA()
    }

    private fun showFragmentA() {
        with(mergeBinding) {

            tvFragmentName.text = String.format(
                resources.getString(R.string.fragment_name),
                CURRENT_FRAGMENT_NAME
            )

            btnNext.apply {
                text = String.format(resources.getString(R.string.next), NEXT_FRAGMENT_NAME)
                setOnClickListener {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fc_screen, FragmentB.instanceFragmentB())
                        .addToBackStack(FRAGMENT_BACKSTACK_NAME)
                        .commit()
                }
            }

            btnPrev.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _mergeBinding = null
    }

    companion object {
        private const val FRAGMENT_BACKSTACK_NAME = "Fragment_A"
        private const val CURRENT_FRAGMENT_NAME = "A"
        private const val NEXT_FRAGMENT_NAME = "B"
    }
}