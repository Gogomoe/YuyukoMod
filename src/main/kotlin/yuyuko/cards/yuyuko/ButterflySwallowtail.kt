package yuyuko.cards.yuyuko

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
import yuyuko.cards.isButterfly
import yuyuko.event.DegradeEvent
import yuyuko.event.DegradeEvent.DegradeReason.USE
import yuyuko.event.EventDispenser
import yuyuko.event.SpecialButterflyCalculateCardDamageEvent
import yuyuko.event.UpgradeAllEvent
import yuyuko.patches.CardColorEnum

class ButterflySwallowtail : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.STATUS, CardColorEnum.YUYUKO_COLOR,
        CardRarity.SPECIAL, CardTarget.ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "Butterfly (Swallowtail)"
        val IMAGE_PATH = "images/yuyuko/cards/butterfly.png"
        val COST = -2
        val ATTACK_DMG = 1
        val UPGRADE_PLUS_DMG = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseDamage = ATTACK_DMG
    }

    override fun makeCopy(): AbstractCard = ButterflySwallowtail()

    override fun calculateCardDamage(mo: AbstractMonster?) {
        EventDispenser.emit(SpecialButterflyCalculateCardDamageEvent(this))
        super.calculateCardDamage(mo)
    }

    override fun canUse(p: AbstractPlayer?, m: AbstractMonster?): Boolean =
            this.cardPlayable(m) && this.hasEnoughEnergy()

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
        EventDispenser.emit(UpgradeAllEvent(AbstractCard::isButterfly))

        EventDispenser.emit(DegradeEvent(this, USE, this::degradeToInitiation))

    }

    override fun canUpgrade(): Boolean = true

    override fun upgrade() {
        upgradeName()
        upgradeDamage(UPGRADE_PLUS_DMG)
    }

    override fun upgradeName() {
        ++this.timesUpgraded
        this.upgraded = true
        this.name = "$NAME+$timesUpgraded"
        this.initializeTitle()
    }

    fun degradeToInitiation() {
        this.upgraded = false
        this.name = NAME
        this.baseDamage -= UPGRADE_PLUS_DMG * this.timesUpgraded
        this.upgradedDamage = false
        this.timesUpgraded = 0
        this.initializeTitle()
    }

}