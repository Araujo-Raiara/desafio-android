package com.picpay.desafio.android.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.data.network.ResponseState
import com.picpay.desafio.android.domain.usecase.FilterListByInputUseCase
import com.picpay.desafio.android.domain.usecase.GetContactsUseCase
import kotlinx.coroutines.launch

class ContactsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getContactsUseCase: GetContactsUseCase,
    private val filterListByInputUseCase: FilterListByInputUseCase
) : ViewModel() {

    private val _users: MutableLiveData<ResponseState> =
        savedStateHandle.getLiveData("contactsList")
    val users: LiveData<ResponseState> get() = _users

    fun getContacts() {
        if (users.value == null) {
            refreshContacts()
        }
    }

    fun refreshContacts() {
        _users.value = ResponseState.Loading
        viewModelScope.launch {
            val response = getContactsUseCase.getContacts()
            savedStateHandle["contactsList"] = response
        }
    }

    fun filterListByInput(textInput: String) {
        val filteredList = filterListByInputUseCase(textInput)
        _users.value = filteredList
    }
}