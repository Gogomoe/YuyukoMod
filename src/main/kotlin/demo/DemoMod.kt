package demo

import basemod.BaseMod
import basemod.ModLabel
import basemod.ModPanel
import basemod.interfaces.EditCardsSubscriber
import basemod.interfaces.EditCharactersSubscriber
import basemod.interfaces.EditKeywordsSubscriber
import basemod.interfaces.EditRelicsSubscriber
import basemod.interfaces.EditStringsSubscriber
import basemod.interfaces.PostInitializeSubscriber
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.localization.CardStrings
import com.megacrit.cardcrawl.localization.CharacterStrings
import com.megacrit.cardcrawl.localization.KeywordStrings
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.localization.RelicStrings
import demo.cards.reimu.Strike_Reimu
import demo.cards.yuyuko.Butterfly
import demo.cards.yuyuko.DeathlyGround
import demo.cards.yuyuko.DreamySakura
import demo.cards.yuyuko.FantasyButterflies
import demo.cards.yuyuko.FaramitasTemptation
import demo.cards.yuyuko.Sakura
import demo.characters.Yuyuko
import demo.patches.CardColorEnum
import demo.patches.PlayerClassEnum
import demo.powers.DiaphaneityPower
import demo.powers.GhostPower
import demo.relics.Yuyukosfan
import org.apache.logging.log4j.LogManager
import java.nio.charset.StandardCharsets


@SpireInitializer
class DemoMod : PostInitializeSubscriber, EditCardsSubscriber, EditCharactersSubscriber,
        EditStringsSubscriber, EditKeywordsSubscriber, EditRelicsSubscriber {

    companion object {
        const val MODNAME = "DemoMod"
        const val AUTHOR = "Gogo"
        const val DESCRIPTION = "DemoMod 0.0.1"

        val logger = LogManager.getLogger("DemoMod")

        @JvmStatic
        fun initialize() {
            logger.info("========================= DEMOMOD INIT =========================")

            DemoMod()

            logger.info("======================= DEMOMOD INIT DONE ======================")
        }
    }

    init {

        logger.info("subscribing to subscriber")
        BaseMod.subscribe(this)

        logger.info("creating the color ReimuColor")
        CharacterColor(CardColorEnum.REIMU_COLOR!!, "reimu", 238f, 48f, 48f).register()

        logger.info("creating the color YuyukoColor")
        CharacterColor(CardColorEnum.YUYUKO_COLOR!!, "yuyuko", 227f, 48f, 255f).register()

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
//        BaseMod.addCharacter(
//                Reimu::class.java,
//                Reimu.NAME,
//                "Reimu class string",
//                CardColorEnum.REIMU_COLOR.toString(),
//                Reimu.NAME,
//                "images/charSelect/reimuButton.png",
//                "images/charSelect/reimuPortrait.jpg",
//                PlayerClassEnum.REIMU.toString()
//        )

        BaseMod.addCharacter(
                Yuyuko::class.java,
                Yuyuko.NAME,
                "Yuyuko class string",
                CardColorEnum.YUYUKO_COLOR.toString(),
                Yuyuko.NAME,
                "images/charSelect/yuyukoButton.png",
                "images/charSelect/yuyukoPortrait.png",
                PlayerClassEnum.YUYOKO.toString()
        )
    }

    override fun receiveEditCards() {
        BaseMod.addCard(Strike_Reimu())

        BaseMod.addCard(FantasyButterflies())
        BaseMod.addCard(DreamySakura())
        BaseMod.addCard(DeathlyGround())
        BaseMod.addCard(FaramitasTemptation())

        BaseMod.addCard(Sakura())
        BaseMod.addCard(Butterfly())
    }

    override fun receiveEditRelics() {
        BaseMod.addRelicToCustomPool(Yuyukosfan(), CardColorEnum.YUYUKO_COLOR.toString())
    }

    override fun receiveEditKeywords() {
        DiaphaneityPower.POWER_ID.loadKerwordsString()
        GhostPower.POWER_ID.loadKerwordsString()
        "UpgradeAll".loadKerwordsString()
        "Reset".loadKerwordsString()
    }

    private fun String.loadKerwordsString() {
        val strings = CardCrawlGame.languagePack.getKeywordString(this)
        BaseMod.addKeyword(strings.TEXT.copyOfRange(0, strings.TEXT.size - 1), strings.TEXT.last())
    }

    override fun receiveEditStrings() {
        val cardStrings = Gdx.files.internal("localization/demomod-cards.json")
                .readString(StandardCharsets.UTF_8.toString())
        BaseMod.loadCustomStrings(CardStrings::class.java, cardStrings)

        val characterStrings = Gdx.files.internal("localization/demomod-characters.json")
                .readString(StandardCharsets.UTF_8.toString())
        BaseMod.loadCustomStrings(CharacterStrings::class.java, characterStrings)

        val powerStrings = Gdx.files.internal("localization/demomod-powers.json")
                .readString(StandardCharsets.UTF_8.toString())
        BaseMod.loadCustomStrings(PowerStrings::class.java, powerStrings)

        val keywordStrings = Gdx.files.internal("localization/demomod-keywords.json")
                .readString(StandardCharsets.UTF_8.toString())
        BaseMod.loadCustomStrings(KeywordStrings::class.java, keywordStrings)

        val relicsStrings = Gdx.files.internal("localization/demomod-relics.json")
                .readString(StandardCharsets.UTF_8.toString())
        BaseMod.loadCustomStrings(RelicStrings::class.java, relicsStrings)


    }


}
