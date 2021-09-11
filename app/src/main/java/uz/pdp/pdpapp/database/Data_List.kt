package uz.pdp.pdpapp.database

import uz.pdp.pdpapp.classes.Group
import uz.pdp.pdpapp.classes.GroupClass
import uz.pdp.pdpapp.classes.MentorClass
import uz.pdp.pdpapp.classes.StudentsClass

interface Data_List {

    fun addCourse(course: GroupClass)
    fun addMentor(mentor: MentorClass)
    fun addGroup(group: Group)
    fun addStudent(student: StudentsClass)

    fun editMentor(mentor: MentorClass)
    fun editGroup(group: Group)
    fun editStudent(student: StudentsClass)

    fun deleteMentor(mentor: MentorClass)
    fun deleteGroup(group: Group)
    fun deleteStudent(student: StudentsClass)

    fun getByIDMentor(n: Int): MentorClass
    fun getByIDGroup(n: Int): Group
    fun getByIDStudent(n: Int): StudentsClass

    fun getListMentor(): List<MentorClass>
    fun getLisIsOpenGroup(n: Int): List<Group>
    fun getListStudent(): List<StudentsClass>
}