package com.deniz.global.ui.home

import com.deniz.global.model.response.UserResponse

/**
 * @author: deniz.demirci
 * @date: 20.04.2021
 */

sealed class HomeViewState {

    data class Initial(val fetchCount: Int): HomeViewState()
    object Loading: HomeViewState()
    data class Success(val data: UserResponse, val fetchCount: Int): HomeViewState()
    data class Error(val message: String): HomeViewState()
}
