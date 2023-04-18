object SerialEmitter { // Envia tramas para os diferentes módulos Serial Receiver.
    enum class Destination { LCD, DOOR }

    var SS = 1

    // Inicia a classe
    fun init() {

    }

    // Envia uma trama para o SerialReceiver identificado o destino em addr e os bits de dados em data.
    fun send(addr: Destination, data: Int) {
        SS = 0
    }

    // Retorna true se o canal série estiver ocupado
    fun isBusy(): Boolean {
        return SS == 0
    }

}