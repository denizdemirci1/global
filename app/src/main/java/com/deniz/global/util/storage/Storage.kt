package com.deniz.global.util.storage

/**
 * @author: deniz.demirci
 * @date: 19.04.2021
 */

interface Storage {

    fun setInt(key: String, value: Int)

    fun getInt(key: String): Int?
}
