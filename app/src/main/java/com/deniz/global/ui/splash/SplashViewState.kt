package com.deniz.global.ui.splash

import com.deniz.global.model.response.NextResponse

/**
 * @author: deniz.demirci
 * @date: 20.04.2021
 */

sealed class SplashViewState {

    object Initial: SplashViewState()
    object Loading: SplashViewState()
    data class Success(val data: NextResponse): SplashViewState()
    data class Error(val message: String): SplashViewState()
}