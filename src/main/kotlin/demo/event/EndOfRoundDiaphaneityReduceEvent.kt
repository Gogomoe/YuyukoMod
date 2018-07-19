package demo.event

import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import demo.powers.DiaphaneityPower

class EndOfRoundDiaphaneityReduceEvent(val power: DiaphaneityPower, val reduceAmount: Int) : Event(ID) {

    companion object {
        val ID = "End of Round Diaphaneity Reduce"
    }

    override fun execute() {
        if (cancel || done) {
            return
        }
        AbstractDungeon.actionManager.addToBottom(
                ReducePowerAction(power.owner, power.owner, power, reduceAmount)
        )
        done()
    }

}