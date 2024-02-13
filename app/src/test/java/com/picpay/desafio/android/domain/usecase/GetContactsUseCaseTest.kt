package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.data.entity.Contact
import com.picpay.desafio.android.data.network.ResponseState
import com.picpay.desafio.android.domain.repository.ContactsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetContactsUseCaseTest {

    private lateinit var getContactsUseCase: GetContactsUseCase

    @MockK
    private lateinit var contactsRepository: ContactsRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getContactsUseCase = GetContactsUseCase(
            contactsRepository = contactsRepository
        )
    }

    @Test
    fun `when call getContacts, response is success should return list of contacts`() = runTest {
        // arrange
        coEvery { contactsRepository.getContactsFromApi() } answers {
            ResponseState.Success(
                contactsList
            )
        }
        val expected = ResponseState.Success(contactsList)

        // act
        val actual = getContactsUseCase.getContacts()

        // assert
        assertEquals(expected, actual)
        coVerify { contactsRepository.getContactsFromApi() }
    }

    @Test
    fun `when call getContacts, response is error should return error`() = runTest {
        // arrange
        coEvery { contactsRepository.getContactsFromApi() } answers {
            ResponseState.Error(RuntimeException())
        }

        // act
        val actual = getContactsUseCase.getContacts()

        // assert
        assertTrue(actual is ResponseState.Error)
        assertTrue((actual as ResponseState.Error).error is RuntimeException)
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