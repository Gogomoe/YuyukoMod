package demo.cards.yuyuko

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import demo.actions.UpgradeAllAction
import demo.patches.CardColorEnum
import demo.powers.DiaphaneityPower
import demo.relics.Yuyukosfan

class Elegance : CustomCard(
        ID, NAME, IMAGE_PATH, COST, DESCRIPTION,
        CardType.SKILL, CardColorEnum.YUYUKO_COLOR,
        CardRarity.COMMON, CardTarget.SELF
) {
    companion object {
        @JvmStatic
        val ID = "Elegance"
        val IMAGE_PATH = "images/yuyuko/cards/skill3.png"
        val COST = 1
        private val CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = CARD_STRINGS.NAME!!
        val DESCRIPTION = CARD_STRINGS.DESCRIPTION!!
    }

    override fun makeCopy(): AbstractCard = Elegance()

    override fun use(self: AbstractPlayer?, target: AbstractMonster?) {
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(
                    MakeTempCardInDrawPileAction(
                            Sakura(), 1, true, true
                    )
            )
        }
        AbstractDungeon.actionManager.addToBottom(
                UpgradeAllAction(Sakura.ID)
        )

        val amount = (self!!.getRelic(Yuyukosfan.ID) as Yuyukosfan?)?.sakuraAmount ?: return
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        self, self,
                        DiaphaneityPower(self, amount),
                        amount
                )
        )
    }

    override fun upgrade() {
        upgradeName()
    }

}