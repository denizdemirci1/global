package com.deniz.global.data.repositories

import com.deniz.global.data.remote.datasources.UserDataSource
import com.deniz.global.model.response.NextResponse
import com.deniz.global.model.response.UserResponse
import com.deniz.global.model.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author: deniz.demirci
 * @date: 19.04.2021
 */

interface UserRepository {

    suspend fun fetchUser(nextPath: String): Flow<Result<UserResponse>>

    suspend fun fetchNextPath(): Flow<Result<NextResponse>>
}

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {

    override suspend fun fetchUser(nextPath: String): Flow<Result<UserResponse>> {
        return userDataSource.fetchUser(nextPath)
    }

    override suspend fun fetchNextPath(): Flow<Result<NextResponse>> {
        return userDataSource.fetchNextPath()
    }
}
