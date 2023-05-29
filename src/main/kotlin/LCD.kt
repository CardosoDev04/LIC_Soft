import isel.leic.utils.Time

const val MSK = 0x0F
var isSerial = true

object LCD {

    // Escreve um nibble de comando/dados no LCD em paralelo
    private fun writeNibbleParallel(rs: Boolean, data: Int) {
        if (rs) HAL.setBits(0x10)
        else HAL.clrBits(0x10)
        HAL.setBits(0x20)
        HAL.writeBits(MSK, data)
        HAL.clrBits(0x20)
    }

    // Escreve um nibble de comando/dados no LCD em série
    private fun writeNibbleSerial(rs: Boolean, data: Int) {
        if (rs) {
            val shifted = data.shl(1)
            SerialEmitter.send(SerialEmitter.Destination.LCD, shifted or 1)
        } else {
            val shifted = data.shl(1)
            SerialEmitter.send(SerialEmitter.Destination.LCD, shifted)

        }
        //
    }


    // Escreve um nibble de comando/dados no LCD
    private fun writeNibble(rs: Boolean, data: Int) {
        if (isSerial) {
            writeNibbleSerial(rs, data)
        } else writeNibbleParallel(rs, data)
    }

    // Escreve um byte de comando/dados no LCD
    private fun writeByte(rs: Boolean, data: Int) {
        writeNibble(rs, data.shr(4))
        writeNibble(rs, data)
    }

    private fun writeCMD(data: Int) {
        writeByte(false, data)
    }

    private fun writeDATA(data: Int) {
        writeByte(true, data)
    }

    // Envia a sequência de iniciação para comunicação a 4 bits.

    fun init() {
        Time.sleep(15)
        writeNibble(false, 0x03)
        Time.sleep(5)
        writeNibble(false, 0x03)
        writeNibble(false, 0x03)
        writeNibble(false, 0x02)
        writeCMD(0x28)
        writeCMD(0x08)
        writeCMD(0x01)
        writeCMD(0x06)
        writeCMD(0x0f)
    }

    // Escreve um caráter na posição corrente.
    fun write(c: Char) {
        writeDATA(c.code)
    }

    // Escreve uma string na posição corrente.
    fun write(text: String) {
        for (c in text) {
            write(c)
        }
    }

    // Envia comando para posicionar cursor (‘line’:0..LINES-1 , ‘column’:0..COLS-1)
    fun cursor(line: Int, column: Int) {
        val data = (line * 4 + 8) * 16 + column
        writeByte(false, data)
    }

    // Envia comando para limpar o ecrã e posicionar o cursor em (0,0)
    fun clear() {
        writeByte(false, 1)
    }


}

fun main() {
    HAL.init()
    SerialEmitter.init()
    LCD.init()
    LCD.write("Hello world!")


}