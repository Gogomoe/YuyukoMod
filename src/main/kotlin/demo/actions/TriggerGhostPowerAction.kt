package demo.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import demo.powers.GhostPower

class TriggerGhostPowerAction : AbstractGameAction() {

    override fun update() {
        val power = AbstractDungeon.player.getPower(GhostPower.POWER_ID) ?: return
        power.onSpecificTrigger()
        this.isDone = true
    }

}