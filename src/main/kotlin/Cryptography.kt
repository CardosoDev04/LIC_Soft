object Cryptography {

    fun pinHashing(number: Int) : Int{
        val firstHalf = number / 100
        val secondHalf = number % 100

        return (secondHalf * 100) + firstHalf
    }

}

fun main(){
    val pin = 1234
    val first = Cryptography.pinHashing(pin)
    val second = Cryptography.pinHashing(first)

    println("$first:$second")
}

