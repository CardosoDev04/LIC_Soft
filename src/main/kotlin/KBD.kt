import isel.leic.utils.Time

object KBD { // Ler teclas. Métodos retornam ‘0’..’9’,’#’,’*’ ou NONE.
    const val NONE = 0;
    val KACK_MSK = 0x80
    val KVAL_MSK = 0x01
    val KMSK = 0x1E
    val digits = arrayOf('1','4','7','*','2','5','8','0','3','6','9','#')

    // Inicia a classe
    fun init() {
        println("Initializing KBD")
    }

    // Retorna de imediato a tecla premida ou NONE se não há tecla premida.
    fun getKey(): Char {
        val key = HAL.readBits(KMSK)

        if (HAL.isBit(KVAL_MSK)){
            HAL.setBits(KACK_MSK)
            println("set kack")
            while(HAL.isBit(KVAL_MSK)){}
            println("clear kack")
            HAL.clrBits(KACK_MSK)
            return digits[key.shr(1)]
        }
        return  NONE.toChar()
    }

// Retorna a tecla premida, caso ocorra antes do ‘timeout’ (representado em milissegundos), ou NONE caso contrário.
    fun waitKey(timeout: Long): Char {
        val t = Time.getTimeInMillis()
    while(Time.getTimeInMillis() - t < timeout){
        val key = getKey()
        if(key != NONE.toChar()) return key
    }
        return NONE.toChar()
    }
}

fun main(){
    HAL.init()
    KBD.init()
      while(true) {
          println(KBD.waitKey(5000))
      }


}
//