package com.example.tuchatapplication.interfaces

import com.example.tuchatapplication.models.Group

interface Generalinterface {
    fun getPhoneDetails(phone: String): String
    fun goToProfile()
    fun goToNewChatRooms()
    fun goToSearch()
    fun logOut()
    fun addChatRoom(group: Group)
    fun goToMainPage()
    fun goToChatPage(groupId: String)
}