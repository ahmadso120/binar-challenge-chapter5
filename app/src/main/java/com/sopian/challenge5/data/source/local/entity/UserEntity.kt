package com.sopian.challenge5.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val email: String,
    val username: String,
    @ColumnInfo(name = "full_name")
    val fullName: String? = null,
    val dob: String? = null,
    val address: String? = null,
    val password: String,
    var isAuthorized: Boolean = false
) : Parcelable
