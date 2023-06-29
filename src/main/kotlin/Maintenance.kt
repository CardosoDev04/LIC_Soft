import isel.leic.utils.Time
import java.io.File
import java.io.FileWriter
import kotlin.system.exitProcess

val maintenanceBox = """
    +------------------+
    |  MAINTENANCE MODE |
    +------------------+
""".trimIndent()


object Maintenance {

    fun getBiggestUIN(): Int? {
        var maxUIN: Int? = null

        for (entry in USERS.userMap.entries) {
            val uin = entry.key
            if (maxUIN == null || uin > maxUIN) {
                maxUIN = uin
            }
        }

        return maxUIN
    }



    /**
     * Entra em modo de manutenção.
     */
    fun inMaintenance(): Boolean{
        return when(HAL.isBit(M_MSK)){
            true -> true
            else -> false
        }
    }

    /**
     * Inicia a rotina de inserção de utilizador
     */

    fun insertUser(){

        var UIN: Int? = 0
        var PIN: Int = 0
        var name: String = ""

//        println("Please insert the UIN.")
//        val tryUIN = readln().toInt()
//        if( tryUIN <= 999){
//            UIN = tryUIN
//        }
//        else{
//            println("Please insert a 3 digit number, reloading...")
//            Time.sleep(2000)
//            insertUser()
//        }

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

        UIN = getBiggestUIN()?.plus(1)
        val entry = "$UIN;$PIN;$name;"
        val file = File("USERS.txt")
        FileWriter(file, true).use { writer -> writer.write("$entry\n")}

        println("User with name: $name and UIN $UIN was added")
        maintenanceRoutine()
    }

    /**
     * Inicia a rotina de remoção de utilizador
     */

    fun removeUser(){
        var UIN = 0
        println("Insert the UIN of the User you wish to remove.")
        val tryUIN = readln().toInt()
        if(tryUIN <= 999){
            UIN = tryUIN
        }
        else{
            println("Please insert a 3 digit number, reloading...")
            Time.sleep(2000)
            removeUser()
        }

        println("Do you really wish to remove? (Y / N)")
        val userInput = readln()
        when(userInput){
            "y" -> {
                USERS.userMap.remove(UIN)
                USERS.updateDB()
                USERS.defineMap()

                maintenanceRoutine()
            }
            "Y" -> {
                USERS.userMap.remove(UIN)
                USERS.updateDB()
                USERS.defineMap()

                maintenanceRoutine()
            }
            "n" -> maintenanceRoutine()
            "N" -> maintenanceRoutine()
            else -> {
                println("Invalid Input")
                removeUser()
            }



        }



    }

    /**
     * Inicia a rotina de inserção de mensagem
     */

    fun insertPhrase() {
        var thisUIN = 0
        println("Insert the UIN of the desired user.")
        val tryUIN = readln().toInt()
        if (tryUIN <= 999) {
            thisUIN = tryUIN
        } else {
            println("Please insert a 3-digit number, reloading...")
            Time.sleep(2000)
            insertPhrase()
        }

        println("Please insert the message you wish to add")
        val phrase = readln().toString()
        USERS.defineMap()
        val user = USERS.userMap[thisUIN]
        if (user != null) {
            val updatedUser = Triple(user.first, user.second, phrase)
            USERS.userMap[thisUIN] = updatedUser
            USERS.updateDB()
            USERS.defineMap()
            println("Message Added Successfully")
        } else {
            println("User with UIN $thisUIN does not exist.")
        }

        maintenanceRoutine()
    }

    /**
     * Desliga o programa
     */

    fun turnOff(){
        println("Do you confirm you wish to turn off the system? (Y / N)")
        val input = readln()

        when(input){
            "y" -> {
                exitProcess(0)
                USERS.updateDB()
                USERS.defineMap()
            }
            "Y" -> {
                exitProcess(0)
                USERS.updateDB()
                USERS.defineMap()
            }
            "n" -> maintenanceRoutine()
            "N" -> maintenanceRoutine()
            else -> {
                println("Invalid input")
                turnOff()
            }
        }
    }

    fun exitMaintenance(){
        HAL.init()

        if(!HAL.isBit(0x80)) {
            TUI.loginRoutine()
        }
        else maintenanceRoutine()
    }

    /**
     * Executa a rotina principal de manutenção
     */

    fun maintenanceRoutine(){
        KBD.isEnabled = false
        println(maintenanceBox)
        println("What would you like to do?")
        println(" 1 - Insert User")
        println(" 2 - Remove User")
        println(" 3 - Add Message to User")
        println(" 4 - Turn the system OFF")


        var optionStr = readln()
        if(optionStr != ""){
            var option = optionStr.toInt()
            when(option){
                1 -> insertUser()
                2 -> removeUser()
                3 -> insertPhrase()
                4 -> turnOff()
                else -> maintenanceRoutine()
            }
        }


    }

}