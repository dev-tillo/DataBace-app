package uz.pdp.pdpapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uz.pdp.pdpapp.classes.*

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION),
    Data_List {

    companion object {
        val DATABASE_NAME = "pdp_db"
        val VERSION = 1


        val TABLE_NAME_COURSE = "course"
        val COURSE_ID = "course_id"
        val COURSE_NAME = "course_name"
        val COURSE_DESCRIPTION = "course_description"

        val TABLE_NAME_GROUP = "groups"
        val GROUP_ID = "group_id"
        val GROUP_NAME = "groups_name"
        val GROUP_DATE = "groups_date"
        val GROUP_TIME = "group_time"
        val IS_OPEN = "is_open"
        val MENTOR_ID_GROUP = "mentor_id_group"

        val TABLE_NAME_MENTOR = "mentor"
        val MENTOR_ID = "mentor_id"
        val MENTOR_NAME = "mentor_name"
        val MENTOR_SURNAME = "mentor_surname"
        val MENTOR_LASTNAME = "mentor_lastname"
        val COURSE_ID_MENTOR = "course_id_mentor"

        val TABLE_NAME_STUDENT = "student"
        val STUDENT_ID = "student_id"
        val STUDENT_NAME = "student_name"
        val STUDENT_SURNAME = "student_surname"
        val STUDENT_LASTNAME = "student_lastname"
        val GROUP_ID_STUDENT = "group_id_student"
        val MENTOR_ID_STUDENT = "mentor_id_student"
        val STUDENT_TIME = "student_time"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query_course =
            "create table $TABLE_NAME_COURSE ($COURSE_ID integer primary key autoincrement not null, $COURSE_NAME text not null, $COURSE_DESCRIPTION text not null)"
        val query_mentor =
            "create table $TABLE_NAME_MENTOR ($MENTOR_ID integer primary key autoincrement not null, $MENTOR_NAME text not null, $MENTOR_SURNAME text not null, $MENTOR_LASTNAME text not null, $COURSE_ID_MENTOR integer not null, foreign key ($COURSE_ID_MENTOR) references $TABLE_NAME_COURSE($COURSE_ID))"
        val query_group =
            "create table $TABLE_NAME_GROUP ($GROUP_ID integer primary key autoincrement not null, $GROUP_NAME text not null, $IS_OPEN integer not null, $GROUP_DATE text not null, $GROUP_TIME text not null, $MENTOR_ID_GROUP integer not null, foreign key ($MENTOR_ID_GROUP) references $TABLE_NAME_MENTOR($MENTOR_ID))"
        val query_student =
            "create table $TABLE_NAME_STUDENT ($STUDENT_ID integer primary key autoincrement not null, $STUDENT_NAME text not null, $STUDENT_SURNAME text not null, $STUDENT_LASTNAME text not null, $STUDENT_TIME text not null, $GROUP_ID_STUDENT integer not null, $MENTOR_ID_STUDENT integer not null, foreign key ($GROUP_ID_STUDENT) references $TABLE_NAME_GROUP($GROUP_ID), foreign key ($MENTOR_ID_STUDENT) references $TABLE_NAME_MENTOR($MENTOR_ID))"
        db?.execSQL(query_course)
        db?.execSQL(query_group)
        db?.execSQL(query_mentor)
        db?.execSQL(query_student)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    override fun addCourse(course: GroupClass) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COURSE_NAME, course.name)
        contentValues.put(COURSE_DESCRIPTION, course.description)
        database.insert(TABLE_NAME_COURSE, null, contentValues)
        database.close()
    }

    override fun addMentor(mentor: MentorClass) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(MENTOR_NAME, mentor.name)
        contentValues.put(MENTOR_SURNAME, mentor.surname)
        contentValues.put(MENTOR_LASTNAME, mentor.lastname)
        contentValues.put(COURSE_ID_MENTOR, mentor.course.id)
        database.insert(TABLE_NAME_MENTOR, null, contentValues)
        database.close()
    }

    override fun addGroup(group: Group) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(GROUP_NAME, group.name)
        contentValues.put(IS_OPEN, group.isOpen)
        contentValues.put(GROUP_DATE, group.date)
        contentValues.put(GROUP_TIME, group.time)
        contentValues.put(MENTOR_ID_GROUP, group.mentor.id)
        database.insert(TABLE_NAME_GROUP, null, contentValues)
        database.close()
    }

    override fun addStudent(student: StudentsClass) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(STUDENT_NAME, student.name)
        contentValues.put(STUDENT_SURNAME, student.surname)
        contentValues.put(STUDENT_LASTNAME, student.lastname)
        contentValues.put(STUDENT_TIME, student.time)
        contentValues.put(GROUP_ID_STUDENT, student.group.id)
        contentValues.put(MENTOR_ID_STUDENT, student.mentor.id)
        database.insert(TABLE_NAME_STUDENT, null, contentValues)
        database.close()
    }

    override fun editMentor(mentor: MentorClass) {
        val databse = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(MENTOR_ID, mentor.id)
        contentValues.put(MENTOR_NAME, mentor.name)
        contentValues.put(MENTOR_SURNAME, mentor.surname)
        contentValues.put(MENTOR_LASTNAME, mentor.lastname)
        contentValues.put(COURSE_ID_MENTOR, mentor.course.id)
        databse.update(TABLE_NAME_MENTOR, contentValues, "$MENTOR_ID = ?", arrayOf("${mentor.id}"))
        databse.close()
    }

    override fun editGroup(group: Group) {
        val databse = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(GROUP_ID, group.id)
        contentValues.put(GROUP_NAME, group.name)
        contentValues.put(IS_OPEN, group.isOpen)
        contentValues.put(GROUP_DATE, group.date)
        contentValues.put(GROUP_TIME, group.time)
        contentValues.put(MENTOR_ID_GROUP, group.mentor.id)
        databse.update(TABLE_NAME_GROUP, contentValues, "$GROUP_ID = ?", arrayOf("${group.id}"))
        databse.close()
    }

    override fun editStudent(student: StudentsClass) {
        val databse = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(STUDENT_ID, student.id)
        contentValues.put(STUDENT_NAME, student.name)
        contentValues.put(STUDENT_SURNAME, student.surname)
        contentValues.put(STUDENT_LASTNAME, student.lastname)
        contentValues.put(STUDENT_TIME, student.time)
        contentValues.put(GROUP_ID_STUDENT, student.group.id)
        contentValues.put(MENTOR_ID_STUDENT, student.mentor.id)
        databse.update(
            TABLE_NAME_STUDENT,
            contentValues,
            "$STUDENT_ID = ?",
            arrayOf("${student.id}")
        )
        databse.close()
    }

    override fun deleteMentor(mentor: MentorClass) {
        deleteStudentsByMentor(mentor.id)
        deleteGroups(mentor.id)
        val database = this.writableDatabase
        database.delete(TABLE_NAME_MENTOR, "$MENTOR_ID = ?", arrayOf("${mentor.id}"))
        database.close()
    }

    override fun deleteGroup(group: Group) {
        deleteStudentsByGroup(group.id)
        val database = this.writableDatabase
        database.delete(TABLE_NAME_GROUP, "$GROUP_ID = ?", arrayOf("${group.id}"))
        database.close()
    }

    override fun deleteStudent(student: StudentsClass) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME_STUDENT, "$STUDENT_ID = ?", arrayOf("${student.id}"))
        database.close()
    }

    fun deleteStudentsByGroup(n: Int) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME_STUDENT, "$GROUP_ID_STUDENT = ?", arrayOf("$n"))
        database.close()
    }

    fun deleteStudentsByMentor(n: Int) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME_STUDENT, "$MENTOR_ID_STUDENT = ?", arrayOf("$n"))
        database.close()
    }

    fun deleteGroups(n: Int) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME_GROUP, "$MENTOR_ID_GROUP = ?", arrayOf("$n"))
        database.close()
    }

    fun getByIDCourse(n: Int): GroupClass {
        val database = this.readableDatabase
        val cursor = database.query(
            TABLE_NAME_COURSE,
            arrayOf(COURSE_ID, COURSE_NAME, COURSE_DESCRIPTION),
            "$COURSE_ID = ?",
            arrayOf("$n"),
            null,
            null,
            null
        )
        cursor.moveToFirst()
        return GroupClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
    }

    override fun getByIDMentor(n: Int): MentorClass {
        val database = this.readableDatabase
        val cursor = database.query(
            TABLE_NAME_MENTOR,
            arrayOf(MENTOR_ID, MENTOR_NAME, MENTOR_SURNAME, MENTOR_LASTNAME, COURSE_ID_MENTOR),
            "$MENTOR_ID = ?", arrayOf("$n"), null, null, null
        )
        cursor.moveToFirst()
        val course = getByIDCourse(cursor.getInt(4))

        return MentorClass(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            course
        )
    }

    override fun getByIDGroup(n: Int): Group {
        val database = this.readableDatabase
        val cursor = database.query(
            TABLE_NAME_GROUP,
            arrayOf(GROUP_ID, GROUP_NAME, IS_OPEN, GROUP_DATE, GROUP_TIME, MENTOR_ID_GROUP),
            "$GROUP_ID = ?", arrayOf("$n"), null, null, null
        )
        cursor.moveToFirst()
        val mentor = getByIDMentor(cursor.getInt(5))

        return Group(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getInt(2),
            cursor.getString(3),
            cursor.getString(4),
            mentor
        )
    }

    override fun getByIDStudent(n: Int): StudentsClass {
        val database = this.readableDatabase
        val cursor = database.query(
            TABLE_NAME_STUDENT,
            arrayOf(
                STUDENT_ID,
                STUDENT_NAME,
                STUDENT_SURNAME,
                STUDENT_LASTNAME,
                STUDENT_TIME,
                GROUP_ID,
                MENTOR_ID
            ),
            "$STUDENT_ID = ?", arrayOf("$n"), null, null, null
        )
        cursor.moveToFirst()
        val mentor = getByIDMentor(cursor.getInt(6))
        val group = getByIDGroup(cursor.getInt(5))

        return StudentsClass(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4),
            group,
            mentor
        )
    }

    fun getCountCourse(): Int {
        val database = this.readableDatabase
        val query = "select * from $TABLE_NAME_COURSE"
        val cursor = database.rawQuery(query, null)

        return cursor.count
    }

    fun getListCourse(): List<GroupClass> {
        val list = ArrayList<GroupClass>()
        val database = this.readableDatabase
        val query = "select * from $TABLE_NAME_COURSE"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                list.add(GroupClass(cursor.getInt(0), cursor.getString(1), cursor.getString(2)))
            } while (cursor.moveToNext())
        }

        return list
    }

    override fun getListMentor(): List<MentorClass> {
        val list = ArrayList<MentorClass>()
        val database = this.readableDatabase
        val query = "select * from $TABLE_NAME_MENTOR"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val course = getByIDCourse(cursor.getInt(4))
                list.add(
                    MentorClass(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        course
                    )
                )
            } while (cursor.moveToNext())
        }

        return list
    }

    fun getListMentorByID(n: Int): List<MentorClass> {
        val list = ArrayList<MentorClass>()
        val database = this.readableDatabase
        val query = "select * from $TABLE_NAME_MENTOR where $COURSE_ID_MENTOR = $n"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val course = getByIDCourse(cursor.getInt(4))
                list.add(
                    MentorClass(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        course
                    )
                )
            } while (cursor.moveToNext())
        }

        return list
    }

    override fun getLisIsOpenGroup(n: Int): List<Group> {
        val list = ArrayList<Group>()
        val database = this.readableDatabase
        val query = "select * from $TABLE_NAME_GROUP where $IS_OPEN = $n"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val mentor = getByIDMentor(cursor.getInt(5))
                list.add(
                    Group(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        mentor
                    )
                )
            } while (cursor.moveToNext())
        }

        return list
    }

    fun getListIsOpenGroup_courseID(n: Int, c: Int): List<Group> {
        val list = ArrayList<Group>()
        val database = this.readableDatabase
        val query =
            "select $GROUP_ID, $GROUP_NAME, $IS_OPEN, $GROUP_DATE, $GROUP_TIME, $MENTOR_ID from ($TABLE_NAME_GROUP inner join $TABLE_NAME_MENTOR on $MENTOR_ID_GROUP = $MENTOR_ID) where $COURSE_ID_MENTOR = $c and $IS_OPEN = $n"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val mentor = getByIDMentor(cursor.getInt(5))
                list.add(
                    Group(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        mentor
                    )
                )
            } while (cursor.moveToNext())
        }

        return list
    }

    override fun getListStudent(): List<StudentsClass> {
        val list = ArrayList<StudentsClass>()
        val database = this.readableDatabase
        val query = "select * from $TABLE_NAME_STUDENT"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val mentor = getByIDMentor(cursor.getInt(6))
                val group = getByIDGroup(cursor.getInt(5))

                list.add(
                    StudentsClass(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        group,
                        mentor
                    )
                )
            } while (cursor.moveToNext())
        }

        return list
    }

    fun getListStudentByID(n: Int): List<StudentsClass> {
        val list = ArrayList<StudentsClass>()
        val database = this.readableDatabase
        val query = "select * from $TABLE_NAME_STUDENT where $GROUP_ID_STUDENT = $n"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val mentor = getByIDMentor(cursor.getInt(6))
                val group = getByIDGroup(cursor.getInt(5))

                list.add(
                    StudentsClass(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        group,
                        mentor
                    )
                )
            } while (cursor.moveToNext())
        }

        return list
    }
}