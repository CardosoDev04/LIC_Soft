import isel.leic.utils.Time

object LCD {
    private const val LINES = 2
    private const val COLS = 16
    var isByte = false

    // Escreve um nibble de comando/dados no LCD em paralelo
    private fun writeNibbleParallel(rs: Boolean, data: Int){
        HAL.writeBits(0x00_F0,data.shl(4))
    }

    // Escreve um nibble de comando/dados no LCD em série
   /** private fun writeNibbleSerial(rs: Boolean, data: Int){
        HAL.writeBits(0xFFFF, data)
    }
   **/

    // Escreve um nibble de comando/dados no LCD
    private fun writeNibble(rs: Boolean, data: Int) {
        writeNibbleParallel(rs,data)
    }

    // Escreve um byte de comando/dados no LCD
    private fun writeByte(rs: Boolean, data: Int){
        writeNibble(rs, data.shr(4))
        writeNibble(rs, data)
    }

    private fun writeCMD(data: Int){
        writeByte(false, data)
}

    private fun writeDATA(data: Int){
        writeByte(true, data)
    }

    // Envia a sequência de iniciação para comunicação a 4 bits.
    fun init() {
            Time.sleep(15)

        writeNibble(false,3)

        Time.sleep(4.1.toLong())

        writeNibble(false, 3)

        Time.sleep(0.11.toLong())

        writeNibble(false, 3)
        writeNibble(false,2)
        writeNibble(false,2)
        writeNibble(false, 8)
        writeNibble(false, 0)
        writeNibble(false,8)
        writeNibble(false, 0)
        writeNibble(false, 1)
        writeNibble(false, 0)
        writeNibble(false, 6)
        writeCMD(15)


    }

    // Escreve um caráter na posição corrente.
    fun write(c: Char) {
        writeDATA(c.code)
    }

    // Escreve uma string na posição corrente.
    fun write(text: String) {
        for (c in  text)
        {write(c)}
    }

    // Envia comando para posicionar cursor (‘line’:0..LINES-1 , ‘column’:0..COLS-1)
    fun cursor(line: Int, column: Int) {

    }

    // Envia comando para limpar o ecrã e posicionar o cursor em (0,0)
    fun clear() {
        TODO()
    }


}

fun main(){
    LCD.init()
    LCD.write("Gay")

}