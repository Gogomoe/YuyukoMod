package demo

import basemod.BaseMod
import basemod.ModLabel
import basemod.ModPanel
import basemod.interfaces.EditCardsSubscriber
import basemod.interfaces.EditCharactersSubscriber
import basemod.interfaces.EditStringsSubscriber
import basemod.interfaces.PostInitializeSubscriber
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.localization.CardStrings
import com.megacrit.cardcrawl.localization.CharacterStrings
import demo.cards.reimu.Strike_Reimu
import demo.characters.Reimu
import demo.patches.CardColorEnum
import demo.patches.PlayerClassEnum
import org.apache.logging.log4j.LogManager
import java.nio.charset.StandardCharsets


@SpireInitializer
class DemoMod : PostInitializeSubscriber, EditCardsSubscriber, EditCharactersSubscriber, EditStringsSubscriber {

    companion object {
        const val MODNAME = "DemoMod"
        const val AUTHOR = "Gogo"
        const val DESCRIPTION = "DemoMod 0.0.1"

        val logger = LogManager.getLogger("DemoMod")

        @JvmStatic
        fun initialize() {
            logger.info("========================= DEMOMOD INIT =========================")

            val mod = DemoMod()

            logger.info("======================= DEMOMOD INIT DONE ======================")
        }
    }

    init {

        logger.info("subscribing to PostInitializeSubscriber")
        BaseMod.subscribe(this, PostInitializeSubscriber::class.java)

        logger.info("subscribing to EditCharactersSubscriber")
        BaseMod.subscribe(this, EditCharactersSubscriber::class.java)

        logger.info("subscribing to EditCardsSubscriber")
        BaseMod.subscribe(this, EditCardsSubscriber::class.java)

        logger.info("subscribing to EditStringsSubscriber")
        BaseMod.subscribe(this, EditStringsSubscriber::class.java)

        logger.info("creating the color " + CardColorEnum.REIMU_COLOR.toString())
        CharacterColor(CardColorEnum.REIMU_COLOR!!, "reimu", 238f, 48f, 48f).register()


    }

    override fun receivePostInitialize() {
        val badgeTexture = Texture("images/DemoModBadge.png")
        val panel = ModPanel()
        val label = ModLabel("This mod does not have any settings.",
                400.0f, 700.0f, panel) { }
        panel.addUIElement(label)
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, panel)

    }

    override fun receiveEditCharacters() {
        logger.info("add Reimu")
        BaseMod.addCharacter(
                Reimu::class.java,
                Reimu.NAME,
                "Reimu class string",
                CardColorEnum.REIMU_COLOR.toString(),
                Reimu.NAME,
                "images/charSelect/reimuButton.png",
                "images/charSelect/reimuPortrait.jpg",
                PlayerClassEnum.REIMU.toString()
        )
    }

    override fun receiveEditCards() {
        BaseMod.addCard(Strike_Reimu())
    }

    override fun receiveEditStrings() {
        val cardStrings = Gdx.files.internal("localization/demomod-cards.json")
                .readString(StandardCharsets.UTF_8.toString())
        BaseMod.loadCustomStrings(CardStrings::class.java, cardStrings)

        val characterStrings = Gdx.files.internal("localization/demomod-characters.json")
                .readString(StandardCharsets.UTF_8.toString())
        BaseMod.loadCustomStrings(CharacterStrings::class.java, characterStrings)
    }


}
