package lab3.contactService

data class Person(
    val firstName: String,
    val lastName: String
) {
    init {
        require(
            firstName.isNotBlank() && lastName.isNotBlank()
        )
    }

    constructor(
        information: String
    ) : this(
        information.split(' ')[0],
        information.split(' ')[1]
    )

    override fun toString(): String {
        return "$firstName $lastName"
    }
}

fun String.toPerson(): Person {
    return Person(this)
}

enum class PhoneType {
    Home, Office, Mobile
}

interface ContactService {
    fun addContact(person: Person, contact: Contact)
    fun removeContact(person: Person, contact: Contact)

    fun addPerson(person: Person)
    fun removePerson(person: Person)

    fun addPhone(person: Person, phone: String, phoneType: PhoneType)
    fun addEmail(person: Person, email: String)
    fun addLink(person: Person, link: String)
    fun addAddress(person: Person, address: String)

    fun getPersonContacts(person: Person): List<Contact>
    fun getPersonPhones(person: Person): List<Contact.Phone>
    fun getPersonEmails(person: Person): List<Contact.Email>
    fun getPersonLinks(person: Person): List<Contact.Link>
    fun getPersonAddresses(person: Person): List<Contact.Address>

    fun getAllPersons(): List<Person>
    fun getAllContacts(): Map<Person, List<Contact>>

    fun findPeopleByFirstNamePattern(name: String): List<Person>
    fun findPeopleByLastNamePattern(surname: String): List<Person>
    fun findPeopleByNamePatterns(name: String, surname: String): List<Person>
}

class DataBase : ContactService {
    private val people = mutableMapOf<Person, MutableList<Contact>>()

    private fun checkPerson(person: Person) {
        if (!people.contains(person)) error("There is no such [person]")
    }

    override fun addContact(person: Person, contact: Contact) {
        checkPerson(person)
        (people[person] as MutableList<Contact>).add(contact)
    }

    override fun removeContact(person: Person, contact: Contact) {
        checkPerson(person)
        if (!(people[person] as MutableList<Contact>).remove(contact)) error("There is no such [contact] attached to this [person]")
    }

    override fun addPerson(person: Person) {
        if (people.contains(person)) error("Cannot add a [person] with an existing name")
        people[person] = mutableListOf()
    }

    override fun removePerson(person: Person) {
        if (people.remove(person) == null) error("There is no such [person]")
    }

    override fun addPhone(person: Person, phone: String, phoneType: PhoneType) {
        checkPerson(person)
        (people[person] as MutableList<Contact>).add(Contact.Phone(phone, phoneType))
    }

    override fun addEmail(person: Person, email: String) {
        checkPerson(person)
        (people[person] as MutableList<Contact>).add(Contact.Email(email))
    }

    override fun addLink(person: Person, link: String) {
        checkPerson(person)
        (people[person] as MutableList<Contact>).add(Contact.Link(link))
    }

    override fun addAddress(person: Person, address: String) {
        checkPerson(person)
        (people[person] as MutableList<Contact>).add(Contact.Address(address))
    }

    override fun getPersonContacts(person: Person): List<Contact> {
        checkPerson(person)
        return (people[person] as MutableList<Contact>).toList()
    }

    override fun getPersonPhones(person: Person): List<Contact.Phone> {
        checkPerson(person)
        return (people[person] as MutableList<Contact>).filterIsInstance<Contact.Phone>()
    }

    override fun getPersonEmails(person: Person): List<Contact.Email> {
        checkPerson(person)
        return (people[person] as MutableList<Contact>).filterIsInstance<Contact.Email>()
    }

    override fun getPersonLinks(person: Person): List<Contact.Link> {
        checkPerson(person)
        return (people[person] as MutableList<Contact>).filterIsInstance<Contact.Link>()
    }

    override fun getPersonAddresses(person: Person): List<Contact.Address> {
        checkPerson(person)
        return (people[person] as MutableList<Contact>).filterIsInstance<Contact.Address>()
    }

    override fun getAllPersons(): List<Person> {
        return people.keys.toList()
    }

    override fun getAllContacts(): Map<Person, List<Contact>> {
        return people.toMap()
    }

    override fun findPeopleByFirstNamePattern(name: String): List<Person> {
        return people.filter {
            it.key
                .firstName
                .contains(name)
        }.keys.toList()
    }

    override fun findPeopleByLastNamePattern(surname: String): List<Person> {
        return people.filter {
            it.key
                .lastName
                .contains(surname)
        }.keys.toList()
    }

    override fun findPeopleByNamePatterns(name: String, surname: String): List<Person> {
        return people.filter {
            it.key
                .firstName
                .contains(name) &&
                    it.key
                        .lastName
                        .contains(surname)
        }.keys.toList()
    }
}