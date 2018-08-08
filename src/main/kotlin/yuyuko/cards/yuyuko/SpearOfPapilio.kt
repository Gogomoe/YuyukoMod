package yuyuko.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import yuyuko.cards.isButterfly
import yuyuko.event.EventDispenser
import yuyuko.event.UpgradeAllEvent
import yuyuko.patches.CardColorEnum

class SpearOfPapilio : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.ATTACK, CardColorEnum.YUYUKO_COLOR,
        CardRarity.COMMON, CardTarget.ENEMY
) {
    companion object {
        @JvmStatic
        val ID = "Spear of Papilio"
        val IMAGE_PATH = "images/yuyuko/cards/attack4.png"
        val COST = 2
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    init {
        this.baseDamage = 5
        this.baseMagicNumber = 2
        this.magicNumber = 2
    }

    override fun makeCopy(): AbstractCard = SpearOfPapilio()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
                DamageAction(
                        target,
                        DamageInfo(self, this.damage, HP_LOSS),
                        BLUNT_HEAVY
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInDrawPileAction(
                        ButterflyDeepRooted(),
                        this.magicNumber,
                        true,
                        true
                )
        )
        EventDispenser.emit(UpgradeAllEvent(AbstractCard::isButterfly))
    }

    override fun upgrade() {
        if (!this.upgraded) {
            this.upgradeName()
            this.upgradeDamage(4)
            this.upgradeMagicNumber(1)
        }
    }

}