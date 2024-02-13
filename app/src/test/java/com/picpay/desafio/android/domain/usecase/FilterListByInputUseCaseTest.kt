package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.data.entity.Contact
import com.picpay.desafio.android.data.network.ResponseState
import com.picpay.desafio.android.domain.repository.ContactsRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FilterListByInputUseCaseTest {

    private lateinit var filterListByInputUseCase: FilterListByInputUseCase

    @MockK
    private lateinit var contactsRepository: ContactsRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        filterListByInputUseCase = FilterListByInputUseCase(
            contactsRepository
        )
    }

    @Test
    fun `when call filterListByInput, textInput is blank should return contacts list `() =
        runTest() {
            // arrange
            val input = " "
            every { contactsRepository.getLatestContacts() } answers { contactsList }
            val expected = ResponseState.Success(contactsList)

            // act
            val actual = filterListByInputUseCase.invoke(input)

            // assert
            assertEquals(expected, actual)
            verify { contactsRepository.getLatestContacts() }
        }

    @Test
    fun `when call filterListByInput, should return contacts list filtered by input `() =
        runTest() {
            // arrange
            val input = "J"
            every { contactsRepository.getLatestContacts() } answers { listOf(jonnyDoe) }
            val expected = ResponseState.Success(listOf(jonnyDoe))

            // act
            val actual = filterListByInputUseCase.invoke(input)

            // assert
            assertEquals(expected, actual)
            verify { contactsRepository.getLatestContacts() }
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
