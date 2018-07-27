package yuyuko.event

class Observer<in T>(val call: (T) -> Unit) {

    operator fun invoke(value: T) {
        call(value)
    }

}