package com.picpay.desafio.android.presenter.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.picpay.desafio.android.data.entity.Contact
import com.picpay.desafio.android.data.network.ResponseState
import com.picpay.desafio.android.domain.usecase.FilterListByInputUseCase
import com.picpay.desafio.android.domain.usecase.GetContactsUseCase
import com.picpay.desafio.android.testextensions.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import io.mockk.verifySequence
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class ContactsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    val dispatcher = StandardTestDispatcher()

    private lateinit var contactsViewModel: ContactsViewModel

    private val savedStateHandle = SavedStateHandle()

    @MockK
    private lateinit var getContactsUseCase: GetContactsUseCase

    @MockK
    private lateinit var filterListByInputUseCase: FilterListByInputUseCase

    @MockK
    private lateinit var observer: Observer<ResponseState?>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        contactsViewModel = ContactsViewModel(
            savedStateHandle = savedStateHandle,
            getContactsUseCase = getContactsUseCase,
            filterListByInputUseCase = filterListByInputUseCase
        )
        contactsViewModel.users.observeForever(observer)
        every { observer.onChanged(any()) } just runs
    }

    @Test
    fun `when call getContacts without cache should call refreshContacts`() = runTest {
        // arrange
        coEvery { getContactsUseCase.getContacts() } answers { ResponseState.Success(contactsList) }

        // act
        contactsViewModel.getContacts()

        // assert
        coVerify { getContactsUseCase.getContacts() }
        verifySequence {
            observer.onChanged(ResponseState.Loading)
            observer.onChanged(ResponseState.Success(contactsList))
        }
    }

    @Test
    fun `when call getContacts with cache should ont call refreshContacts`() = runTest {
        //act
        savedStateHandle["contactsList"] = ResponseState.Success(contactsList)
        contactsViewModel.getContacts()

        // assert
        verifySequence {
            observer.onChanged(ResponseState.Success(contactsList))
        }
        coVerify(exactly = 0) { getContactsUseCase.getContacts() }
    }

    @Test
    fun `when call filterListByInput, liveData should be a list filtered by text input`() =
        runTest() {
            // arrange
            every { filterListByInputUseCase(any()) } answers {
                ResponseState.Success(
                    listOf(
                        clairBrumm
                    )
                )
            }
            val input = "C"
            val expected = ResponseState.Success(listOf(clairBrumm))

            // act
            contactsViewModel.filterListByInput(input)
            val actual = contactsViewModel.users.value

            // assert
            assertEquals(expected, actual)
            verify { filterListByInputUseCase(input) }
        }
}

private val jonnyDoe = Contact(
    img = "img.png",
    name = "John Doe",
    id = 12,
    username = "@Jdoe"
)
private val clairBrumm = Contact(
    img = "img.png",
    name = "Clair Brumm",
    id = 13,
    username = "@clairBoom"
)
private val leoWhite = Contact(
    img = "img.png",
    name = "Leonardo White",
    id = 14,
    username = "@WhiteLeo"
)

private val contactsList = listOf(jonnyDoe, clairBrumm, leoWhite)