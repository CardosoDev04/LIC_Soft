    import isel.leic.utils.Time
    import java.time.LocalTime
    import java.time.LocalDate
    import java.time.format.DateTimeFormatter

    object TUI {
        /**
         * Função que lê o input e transforma num inteiro para comparar às credenciais confiadas.
         */
        fun getInt(qty: Int, line: Int): Int {
            var array = emptyArray<Int>()
            var clearInput = false // Flag to track whether the input should be cleared

            for (i in 1..qty) {
                val key = KBD.waitKey(5000).code
                if (key != 0.toChar().code && key != '*'.code) {
                    array += key - 48
                    LCD.write(key.toChar())
                } else if (key == '*'.code) {
                    clearInput = true // Set the flag to clear the input
                    break
                } else {
                    loginRoutine()
                }
            }

            if (clearInput) {
                LCD.cursor(line, 4)
                when (qty) {
                    3 -> LCD.write("???")
                    4 -> LCD.write("????")
                }
                LCD.cursor(line, 4)
                return getInt(qty, line) // Recursive call to get a new input
            }

            return array.joinToString("").toInt()
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
            LCD.clear()
            LCD.write("Hello user,")
            Time.sleep(1000)
            LCD.clear()
            LCD.write(getTimeAndDate())
            LCD.cursor(1,0)
            LCD.write("UIN=???")
            LCD.cursor(1,4)
            val id = getInt(3,1)
            println(id)
            LCD.clear()
            LCD.write(getTimeAndDate())
            LCD.cursor(1,0)
            LCD.write("PIN=????")
            LCD.cursor(1,4)
            val pin = getInt(4,1)
            println(pin)
            LCD.clear()
            App.mainLog(id, pin)

        }

    }

    fun main() {

        USERS.clearMap()
        USERS.defineMap()
        println(TUI.getTimeAndDate())
        HAL.init()
        SerialEmitter.init()
        LCD.init()
        KBD.init()
        TUI.loginRoutine()


    }