import isel.leic.utils.Time
import java.time.LocalTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object TUI {
    /**
     * Função que lê o input e transforma num inteiro para comparar às credenciais confiadas.
     */
    fun getInt(qty: Int): Int {

        var array = emptyArray<Int>()

        for (i in 1..qty) {   //A variável qty, vai definir quantos digitos queremos ler do input (Ex: 4 para o PIN, 3 para o ID)
            var key = KBD.waitKey(20000).code
            if (key != 0.toChar().code) {   // Se a key premida não for nula, é adicionada ao array
                array += key - 48
                LCD.write(key.toChar())
            }

        }
        return array.joinToString("").toInt()  // O array é joined para um inteiro que será o valor comparado às credenciais
    }

    /**
     * Função que faz get da Data e Hora e formata-as para o tempo correto.
     */
    fun getTimeAndDate(): String{
        while(true){
            val currentTime = LocalTime.now()
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val formattedTime = currentTime.format(formatter)

            val currentDate = LocalDate.now()
            val formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val formattedDate = currentDate.format(formatter1)

            return "$formattedDate $formattedTime"
        }
    }

    /**
     * Executa a rotina de login.
     */
    fun loginRoutine() {
        LCD.write("Hello user,")
        Time.sleep(1000)
        LCD.clear()
        LCD.write(getTimeAndDate())
        LCD.cursor(1,0)
        LCD.write("UIN=???")
        LCD.cursor(1,4)
        val id = getInt(3)
        println(id)
        LCD.clear()
        LCD.write(getTimeAndDate())
        LCD.cursor(1,0)
        LCD.write("PIN=????")
        LCD.cursor(1,4)
        val pin = getInt(4)
        println(pin)
        LCD.clear()
        App.mainLog(id, pin)

    }

}

fun main() {
    USERS.defineMap()
    println(TUI.getTimeAndDate())
    HAL.init()
    SerialEmitter.init()
    LCD.init()
    KBD.init()
    TUI.loginRoutine()


}