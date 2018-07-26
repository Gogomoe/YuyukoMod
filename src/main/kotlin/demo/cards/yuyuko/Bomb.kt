package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower
import demo.cards.CardProperty
import demo.patches.CardColorEnum

class Bomb : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.ATTACK, CardColorEnum.YUYUKO_COLOR,
        CardRarity.UNCOMMON, CardTarget.ALL_ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "Bomb"
        val IMAGE_PATH = "images/yuyuko/cards/attack2.png"
        val COST = 3
        val ATTACK_DMG = 9
        val UPGRADE_PLUS_DMG = 6
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!

        init {
            CardProperty.put<AbstractCard.() -> Unit>("$ID:triggerOnDiscard") {
                AbstractDungeon.actionManager.addToBottom(
                        MakeTempCardInDiscardAction(this.makeStatEquivalentCopy(), 1)
                )
            }
        }
    }

    init {
        this.baseDamage = ATTACK_DMG
        this.exhaust = true
    }

    override fun makeCopy(): AbstractCard = Bomb()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                DamageAllEnemiesAction(
                        self,
                        this.multiDamage,
                        DamageType.NORMAL,
                        AttackEffect.SLASH_DIAGONAL
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        IntangiblePlayerPower(self, 1),
                        1
                )
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeDamage(UPGRADE_PLUS_DMG)
        }
    }

}