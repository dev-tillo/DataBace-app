package uz.pdp.pdpapp.classes

import java.io.Serializable

data class StudentsClass(
    var id: Int = 0,
    var name: String,
    var surname: String,
    var lastname: String,
    var time: String,
    var group: Group,
    var mentor: MentorClass
) :
    Serializable