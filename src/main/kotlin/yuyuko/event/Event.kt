package yuyuko.event

abstract class Event(val ID: String) {
    var cancel = false
    var done = false

    abstract fun execute()

    fun cancel() {
        cancel = true
    }

    fun done() {
        done = true
    }

    companion object {

        operator fun invoke(ID: String, call: () -> Unit) = object : Event(ID) {
            override fun execute() {
                call()
            }
        }

    }
}