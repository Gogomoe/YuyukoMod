package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.patches.CardColorEnum
import demo.powers.DizzinessPower
import demo.powers.GhostPower

class AllWander : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.COMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "All Wander"
        val IMAGE_PATH = "images/yuyuko/cards/skill5.png"
        val COST = 1
        val UPGRADE_PLUS_COST = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseMagicNumber = COST
        this.magicNumber = COST
    }

    override fun makeCopy(): AbstractCard = AllWander()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        GhostPower(self!!, this.magicNumber),
                        this.magicNumber
                )
        )

        if (AbstractDungeon.player.cardsPlayedThisTurn == 1) {
            AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(
                            self, self,
                            DizzinessPower(),
                            1
                    )
            )
            AbstractDungeon.getCurrRoom().endTurn()
        }

    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeMagicNumber(UPGRADE_PLUS_COST)
            this.upgradeBaseCost(2)
        }
    }


}