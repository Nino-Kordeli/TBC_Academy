package com.example.tbcacademy.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tbcacademy.data.mapper.toDomain
import com.example.tbcacademy.data.remote.AuthApi
import com.example.tbcacademy.domain.model.User

class UsersPagingSource(
    private val api: AuthApi
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 1
            val response = api.getUsers(page)

            if (response.isSuccessful) {
                val usersDto = response.body()?.data ?: emptyList()
                val users = usersDto.map { it.toDomain() }

                LoadResult.Page(
                    data = users,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (users.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Exception("HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}