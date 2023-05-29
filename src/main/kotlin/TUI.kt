import isel.leic.utils.Time

fun getInt(qty: Int): Int {

    var array = emptyArray<Int>()
    for (i in 1..qty) {   //A variável qty, vai definir quantos digitos queremos ler do input (Ex: 4 para o PIN, 3 para o ID)
        var key = KBD.waitKey(5000).code
        if(key != 0.toChar().code  ){
            array += key - 48
        }

    }
    return array.joinToString("").toInt()
}
fun main (){

    HAL.init()
    SerialEmitter.init()
    LCD.init()
    KBD.init()


    LCD.write("Enter user ID")
    val id = getInt(1)
    println(id)
    LCD.clear()
    LCD.write("Enter your PIN")
    val pin = getInt(4)
    println(pin)
    LCD.clear()

    val isLogged = App.login(id,pin)
    App.openDoor(isLogged)
}