package com.example.iouapp.data

import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    fun getUsers(): Flow<List<User>> = userDao.observeUsers()

    fun getUser(userId: Long): Flow<User?> = userDao.observeUser(userId)

    suspend fun addUser(name: String) {
        userDao.insert(User(name = name))
    }

    suspend fun updateUser(user: User) {
        userDao.update(user)
    }
}
