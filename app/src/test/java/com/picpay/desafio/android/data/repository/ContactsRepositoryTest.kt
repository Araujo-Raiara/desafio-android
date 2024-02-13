package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.entity.Contact
import com.picpay.desafio.android.data.network.PicPayService
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
class ContactsRepositoryTest {
    private lateinit var contactsRepository: ContactsRepository

    @MockK
    private lateinit var picPayService: PicPayService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        contactsRepository = ContactsRepositoryImpl(
            picPayService = picPayService
        )
    }

    @Test
    fun `when call getContactsFromApi, response is success should return list of contacts`() =
        runTest {
            // arrange
            coEvery { picPayService.getContacts() } answers { contactsList }
            val expected = ResponseState.Success(contactsList)

            // act
            val actual = contactsRepository.getContactsFromApi()

            // assert
            assertEquals(expected, actual)
            coVerify { picPayService.getContacts() }
        }

    @Test
    fun `when call getContactsFromApi, response is error, should return error`() = runTest {
        // arrange
        coEvery { picPayService.getContacts() } throws RuntimeException()

        // act
        val actual = contactsRepository.getContactsFromApi()

        // assert
        assertTrue(actual is ResponseState.Error)
        assertTrue((actual as ResponseState.Error).error is RuntimeException)
    }

    @Test
    fun `when call getLatestContacts before calling api should return empty list`() = runTest {
        // arrange
        val expected = emptyList<Contact>()

        // act
        val actual = contactsRepository.getLatestContacts()

        // assert
        assertEquals(expected, actual)
    }

    @Test
    fun `when call getLatestContacts after calling api should return contacts`() = runTest {
        // arrange
        coEvery { picPayService.getContacts() } answers { contactsList }
        val expected = contactsList

        // act
        contactsRepository.getContactsFromApi()
        val actual = contactsRepository.getLatestContacts()

        // assert
        assertEquals(expected, actual)
        coVerify { picPayService.getContacts() }
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