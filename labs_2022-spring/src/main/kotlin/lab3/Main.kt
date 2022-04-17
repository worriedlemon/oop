package lab3

import lab3.contactService.*

fun main() {
    val data = DataBase

    println("Adding different people with name or surname consisting of [Ivan] and/or [Petr]...")
    data.addPerson(Person("Ivan Ivanov"))
    data.addPerson(Person("Petr Ivanov"))
    data.addPerson(Person("Petr Petrov"))
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

    println("Adding Ivan Ivanov's contacts:")
    data.addContact(Person("Ivan Ivanov"), Contact.Address("Saint-Petersburg, Grazhdanskiy avenue, 146"))
    data.addEmail(Person("Ivan Ivanov"), "ivan_ivanov@mail.ru")
    data.addPhone(Person("Ivan", "Ivanov"), "+79027860986", PhoneType.Mobile)
    data.addContact(Person("Ivan Ivanov"), Contact.Phone("+78125633441", PhoneType.Home))
    data.addLink(Person("Ivan", "Ivanov"), "http://pi-ivanov.ru/")

    data.getPersonContacts(Person("Ivan Ivanov")).forEach { println("$it") }

    print("\nHe has two numbers, which are ")
    data.getPersonPhones(Person("Ivan", "Ivanov")).forEach { print("$it; ") }

    println("\nAdding Ivan Petrov's contacts...")
    data.addAddress(Person("Ivan Petrov"), "Volgograd, Lenin st., 28A")
    data.addEmail(Person("Ivan", "Petrov"), "petya228@gmail.com")
    data.addContact(Person("Ivan Petrov"), Contact.Phone("+79531765089", PhoneType.Mobile))

    println("Adding Petr Petrov's contacts...")

    data.addAddress(Person("Petr Petrov"), "Zelenograd, Severo-Zapadnaya st., 14")
    data.addContact(Person("Petr Petrov"), Contact.Email("pipetka", "rambler.ru"))
    data.addLink(Person("Petr Petrov"), "http://tender.ru/petr_x2/")
    data.addContact(Person("Petr Petrov"), Contact.Link("http://medtoolsforsail.org/"))
    data.addAddress(Person("Petr Petrov"), "Moscow, Severo-Zapadnaya st., 14")

    println("\nPetr Petrov has two addresses:")
    data.getPersonAddresses(Person("Petr", "Petrov")).forEach {
        print("$it; ")
    }

    println("\nOne address is expired, so removing it...")
    data.removeContact(Person("Petr Petrov"), Contact.Address("Moscow, Severo-Zapadnaya st., 14"))

    println("\nAdding Petr Ivanov's contacts...")

    data.addEmail(Person("Petr Ivanov"), "pIvo_nov@stud.etu.ru")
    data.addContact(Person("Petr Ivanov"), Contact.Email("incognito", "yandex.ru"))
    data.addPhone(Person("Petr Ivanov"), "+77777777777", PhoneType.Office)

    println("\nSearching people with name like \'van\'")
    data.findPersonsByFirstNamePart("van").forEach { println(it.toString()) }

    println("\nSearching people with surname like \'ov\'")
    data.findPersonsByLastNamePart("ov").forEach { println(it.toString()) }

    println("\nSearching people with name and surname like \'Pet\'")
    data.findPersonsByNameParts("Pet", "Pet").forEach { println(it.toString()) }

    println("\nSummary:")
    data.getAllContacts().forEach { person ->
        println("${person.key}:")
        person.value.forEach {
            print("$it; ")
        }
        println("\n")
    }

    println("Taking all emails:")
    data.getAllPersons().forEach { person ->
        data.getPersonEmails(person).forEach {
            println(it.toString())
        }
    }
}