package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HEAVY
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.actions.HideAction
import demo.cards.HideCards
import demo.patches.CardColorEnum
import kotlin.math.abs

class FinalOfFinal : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.ATTACK, CardColorEnum.YUYUKO_COLOR,
        CardRarity.COMMON, CardTarget.ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "Final of Final"
        val IMAGE_PATH = "images/yuyuko/cards/attack4.png"
        val COST = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
        val UPGRADE_DESCRIPTION = CARD_STRINGS.UPGRADE_DESCRIPTION!!
    }


    override fun makeCopy(): AbstractCard = FinalOfFinal()

    override fun calculateCardDamage(mo: AbstractMonster?) {
        val player = AbstractDungeon.player
        this.baseDamage = abs(player.drawPile.size() - player.discardPile.size())
        super.calculateCardDamage(mo)
    }

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                DamageAction(
                        target,
                        DamageInfo(self, this.damage, NORMAL),
                        SLASH_HEAVY
                )
        )
    }

    override fun triggerWhenDrawn() {
        if (!upgraded) {
            return
        }
        if (HideCards.shouldHide()) {
            AbstractDungeon.actionManager.addToTop(
                    HideAction(this)
            )
        }
    }


    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.rawDescription = UPGRADE_DESCRIPTION
            this.initializeDescription()
        }
    }

}