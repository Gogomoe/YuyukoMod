package yuyuko.potions

import basemod.abstracts.CustomPotion
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.potions.AbstractPotion
import com.megacrit.cardcrawl.rooms.AbstractRoom
import yuyuko.actions.LosePotionSlotAction


class GelsemiumTeaPotion : CustomPotion(NAME, ID,
        PotionRarity.COMMON, PotionSize.M, PotionColor.GREEN) {
    companion object {
        @JvmStatic
        val ID = "Gelsemium Tea"
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
        this.description = DESCRIPTIONS[0]
        this.isThrown = true
        this.targetRequired = true
        this.tips.add(PowerTip(this.name, this.description))
    }

    override fun use(target: AbstractCreature?) {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            return
        }
        val damage = target!!.maxHealth + target.currentBlock
        AbstractDungeon.actionManager.addToBottom(
                DamageAction(
                        target,
                        DamageInfo(target, damage, DamageInfo.DamageType.HP_LOSS),
                        AbstractGameAction.AttackEffect.FIRE
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                LosePotionSlotAction()
        )
    }

    override fun getPotency(p0: Int): Int {
        return 0
    }

    override fun makeCopy(): AbstractPotion = GelsemiumTeaPotion()

}