package demo

import basemod.BaseMod
import basemod.ModLabel
import basemod.ModPanel
import basemod.interfaces.PostInitializeSubscriber
import com.badlogic.gdx.graphics.Texture
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import demo.patches.CardColorEnum
import demo.reimu.Reimu
import org.apache.logging.log4j.LogManager


@SpireInitializer
class DemoMod : PostInitializeSubscriber {

    companion object {
        @JvmField
        val MODNAME = "DemoMod"
        @JvmField
        val AUTHOR = "Gogo"
        @JvmField
        val DESCRIPTION = "DemoMod 0.0.1"

        val logger = LogManager.getLogger("DemoMod")

        @JvmStatic
        fun initialize() {
            val mod = DemoMod()
        }
    }

    init {
        logger.info("========================= DEMOMOD INIT =========================")

        logger.info("subscribe PostInitializeSubscriber")
        BaseMod.subscribe(this, PostInitializeSubscriber::class.java)

        logger.info("creating the color " + CardColorEnum.REIMU_COLOR.toString())
        Reimu.color.register()

        logger.info("======================= DEMOMOD INIT DONE ======================")

    }

    override fun receivePostInitialize() {
        val badgeTexture = Texture("img/DemoModBadge.png")
        val panel = ModPanel()
        val label = ModLabel("This mod does not have any settings.",
                400.0f, 700.0f, panel, { it -> })
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, panel)

    }


}
