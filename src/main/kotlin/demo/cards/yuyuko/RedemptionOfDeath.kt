package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.actions.ReviveAction
import demo.patches.CardColorEnum

class RedemptionOfDeath : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.ATTACK, CardColorEnum.YUYUKO_COLOR,
        CardRarity.COMMON, CardTarget.ALL_ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "Redemption of Death"
        val IMAGE_PATH = "images/yuyuko/cards/attack4.png"
        val COST = 1
        val ATTACK_DMG = 6
        val UPGRADE_PLUS_DMG = 3
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseDamage = ATTACK_DMG
    }

    override fun makeCopy(): AbstractCard = RedemptionOfDeath()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                DamageAllEnemiesAction(
                        self,
                        this.multiDamage,
                        DamageType.NORMAL,
                        AttackEffect.SLASH_DIAGONAL
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                ReviveAction(Butterfly.ID)
        )
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeDamage(UPGRADE_PLUS_DMG)
        }
    }

}