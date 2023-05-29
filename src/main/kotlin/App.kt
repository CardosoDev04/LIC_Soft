import isel.leic.utils.Time
import kotlin.math.log

data class TrustedUser(val id: Int, val pin: Int)

var invalidLog = false

val trusted = TrustedUser(0, 1234) // Hardcode do utilizador confiado

object App {

    fun mainLog(id: Int, pin: Int) {
        val trusted = login(id, pin)
        openDoor(trusted)
        if (invalidLog) TUI.loginRoutine()
    }

    fun login(id: Int, pin: Int): Boolean {
        return id == trusted.id && pin == trusted.pin  // Verifica se o ID e PIN inseridos condizem com os do utilizador confiado
    }

    fun openDoor(isTrusted: Boolean) {
        if (isTrusted) {    //Se o utilizador for o confiado, abre a porta caso contrario fecha e dá Error
            Doormechanism.open(15)
            LCD.write("Welcome")
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

