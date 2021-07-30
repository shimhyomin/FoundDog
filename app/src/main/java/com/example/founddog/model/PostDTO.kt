package com.example.founddog.model

import android.os.Parcel
import android.os.Parcelable

data class PostDTO (var imgUrl: String? = null,
                    var title : String? = null,
                    var content : String? = null,
                    var uid : String? = null,
                    var timestamp : Long? = null,
                    var label : Int = 0
        ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imgUrl)
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(uid)
        parcel.writeValue(timestamp)
        parcel.writeInt(label)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostDTO> {
        override fun createFromParcel(parcel: Parcel): PostDTO {
            return PostDTO(parcel)
        }

        override fun newArray(size: Int): Array<PostDTO?> {
            return arrayOfNulls(size)
        }
    }
}