package com.example.iouapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.iouapp.data.User
import com.example.iouapp.data.UserDatabase
import com.example.iouapp.data.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UserRepository(UserDatabase.getInstance(application).userDao())

    val users: StateFlow<List<User>> = repository
        .getUsers()
        .map { users -> users.sortedBy { it.name.lowercase() } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun user(userId: Long): Flow<User?> = repository.getUser(userId)

    fun addUser(name: String) {
        val trimmedName = name.trim()
        if (trimmedName.isEmpty()) return
        viewModelScope.launch {
            repository.addUser(trimmedName)
        }
    }

    fun updateUser(user: User, newName: String) {
        val trimmedName = newName.trim()
        if (trimmedName.isEmpty()) return
        viewModelScope.launch {
            repository.updateUser(user.copy(name = trimmedName))
        }
    }

    fun addToBalance(user: User, amount: Double) {
        if (amount == 0.0) return
        viewModelScope.launch {
            repository.updateUser(user.copy(balance = user.balance + amount))
        }
    }

    fun subtractFromBalance(user: User, amount: Double) {
        if (amount == 0.0) return
        viewModelScope.launch {
            repository.updateUser(user.copy(balance = user.balance - amount))
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            repository.deleteUser(user)
        }
    }
}
