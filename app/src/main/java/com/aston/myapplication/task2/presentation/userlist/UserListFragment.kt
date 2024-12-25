package com.aston.myapplication.task2.presentation.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import com.aston.myapplication.R
import com.aston.myapplication.databinding.FragmentUserListBinding
import com.aston.myapplication.task2.domain.entity.User
import com.aston.myapplication.task2.presentation.useredit.UserEditInformationFragment
import com.aston.myapplication.task2.presentation.userviewmodel.UserViewModel

class UserListFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }

    private val userAdapter by lazy {
        UserListAdapter()
    }

    private var _binding: FragmentUserListBinding? = null
    private val binding: FragmentUserListBinding
        get() = _binding ?: throw RuntimeException("UserListFragment == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            loadUserList()
            binding.rvUserList.adapter = userAdapter.apply {
                onClickUserListener = {user ->
                    launchFragmentManager(user)
                }
            }
        }

        parentFragmentManager.setFragmentResultListener(
            KEY_RESULT,
            viewLifecycleOwner
        ) { _, bundle ->
            val isUpdatedUser = bundle.getBoolean(UserEditInformationFragment.EDIT_STATUS)
            if (isUpdatedUser) viewModel.getUserList()
        }
    }

    private fun launchFragmentManager(user: User) {
        val fcv = requireActivity()
            .findViewById<FragmentContainerView>(R.id.fcvUserEditInformation)
        val resLayout = if (fcv != null) {
            R.id.fcvUserEditInformation
        } else {
            R.id.fcvUserList
        }
        requireActivity().supportFragmentManager.popBackStack()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(resLayout, UserEditInformationFragment.newInstance(user))
            .addToBackStack(FRAGMENT_NAME)
            .commit()
    }

    private fun loadUserList() {
        viewModel.getUserList()

        viewModel.userList.observe(viewLifecycleOwner) {
            userAdapter.submitList(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val FRAGMENT_NAME = "user_list"
        const val KEY_RESULT = "editUserResult"
        fun newInstance() = UserListFragment()
    }
}