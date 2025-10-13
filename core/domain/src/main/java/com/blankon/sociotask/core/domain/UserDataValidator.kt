package com.blankon.sociotask.core.domain

class UserDataValidator(
    private val options: Options = Options()
) {
    data class Options(
        val minPasswordLength: Int = 8,
        val requireUppercase: Boolean = true,
        val requireDigit: Boolean = true,
        val requireSpecialChar: Boolean = true,
        val usernameMin: Int = 3,
        val usernameMax: Int = 20,
        val reservedUsernames: Set<String> = setOf("admin", "root", "support")
    )

    fun validatePassword(password: String): Result<Unit, PasswordDomainError> {
        if (password.length < options.minPasswordLength) {
            return Result.Error(PasswordDomainError.TOO_SHORT)
        }

        if (options.requireDigit && password.none(Char::isDigit)) {
            return Result.Error(PasswordDomainError.NO_NUMBER)
        }
        if (options.requireUppercase && password.none(Char::isUpperCase)) {
            return Result.Error(PasswordDomainError.NO_UPPERCASE)
        }
        if (options.requireSpecialChar && password.all { it.isLetterOrDigit() }) {
            return Result.Error(PasswordDomainError.NO_SPECIAL_CHAR)
        }
        return Result.Success(Unit)
    }

    fun validateConfirmPassword(
        password: String,
        confirmPassword: String
    ): Result<Unit, ConfirmPasswordDomainError> {
        return if (password == confirmPassword) Result.Success(Unit)
        else Result.Error(ConfirmPasswordDomainError.MISMATCH)

    }

//    // ---------- FULL NAME (opsional) ----------
//    fun validateFullName(fullName: String?): Result<Unit, NameError> {
//        val name = fullName?.trim().orEmpty()
//        if (name.isEmpty()) return Result.Success(Unit) // boleh kosong
//        if (name.length < 2) return Result.Error(NameError.TOO_SHORT)
//        // huruf/space/tanda umum nama
//        val allowed = "^[\\p{L} .'-]+$".toRegex() // \p{L} untuk unicode letter
//        if (!allowed.matches(name)) return Result.Error(NameError.INVALID_CHAR)
//        return Result.Success(Unit)
//    }


    enum class Field { EMAIL, USERNAME, FULL_NAME, PASSWORD, CONFIRM_PASSWORD }

    enum class EmailError { EMPTY, INVALID_FORMAT, TOO_LONG }

    enum class UsernameError {
        INVALID_LENGTH, INVALID_CHAR, CANNOT_START_END_DOT, CONSECUTIVE_DOT, RESERVED
    }

    enum class NameError { TOO_SHORT, INVALID_CHAR }

    enum class PasswordDomainError : DomainError {
        TOO_SHORT,
        NO_NUMBER,
        NO_UPPERCASE,
        NO_SPECIAL_CHAR
    }

    enum class ConfirmPasswordDomainError : DomainError {
        MISMATCH
    }

}