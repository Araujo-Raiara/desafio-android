package com.picpay.desafio.android.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.data.network.ResponseState
import com.picpay.desafio.android.domain.usecase.GetContactsUseCase
import kotlinx.coroutines.launch

class ContactsViewModel(
    private val getContactsUseCase: GetContactsUseCase
): ViewModel() {

    private val _users : MutableLiveData<ResponseState> = MutableLiveData()
    val users: LiveData<ResponseState> get() = _users

    fun getContacts() {
        _users.value = ResponseState.Loading
        viewModelScope.launch {
            val response = getContactsUseCase.getContacts()
            _users.value = response
        }
    }
}