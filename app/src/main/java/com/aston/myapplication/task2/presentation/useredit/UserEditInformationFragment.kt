package com.aston.myapplication.task2.presentation.useredit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.BundleCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aston.myapplication.databinding.FragmentUserEditInformationBinding
import com.aston.myapplication.task2.domain.entity.User
import com.aston.myapplication.task2.presentation.userlist.UserListFragment
import com.aston.myapplication.task2.presentation.userviewmodel.UserItemViewModel

class UserEditInformationFragment : Fragment() {
    private var imageRes = ""
    private var user: User? = null

    private val viewModel by lazy {
        ViewModelProvider(this)[UserItemViewModel::class.java]
    }

    private var _binding: FragmentUserEditInformationBinding? = null
    private val binding: FragmentUserEditInformationBinding
        get() = _binding ?: throw RuntimeException("UserListFragment == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = BundleCompat.getParcelable(it, ARG_USER, User::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserEditInformationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers(view)

        user?.let {
            viewModel.getUser(it.id)
        }

        setOnClickListener()

        textChangeListener()

    }

    private fun setObservers(view: View) {
        viewModel.userLD.observe(viewLifecycleOwner) {
            val drawable = ContextCompat.getDrawable(view.context, it.image)
            binding.ivAvatar.setImageDrawable(drawable)
            binding.etName.setText(it.name)
            binding.etLastname.setText(it.lastName)
            binding.etPhone.setText(it.phone)
        }

        viewModel.currentImage.observe(viewLifecycleOwner) {
            val drawable = ContextCompat.getDrawable(view.context, it)
            binding.ivAvatar.setImageDrawable(drawable)
            imageRes = it.toString()
        }

        viewModel.shouldClose.observe(viewLifecycleOwner) {
            val resultBundle = Bundle().apply {
                putBoolean(EDIT_STATUS, true)
            }
            parentFragmentManager.setFragmentResult(
                UserListFragment.KEY_RESULT,
                resultBundle
            )
            requireActivity().supportFragmentManager.popBackStack()
        }

        viewModel.errorInputName.observe(viewLifecycleOwner) {
            binding.tilName.error = if (it) "Enter name" else null
        }

        viewModel.errorInputLastname.observe(viewLifecycleOwner) {
            binding.tilName.error = if (it) "Enter lastname" else null
        }

        viewModel.errorInputPhone.observe(viewLifecycleOwner) {
            binding.tilName.error = if (it) "Enter phone" else null
        }
    }

    private fun setOnClickListener() {
        binding.iBtnLeft.setOnClickListener { viewModel.previousAvatar() }
        binding.iBtnRight.setOnClickListener { viewModel.nextAvatar() }

        binding.btnSave.setOnClickListener {
            val nameInput = binding.etName.text.toString()
            val lastNameInput = binding.etLastname.text.toString()
            val phoneInput = binding.etPhone.text.toString()
            viewModel.saveChanges(
                imageRes,
                nameInput,
                lastNameInput,
                phoneInput
            )
        }
    }

    private fun textChangeListener() {
        binding.etName.doOnTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputName()
        }
        binding.etLastname.doOnTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputLastname()
        }
        binding.etPhone.doOnTextChanged { _, _, _, _ ->
            viewModel.resetErrorInputPhone()
        }
    }

    companion object {
        private const val ARG_USER = "user"
        const val EDIT_STATUS = "editingFinished"
        fun newInstance(user: User) =
            UserEditInformationFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_USER, user)
                }
            }
    }
}