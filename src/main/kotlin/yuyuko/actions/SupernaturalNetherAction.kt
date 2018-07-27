package yuyuko.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import yuyuko.event.ApplyDiaphaneityPowerEvent
import yuyuko.event.ApplyDiaphaneityPowerEvent.ApplyDiaphaneityPower.EFFECT
import yuyuko.event.EventDispenser
import yuyuko.powers.DiaphaneityPower


class SupernaturalNetherAction : AbstractGameAction() {

    init {
        this.duration = Settings.ACTION_DUR_MED
        this.actionType = ActionType.POWER
    }

    override fun update() {
        val monsters = AbstractDungeon.getCurrRoom().monsters.monsters
                .filter { !it.isDeadOrEscaped }
                .toTypedArray()
        val all = listOf(AbstractDungeon.player, *monsters)
        all.forEach {
            val amount = it.getPower(DiaphaneityPower.POWER_ID)?.amount ?: 0
            val toAdd = 20 - amount
            if (amount < 20) {
                EventDispenser.emit(ApplyDiaphaneityPowerEvent(it, AbstractDungeon.player, toAdd, EFFECT))
            }
        }
        this.isDone = true
    }

}