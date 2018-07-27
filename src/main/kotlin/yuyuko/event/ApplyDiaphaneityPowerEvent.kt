package yuyuko.event

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import yuyuko.powers.DiaphaneityPower

class ApplyDiaphaneityPowerEvent(
        var target: AbstractCreature,
        var source: AbstractCreature,
        var amount: Int,
        val reason: ApplyDiaphaneityPower
) : Event(ID) {

    companion object {
        val ID = "Apply Diaphaneity Power"
    }


    override fun execute() {
        if (cancel || done) {
            return
        }
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        target, source,
                        DiaphaneityPower(target, amount),
                        amount
                )
        )
        done()
    }

    enum class ApplyDiaphaneityPower {
        CARD, EFFECT
    }

}