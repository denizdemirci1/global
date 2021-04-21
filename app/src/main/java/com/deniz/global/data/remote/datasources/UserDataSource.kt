package com.deniz.global.data.remote.datasources

import com.deniz.global.data.service.Service
import com.deniz.global.di.IoDispatcher
import com.deniz.global.model.response.NextResponse
import com.deniz.global.model.response.UserResponse
import com.deniz.global.model.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author: deniz.demirci
 * @date: 20.04.2021
 */

interface UserDataSource {

    suspend fun fetchUser(nextPath: String): Flow<Result<UserResponse>>

    suspend fun fetchNextPath(): Flow<Result<NextResponse>>
}

class UserDataSourceImpl @Inject constructor(
    private val service: Service,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UserDataSource {

    override suspend fun fetchUser(nextPath: String): Flow<Result<UserResponse>> {
        return flow {
            emit(Result.Loading)
            emit(Result.Success(service.fetchUser(nextPath)))
        }.catch { error ->
            emit(Result.Error(error.message.orEmpty()))
        }.flowOn(dispatcher)
    }

    override suspend fun fetchNextPath(): Flow<Result<NextResponse>> {
        return flow {
            emit(Result.Loading)
            emit(Result.Success(service.fetchNextPath()))
        }.catch { error ->
            emit(Result.Error(error.message.orEmpty()))
        }.flowOn(dispatcher)
    }
}
