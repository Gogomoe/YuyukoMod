package yuyuko.event

import com.megacrit.cardcrawl.cards.AbstractCard

class DegradeEvent(val card: AbstractCard, val reason: DegradeReason, val degradeFun: () -> Unit) : Event(ID) {

    companion object {
        val ID = "Degrade"
    }

    enum class DegradeReason {
        USE, EFFECT
    }

    override fun execute() {
        if (cancel || done) {
            return
        }
        degradeFun()
        done()
    }

}