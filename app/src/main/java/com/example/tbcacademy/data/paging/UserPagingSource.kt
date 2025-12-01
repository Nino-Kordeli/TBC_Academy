package com.example.tbcacademy.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tbcacademy.data.dto.UserDto
import com.example.tbcacademy.data.remote.AuthApi

class UsersPagingSource(
    private val api: AuthApi
) : PagingSource<Int, UserDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserDto> {
        return try {
            val page = params.key ?: 1
            val response = api.getUsers(page)

            if (response.isSuccessful) {
                val users = response.body()?.data ?: emptyList()
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

    override fun getRefreshKey(state: PagingState<Int, UserDto>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}
