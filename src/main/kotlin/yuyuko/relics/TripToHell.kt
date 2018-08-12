package yuyuko.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.relics.AbstractRelic

class TripToHell : CustomRelic(
        ID,
        ImageMaster.loadImage(IMAGE_PATH),
        RelicTier.RARE,
        LandingSound.MAGICAL
) {
    override fun makeCopy(): AbstractRelic = TripToHell()

    companion object {
        @JvmStatic
        val ID = "Trip to Hell"
        val IMAGE_PATH = "images/relics/tripToHell.png"
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

}