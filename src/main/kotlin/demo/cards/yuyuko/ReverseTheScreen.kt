package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.actions.HideAction
import demo.patches.CardColorEnum
import demo.powers.DiaphaneityPower
import kotlin.math.abs


class ReverseTheScreen : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.COMMON, CardTarget.ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "Reverse The Screen"
        val IMAGE_PATH = "images/yuyuko/cards/skill3.png"
        val COST = 0
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPDEAGE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!
    }


    override fun makeCopy(): AbstractCard = ReverseTheScreen()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        val selfAmount = self!!.getPower(DiaphaneityPower.POWER_ID)?.amount ?: 0
        val targetAmount = target!!.getPower(DiaphaneityPower.POWER_ID)?.amount ?: 0
        val diff = targetAmount - selfAmount
        when {
            diff > 0 -> {
                exchangePower(self, self, target, diff)
            }
            diff < 0 -> {
                exchangePower(self, target, self, -diff)
            }
            else -> return
        }

        AbstractDungeon.actionManager.addToBottom(
                GainBlockAction(self, self, abs(diff))
        )
    }

    private fun exchangePower(source: AbstractPlayer?, toStack: AbstractCreature, toReduce: AbstractCreature, diff: Int) {
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        toStack, source,
                        DiaphaneityPower(toStack, diff),
                        diff
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                ReducePowerAction(
                        toReduce, source, DiaphaneityPower.POWER_ID, diff
                )
        )
    }

    override fun triggerWhenDrawn() {
        val player = AbstractDungeon.player
        val drawPile = AbstractDungeon.player.drawPile.group
        val remain = drawPile.filter { it.isHide() }
        if (drawPile.size != remain.size) {
            AbstractDungeon.actionManager.addToBottom(
                    HideAction(this)
            )
            AbstractDungeon.actionManager.addToBottom(
                    DrawCardAction(player, 1)
            )
        }
    }

    override fun upgrade() {
        if (!this.upgraded) {
            upgradeName()
            this.rawDescription = UPDEAGE_DESCRIPTION
            this.initializeDescription()
        }
    }


}