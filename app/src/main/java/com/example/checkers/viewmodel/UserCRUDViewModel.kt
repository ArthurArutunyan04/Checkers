package com.example.checkers.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.checkers.database.UserDatabase
import com.example.checkers.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserCRUDViewModel : ViewModel() {
    private var database: UserDatabase? = null
    private val _allUsers = MutableStateFlow<List<User>>(emptyList())
    val allUsers: StateFlow<List<User>> = _allUsers

    fun initializeDatabase(database: UserDatabase) {
        this.database = database
    }

    fun loadAllUsers() {
        viewModelScope.launch {
            val users = database?.userDao()?.getAllUsers() ?: emptyList()
            _allUsers.value = users
        }
    }

    fun createUser(user: User) {
        viewModelScope.launch {
            database?.userDao()?.insertUser(user)
            loadAllUsers()
        }
    }

    fun readUser(username: String): User? {
        return null
    }

    fun getUserByUsername(username: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user = database?.userDao()?.getUserByUsername(username)
            onResult(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            database?.userDao()?.updateUser(user)
            loadAllUsers()
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            database?.userDao()?.deleteUser(user)
            loadAllUsers()
        }
    }

    fun deleteUserByUsername(username: String) {
        viewModelScope.launch {
            database?.userDao()?.deleteUserByUsername(username)
            loadAllUsers()
        }
    }
}