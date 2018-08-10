package yuyuko.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.relics.AbstractRelic
import yuyuko.cards.yuyuko.Soul
import yuyuko.powers.GhostPower


class BlueKimonoPhantom : CustomRelic(
        ID,
        ImageMaster.loadImage(IMAGE_PATH),
        RelicTier.SPECIAL,
        LandingSound.MAGICAL
) {
    override fun makeCopy(): AbstractRelic = BlueKimonoPhantom()

    init {
        this.counter = 1
    }

    companion object {
        @JvmStatic
        val ID = "Blue Kimono (Phantom)"
        val IMAGE_PATH = "images/relics/blueKimono.png"
        val NAME: String
        val DESCRIPTION: String

        init {
            val strings = CardCrawlGame.languagePack.getRelicStrings(ID)
            NAME = strings.NAME
            DESCRIPTION = strings.DESCRIPTIONS[0]
        }
    }

    override fun obtain() {
        if (AbstractDungeon.player.hasRelic(BlueKimono.ID)) {
            instantObtain(
                    AbstractDungeon.player,
                    AbstractDungeon.player.relics.indexOfFirst { it.relicId == BlueKimono.ID },
                    false
            )
        } else {
            super.obtain()
        }

    }

    override fun atBattleStart() {
        this.counter = 1
    }

    override fun onPlayerEndTurn() {
        if (counter == 3) {
            counter = 1
        } else {
            this.counter++
        }
        if (counter == 3) {
            this.flash()
            AbstractDungeon.player.getPower(GhostPower.POWER_ID)?.atEndOfTurn(true)
        }
    }

    override fun atTurnStart() {
        AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInHandAction(Soul(), 1)
        )
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

}