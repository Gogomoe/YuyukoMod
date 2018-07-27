package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.actions.DeathDancingAction
import yuyuko.patches.CardColorEnum
import yuyuko.powers.DiaphaneityPower

class DeathDancing : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.ATTACK, CardColorEnum.YUYUKO_COLOR,
        CardRarity.UNCOMMON, CardTarget.ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "Death Dancing"
        val IMAGE_PATH = "images/yuyuko/cards/attack3.png"
        val COST = 1
        val DIAPHANEITY_AMOUNT = 7
        val UPGRADE_PLUS_AMOUNT = 3
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseMagicNumber = DIAPHANEITY_AMOUNT
        this.magicNumber = DIAPHANEITY_AMOUNT
    }

    override fun makeCopy(): AbstractCard = DeathDancing()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        target, self,
                        DiaphaneityPower(target!!, this.magicNumber),
                        this.magicNumber
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                DeathDancingAction(target, self!!)
        )
    }


    override fun upgrade() {
        if (!this.upgraded) {
            upgradeName()
            upgradeMagicNumber(UPGRADE_PLUS_AMOUNT)
        }
    }

}