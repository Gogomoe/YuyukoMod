package yuyuko.potions

import basemod.abstracts.CustomPotion
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.potions.AbstractPotion


class FireflyPotion : CustomPotion(NAME, ID,
        PotionRarity.COMMON, PotionSize.FAIRY, PotionColor.GREEN) {
    companion object {
        @JvmStatic
        val ID = "Firefly"
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
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1]
        this.isThrown = false
        this.tips.add(PowerTip(this.name, this.description))
    }

    override fun use(target: AbstractCreature?) {
        val healAmt = (AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth) *
                getPotency() / 100
        AbstractDungeon.player.heal(healAmt)
    }

    override fun getPotency(p0: Int): Int {
        return 50
    }

    override fun makeCopy(): AbstractPotion = FireflyPotion()

}