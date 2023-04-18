object KBD { // Ler teclas. Métodos retornam ‘0’..’9’,’#’,’*’ ou NONE.
    const val NONE = 0;
    val KACK_MASK = 0x80

    // Inicia a classe
    fun init() {
        HAL.clrBits(KACK_MASK)
    }

    // Retorna de imediato a tecla premida ou NONE se não há tecla premida.
    fun getKey(): Char {
        var output: Char
        var input: String
        do{
            input = readln()
            output = input.first()
        }
            while(input.isNotEmpty())

            output = NONE.toChar()
        return output
    }

// Retorna a tecla premida, caso ocorra antes do ‘timeout’ (representado em milissegundos), ou NONE caso contrário.
    fun waitKey(timeout: Long): Char {
        val key = getKey()
    if(key == NONE.toChar()) return NONE.toChar()
    else Thread.sleep(timeout)
        return NONE.toChar()
    }
}

fun main(){
    KBD.init()
    while(true){
        println(KBD.getKey())
    }
}