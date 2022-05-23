package br.com.testedevandroid.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Person(val name: String, val email: String): Parcelable
