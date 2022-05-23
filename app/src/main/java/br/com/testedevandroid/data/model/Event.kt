package br.com.testedevandroid.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class Event(
    val id: Int,
    val title: String,
    val price: Double,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
    val image: String,
    val description: String,
    val date: Long,
    val people: List<Person> = emptyList()
): Parcelable
