package app.k9mail.feature.account.common.domain.input

import app.k9mail.core.common.domain.usecase.validation.ValidationError
import app.k9mail.core.common.domain.usecase.validation.ValidationResult

class StringInputField(
    override val value: String = "",
    override val error: ValidationError? = null,
    override val isValid: Boolean = false,
) : InputField<String> {

    override fun updateValue(value: String): StringInputField {
        return StringInputField(
            value = value,
            error = null,
            isValid = false,
        )
    }

    override fun updateError(error: ValidationError?): StringInputField {
        return StringInputField(
            value = value,
            error = error,
            isValid = false,
        )
    }

    override fun updateValidity(isValid: Boolean): StringInputField {
        if (isValid == this.isValid) return this

        return StringInputField(
            value = value,
            error = null,
            isValid = isValid,
        )
    }

    override fun updateFromValidationResult(result: ValidationResult): StringInputField {
        return when (result) {
            is ValidationResult.Success -> StringInputField(
                value = value,
                error = null,
                isValid = true,
            )

            is ValidationResult.Failure -> StringInputField(
                value = value,
                error = result.error,
                isValid = false,
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StringInputField

        if (value != other.value) return false
        if (error != other.error) return false
        return isValid == other.isValid
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + (error?.hashCode() ?: 0)
        result = 31 * result + isValid.hashCode()
        return result
    }

    override fun toString(): String {
        return "StringInputField(value='$value', error=$error, isValid=$isValid)"
    }
}
