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
        val player = AbstractDungeon.player
        player.potionSlots -= 1
        val potions = player.potions
        val index = potions.indexOfFirst { it is PotionSlot }

        player.obtainPotion(index, potions[index + 1].makeCopy())
        for (i in (index + 1) until player.potionSlots) {
            player.removePotion(potions[i])
            player.obtainPotion(i, potions[i + 1].makeCopy())
        }
        potions.removeAt(player.potionSlots)

        player.adjustPotionPositions()
        this.isDone = true
    }

}