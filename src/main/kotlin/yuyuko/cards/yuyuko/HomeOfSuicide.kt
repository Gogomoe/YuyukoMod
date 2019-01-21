package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RollMoveAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardQueueItem
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import yuyuko.actions.EndTurnAction
import yuyuko.cards.isButterfly
import yuyuko.cards.isSakura
import yuyuko.patches.CardColorEnum
import yuyuko.powers.DizzinessPower
import yuyuko.powers.GhostPower

class HomeOfSuicide : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.RARE, CardTarget.ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "Home of Suicide"
        val IMAGE_PATH = "images/yuyuko/cards/skill2.png"
        val COST = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.exhaust = true
    }

    override fun makeCopy(): AbstractCard = HomeOfSuicide()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.player.exhaustPile.group
                .filter { it!!.isButterfly() || it.isSakura() }
                .forEach { card ->
                    val tmp = card.makeStatEquivalentCopy()
                    tmp.cardID = "${tmp.cardID}_FAKE"

                    AbstractDungeon.player.limbo.addToBottom(tmp)
                    tmp.current_x = card.current_x
                    tmp.current_y = card.current_y
                    tmp.target_x = Settings.WIDTH.toFloat() / 2.0f - 300.0f * Settings.scale
                    tmp.target_y = Settings.HEIGHT.toFloat() / 2.0f
                    tmp.freeToPlayOnce = true

                    if (card!!.isButterfly()) {
                        tmp.calculateCardDamage(target)
                    }

                    val monster = if (card.isButterfly()) target else null

                    tmp.purgeOnUse = true
                    AbstractDungeon.actionManager.cardQueue.add(
                            CardQueueItem(tmp, monster, card.energyOnUse)
                    )
                }
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeBaseCost(0)
        }
    }


}