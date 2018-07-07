package demo.cards.reimu

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.patches.CardColorEnum
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect

class Strike_Reimu : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        AbstractCard.CardType.ATTACK, CardColorEnum.REIMU_COLOR,
        AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "Strike_Reimu"
        val IMAGE_PATH = "images/reimu/attack/strike.png"
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

    override fun makeCopy(): AbstractCard = Strike_Reimu()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                DamageAction(
                        target,
                        DamageInfo(self, damage, damageTypeForTurn),
                        AttackEffect.SLASH_DIAGONAL
                )
        )
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeDamage(UPGRADE_PLUS_DMG)
        }
    }

}