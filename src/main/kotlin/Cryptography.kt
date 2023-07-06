object Cryptography {

    fun pinHashing(input: Int?) : Int{
        var output: Int = 0
        val length = input.toString().length
            val firstHalf = input.toString().substring(0,length)
            val secondHalf = input.toString().substring(length)
            output = (secondHalf + firstHalf).toInt()

        return output
    }

}

fun main(){
    val pin = 1234
    val first = Cryptography.pinHashing(pin)
    val second = Cryptography.pinHashing(first)

    println("$first:$second")
}

