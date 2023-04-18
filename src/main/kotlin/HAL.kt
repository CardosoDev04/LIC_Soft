import isel.leic.UsbPort


object HAL { // Virtualiza o acesso ao sistema UsbPort
    var x = 0

    // Inicia a classe
    fun init() {

    }

    // Retorna true se o bit tiver o valor lógico ‘1’
    fun isBit(mask: Int): Boolean {
        val res = UsbPort.read() and mask
        return res != 0
    }

    // Retorna os valores dos bits representados por mask presentes no UsbPort
    fun readBits(mask: Int): Int {
        return UsbPort.read() and mask
    }

    // Escreve nos bits representados por mask o valor de value
    fun writeBits(mask: Int, value: Int){
        x = (value and mask) or (x and mask.inv())
        UsbPort.write(x)
    }
    // Coloca os bits representados por mask no valor lógico ‘1’
    fun setBits(mask: Int) {
        if(isBit(mask)){
            UsbPort.write(1)
        }
    }
    // Coloca os bits representados por mask no valor lógico ‘0’
    fun clrBits(mask: Int) {
        if(isBit(mask)){
            UsbPort.write(0)
        }
    }

    fun main() {

    }
}