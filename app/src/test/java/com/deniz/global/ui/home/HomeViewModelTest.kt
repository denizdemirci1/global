package com.deniz.global.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.deniz.global.data.repositories.UserRepository
import com.deniz.global.extensions.orZero
import com.deniz.global.model.response.NextResponse
import com.deniz.global.model.response.UserResponse
import com.deniz.global.model.util.Result
import com.deniz.global.util.MainCoroutineRule
import com.deniz.global.util.storage.Constants
import com.deniz.global.util.storage.Storage
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author: deniz.demirci
 * @date: 21.04.2021
 */

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    // Executes each task synchronously.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Swaps main dispatcher for coroutines
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var homeViewModel: HomeViewModel

    @MockK
    private lateinit var repository: UserRepository

    @MockK
    private lateinit var storage: Storage

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        homeViewModel = HomeViewModel(repository, storage)
    }

    @Test
    fun `when viewModel is initialized, state value is set to Initial`() =
        mainCoroutineRule.runBlockingTest {
            Truth.assertThat(homeViewModel.state.value)
                .isInstanceOf(HomeViewState.Initial::class.java)
        }

    @Test
    fun `when getUserId is called, state is set Success if response is Success`() =
        mainCoroutineRule.runBlockingTest {
            val response = UserResponse("path", "response")
            val res = MutableStateFlow( Result.Success(response))

            coEvery { repository.fetchUser(any()) } returns res

            homeViewModel.getUserId("")

            Truth.assertThat(homeViewModel.state.value)
                .isInstanceOf(HomeViewState.Success::class.java)
        }

    @Test
    fun `when getUserId is called, state is set Loading if response is Loading`() =
        mainCoroutineRule.runBlockingTest {
            val res = MutableStateFlow(Result.Loading)

            coEvery { repository.fetchUser(any()) } returns res

            homeViewModel.getUserId("")

            Truth.assertThat(homeViewModel.state.value)
                .isInstanceOf(HomeViewState.Loading::class.java)
        }

    @Test
    fun `when getUserId is called, state is set Error if response is Error`() =
        mainCoroutineRule.runBlockingTest {
            val errorMessage = "error"
            val res = MutableStateFlow(Result.Error(errorMessage))

            coEvery { repository.fetchUser(any()) } returns res

            homeViewModel.getUserId("")

            Truth.assertThat(homeViewModel.state.value)
                .isInstanceOf(HomeViewState.Error::class.java)
        }

    @Test
    fun `when onSuccess is called, fetchCount is refreshed, state value is set to Success`() =
        mainCoroutineRule.runBlockingTest {
            val data = UserResponse("path", "response")
            val fetchCount = storage.getInt(Constants.TIMES_FETCHED_KEY).orZero().inc()
            homeViewModel.onSuccess(data)

            verify { storage.getInt(Constants.TIMES_FETCHED_KEY) }
            verify { storage.setInt(
                Constants.TIMES_FETCHED_KEY,
                fetchCount
            ) }

            Truth.assertThat(homeViewModel.state.value)
                .isEqualTo(HomeViewState.Success(data, fetchCount))
        }
}
