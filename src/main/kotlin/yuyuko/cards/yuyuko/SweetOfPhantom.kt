package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.ConstrictedPower
import yuyuko.patches.CardColorEnum
import yuyuko.powers.GhostPower

class SweetOfPhantom : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.COMMON, CardTarget.ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "Sweet of Phantom"
        val IMAGE_PATH = "images/yuyuko/cards/skill5.png"
        val COST = 0
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseMagicNumber = 1
        this.magicNumber = 1
    }

    override fun makeCopy(): AbstractCard = SweetOfPhantom()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        GhostPower(self!!, this.magicNumber),
                        this.magicNumber
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        target, self,
                        ConstrictedPower(target!!, self, this.magicNumber),
                        this.magicNumber
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                DrawCardAction(self, this.magicNumber)
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeMagicNumber(1)
        }
    }


}