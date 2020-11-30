package com.alexd23.example.myapplication

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable


data class Audio (
    val id : Int,
    val name : String,
    val author: String,
    val musicRes : Int,
    val icon : Int
)  : Parcelable, Serializable{

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(author)
        parcel.writeInt(musicRes)
        parcel.writeInt(icon)
    }

    private constructor(inParcel: Parcel) :
            this(inParcel.readInt(),inParcel.readString()?:"",inParcel.readString()?:"",inParcel.readInt(),inParcel.readInt())

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Audio> {
        override fun createFromParcel(parcel: Parcel): Audio {
            return Audio(parcel)
        }

        override fun newArray(size: Int): Array<Audio?> {
            return arrayOfNulls(size)
        }
    }


}