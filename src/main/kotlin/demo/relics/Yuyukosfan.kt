package demo.relics

import basemod.abstracts.CustomRelic
import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.relics.AbstractRelic
import demo.cards.yuyuko.Butterfly
import demo.cards.yuyuko.Sakura
import demo.powers.FanPower


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

    var fanAmount = 5

    init {
        this.counter = fanAmount
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
        val player = AbstractDungeon.player
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        player, player,
                        FanPower(fanAmount),
                        fanAmount
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInDrawPileAction(
                        Sakura(), fanAmount, true, true
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInDrawPileAction(
                        Butterfly(), fanAmount, true, true
                )
        )
    }

    fun updateDescription() {
        this.counter = fanAmount
        this.description = updatedDescription
        initializeTips()
        this.tips.removeAt(0)
        this.tips.add(PowerTip(this.name, this.description))
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0] + fanAmount + DESCRIPTIONS[1] + fanAmount + DESCRIPTIONS[2]
    }

    override fun makeCopy(): AbstractRelic {
        return Yuyukosfan()
    }


}