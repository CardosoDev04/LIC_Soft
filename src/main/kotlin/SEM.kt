import isel.leic.utils.Time

const val SS = 0x20
const val SDX = 0x01
const val SCLK = 0x40
const val sLCD = 0x02
const val sDOOR = 0x04


object SerialEmitter { // Envia tramas para os diferentes módulos Serial Receiver.
    enum class Destination { LCD, DOOR }


    // Inicia a classe
    fun init() {
        HAL.setBits(sLCD)
        HAL.setBits(sDOOR)
        HAL.clrBits(SCLK)

    }

    // Envia uma trama para o SerialReceiver identificado o destino em addr e os bits de dados em data.
    fun send(addr: Destination, data: Int) {

        val mask = if (addr == Destination.LCD) sLCD else sDOOR

        HAL.clrBits(mask)
        HAL.clrBits(SCLK)

        for (i in 0..4) {
            HAL.clrBits(SCLK)
            HAL.writeBits(SDX, data.shr(i))
            HAL.setBits(SCLK)
            Time.sleep(1)
        }
        HAL.clrBits(SCLK)
        HAL.setBits(mask)


    }

    // Retorna true se o canal série estiver ocupado
    fun isBusy(): Boolean {
        return HAL.isBit(SS)
    }


}

fun main() {
    HAL.init()
    SerialEmitter.init()
    SerialEmitter.send(SerialEmitter.Destination.LCD, 10101)
}