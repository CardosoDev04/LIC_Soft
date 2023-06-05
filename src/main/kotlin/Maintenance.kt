import isel.leic.utils.Time
import java.io.File
import java.io.FileWriter

val maintenanceBox = """
    +------------------+
    |  MAINTENANCE MODE |
    +------------------+
""".trimIndent()


object Maintenance {



    /**
     * Entra em modo de manutenção.
     */
    fun inMaintenance(): Boolean{
        return when(HAL.isBit(M_MSK)){
            true -> true
            else -> false
        }
    }

    fun insertUser(){

        var UIN: Int = 0
        var PIN: Int = 0
        var name: String = ""

        println("Please insert the UIN.")
        val tryUIN = readln().toInt()
        if( tryUIN <= 999){
            UIN = tryUIN
        }
        else{
            println("Please insert a 3 digit number, reloading...")
            Time.sleep(2000)
            insertUser()
        }

        println("Please insert the PIN code")
        val tryPIN = readln().toInt()
        if( tryPIN <= 9999){
            PIN = tryPIN
        }
        else{
            println("Please insert a 4 digit number, reloading...")
            Time.sleep(2000)
            insertUser()
        }

        println("Please insert the user's name")
        val tryName = readln()
        val pattern = Regex("^[A-Za-z]+(?: [A-Za-z]+)?\$")
        if(tryName.length <= 16 && tryName.matches(pattern)){
            name = tryName
        }
        else{
            println("Please insert a username with only A-Z And Spaces")
            Time.sleep(2000)
            insertUser()
        }


        val entry = "$UIN;$PIN;$name;"
        val file = File("USERS.txt")
        FileWriter(file, true).use { writer -> writer.write("$entry\n")}

        println("User with name: $name was added")
        maintenanceRoutine()
    }


    fun maintenanceRoutine(){
        println(maintenanceBox)
        println("What would you like to do?")
        println(" 1 - Insert User")
        val option = readln().toInt()

        when(option){
            1 -> insertUser()
            else -> maintenanceRoutine()
        }
    }

}