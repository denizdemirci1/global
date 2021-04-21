package com.deniz.global.ui.home

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.deniz.global.R
import com.deniz.global.launchFragmentInHiltContainer
import com.deniz.global.model.response.UserResponse
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.not
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
class HomeFragmentTest {

    @MockK
    lateinit var navController: NavController

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // Subject under test
    private lateinit var homeFragment: HomeFragment


    @Before
    fun setup() {
        hiltRule.inject()
        MockKAnnotations.init(this, relaxed = true)
        val fragmentArgs = bundleOf("nextPath" to "test_path")
        launchFragmentInHiltContainer<HomeFragment>(fragmentArgs) {
            homeFragment = this
            Navigation.setViewNavController(requireView(), navController)
        }
    }

    @Test
    fun when_onSuccess_is_called__texts_of_textViews_are_set_accordingly() {
        val userResponse = UserResponse("path", "response_code")
        val fetchCount = 3

        homeFragment.onSuccess(userResponse, fetchCount)

        onView(withId(R.id.response_code)).check(matches(withText("response_code")))
        onView(withId(R.id.times_fetched)).check(matches(withText("3")))
    }

    @Test
    fun when_onLoading_is_called_with_true__progress_is_shown() {
        homeFragment.onLoading(true)
        onView(withId(R.id.home_progress)).check(matches(isDisplayed()))
    }

    @Test
    fun when_onLoading_is_called_with_false__progress_is_shown() {
        homeFragment.onLoading(false)
        onView(withId(R.id.home_progress)).check(matches(not(isDisplayed())))
    }

    @Test
    fun when_onInitial_is_called__text_of_textViews_are_set_accordingly() {
        homeFragment.onInitial(5)
        onView(withId(R.id.response_code)).check(matches(withText(R.string.click_to_get_response_code)))
        onView(withId(R.id.times_fetched)).check(matches(withText("5")))
    }
}