package yuyuko.event

import com.megacrit.cardcrawl.cards.AbstractCard

class OnDrawEvent(val card: AbstractCard) : Event(ID) {

    companion object {
        val ID = "On Draw"
    }

    override fun execute() {
        if (cancel || done) {
            return
        }
        done()
    }

}