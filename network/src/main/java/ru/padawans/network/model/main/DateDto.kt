package ru.padawans.network.model.main

import com.google.gson.annotations.SerializedName
import ru.padawans.domain.model.main.Dates


class DatesDto (

    @SerializedName("maximum") val maximum : String?,
    @SerializedName("minimum") val minimum : String?
){
    fun converter(): Dates {
        return Dates(maximum!!, minimum!!)
    }
}