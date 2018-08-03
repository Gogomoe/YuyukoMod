package yuyuko.event

import com.megacrit.cardcrawl.cards.AbstractCard

class SpecialButterflyCalculateCardDamageEvent(val card: AbstractCard, var baseDamage: Int = card.baseDamage) : Event(ID) {

    companion object {
        val ID = "Butterfly Calculate Card Damage"
    }

    override fun execute() {
        if (cancel || done) {
            return
        }
        card.baseDamage = baseDamage
        done()
    }

}