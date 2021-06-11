package id.ac.unhas.tugascrud

import java.util.*

data class ModelCRUD (
    val id: Int = getAutoId(),
    var username: String = "",
    var email: String = ""

) {
    companion object{
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(100)
        }
    }
}