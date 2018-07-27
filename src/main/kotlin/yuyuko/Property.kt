package yuyuko

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.monsters.MonsterGroup
import com.megacrit.cardcrawl.powers.AbstractPower

@Suppress("UNCHECKED_CAST")
object Property {
    private val map = mutableMapOf<String, Any>()
    fun <T : Any> put(id: String, value: T) {
        map[id] = value
    }

    fun <T : Any> get(id: String): T? = map[id] as T?
}

fun AbstractCard.triggerOnDiscard() {
    val func = Property.get<AbstractCard.() -> Unit>("${this.cardID}:triggerOnDiscard")
            ?: return
    this.func()
}

fun AbstractPower.triggerOnShuffle() {
    val func = Property.get<AbstractPower.() -> Unit>("${this.ID}:triggerOnShuffle")
            ?: return
    this.func()
}

fun AbstractCreature.applyShufflePowers() {
    this.powers.forEach(AbstractPower::triggerOnShuffle)
}

fun MonsterGroup.applyShufflePowers() {
    this.monsters
            .filter { !it.isDeadOrEscaped }
            .forEach(AbstractCreature::applyShufflePowers)
}