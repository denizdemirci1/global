package com.deniz.global.ui.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.deniz.global.data.repositories.UserRepository
import com.deniz.global.model.response.NextResponse
import com.deniz.global.model.util.Result
import com.deniz.global.util.MainCoroutineRule
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class SplashViewModelTest {

    // Executes each task synchronously.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Swaps main dispatcher for coroutines
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var splashViewModel: SplashViewModel

    @MockK
    private lateinit var repository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        splashViewModel = SplashViewModel(repository)
    }

    @Test
    fun `when viewModel is initialized, state value is set to Initial`() =
        mainCoroutineRule.runBlockingTest {
            Truth.assertThat(splashViewModel.state.value)
                .isInstanceOf(SplashViewState.Initial::class.java)
        }

    @Test
    fun `when getNextPath is called, state is set Success if response is Success`() =
        mainCoroutineRule.runBlockingTest {
            val response = NextResponse("path")
            val res = MutableStateFlow(Result.Success(response))

            coEvery { repository.fetchNextPath() } returns res

            splashViewModel.getNextPath()

            Truth.assertThat(splashViewModel.state.value)
                .isInstanceOf(SplashViewState.Success::class.java)
        }

    @Test
    fun `when getNextPath is called, state is set Loading if response is Loading`() =
        mainCoroutineRule.runBlockingTest {
            val res = MutableStateFlow(Result.Loading)

            coEvery { repository.fetchNextPath() } returns res

            splashViewModel.getNextPath()

            Truth.assertThat(splashViewModel.state.value)
                .isInstanceOf(SplashViewState.Loading::class.java)
        }

    @Test
    fun `when getNextPath is called, state is set Error if response is Error`() =
        mainCoroutineRule.runBlockingTest {
            val errorMessage = "error"
            val res = MutableStateFlow(Result.Error(errorMessage))

            coEvery { repository.fetchNextPath() } returns res

            splashViewModel.getNextPath()

            Truth.assertThat(splashViewModel.state.value)
                .isInstanceOf(SplashViewState.Error::class.java)
        }
}
