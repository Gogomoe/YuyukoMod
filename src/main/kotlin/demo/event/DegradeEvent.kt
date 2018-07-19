package demo.event

import com.megacrit.cardcrawl.cards.AbstractCard

class DegradeEvent(val card: AbstractCard, val degradeFun: () -> Unit) : Event(ID) {

    companion object {
        val ID = "Degrade"
    }

    override fun execute() {
        if (cancel || done) {
            return
        }
        degradeFun()
        done()
    }

}