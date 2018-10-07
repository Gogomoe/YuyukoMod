package yuyuko.actions

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.potions.PotionSlot

class LosePotionSlotAction : AbstractGameAction() {

    init {
        this.duration = Settings.ACTION_DUR_MED
        this.actionType = ActionType.WAIT
    }

    override fun update() {
        AbstractDungeon.player.potionSlots -= 1
        AbstractDungeon.player.potions.remove(
                AbstractDungeon.player.potions.find { it is PotionSlot }
        )
        this.isDone = true
    }

}