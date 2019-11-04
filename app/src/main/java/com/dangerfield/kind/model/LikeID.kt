package com.dangerfield.kind.model

//import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
//import androidx.versionedparcelable.ParcelField

@Entity(tableName = "likeIDTable")
data class LikeID(@PrimaryKey var id: String)
//    { constructor():this("") }
//        Parcelable { constructor():this("") }
