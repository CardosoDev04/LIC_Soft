object Doormechanism { // Controla o estado do mecanismo de abertura da porta.

    var OC = 0

        // Inicia a classe, estabelecendo os valores iniciais.
        fun init() {
            var HAL = HAL
            var SEM = SerialEmitter
        }

        // Envia comando para abrir a porta, com o parâmetro de velocidade
        fun open(velocity: Int) {
            OC = 1
            var cmd = (OC + velocity).toString().toInt()
            SerialEmitter.send(SerialEmitter.Destination.DOOR,cmd)

        }

        // Envia comando para fechar a porta, com o parâmetro de velocidade
        fun close(velocity: Int) {
            OC = 0
            var cmd = (OC + velocity).toString().toInt()
            SerialEmitter.send(SerialEmitter.Destination.DOOR,cmd)
        }

        // Verifica se o comando anterior está concluído
        fun finished() : Boolean {
            return !SerialEmitter.isBusy()
        }
    }
