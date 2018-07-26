package demo.powers

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.AbstractPower
import demo.cards.isButterfly
import demo.cards.isSakura
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min

class FullInkySakuraPower(amount: Int = 1) : AbstractPower() {

    companion object {
        @JvmStatic
        val POWER_ID = "Full Inky Sakura"
        private val POWER_STRINGS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = POWER_STRINGS.NAME!!
        val DESCRIPTIONS = POWER_STRINGS.DESCRIPTIONS!!
    }

    init {
        this.name = NAME
        this.ID = POWER_ID
        this.owner = AbstractDungeon.player
        this.amount = min(max(amount, 0), 999)
        this.updateDescription()
        this.type = PowerType.BUFF
        this.isTurnBased = false
        this.img = ImageMaster.loadImage("images/powers/power.png")
    }

    var available = false

    override fun onUseCard(card: AbstractCard?, action: UseCardAction?) {
        if (card!!.isSakura()) {
            this.flash()
            available = true
            return
        }
        if (card.isButterfly() && available) {
            this.flash()
            available = false

            val monsters = AbstractDungeon.getCurrRoom().monsters.monsters
            val damage = IntArray(monsters.size)
            monsters.forEachIndexed { i, m ->
                damage[i] = ceil(m.maxHealth * 0.05f).toInt()
            }

            repeat(amount) {
                AbstractDungeon.actionManager.addToBottom(
                        DamageAllEnemiesAction(
                                owner, damage, DamageType.HP_LOSS, AttackEffect.FIRE
                        )
                )
            }
        }
    }

    override fun updateDescription() {
        this.description = DESCRIPTIONS[0]
    }

}
