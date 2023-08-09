package com.example.recipemobileapp.Database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
@Entity(tableName ="Meal")
data class Meal(
    @PrimaryKey(autoGenerate = false) val idMeal: String,
    val strArea: String="",
    val strCategory: String="",
    val strInstructions: String="",
    val strMeal: String="",
    val strMealThumb: String="",
    val strTags: String?="",
    val strYoutube: String=""


) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idMeal)
        parcel.writeString(strArea)
        parcel.writeString(strCategory)
        parcel.writeString(strInstructions)
        parcel.writeString(strMeal)
        parcel.writeString(strMealThumb)
        parcel.writeString(strTags)
        parcel.writeString(strYoutube)
    }

    override fun describeContents(): Int {
        return 0
    }


    companion object CREATOR : Parcelable.Creator<Meal> {
        override fun createFromParcel(parcel: Parcel): Meal {
            return Meal(parcel)
        }

        override fun newArray(size: Int): Array<Meal?> {
            return arrayOfNulls(size)
        }


    }
}

