package ui.model

enum class ProviderType(val value: String) {
    Google("GOOGLE"),
    Apple("APPLE"),
    Unknown("UNKNOWN")
    ;

    companion object {
        fun from(type: String) = entries.find { it.value == type } ?: Unknown
    }
}
