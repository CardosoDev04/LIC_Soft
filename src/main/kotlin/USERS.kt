import java.io.File
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
}

/**
 * A função main define o mapa inicialmente para poder ser utilizado.
 */
fun main() {
    USERS.defineMap()

}