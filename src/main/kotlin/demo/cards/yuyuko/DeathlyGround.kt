package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.patches.CardColorEnum
import demo.powers.GhostPower

class DeathlyGround : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.ATTACK, CardColorEnum.YUYUKO_COLOR,
        CardRarity.BASIC, CardTarget.ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "Deathly Ground"
        val IMAGE_PATH = "images/yuyuko/cards/attack.png"
        val COST = 0
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseDamage = 4
        this.baseMagicNumber = 1
        this.magicNumber = 1
    }

    override fun makeCopy(): AbstractCard = DeathlyGround()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                DamageAction(
                        target,
                        DamageInfo(
                                self, this.damage, NORMAL
                        ),
                        BLUNT_HEAVY
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        GhostPower(self!!, this.magicNumber),
                        this.magicNumber
                )
        )
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeDamage(2)
            upgradeMagicNumber(1)
        }
    }

}