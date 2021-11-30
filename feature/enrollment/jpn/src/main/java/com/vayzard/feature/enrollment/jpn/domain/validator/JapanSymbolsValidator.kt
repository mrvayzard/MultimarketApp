package com.vayzard.feature.enrollment.jpn.domain.validator

object JapanSymbolsValidator {
  // Regex for matching Hiragana or Katakana
  private const val JAPAN_SYMBOLS_PATTERN = "([ぁ-んァ-ン])"

  fun isValid(value: String): Boolean {
    return Regex(JAPAN_SYMBOLS_PATTERN).matches(value)
  }
}