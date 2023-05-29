import java.io.File
import java.util.HashMap


object USERS {

     val userMap: HashMap<Int, Triple<Int, String, String>> = HashMap()
    fun defineMap() {
        val file = File("USERS.txt")
        file.forEachLine { line ->
            val parts = line.split(";")
            val info = Triple(parts[1].toInt(), parts[2], parts[3])
            val id: String = when(parts[0].length){
                1 -> "00" + parts[0]
                2 -> "0" + parts[0]
                else -> parts[0]
            }
            userMap[id.toInt()] = info
        }
    }


    fun getUserPin(id: Int): Int? {
        return userMap[id]?.first
    }

    fun getUsername(id: Int): String? {
        return userMap[id]?.second
    }

    fun getPhrase(id: Int): String? {
        return userMap[id]?.third
    }
}

fun main() {
    USERS.defineMap()
    println(USERS.getUserPin(0))
    println(USERS.getUsername(0))
    println(USERS.getPhrase(0))

}