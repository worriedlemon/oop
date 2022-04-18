package lab3

import lab3.contactService.*

fun main() {
    val data = DataBase()

    println("Adding different people with name or surname consisting of [Ivan] and/or [Petr]...")

    // At first, adding people

    // Adding person using parse function for string value
    data.addPerson("Ivan Ivanov".toPerson())

    // Adding person using constructor of full name
    data.addPerson(Person("Petr Ivanov"))

    // Adding person using name and surname individually
    data.addPerson(Person("Petr", "Petrov"))

    data.addPerson(Person("Ivan Petrov"))
    data.addPerson(Person("Petya Sidorov"))

    println("They are:")
    data.getAllPersons().forEach { println(it.toString()) }

    println("\nPetya Sidorov should be removed")
    data.removePerson(Person("Petya", "Sidorov"))

    try {
        data.addPerson(Person("Ivan Ivanov"))
    } catch (e: IllegalStateException) {
        println("\nAdding Ivan Ivanov again: ${e.message}\n")
    }

    // Adding Ivan Ivanov's contacts

    println("Adding Ivan Ivanov's contacts:")
    data.addContact(
        "Ivan Ivanov".toPerson(),
        Contact.Address("Saint-Petersburg, Grazhdanskiy avenue, 146")
    )

    data.addEmail(
        "Ivan Ivanov".toPerson(),
        "ivan_ivanov@mail.ru"
    )

    data.addPhone(
        Person("Ivan", "Ivanov"),
        "+79027860986", PhoneType.Mobile
    )

    data.addContact(
        Person("Ivan Ivanov"),
        Contact.Phone("+78125633441", PhoneType.Home)
    )

    data.addLink(
        Person("Ivan", "Ivanov"),
        "http://pi-ivanov.ru/"
    )

    // End of Ivan Ivanov's contacts

    data.getPersonContacts("Ivan Ivanov".toPerson()).forEach { println("$it") }

    print("\nHe has two numbers, which are ")
    data.getPersonPhones(Person("Ivan", "Ivanov")).forEach { print("$it; ") }

    // Adding Ivan Petrov's contacts

    println("\nAdding Ivan Petrov's contacts...")
    data.addAddress(
        Person("Ivan Petrov"),
        "Volgograd, Lenin st., 28A"
    )

    data.addEmail(
        Person("Ivan", "Petrov"),
        "petya228@gmail.com"
    )

    data.addContact(
        Person("Ivan Petrov"),
        Contact.Phone("+79531765089", PhoneType.Mobile)
    )

    // End of Ivan Petrov's contacts

    // Adding Petr Petrov's contacts

    println("Adding Petr Petrov's contacts...")

    data.addAddress(
        "Petr Petrov".toPerson(),
        "Zelenograd, Severo-Zapadnaya st., 14"
    )

    data.addContact(
        "Petr Petrov".toPerson(),
        Contact.Email("pipetka", "rambler.ru")
    )

    data.addLink(
        "Petr Petrov".toPerson(),
        "http://tender.ru/petr_x2/"
    )

    data.addContact(
        "Petr Petrov".toPerson(),
        Contact.Link("http://medtoolsforsail.org/")
    )

    data.addAddress(
        "Petr Petrov".toPerson(),
        "Moscow, Severo-Zapadnaya st., 14"
    )

    // End of Petr Petrov contacts

    println("\nPetr Petrov has two addresses:")
    data.getPersonAddresses(Person("Petr", "Petrov")).forEach {
        print("$it; ")
    }

    println("\nOne address is expired, so removing it...")
    data.removeContact(
        Person("Petr Petrov"),
        Contact.Address("Moscow, Severo-Zapadnaya st., 14")
    )

    // Adding Petr Ivanov's contacts

    println("\nAdding Petr Ivanov's contacts...")

    data.addEmail(
        "Petr Ivanov".toPerson(),
        "pIvo_nov@stud.etu.ru"
    )

    data.addContact(
        Person("Petr", "Ivanov"),
        Contact.Email("incognito", "yandex.ru")
    )

    data.addPhone(
        Person("Petr Ivanov"),
        "+77777777777",
        PhoneType.Office
    )

    // End of Petr Ivanov's contacts

    // Searching by patterns

    println("\nSearching people with name like \'van\'")
    data.findPeopleByFirstNamePattern("van").forEach { println(it.toString()) }

    println("\nSearching people with surname like \'ov\'")
    data.findPeopleByLastNamePattern("ov").forEach { println(it.toString()) }

    println("\nSearching people with name and surname like \'Pet\'")
    data.findPeopleByNamePatterns("Pet", "Pet").forEach { println(it.toString()) }

    // Summary

    println("\nSummary:")
    data.getAllContacts().forEach { person ->
        println("${person.key}:")
        person.value.forEach {
            print("$it; ")
        }
        println("\n")
    }

    // Workaround filtering

    println("Taking all emails:")
    data.getAllPersons().forEach { person ->
        data.getPersonEmails(person).forEach {
            println(it.toString())
        }
    }
}