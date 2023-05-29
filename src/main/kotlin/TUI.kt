import isel.leic.utils.Time

object TUI {
    fun getInt(qty: Int): Int {   // Função que lê o input e transforma num inteiro para comparar às credenciais confiadas.

        var array = emptyArray<Int>()
        for (i in 1..qty) {   //A variável qty, vai definir quantos digitos queremos ler do input (Ex: 4 para o PIN, 3 para o ID)
            var key = KBD.waitKey(5000).code
            if (key != 0.toChar().code) {
                array += key - 48
            }

        }
        return array.joinToString("").toInt()
    }

    fun loginRoutine() {
        LCD.write("Hello user,")
        Time.sleep(1000)
        LCD.clear()
        LCD.write("Enter your ID")
        val id = getInt(1)
        println(id)
        LCD.clear()
        LCD.write("Enter your PIN")
        val pin = getInt(4)
        println(pin)
        LCD.clear()
        App.mainLog(id, pin)
    }

}

fun main() {

    HAL.init()
    SerialEmitter.init()
    LCD.init()
    KBD.init()
    TUI.loginRoutine()


}