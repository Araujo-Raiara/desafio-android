package com.picpay.desafio.android.presenter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.entity.Contact
import com.picpay.desafio.android.data.network.ResponseState
import com.picpay.desafio.android.databinding.FragmentContactsBinding
import com.picpay.desafio.android.presenter.adapter.UserListAdapter
import com.picpay.desafio.android.presenter.viewmodel.ContactsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: UserListAdapter
    private lateinit var contactsList: List<Contact>

    private val contactsViewModel: ContactsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        setupAdapter()
        setupObservers()
        contactsViewModel.getContacts()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
    }

    private fun setupAdapter() {
        binding.apply {
            adapter = UserListAdapter()
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupObservers() {
        contactsViewModel.users.observe(viewLifecycleOwner) { responseState ->
            when (responseState) {
                ResponseState.Loading -> adapter.isLoading()
                is ResponseState.Success -> setupSuccessState(responseState.contactsList)
                is ResponseState.Error -> TODO()
            }
        }
    }


    private fun setupSuccessState(contactsList: List<Contact>) {
        adapter.contacts = contactsList
        this.contactsList = adapter.contacts
    }


     private fun setupMenu() {
         val menuHost: MenuHost = requireActivity()
         menuHost.addMenuProvider(object : MenuProvider {
             override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                 menuInflater.inflate(R.menu.options_menu, menu)
                 val searchView = menu.findItem(R.id.search).actionView as SearchView
                 searchView.apply {
                     queryHint = getString(R.string.search_hint)
                     maxWidth = Integer.MAX_VALUE
                     setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                         override fun onQueryTextSubmit(query: String): Boolean {
                             return false
                         }

                         override fun onQueryTextChange(newText: String): Boolean {
                             contactsViewModel.filterListByInput(newText)
                             return false
                         }
                     })
                 }
             }

             override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                 return true
             }

         })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}