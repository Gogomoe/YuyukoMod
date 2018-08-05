package yuyuko.event

class Subscriber<T : Event> {

    private val observers: MutableSet<Observer<T>> = mutableSetOf()

    fun subscribe(observer: T.() -> Unit): Observer<T> = subscribe(Observer(observer))

    fun subscribe(observer: Observer<T>): Observer<T> {
        observers.add(observer)
        return observer
    }

    fun removeSubscriber(observer: Observer<T>) {
        observers.remove(observer)
    }

    fun emit(event: T) {
        observers.forEach {
            it(event)
        }
        event.execute()
    }

}