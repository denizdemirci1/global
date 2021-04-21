package com.deniz.global.util.storage

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * @author: deniz.demirci
 * @date: 19.04.2021
 */

class SharedPreferencesStorage @Inject constructor(
    @ApplicationContext context: Context
) : Storage {

    private val sharedPreferences = context.getSharedPreferences(
        "Global",
        Context.MODE_PRIVATE
    )

    override fun setInt(key: String, value: Int) {
        with(sharedPreferences.edit()) {
            putInt(key, value)
            apply()
        }
    }

    override fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }
}
