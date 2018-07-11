package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.patches.CardColorEnum

class Butterfly : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        AbstractCard.CardType.ATTACK, CardColorEnum.YUYUKO_COLOR,
        AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "Butterfly"
        val IMAGE_PATH = "images/yuyuko/cards/butterfly.png"
        val COST = 0
        val ATTACK_DMG = 1
        val UPGRADE_PLUS_DMG = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseDamage = ATTACK_DMG
    }

    override fun makeCopy(): AbstractCard = Butterfly()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                DamageAction(
                        target,
                        DamageInfo(self, damage, damageTypeForTurn),
                        AttackEffect.SLASH_DIAGONAL
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                DrawCardAction(self, 1, false)
        )
        degradeToInitiation()
    }

    override fun canUpgrade(): Boolean = true

    override fun upgrade() {
        upgradeName()
        upgradeDamage(UPGRADE_PLUS_DMG)
    }

    private fun degradeToInitiation() {
        this.timesUpgraded = 0
        this.upgraded = false
        this.name = NAME
        this.initializeTitle()
        this.baseDamage -= UPGRADE_PLUS_DMG
        this.upgradedDamage = false
    }

}