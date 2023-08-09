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
    // val strCreativeCommonsConfirmed: Any,
    //  val strDrinkAlternate: Any,
//    val strImageSource: Any,
    val strIngredient1: String="",
    val strIngredient10: String="",
    val strIngredient11: String="",
    val strIngredient12: String="",
    val strIngredient13: String="",
    val strIngredient14: String="",
    val strIngredient15: String="",
    val strIngredient16: String="",
    val strIngredient17: String="",
    val strIngredient18: String="",
    val strIngredient19: String="",
    val strIngredient2: String="",
    val strIngredient20: String="",
    val strIngredient3: String="",
    val strIngredient4: String="",
    val strIngredient5: String="",
    val strIngredient6: String="",
    val strIngredient7: String="",
    val strIngredient8: String="",
    val strIngredient9: String="",
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
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
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
        parcel.writeString(strIngredient1)
        parcel.writeString(strIngredient10)
        parcel.writeString(strIngredient11)
        parcel.writeString(strIngredient12)
        parcel.writeString(strIngredient13)
        parcel.writeString(strIngredient14)
        parcel.writeString(strIngredient15)
        parcel.writeString(strIngredient16)
        parcel.writeString(strIngredient17)
        parcel.writeString(strIngredient18)
        parcel.writeString(strIngredient19)
        parcel.writeString(strIngredient2)
        parcel.writeString(strIngredient20)
        parcel.writeString(strIngredient3)
        parcel.writeString(strIngredient4)
        parcel.writeString(strIngredient5)
        parcel.writeString(strIngredient6)
        parcel.writeString(strIngredient7)
        parcel.writeString(strIngredient8)
        parcel.writeString(strIngredient9)
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

