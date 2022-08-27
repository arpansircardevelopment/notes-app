package com.arpansircar.notes_app.common

object ConstantsBase {

    // ERROR MESSAGES
    const val USER_DOES_NOT_EXIST_ERROR = "The email provided doesn\'t exist. Check and try again."
    const val INCORRECT_PASSWORD_ERROR = "The password provided is incorrect. Check and try again."
    const val INTERNET_CONNECTIVITY_ISSUES_ERROR = "Check your internet connection and try again."
    const val USER_ALREADY_EXISTS_ERROR = "The email entered is already in use."

    // DATABASE CONSTANTS

    const val NOTES_DATABASE = "notes_database"

    const val TABLE_NOTES = "notes"
    const val NOTE_ID = "note_id"
    const val NOTE_TITLE = "note_title"
    const val NOTE_DETAIL = "note_detail"
    const val CREATED_AT = "created_at"

}