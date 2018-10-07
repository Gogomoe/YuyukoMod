package yuyuko.potions

import basemod.abstracts.CustomPotion
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.actions.common.HealAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.potions.AbstractPotion
import com.megacrit.cardcrawl.rooms.AbstractRoom


class PcklesPotion : CustomPotion(NAME, ID,
        PotionRarity.RARE, PotionSize.S, PotionColor.GREEN) {
    companion object {
        @JvmStatic
        val ID = "Pckles"
        val NAME: String
        val DESCRIPTIONS: Array<String>

        init {
            val strings = CardCrawlGame.languagePack.getPotionString(ID)
            NAME = strings.NAME
            DESCRIPTIONS = strings.DESCRIPTIONS
        }
    }

    init {
        this.potency = this.getPotency()
        this.description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1] + potency + DESCRIPTIONS[2]
        this.isThrown = false
        this.tips.add(PowerTip(this.name, this.description))
    }

    override fun use(target: AbstractCreature?) {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            return
        }
        val player = AbstractDungeon.player
        AbstractDungeon.actionManager.addToBottom(
                GainBlockAction(player, player, this.potency)
        )
        AbstractDungeon.actionManager.addToBottom(
                HealAction(player, player, 1)
        )
        AbstractDungeon.getCurrRoom().addPotionToRewards(this.makeCopy())
    }

    override fun getPotency(p0: Int): Int {
        return 1
    }

    override fun makeCopy(): AbstractPotion = PcklesPotion()

}