package util

import java.text.DecimalFormat

object FormatNumber {

  def format(number: Double): String = {
    val formatter = new DecimalFormat("#,##0.00")
    return formatter.format(number).trim
  }

}