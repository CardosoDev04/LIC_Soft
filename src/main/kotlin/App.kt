import isel.leic.utils.Time
import java.io.File
import java.io.FileWriter



var invalidLog = false // Variável que é alterada para true se o login for inválido

const val M_MSK = 0x80



const val NONE = 0;

object App {

    /**
     * Executa a main routine de login, com todas as funções para o login.
     */
    fun mainLog(id: Int, pin: Int) {
        val trusted = login(id, pin)
        openDoor(trusted,id)
        if (invalidLog) TUI.loginRoutine()
    }

    /**
     *  Verifica se o ID e PIN inseridos condizem com os do utilizador confiado
     */

    fun login(id: Int, pin: Int): Boolean {
        return  pin == USERS.getUserPin(id)
    }

    /**
     * Se o utilizador for o confiado, abre a porta caso contrario fecha e dá Error
     */
    fun openDoor(isTrusted: Boolean,id: Int) {
        if (isTrusted) {
            Doormechanism.open(15)
            LCD.write("Welcome,")
            LCD.cursor(1,0)
            LCD.write("${USERS.getUsername(id)}")
            Time.sleep(2000)
            LCD.clear()
            if(USERS.getPhrase(id)?.isNotEmpty() == true && USERS.getPhrase(id) != null) LCD.write("${USERS.getPhrase(id)}")
            var entry = createLogEntry(id)
            appendEntry(entry)

            var key = NONE.toChar()
            while(key == NONE.toChar()) {
                key = KBD.getKey()
            }
            if(key == '#') {
                replacePINRoutine(id)
            }
            else if(key == '*'){
                USERS.removePhrase(id)
            }
            invalidLog = false
        }

        else {
            Doormechanism.close(15)
            LCD.write("Invalid Login")
            invalidLog = true
            Time.sleep(1500)
            LCD.clear()
        }


    }

    /**
     * Cria uma entrada do tipo Log com a data e hora atual
     */
    fun createLogEntry(id: Int): String{
        val timeDate = TUI.getTimeAndDate()
        return "$timeDate:UIN $id"
    }

    /**
     * Adiciona a entrada ao ficheiros LOGS
     */
    fun appendEntry(entry: String){
        val logs = File("LOGS.txt")
        FileWriter(logs, true).use { writer -> writer.write("$entry\n")}

    }

    /**
     * Rotina de alteração do PIN
     */
    fun replacePINRoutine(id: Int){
        LCD.clear()
        LCD.write("PIN=????")
        LCD.cursor(0,4)
        val newPIN = TUI.getInt(4,0)
        LCD.clear()
        LCD.write("Confirm your PIN")
        Time.sleep(3000)
        LCD.clear()
        LCD.write("PIN=????")
        LCD.cursor(0,4)
        val newPINConfirm = TUI.getInt(4,0)

        if(newPIN == newPINConfirm) {
            USERS.replacePIN(id, newPIN)
        }
        else{
            LCD.clear()
            LCD.write("Mismatch")
            Time.sleep(2000)
            replacePINRoutine(id)
        }
    }


}

