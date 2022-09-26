package com.example.tuchatapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tuchatapplication.models.User
import com.example.tuchatapplication.reporitories.AuthRepository
import com.example.tuchatapplication.reporitories.MembersRepository
import kotlinx.coroutines.launch

class RegisterActivityViewModel(application: Application): AndroidViewModel(application) {
    private var authRepository: AuthRepository? = null
    private var membersRepository: MembersRepository? = null
    private var result: Long? = null
    private var user:User? = null
    private var updateCode: Int? = null
    private var lst: ArrayList<User>? = null
    private val TAG = "RegisterActivityViewMod"

    init {
        authRepository = AuthRepository.setInstance(application)
        membersRepository = MembersRepository.setInstance(application)
    }

    //register user

    fun createAccount(user: User): Long{
        viewModelScope.launch {
            result = authRepository!!.createAccount(user)
        }

        return result!!
    }

    //login user

    fun loginUser(email: String, password: String): User{
        viewModelScope.launch {
            user = authRepository!!.loginUser(email, password)
        }

        return user!!
    }

    //update user

    fun updateUser(userId: String, firstName: String, lastName: String, phone: String, password: String): Int{
        viewModelScope.launch {
            updateCode = authRepository!!.updateUser(userId, firstName, lastName, phone, password)
        }

        return updateCode!!
    }

    //retrieve users

    fun getAllUsers(): ArrayList<User>{
        viewModelScope.launch {
            lst = authRepository!!.getUsers()
        }

        return lst!!
    }


}