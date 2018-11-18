package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RollMoveAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import yuyuko.actions.EndTurnAction
import yuyuko.patches.CardColorEnum
import yuyuko.powers.DizzinessPower
import yuyuko.powers.GhostPower

class AllWander : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.COMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "All Wander"
        val IMAGE_PATH = "images/yuyuko/cards/skill5.png"
        val COST = -1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!
    }

    init {
        this.baseMagicNumber = 0
        this.magicNumber = 0
    }

    override fun makeCopy(): AbstractCard = AllWander()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount
        }

        if (AbstractDungeon.player.cardsPlayedThisTurn == 1) {
            AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(
                            self, self,
                            DizzinessPower(),
                            1
                    )
            )
            AbstractDungeon.actionManager.addToBottom(
                    EndTurnAction()
            )

            AbstractDungeon.getCurrRoom().monsters.monsters
                    .filter { !it.isDeadOrEscaped }
                    .forEach {
                        AbstractDungeon.actionManager.addToBottom(
                                RollMoveAction(it)
                        )
                    }

        } else {
            val amount = this.energyOnUse + this.magicNumber
            AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(
                            self, self,
                            GhostPower(self!!, amount),
                            amount
                    )
            )
        }

        self!!.energy.use(EnergyPanel.totalCount)
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeMagicNumber(1)
            this.rawDescription = UPGRADE_DESCRIPTION
            this.initializeDescription()
        }
    }


}