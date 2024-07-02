package uk.gleissner.javakotlin.dto

interface DtoSample {
    fun departmentJson(departmentName: String, departmentHeadName: String?): String

    fun deserializedDepartmentJsonMatches(json: String, json2: String): Boolean
}
