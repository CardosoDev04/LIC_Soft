import isel.leic.utils.Time
import java.io.File
import java.io.PrintWriter
import java.util.HashMap


object USERS {

     val userMap: HashMap<Int, Triple<Int, String, String>> = HashMap()

    /**
       * Define o Hashmap a ser usado, para que cada linha do ficheiro USERS.txt seja separada em partes,
       * sendo a parte[0] o UIN, parte[1] o PIN, parte[2] o nome e parte[3] a frase caracteristica.
     * As partes 1-3 são agrupadas num Triple para efeitos de eficiência.
     **/
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

    /**
     * Função que limpa as entradas do HashMap
     */

    fun clearMap(){
        userMap.clear()
    }

    /**
     * Função de search que procura pelo valor do pin para um dado UIN.
     */
    fun getUserPin(id: Int): Int? {
        return userMap[id]?.first
    }

    /**
     * Função de search que procura pelo valor de username para um dado UIN.
     */
    fun getUsername(id: Int): String? {
        return userMap[id]?.second
    }

    /**
     * Função de search que procura pelo valor de frase para um dado UIN.
     */
    fun getPhrase(id: Int): String? {
        return userMap[id]?.third
    }

    /**
     * Função que altera o PIN atual com o valor de newPIN
     */
    fun replacePIN(id: Int, newPIN: Int){
        val currentInfo = userMap[id]
        if(currentInfo != null) {
            val updatedInfo = Triple(newPIN, currentInfo.second, currentInfo.third)
            userMap[id] = updatedInfo
        }
        updateDB()
        LCD.clear()
        LCD.write("PIN Updated")
        Time.sleep(2000)
        Doormechanism.close(15)
        LCD.clear()
        TUI.loginRoutine()
    }

    /**
     * Função que remove a mensagem/frase de um dado UIN
     */
    fun removePhrase(id: Int){
        val info = userMap[id]
        if(info != null) {
            val updatedInfo = Triple(info.first,info.second,"")
            userMap[id] = updatedInfo
        }
        updateDB()
        LCD.clear()
        LCD.write("Message removed")
        Time.sleep(2000)
        Doormechanism.close(15)
        LCD.clear()
        TUI.loginRoutine()
    }
}

/**
 * Atualiza os valores no ficheiro USERS.txt (Database) com as novas entradas do userMap
 */
fun updateDB() {
    val file = File("USERS.txt")
    val writer = PrintWriter(file)

    USERS.userMap.forEach { entry: Map.Entry<Int, Triple<Int, String, String>> ->
        val (id, triple) = entry
        val formattedEntry = "$id;${triple.first};${triple.second};${triple.third}"
        writer.println(formattedEntry)
    }

    writer.close()
    println("USERS.txt file updated.")
}




/**
 * A função main define o mapa inicialmente para poder ser utilizado.
 */
fun main() {
    USERS.clearMap()
    USERS.defineMap()
    println(USERS.userMap)

}