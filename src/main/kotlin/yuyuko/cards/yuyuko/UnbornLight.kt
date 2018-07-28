package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.event.ApplyDiaphaneityPowerEvent
import yuyuko.event.ApplyDiaphaneityPowerEvent.ApplyDiaphaneityPower.CARD
import yuyuko.event.EventDispenser
import yuyuko.patches.CardColorEnum

class UnbornLight : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.ATTACK, CardColorEnum.YUYUKO_COLOR,
        CardRarity.COMMON, CardTarget.ALL_ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "Unborn Light"
        val IMAGE_PATH = "images/yuyuko/cards/attack3.png"
        val COST = 2
        val ATTACK_DMG = 8
        val UPGRADE_PLUS_DMG = 4
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseDamage = ATTACK_DMG
        this.baseMagicNumber = ATTACK_DMG
        this.magicNumber = ATTACK_DMG
        this.isMultiDamage = true
    }

    override fun makeCopy(): AbstractCard = UnbornLight()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {

        AbstractDungeon.getCurrRoom().monsters.monsters
                .filter { !it.isDeadOrEscaped }
                .forEach {
                    EventDispenser.emit(ApplyDiaphaneityPowerEvent(it, self!!, this.magicNumber, CARD))

                }

        AbstractDungeon.actionManager.addToBottom(
                DamageAllEnemiesAction(
                        self, this.multiDamage, NORMAL, AttackEffect.SLASH_DIAGONAL
                )
        )

    }

    override fun upgrade() {
        if (!this.upgraded) {
            upgradeName()
            upgradeDamage(UPGRADE_PLUS_DMG)
            upgradeMagicNumber(UPGRADE_PLUS_DMG)
        }
    }

}