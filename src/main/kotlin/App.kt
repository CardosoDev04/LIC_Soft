import isel.leic.utils.Time
import kotlin.math.log


data class TrustedUser(val id: Int, val pin: Int)

var invalidLog = false // Variável que é alterada para true se o login for inválido

val trusted = TrustedUser(0, 1234) // Hardcode do utilizador confiado

object App {

    fun mainLog(id: Int, pin: Int) {  // Executa a main routine de login, com todas as funções para o login.
        val trusted = login(id, pin)
        openDoor(trusted,id)
        if (invalidLog) TUI.loginRoutine()
    }

    fun login(id: Int, pin: Int): Boolean {
        return  pin == USERS.getUserPin(id)  // Verifica se o ID e PIN inseridos condizem com os do utilizador confiado
    }

    fun openDoor(isTrusted: Boolean,id: Int) {
        if (isTrusted) {    //Se o utilizador for o confiado, abre a porta caso contrario fecha e dá Error
            Doormechanism.open(15)
            LCD.write("Welcome,")
            LCD.cursor(1,0)
            LCD.write("${USERS.getUsername(id)}")
            Time.sleep(2000)
            LCD.clear()
            LCD.write("${USERS.getPhrase(id)}")
            invalidLog = false
        } else {
            Doormechanism.close(15)
            LCD.write("Invalid Login")
            invalidLog = true
            Time.sleep(1500)
            LCD.clear()
        }


    }
}

