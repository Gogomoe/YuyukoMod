package yuyuko.potions

import basemod.abstracts.CustomPotion
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.potions.AbstractPotion
import com.megacrit.cardcrawl.rooms.AbstractRoom


class KeinePotion : CustomPotion(NAME, ID,
        PotionRarity.COMMON, PotionSize.BOTTLE, PotionColor.GREEN) {
    companion object {
        @JvmStatic
        val ID = "Keine"
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
        this.description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1]
        this.isThrown = false
        this.tips.add(PowerTip(this.name, this.description))
    }

    override fun use(target: AbstractCreature?) {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            return
        }
        val discard = AbstractDungeon.player.discardPile
        discard.group.toList().forEach {
            if (it.exhaust) {
                discard.moveToExhaustPile(it)
            } else {
                discard.moveToDeck(it, true)
            }
        }
        AbstractDungeon.actionManager.addToBottom(
                DrawCardAction(AbstractDungeon.player, this.potency)
        )
    }

    override fun getPotency(p0: Int): Int {
        return 2
    }

    override fun makeCopy(): AbstractPotion = KeinePotion()

}