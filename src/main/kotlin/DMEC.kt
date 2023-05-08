object Doormechanism { // Controla o estado do mecanismo de abertura da porta.

    var OC = 0

        // Inicia a classe, estabelecendo os valores iniciais.
        fun init() {
            OC = 0
            var HAL = HAL
            var SEM = SerialEmitter
        }

    //1 + velocity or 0 + velocity

        // Envia comando para abrir a porta, com o parâmetro de velocidade
        fun open(velocity: Int) {
            OC = 1
            var cmd = velocity.shl(1)
            SerialEmitter.send(SerialEmitter.Destination.DOOR,cmd or OC)  // 1velocity

        }

        // Envia comando para fechar a porta, com o parâmetro de velocidade
        fun close(velocity: Int) {
            var cmd = velocity.shl(1)
            SerialEmitter.send(SerialEmitter.Destination.DOOR,cmd or OC) //0velocity
        }

        // Verifica se o comando anterior está concluído
        fun finished() : Boolean {
            return !SerialEmitter.isBusy()  //if not busy, then is finished
        }
    }
