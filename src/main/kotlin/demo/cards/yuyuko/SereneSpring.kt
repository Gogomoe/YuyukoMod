package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HEAVY
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.actions.HideAction
import demo.cards.HideCards
import demo.patches.CardColorEnum

class SereneSpring : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.ATTACK, CardColorEnum.YUYUKO_COLOR,
        CardRarity.UNCOMMON, CardTarget.ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "Serene Spring"
        val IMAGE_PATH = "images/yuyuko/cards/attack4.png"
        val COST = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseDamage = 10
        this.isEthereal = true
    }

    override fun makeCopy(): AbstractCard = SereneSpring()

    override fun calculateCardDamage(mo: AbstractMonster?) {
        super.calculateCardDamage(mo)
        val isHealthLessThanPercent = mo!!.currentHealth <= mo.maxHealth * (baseDamage.toFloat() / 100)
        if (isHealthLessThanPercent) {
            this.damage = mo.currentHealth + mo.currentBlock
        }
    }

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                DamageAction(
                        target,
                        DamageInfo(self, this.damage, NORMAL),
                        SLASH_HEAVY
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInDrawPileAction(
                        SakuraDormancy(), 1, true, true
                )
        )
    }

    override fun triggerWhenDrawn() {
        if (HideCards.shouldHide()) {
            AbstractDungeon.actionManager.addToTop(
                    HideAction(this)
            )
        }
    }


    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeDamage(5)
        }
    }

}