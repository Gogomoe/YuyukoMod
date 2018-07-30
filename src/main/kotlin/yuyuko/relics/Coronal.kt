package yuyuko.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.helpers.input.InputHelper
import com.megacrit.cardcrawl.powers.ArtifactPower
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower
import com.megacrit.cardcrawl.relics.AbstractRelic


class Coronal : CustomRelic(
        ID,
        ImageMaster.loadImage(IMAGE_PATH),
        RelicTier.STARTER,
        LandingSound.MAGICAL
) {
    override fun makeCopy(): AbstractRelic = Coronal()

    companion object {
        @JvmStatic
        val ID = "Coronal"
        val IMAGE_PATH = "images/relics/coronal.png"
    }

    init {
        this.counter = 0
        updateDescription()
    }

    private var victoryCount = 0

    override fun onVictory() {
        victoryCount++
        if (victoryCount == 5) {
            victoryCount = 0
            this.counter++
            this.flash()
        }
        updateDescription()
    }

    fun onRightClick() {
        if (this.counter <= 0) {
            return
        }
        this.counter--
        val player = AbstractDungeon.player
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        player, player,
                        IntangiblePlayerPower(player, 1),
                        1
                )
        )
        AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                        player, player,
                        ArtifactPower(player, 1),
                        1
                )
        )
        this.flash()
        this.updateDescription()
    }

    fun updateDescription() {
        this.description = updatedDescription
        this.tips.clear()
        this.tips.add(PowerTip(this.name, this.description))
        initializeTips()
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0] + counter + DESCRIPTIONS[1] + (5 - victoryCount) + DESCRIPTIONS[2]
    }

    private var RclickStart = false
    private var Rclick = false

    override fun update() {
        super.update()
        if (!this.isObtained) {
            return
        }

        if (this.RclickStart && InputHelper.justReleasedClickRight) {
            if (this.hb.hovered) {
                this.Rclick = true
            }

            this.RclickStart = false
        }

        if (this.hb != null && this.hb.hovered && InputHelper.justClickedRight) {
            this.RclickStart = true
        }

        if (this.Rclick) {
            this.Rclick = false
            this.onRightClick()
        }

    }


}