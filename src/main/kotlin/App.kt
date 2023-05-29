data class TrustedUser(val id: Int, val pin: Int)

val trusted = TrustedUser(0,1234)

object App {

    fun login(id: Int, pin: Int): Boolean {
        return id == trusted.id && pin == trusted.pin
    }

    fun openDoor(isTrusted: Boolean){
        if(isTrusted) {
            Doormechanism.open(15)
            LCD.write("Success")
        }
        else {
            Doormechanism.close(15)
            LCD.write("Error")
        }
    }
}

