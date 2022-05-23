package br.com.testedevandroid.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class Formatter {
    companion object {
        fun formatDate(date: Long): String {
            return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                .format(Date(date))
        }

        fun formatCurrency(price: Double): String {
            return NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
                .format(price)
        }
    }
}