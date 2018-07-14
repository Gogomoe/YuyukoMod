package demo.relics

import basemod.abstracts.CustomRelic
import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.relics.AbstractRelic
import demo.cards.yuyuko.Butterfly
import demo.cards.yuyuko.Sakura


class Yuyukosfan : CustomRelic(
        ID,
        Texture(IMAGE_PATH),
        RelicTier.STARTER,
        LandingSound.MAGICAL
) {
    companion object {
        @JvmStatic
        val ID = "Yuyuko's fan"
        val IMAGE_PATH = "images/relics/relic.png"
    }

    var sakuraAmount = 5
    var butterflyAmount = 5

    init {
        updateDescription()
    }

    private var lostBlock = 0

    override fun atTurnStart() {
        lostBlock = AbstractDungeon.player.currentBlock
    }

    override fun atTurnStartPostDraw() {
        AbstractDungeon.player.currentBlock = lostBlock
    }

    override fun atBattleStartPreDraw() {
        AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInDrawPileAction(
                        Sakura(), sakuraAmount, true, true
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInDrawPileAction(
                        Butterfly(), butterflyAmount, true, true
                )
        )
    }

    fun updateDescription() {
        this.description = updatedDescription
        initializeTips()
        this.tips.removeAt(0)
        this.tips.add(PowerTip(this.name, this.description))
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0] + sakuraAmount + DESCRIPTIONS[1] + butterflyAmount + DESCRIPTIONS[2]
    }

    override fun makeCopy(): AbstractRelic {
        return Yuyukosfan()
    }


}