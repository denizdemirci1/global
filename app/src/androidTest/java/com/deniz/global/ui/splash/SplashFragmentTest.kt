package com.deniz.global.ui.splash

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.deniz.global.R
import com.deniz.global.launchFragmentInHiltContainer
import com.deniz.global.model.response.NextResponse
import com.deniz.global.ui.home.HomeFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author: deniz.demirci
 * @date: 20.04.2021
 */

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class SplashFragmentTest {

    @MockK
    lateinit var navController: NavController

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // Subject under test
    private lateinit var splashFragment: SplashFragment


    @Before
    fun setup() {
        hiltRule.inject()
        MockKAnnotations.init(this, relaxed = true)
        launchFragmentInHiltContainer<SplashFragment> {
            splashFragment = this
            Navigation.setViewNavController(requireView(), navController)
        }
    }

    @Test
    fun when_OnSuccess_is_called__navigate_is_called_on_navController() {
        val nextResponse = NextResponse("path")
        splashFragment.onSuccess(nextResponse)

        val arg = nextResponse.nextPath

        val direction = SplashFragmentDirections.actionSplashFragmentToHomeFragment(arg)

        verify { navController.navigate(direction) }
    }
}