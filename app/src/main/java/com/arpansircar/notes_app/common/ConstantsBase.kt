package com.arpansircar.notes_app.common

object ConstantsBase {

    // error message constants
    const val USER_DOES_NOT_EXIST_ERROR = "The email provided doesn\'t exist. Check and try again."
    const val INCORRECT_PASSWORD_ERROR = "The password provided is incorrect. Check and try again."
    const val INTERNET_CONNECTIVITY_ISSUES_ERROR = "Check your internet connection and try again."
    const val USER_ALREADY_EXISTS_ERROR = "The email entered is already in use."

    // database constants
    const val NOTES_DATABASE = "notes_database"
    const val TABLE_NOTES = "notes"
    const val NOTE_ID = "note_id"
    const val NOTE_UUID = "note_uuid"
    const val NOTE_TITLE = "note_title"
    const val NOTE_DETAIL = "note_detail"
    const val CREATED_AT = "created_at"

    // note type constants
    const val NOTE_TYPE = "type"
    const val NOTE_TYPE_ADD = "add"
    const val NOTE_TYPE_EDIT = "edit"

    // dialog type constants
    const val DIALOG_TYPE_DELETE = "delete_dialog"
    const val DIALOG_TYPE_EXIT = "exit_dialog"

}