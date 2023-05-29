

fun getInt(qty: Int): Int {

    var array = emptyArray<String>()
    for (i in 1..qty) {   //A variável qty, vai definir quantos digitos queremos ler do input (Ex: 4 para o PIN, 3 para o ID)
        var digit = KBD.waitKey(2500).code.toString()

        if(digit != "") {  //Se não for lido digito algum, deve tentar ler de novo (Pode ter ocorrido pelo utilizador demorar demasiado)
            array += digit
        } else {
            digit = KBD.waitKey(2500).code.toString()
            array += digit
        }

    }
    return array.joinToString().toInt()
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