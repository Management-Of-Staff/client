package myapplication.second.workinghourmanagement.dto

import android.os.Parcel
import android.os.Parcelable

class User(
    val name: String,
    val phone: String,
    val email: String?,
    val birthDate: ArrayList<Int>?,
    val role: String,
    val uuid: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readSerializable() as ArrayList<Int>?,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(phone)
        parcel.writeString(email)
        parcel.writeSerializable(birthDate)
        parcel.writeString(role)
        parcel.writeString(uuid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}