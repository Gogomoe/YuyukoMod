package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.NoDrawPower
import demo.actions.RetrievalAction
import demo.patches.CardColorEnum
import demo.powers.GhastlyDreamPower

class GhastlyDream : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.RARE, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Ghastly Dream"
        val IMAGE_PATH = "images/yuyuko/cards/skill4.png"
        val COST = 2
        val POWER_AMOUNT = 1
        val UPGRADE_PLUS_AMOUNT = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseMagicNumber = POWER_AMOUNT
        this.magicNumber = POWER_AMOUNT
    }

    override fun makeCopy(): AbstractCard = GhastlyDream()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        val count = 10 - self!!.hand.group.size
        AbstractDungeon.actionManager.addToBottom(
                RetrievalAction(Butterfly.ID, count)
        )
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        GhastlyDreamPower(this.magicNumber),
                        this.magicNumber
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        NoDrawPower(self),
                        1
                )
        )

    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeMagicNumber(UPGRADE_PLUS_AMOUNT)
        }
    }

}