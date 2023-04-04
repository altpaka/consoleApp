package com.github.altpaka.consoleApp.sql

import com.github.altpaka.consoleApp.commands.AbstractPersonCollection
import com.github.altpaka.consoleApp.commands.AuthentificationPersonCollection
import com.github.altpaka.consoleApp.exception.NotAuthorizedException
import com.github.altpaka.consoleApp.model.Color
import com.github.altpaka.consoleApp.model.Coordinates
import com.github.altpaka.consoleApp.model.Location
import com.github.altpaka.consoleApp.model.Person
import com.github.altpaka.consoleApp.users.User
import java.io.FileNotFoundException
import java.io.FileReader
import java.security.MessageDigest
import java.sql.*
import java.sql.Date
import java.time.LocalDate
import java.util.*

class DatabaseManager: AuthentificationPersonCollection {
    private val conn: Connection

    init {
//        try {
            val auth = Scanner(FileReader("C:\\Users\\Polina\\IdeaProjects\\lab5\\server\\src\\main\\resources\\db.txt"))
            val username = auth.nextLine().trim { it <= ' ' }
            val password = auth.nextLine().trim { it <= ' ' }
            this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", username, password)
            val ps: PreparedStatement = this.conn.prepareStatement(
                "drop type if exists " +
                        "color " +
                        "cascade;" +
                "CREATE TABLE IF NOT EXISTS users (" +
                        "login VARCHAR(20) PRIMARY KEY," +
                        "password VARCHAR(100) NOT NULL UNIQUE," +
                        "salt VARCHAR(10) NOT NULL" +
                        ");" +
                        "CREATE TYPE color AS ENUM('RED', 'BLACK', 'BLUE', 'YELLOW', 'WHITE');" +
                        "CREATE TABLE IF NOT EXISTS person (" +
                        "person_id SERIAL PRIMARY KEY CHECK (person_id > 0)," +
                        "name VARCHAR(20) NOT NULL," +
                        "coordinates_x BIGINT NOT NULL," +
                        "coordinates_y DOUBLE PRECISION NOT NULL," +
                        "creation_date TIMESTAMP NOT NULL," +
                        "height BIGINT NOT NULL CHECK (height > 0)," +
                        "weight DOUBLE PRECISION NOT NULL CHECK (weight > 0)," +
                        "passport_id VARCHAR(41) UNIQUE," +
                        "color color NOT NULL," +
                        "location_x DOUBLE PRECISION NOT NULL," +
                        "location_y DOUBLE PRECISION NOT NULL," +
                        "location_name VARCHAR(41) NOT NULL," +
                        "login VARCHAR(20) references users(login) ON DELETE SET NULL" +
                        ");"
            )
            ps.executeUpdate()
//        } catch (e: SQLException) {
//            println(e.message)
//            println("Ошибка инициализации БД")
//            System.exit(0)
//        } catch (e: FileNotFoundException) {
//            println("Не найден файл db.txt для аутенфикации")
//            System.exit(1)
//        }
    }


    fun all(): List<Person> {
            val persons: MutableList<Person> = ArrayList<Person>()
                val ps = conn.prepareStatement("SELECT * FROM person")
                val rs = ps.executeQuery()
                while (rs.next()) {
                    val person = Person(
                        rs.getLong("person_id"),
                        rs.getString("name"),
                        Coordinates(rs.getLong("coordinates_x"), rs.getDouble("coordinates_y")),
                        rs.getDate("creationDate").toLocalDate(),
                        rs.getLong("height"),
                        rs.getDouble("weight"),
                        if (rs.getString("passportId") == null) null else rs.getString("passportId"),
                        if (rs.getString("hairColor") == null) null
                        else Color.stringToType[rs.getString("hairColor")],
                        if (rs.getFloat("location_x") == null &&
                            rs.getDouble("location_y") == null &&
                            rs.getString("location_name") == null
                        ) {
                            null
                        } else if (rs.getString("location_name") != null) {
                            Location(
                                rs.getFloat("location_x"),
                                rs.getDouble("location_y"),
                                rs.getString("location_name")
                            )
                        } else {
                            Location(
                                rs.getFloat("location_x"),
                                rs.getDouble("location_y"),
                                null
                            )
                        },
                        rs.getString("user")
                    )
                    persons.add(person)
            }

            return persons
        }

    override fun info(user: User): AbstractPersonCollection.Information {
        val ps = conn.prepareStatement("SELECT count(*) FROM person WHERE login = ?")
        ps.setString(1, user.login)
        val psDate = conn.prepareStatement("SELECT creation_date FROM person WHERE login = ? LIMIT 1")
        psDate.setString(1, user.login)
        val rs = psDate.executeQuery()
        var date: LocalDate = LocalDate.now()
        if (rs.next()){
            date = rs.getDate("creation_date").toLocalDate()
        }
        return AbstractPersonCollection.Information(ps.executeUpdate(), date)

    }

    override fun add(person: Person, user: User): Long {
            val ps = conn.prepareStatement(
                "INSERT INTO person(name, coordinates_x, coordinates_y, " +
                        "creation_date, height, weight, passport_id, color, " +
                        "location_x, location_y, location_name, login)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                        "RETURNING person_id"
            )
            ps.setString(1, person.name)
            ps.setLong(2, person.coordinates.x)
            ps.setDouble(3, person.coordinates.y)
            ps.setDate(4, Date.valueOf(person.creationDate))
            ps.setLong(5, person.height)
            ps.setDouble(6, person.weight)
            if (person.passportID != null) ps.setString(7, person.passportID) else ps.setNull(7, Types.NULL)
            ps.setString(8, Color.typeToString[person.hairColor])
            if (person.location == null) {
                ps.setNull(9, Types.NULL)
                ps.setNull(10, Types.NULL)
                ps.setNull(11, Types.NULL)
            } else {
                ps.setFloat(9, person.location!!.x!!)
                ps.setDouble(10, person.location!!.y!!) 
                if (person.location!!.name != null) { 
                    ps.setString(11, person.location!!.name)
                } else {
                    ps.setNull(11, Types.NULL)
                }
            }
            ps.setString(12, user.login)
            val rs = ps.executeQuery()
            rs.next()
        return rs.getLong("person_id")
    }

    override fun update(id: Long, person: Person, user: User): AbstractPersonCollection.UpdateResult {
            val ps = conn.prepareStatement(
                "UPDATE movie SET (name, coordinates_x, coordinates_y, " +
                        "creation_date, height, weight, passport_id, color, " +
                        "location_x, location_y, location_name) " +
                        "= (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) WHERE person_id = ? AND login = ?"
            )
            ps.setString(1, person.name)
            ps.setLong(2, person.coordinates.x)
            ps.setDouble(3, person.coordinates.y)
            ps.setDate(4, Date.valueOf(person.creationDate))
            ps.setLong(5, person.height)
            ps.setDouble(6, person.weight)
            if (person.passportID != null) ps.setString(7, person.passportID) else ps.setNull(7, Types.NULL)
            ps.setString(8, Color.typeToString[person.hairColor])
            if (person.location == null) {
                ps.setNull(9, Types.NULL)
                ps.setNull(10, Types.NULL)
                ps.setNull(11, Types.NULL)
            } else {
                ps.setFloat(9, person.location!!.x!!)
                ps.setDouble(10, person.location!!.y!!)
                if (person.location!!.name != null) {
                    ps.setString(11, person.location!!.name)
                } else {
                    ps.setNull(11, Types.NULL)
                }
            }
            ps.setLong(12, id)
            ps.setString(13, user.login)
            return if (ps.executeUpdate() > 0) {
                AbstractPersonCollection.UpdateResult.UPDATED
            } else {
                AbstractPersonCollection.UpdateResult.NOT_FOUND
            }
    }

    override fun removeById(id: Long, user: User): AbstractPersonCollection.RemoveByIdResult {
        val ps = conn.prepareStatement("DELETE FROM person WHERE person_id = ? AND login = ?")
        ps.setLong(1, id)
        ps.setString(2, user.login)
        return if (ps.executeUpdate() > 0) {
            AbstractPersonCollection.RemoveByIdResult.DELETED
        } else {
            AbstractPersonCollection.RemoveByIdResult.NOT_FOUND
        }
    }

    override fun clear(user: User): AbstractPersonCollection.ClearResult {
        val ps = conn.prepareStatement("DELETE FROM person WHERE login = ?")
        ps.setString(1, user.login)
        return if (ps.executeUpdate() > 0) {
            AbstractPersonCollection.ClearResult.DELETED
        } else {
            AbstractPersonCollection.ClearResult.NOT_FOUND
        }
    }

    override fun removeFirst(user: User): AbstractPersonCollection.RemoveFirstResult {
        val ps = conn.prepareStatement("DELETE FROM person WHERE person_id = MIN(person_id) AND WHERE login = ?")
        ps.setString(1, user.login)
        return if (ps.executeUpdate() > 0) {
            AbstractPersonCollection.RemoveFirstResult.DELETED
        } else {
            AbstractPersonCollection.RemoveFirstResult.NOT_FOUND
        }
    }

    override fun removeGreater(id: Long, user: User): AbstractPersonCollection.RemoveGreaterResult {
        val ps = conn.prepareStatement("DELETE FROM person WHERE person_id > ?")
        ps.setLong(1, id)
        return if (ps.executeUpdate() > 0) {
            AbstractPersonCollection.RemoveGreaterResult.DELETED
        } else {
            AbstractPersonCollection.RemoveGreaterResult.GREAT_NOT_FOUND
        }
    }

    override fun maxByCoordinates(user: User): Long {
        val ps = conn.prepareStatement("SELECT MAX(coordinates_x) FROM person WHERE login = ?")
        ps.setString(1, user.login)
        val rs = ps.executeQuery()
        return if (rs.next()) {
            rs.getLong("coordinates_x")
        } else {
            (0).toLong()
        }
    }

    override fun iterator(): Iterator<Person> {
        return all().iterator()
    }

//
//    override fun filterContainsName(contain_name: String, user: User){
//        val ps = conn.prepareStatement("SELECT name FROM person WHERE login = ? AND ")
//        ps.setString(1, contain_name)
//        ps.setString(2, user.login)
//        val rs = ps.executeQuery()
//        val container = listOf<String>()
//        while (rs.next()) {
//
//        }
//        return
//    }
//
//    override fun sortDescending(user: User){
//        val ps = conn.prepareStatement("SELECT person FROM person WHERE login = user.login ORDER BY name ")
//        val rs = ps.executeQuery()
//        ps.setString(1, user.login)
//        val container = listOf<String>()
//        return if (rs.next()) {
//            rs.next()
//        } else {
//
//        }
//    }

//    fun existsUser(login: String?, password: String?): Boolean {
//        return try {
//            val ps = conn.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?")
//            ps.setString(1, login)
//            ps.setString(2, password)
//            val rs = ps.executeQuery()
//            rs.next()
//        } catch (e: SQLException) {
//            false
//        }
//    }

//    fun register(login: String?, password: String?): Boolean {
//        return try {
//            var ps = conn.prepareStatement("SELECT * FROM users WHERE login = ?")
//            ps.setString(1, login)
//            val rs = ps.executeQuery()
//            if (rs.next()) {
//                return false
//            }
//            ps = conn.prepareStatement("INSERT INTO users (login, password, salt) VALUES (?, ?, ?)")
//            ps.setString(1, login)
//            ps.setString(2, password)
//            ps.setString(3, "")
//            ps.executeUpdate() > 0
//        } catch (e: SQLException) {
//            false
//        }
//    }

//    private fun checkRights(person_id: Long, login: String): Boolean {
//        return try {
//            val ps = conn.prepareStatement(
//                "SELECT * FROM person WHERE person_id = ? AND login = ?"
//            )
//            ps.setLong(1, person_id)
//            ps.setString(2, login)
//            val rs = ps.executeQuery()
//            rs.next()
//        } catch (e: SQLException) {
//            false
//        }
//    }

    @Synchronized
    override fun registration(login: String, password: String): User {
        val ps = conn.prepareStatement("INSERT INTO users(?, ?, ?)")
        val newSalt = UUID.randomUUID().toString().substring(0, 5)
        ps.setString(1, login)
        ps.setString(2, hash(password, newSalt))
        ps.setString(3, newSalt)
        ps.executeUpdate()
        return User(login, password)
    }

        @Synchronized
    override fun authorization(login: String, password: String): User {
        val ps = conn.prepareStatement("SELECT FROM users WHERE login = ?")
        ps.setString(1, login)
        var rs = ps.executeQuery()
        var newSalt = ""
        if (rs.next()) {
            newSalt = rs.getString("salt")
        } else {
            throw NotAuthorizedException("Неверный логин, введите команду заново")
        }
        val newps = conn.prepareStatement("SELECT FROM users WHERE login = ? AND password = ?")
        newps.setString(1, login)
        newps.setString(2, hash(password, newSalt))
        rs = newps.executeQuery()
        if (rs.next()) {
            return User(rs.getString("login"), rs.getString("password"))
        } else {
            throw NotAuthorizedException("Неверный пароль, введите команду заново")
        }
    }

    fun hash(password: String, salt: String): String {
        val hash = StringBuilder()
        val pepper = "#@*"
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest((pepper + password + salt).toByteArray())
        for (i in digest) {
            val str = "0" + Integer.toHexString(i.toInt())
            hash.append(str.substring(str.length - 2))
            println(hash.toString())
        }
        return hash.toString()
    }

    companion object {
        var instance: DatabaseManager? = null
        get() {
            if (field == null) {
                field = DatabaseManager()
            }
            return field
        }
    }
}