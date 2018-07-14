package demo.cards

import com.megacrit.cardcrawl.cards.AbstractCard

@Suppress("UNCHECKED_CAST")
object CardProperty {
    private val map = mutableMapOf<String, Any>()
    fun <T : Any> put(id: String, value: T) {
        map[id] = value
    }

    fun <T : Any> get(id: String): T? = map[id] as T?
}

fun AbstractCard.triggerOnDiscard(isEndTurn: Boolean) {
    val func = CardProperty.get<AbstractCard.(isEndTurn: Boolean) -> Unit>("${this.cardID}:triggerOnDiscard") ?: return
    this.func(isEndTurn)
}