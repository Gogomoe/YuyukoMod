package yuyuko.event

object EventDispenser {

    private val subscribers: MutableMap<String, Subscriber<*>> = mutableMapOf()

    fun <T : Event> subscribe(ID: String, observer: (T) -> Unit): Observer<T> = subscribe(ID, Observer(observer))

    fun <T : Event> subscribe(ID: String, observer: Observer<T>): Observer<T> {
        val subscriber = (subscribers[ID] as Subscriber<T>?) ?: Subscriber<T>().also { subscribers[ID] = it }
        return subscriber.subscribe(observer)
    }

    fun <T : Event> emit(event: T) {
        val subscriber = (subscribers[event.ID] as Subscriber<T>?)
                ?: Subscriber<T>().also { subscribers[event.ID] = it }
        subscriber.emit(event)
    }

    fun <T : Event> unsubscribe(ID: String, observer: Observer<T>) {
        (subscribers[ID] as Subscriber<T>?)?.removeSubscriber(observer)
    }

    fun clear() {
        subscribers.clear()
    }

}