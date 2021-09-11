package uz.pdp.pdpapp.classes

import java.io.Serializable

data class Group(
    var id: Int = 0,
    var name: String,
    var isOpen: Int,
    var date: String,
    var time: String,
    var mentor: MentorClass

) : Serializable
