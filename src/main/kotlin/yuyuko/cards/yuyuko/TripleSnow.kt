package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.patches.CardColorEnum
import yuyuko.powers.PreparePower
import yuyuko.powers.TripleSnowPower

class TripleSnow : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.UNCOMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Triple Snow"
        val IMAGE_PATH = "images/yuyuko/cards/skill3.png"
        val COST = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.exhaust = true
    }

    override fun makeCopy(): AbstractCard = TripleSnow()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        val toRun = {
            AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(
                            self, self,
                            TripleSnowPower(),
                            1
                    )
            )
        }
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        PreparePower(ID, toRun)
                )
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeBaseCost(0)
        }
    }


}