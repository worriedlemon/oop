package lab3.contactService

sealed class Contact(
    open val information: String
) {
    data class Phone(
        override val information: String,
        private val Type: PhoneType
    ) : Contact(information) {
        init {
            require(information.isNotBlank())
        }

        override fun toString(): String = information
    }

    data class Email(
        private val login: String,
        private val domain: String
    ) : Contact("$login@$domain") {
        init {
            require(login.isNotBlank() && domain.isNotBlank())
        }

        constructor(
            value: String
        ) : this(value.split('@')[0], value.split('@')[1]) {
            require(value.contains('@'))
        }

        override fun toString(): String {
            return "$login@$domain"
        }
    }

    data class Link(
        override val information: String
    ) : Contact(information) {
        init {
            require(information.isNotBlank())
        }

        override fun toString(): String = information
    }

    data class Address(
        override val information: String
    ) : Contact(information) {
        init {
            require(information.isNotBlank())
        }

        override fun toString(): String = information
    }
}