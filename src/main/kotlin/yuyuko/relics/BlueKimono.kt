package yuyuko.relics

import basemod.abstracts.CustomCard
import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.SPECIAL
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.NONE
import com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.relics.AbstractRelic
import yuyuko.patches.CardColorEnum


class BlueKimono : CustomRelic(
        ID,
        ImageMaster.loadImage(IMAGE_PATH),
        RelicTier.SPECIAL,
        LandingSound.MAGICAL
) {
    override fun makeCopy(): AbstractRelic = BlueKimono()

    companion object {
        @JvmStatic
        val ID = "Blue Kimono"
        val IMAGE_PATH = "images/relics/blueKimono.png"
    }

    private var selected = false

    override fun onEquip() {
        this.selected = false
        AbstractDungeon.gridSelectScreen.open(getGroup(), 1, DESCRIPTIONS[1], false, false, false, false)
    }

    private fun getGroup(): CardGroup =
            CardGroup(UNSPECIFIED).apply {
                addToBottom(Sakura())
                addToBottom(Butterfly())
                addToBottom(Phantom())
                addToBottom(FullMoon())
            }

    override fun update() {
        super.update()
        if (!this.selected && AbstractDungeon.gridSelectScreen.selectedCards.size == 1) {
            this.selected = true
            val c = (AbstractDungeon.gridSelectScreen.selectedCards[0]) as ChooseCard
            c.relic.obtain()
            AbstractDungeon.gridSelectScreen.selectedCards.clear()
        }
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

    private class Sakura : ChooseCard(
            BlueKimonoSakura.ID,
            BlueKimonoSakura.NAME,
            "images/yuyuko/cards/sakura.png",
            BlueKimonoSakura.DESCRIPTION,
            BlueKimonoSakura()
    )

    private class Butterfly : ChooseCard(
            BlueKimonoButterfly.ID,
            BlueKimonoButterfly.NAME,
            "images/yuyuko/cards/butterfly.png",
            BlueKimonoButterfly.DESCRIPTION,
            BlueKimonoButterfly()
    )

    private class Phantom : ChooseCard(
            BlueKimonoPhantom.ID,
            BlueKimonoPhantom.NAME,
            "images/yuyuko/cards/phantom.png",
            BlueKimonoPhantom.DESCRIPTION,
            BlueKimonoPhantom()
    )

    private class FullMoon : ChooseCard(
            BlueKimonoFullMoon.ID,
            BlueKimonoFullMoon.NAME,
            "images/yuyuko/cards/moon.png",
            BlueKimonoFullMoon.DESCRIPTION,
            BlueKimonoFullMoon()
    )

    abstract class ChooseCard(ID: String, NAME: String, img: String, DESCRIPTION: String, val relic: AbstractRelic) : CustomCard(
            ID, NAME, img, -2, DESCRIPTION, STATUS, CardColorEnum.YUYUKO_COLOR, SPECIAL, NONE) {

        override fun makeCopy(): AbstractCard? = null

        override fun use(p0: AbstractPlayer?, p1: AbstractMonster?) {}

        override fun upgrade() {}
    }

}