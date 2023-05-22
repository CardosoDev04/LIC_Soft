
const val SS= 0x20
const val SDX = 0x80
const val SCLK = 0x40


object SerialEmitter { // Envia tramas para os diferentes módulos Serial Receiver.
    enum class Destination { LCD, DOOR }


    // Inicia a classe
    fun init() {
        HAL.init()
        HAL.setBits(SS)

    }

    // Envia uma trama para o SerialReceiver identificado o destino em addr e os bits de dados em data.
    fun send(addr: Destination, data: Int) {
        if(addr == Destination.LCD){
            HAL.clrBits(SS)
            HAL.clrBits(SCLK)

            for(i in 0 .. 4){
                HAL.clrBits(SCLK)
                HAL.writeBits(SDX,data.shr(i).shl(1))
                HAL.setBits(SCLK)

            }
            HAL.setBits(SS)
        }

    }

    // Retorna true se o canal série estiver ocupado
    fun isBusy(): Boolean {
        return HAL.isBit(SS)
    }




}

fun main(){
    HAL.init()
    SerialEmitter.init()
    SerialEmitter.send(SerialEmitter.Destination.LCD,10101)
}