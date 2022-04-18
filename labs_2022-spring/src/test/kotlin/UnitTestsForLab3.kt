import org.junit.jupiter.api.Test
import kotlin.test.*
import lab3.contactService.*
import org.junit.jupiter.api.assertDoesNotThrow

class Lab3UnitTests {
    @Test
    fun `Add person test does not fail`() {
        val data = DataBase()

        assertDoesNotThrow { data.addPerson("Emil Shteinberg".toPerson()) }
    }

    @Test
    fun `Add person in one word fail`() {
        val data = DataBase()

        assertFails { data.addPerson("EmilShteinberg".toPerson()) }
    }

    @Test
    fun `Get persons list test`() {
        val data = DataBase()

        data.addPerson(Person("Ivan", "Ivanov"))
        data.addPerson(Person("Ivan", "Petrov"))
        data.addPerson(Person("Ivan", "Sidorov"))

        assertEquals(
            listOf(
                Person("Ivan Ivanov"),
                Person("Ivan Petrov"),
                Person("Ivan Sidorov")
            ),
            data.getAllPersons()
        )
    }

    @Test
    fun `Add person test fail on same person`() {
        val data = DataBase()

        data.addPerson("Emil Shteinberg".toPerson())

        assertFails { data.addPerson("Emil Shteinberg".toPerson()) }
    }

    @Test
    fun `Remove person test pass`() {
        val data = DataBase()

        data.addPerson("Emil Shteinberg".toPerson())

        assertDoesNotThrow { data.removePerson("Emil Shteinberg".toPerson()) }
    }

    @Test
    fun `Remove person test fail`() {
        val data = DataBase()

        assertFails { data.removePerson("Emil Shteinberg".toPerson()) }
    }

    @Test
    fun `Add email without '@' fail`() {
        val data = DataBase()

        data.addPerson("Petya Sidorov".toPerson())

        assertFails { data.addEmail("Petya Sidorov".toPerson(), "mail_without_at") }
    }

    @Test
    fun `Add and get person contacts explicit test`() {
        val data = DataBase()
        val targetPerson = "Emil Shteinberg".toPerson()

        data.addPerson(targetPerson)

        data.addContact(
            targetPerson,
            Contact.Address("Saint-Petersburg, Professora Popova st., 5B")
        )

        data.addContact(
            targetPerson,
            Contact.Link("https://github.com/worriedlemon/oop/")
        )

        data.addContact(
            targetPerson,
            Contact.Phone("No", PhoneType.Office)
        )

        data.addContact(
            targetPerson,
            Contact.Phone("Yes", PhoneType.Mobile)
        )

        data.addContact(
            targetPerson,
            Contact.Email("emilshteinberg@yandex.ru")
        )

        data.addContact(
            targetPerson,
            Contact.Email("emii.shteinberg60", "gmail.com")
        )

        assertEquals(
            listOf(
                Contact.Address("Saint-Petersburg, Professora Popova st., 5B"),
                Contact.Link("https://github.com/worriedlemon/oop/"),
                Contact.Phone("No", PhoneType.Office),
                Contact.Phone("Yes", PhoneType.Mobile),
                Contact.Email("emilshteinberg@yandex.ru"),
                Contact.Email("emii.shteinberg60", "gmail.com")
            ),
            data.getPersonContacts(targetPerson)
        )
    }

    @Test
    fun `Add and get person contacts implicit test`() {
        val data = DataBase()
        val targetPerson = "Emil Shteinberg".toPerson()

        data.addPerson(targetPerson)

        data.addAddress(
            targetPerson,
            "Saint-Petersburg, Professora Popova st., 5B"
        )

        data.addLink(
            targetPerson,
            "https://github.com/worriedlemon/oop/"
        )

        data.addPhone(
            targetPerson,
            "No",
            PhoneType.Office
        )

        data.addPhone(
            targetPerson,
            "Yes",
            PhoneType.Mobile
        )

        data.addEmail(
            targetPerson,
            "emilshteinberg@yandex.ru"
        )

        data.addEmail(
            targetPerson,
            "emii.shteinberg60@gmail.com"
        )

        assertEquals(
            listOf(
                Contact.Address("Saint-Petersburg, Professora Popova st., 5B"),
                Contact.Link("https://github.com/worriedlemon/oop/"),
                Contact.Phone("No", PhoneType.Office),
                Contact.Phone("Yes", PhoneType.Mobile),
                Contact.Email("emilshteinberg", "yandex.ru"),
                Contact.Email("emii.shteinberg60@gmail.com")
            ),
            data.getPersonContacts(targetPerson)
        )
    }

    @Test
    fun `Get person phones test`() {
        val data = DataBase()
        val targetPerson = "Ivan Ivanov".toPerson()

        data.addPerson(targetPerson)

        data.addPhone(
            targetPerson,
            "+71234567890",
            PhoneType.Mobile
        )

        data.addPhone(
            targetPerson,
            "12345",
            PhoneType.Home
        )

        data.addPhone(
            targetPerson,
            "88005553535",
            PhoneType.Office
        )

        assertEquals(
            listOf(
                Contact.Phone("+71234567890", PhoneType.Mobile),
                Contact.Phone("12345", PhoneType.Home),
                Contact.Phone("88005553535", PhoneType.Office)
            ),
            data.getPersonPhones(targetPerson)
        )
    }

    @Test
    fun `Get person emails test`() {
        val data = DataBase()
        val targetPerson = "Ivan Petrov".toPerson()

        data.addPerson(targetPerson)

        data.addEmail(
            targetPerson,
            "email1@mail.ru"
        )

        data.addEmail(
            targetPerson,
            "email2@mail.ru"
        )

        data.addEmail(
            targetPerson,
            "email3@mail.ru"
        )

        assertEquals(
            listOf(
                Contact.Email("email1@mail.ru"),
                Contact.Email("email2@mail.ru"),
                Contact.Email("email3@mail.ru")
            ),
            data.getPersonEmails(targetPerson)
        )
    }

    @Test
    fun `Get person addresses test`() {
        val data = DataBase()
        val targetPerson = "Petr Ivanov".toPerson()

        data.addPerson(targetPerson)

        data.addAddress(
            targetPerson,
            "Saint-Petersburg, Narodnogo Opolcheniya avenue, 111"
        )

        data.addAddress(
            targetPerson,
            "Saint-Petersburg, Stachek avenue, 112"
        )

        data.addAddress(
            targetPerson,
            "Saint-Petersburg, Engel'sa avenue, 113"
        )

        assertEquals(
            listOf(
                Contact.Address("Saint-Petersburg, Narodnogo Opolcheniya avenue, 111"),
                Contact.Address("Saint-Petersburg, Stachek avenue, 112"),
                Contact.Address("Saint-Petersburg, Engel'sa avenue, 113")
            ),
            data.getPersonAddresses(targetPerson)
        )
    }

    @Test
    fun `Get person links test`() {
        val data = DataBase()
        val targetPerson = "Petr Petrov".toPerson()

        data.addPerson(targetPerson)

        data.addLink(
            targetPerson,
            "https://google.com/"
        )

        data.addLink(
            targetPerson,
            "https://yandex.ru/"
        )

        data.addLink(
            targetPerson,
            "https://bing.com/"
        )

        assertEquals(
            listOf(
                Contact.Link("https://google.com/"),
                Contact.Link("https://yandex.ru/"),
                Contact.Link("https://bing.com/")
            ),
            data.getPersonLinks(targetPerson)
        )
    }

    @Test
    fun `Get all contacts test`() {
        val data = DataBase()

        data.addPerson(Person("Ivan", "Ivanov"))
        data.addPerson(Person("Ivan", "Petrov"))
        data.addPerson(Person("Petr", "Petrov"))
        data.addPerson(Person("Petr", "Ivanov"))

        data.addPhone("Ivan Ivanov".toPerson(), "89111357902", PhoneType.Office)
        data.addEmail("Ivan Petrov".toPerson(), "ivanpetrov@rambler.ru")
        data.addAddress("Petr Petrov".toPerson(), "Saint-Petersburg, Obukhovskoy oborony avenue, 246")
        data.addLink("Petr Ivanov".toPerson(), "https://vk.com/durov")

        assertEquals(
            mapOf(
                Pair("Ivan Ivanov".toPerson(), listOf(Contact.Phone("89111357902", PhoneType.Office))),
                Pair("Ivan Petrov".toPerson(), listOf(Contact.Email("ivanpetrov", "rambler.ru"))),
                Pair("Petr Petrov".toPerson(), listOf(Contact.Address("Saint-Petersburg, Obukhovskoy oborony avenue, 246"))),
                Pair("Petr Ivanov".toPerson(), listOf(Contact.Link("https://vk.com/durov")))
            ),
            data.getAllContacts()
        )
    }

    @Test
    fun `Find people by first name test`() {
        val data = DataBase()

        data.addPerson(Person("Ivan", "Ivanov"))
        data.addPerson(Person("Ivan", "Petrov"))
        data.addPerson(Person("Petr", "Petrov"))
        data.addPerson(Person("Petr", "Ivanov"))

        assertEquals(
            listOf(Person("Ivan", "Ivanov"), Person("Ivan", "Petrov")),
            data.findPeopleByFirstNamePattern("va")
        )
    }

    @Test
    fun `Find people by last name test`() {
        val data = DataBase()

        data.addPerson(Person("Ivan", "Ivanov"))
        data.addPerson(Person("Ivan", "Petrov"))
        data.addPerson(Person("Petr", "Petrov"))
        data.addPerson(Person("Petr", "Ivanov"))

        assertEquals(
            listOf(Person("Ivan", "Ivanov"), Person("Petr", "Ivanov")),
            data.findPeopleByLastNamePattern("vano")
        )
    }

    @Test
    fun `Find people by name test`() {
        val data = DataBase()

        data.addPerson(Person("Ivan", "Ivanov"))
        data.addPerson(Person("Ivan", "Petrov"))
        data.addPerson(Person("Petr", "Petrov"))
        data.addPerson(Person("Petr", "Ivanov"))

        assertEquals(
            listOf(Person("Petr", "Petrov"), Person("Petr", "Ivanov")),
            data.findPeopleByNamePatterns("Pet", "ov")
        )
    }
}