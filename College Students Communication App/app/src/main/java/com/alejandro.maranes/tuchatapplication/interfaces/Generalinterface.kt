package com.alejandro.maranes.tuchatapplication.interfaces

import com.alejandro.maranes.tuchatapplication.models.Group

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