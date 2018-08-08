package yuyuko.event

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import yuyuko.actions.UpgradeAllAction

class UpgradeAllEvent(val condition: (AbstractCard) -> Boolean, var times: Int = 1) : Event(ID) {

    companion object {
        val ID = "UpgradeAll"
    }

    override fun execute() {
        if (cancel || done) {
            return
        }
        AbstractDungeon.actionManager.addToBottom(
                UpgradeAllAction(condition, times)
        )
        done()
    }

}