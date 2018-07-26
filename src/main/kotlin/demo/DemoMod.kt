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
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.localization.CardStrings
import com.megacrit.cardcrawl.localization.CharacterStrings
import com.megacrit.cardcrawl.localization.KeywordStrings
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.localization.RelicStrings
import demo.cards.yuyuko.AllWander
import demo.cards.yuyuko.BecomeGhost
import demo.cards.yuyuko.Bloom
import demo.cards.yuyuko.Bomb
import demo.cards.yuyuko.BuryInTheTemplate
import demo.cards.yuyuko.ButterfliesLoveFlowers
import demo.cards.yuyuko.ButterfliesRainbow
import demo.cards.yuyuko.Butterfly
import demo.cards.yuyuko.ButterflyDeepRooted
import demo.cards.yuyuko.ButterflyDelusion
import demo.cards.yuyuko.ButterflyGhost
import demo.cards.yuyuko.ButterflySwallowtail
import demo.cards.yuyuko.DancingButterflies
import demo.cards.yuyuko.DeathDancing
import demo.cards.yuyuko.DeathlyGround
import demo.cards.yuyuko.DreamySakura
import demo.cards.yuyuko.DyingButterflies
import demo.cards.yuyuko.DyingDream
import demo.cards.yuyuko.Elegance
import demo.cards.yuyuko.EndOfFaramita
import demo.cards.yuyuko.Explore
import demo.cards.yuyuko.ExploreGhostdom
import demo.cards.yuyuko.FantasyButterflies
import demo.cards.yuyuko.FaramitasTemptation
import demo.cards.yuyuko.FinalOfFinal
import demo.cards.yuyuko.FloatOnMoon
import demo.cards.yuyuko.FondlingOfPapilio
import demo.cards.yuyuko.FullInkySakura
import demo.cards.yuyuko.GatherThePhantom
import demo.cards.yuyuko.GatherTheSpring
import demo.cards.yuyuko.GauzySakura
import demo.cards.yuyuko.GhastlyDream
import demo.cards.yuyuko.GhostdomSakura
import demo.cards.yuyuko.Gone
import demo.cards.yuyuko.ImmigrantPhantom
import demo.cards.yuyuko.InfiniteSin
import demo.cards.yuyuko.LivingToDie
import demo.cards.yuyuko.Lunch
import demo.cards.yuyuko.MirrorOfMind
import demo.cards.yuyuko.MonsterCherryTree
import demo.cards.yuyuko.Nihility
import demo.cards.yuyuko.OpenTheFan
import demo.cards.yuyuko.PhantomButterflies
import demo.cards.yuyuko.PhantomGift
import demo.cards.yuyuko.PhantomVillage
import demo.cards.yuyuko.Photo
import demo.cards.yuyuko.RedemptionOfDeath
import demo.cards.yuyuko.RemainHere
import demo.cards.yuyuko.ReverseTheScreen
import demo.cards.yuyuko.ReviveTheButterflies
import demo.cards.yuyuko.Sakura
import demo.cards.yuyuko.SakuraBloom
import demo.cards.yuyuko.SakuraDormancy
import demo.cards.yuyuko.SakuraSeal
import demo.cards.yuyuko.SakuraSuicide
import demo.cards.yuyuko.SakuraWard
import demo.cards.yuyuko.SceneryOfPapilio
import demo.cards.yuyuko.SereneSpring
import demo.cards.yuyuko.ShowyWithering
import demo.cards.yuyuko.Snow
import demo.cards.yuyuko.SnowingSakura
import demo.cards.yuyuko.SongOfPapilio
import demo.cards.yuyuko.SpearOfPapilio
import demo.cards.yuyuko.SteamedPhantom
import demo.cards.yuyuko.Stifle
import demo.cards.yuyuko.Suicide
import demo.cards.yuyuko.SupernaturalNether
import demo.cards.yuyuko.SweetOfPhantom
import demo.cards.yuyuko.TheForgottenWinter
import demo.cards.yuyuko.TheNether
import demo.cards.yuyuko.UnbornLight
import demo.cards.yuyuko.UnfulfillingAttachment
import demo.cards.yuyuko.UnpavedWay
import demo.cards.yuyuko.Unreal
import demo.cards.yuyuko.UnstableWard
import demo.cards.yuyuko.WanderingSoul
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

        //logger.info("creating the color ReimuColor")
        //CharacterColor(CardColorEnum.REIMU_COLOR!!, "reimu", 238f, 48f, 48f).register()

        logger.info("creating the color YuyukoColor")
        CharacterColor(CardColorEnum.YUYUKO_COLOR!!, "yuyuko", 227f, 48f, 255f).register()

    }

    override fun receivePostInitialize() {
        val badgeTexture = ImageMaster.loadImage("images/DemoModBadge.png")
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
        // BaseMod.addCard(Strike_Reimu())

        BaseMod.addCard(FantasyButterflies())
        BaseMod.addCard(DreamySakura())
        BaseMod.addCard(DeathlyGround())
        BaseMod.addCard(FaramitasTemptation())

        BaseMod.addCard(Sakura())
        BaseMod.addCard(SakuraSeal())
        BaseMod.addCard(SakuraSuicide())
        BaseMod.addCard(SakuraDormancy())
        BaseMod.addCard(SakuraBloom())

        BaseMod.addCard(Butterfly())
        BaseMod.addCard(ButterflyGhost())
        BaseMod.addCard(ButterflySwallowtail())
        BaseMod.addCard(ButterflyDeepRooted())
        BaseMod.addCard(ButterflyDelusion())

        BaseMod.addCard(Explore())
        BaseMod.addCard(Photo())


        BaseMod.addCard(GhostdomSakura())
        BaseMod.addCard(GauzySakura())
        BaseMod.addCard(Bloom())
        BaseMod.addCard(WanderingSoul())
        BaseMod.addCard(OpenTheFan())
        BaseMod.addCard(Suicide())
        BaseMod.addCard(MonsterCherryTree())
        BaseMod.addCard(ButterfliesLoveFlowers())
        BaseMod.addCard(SakuraWard())
        BaseMod.addCard(Bomb())
        BaseMod.addCard(Lunch())
        BaseMod.addCard(ShowyWithering())
        BaseMod.addCard(MirrorOfMind())
        BaseMod.addCard(ExploreGhostdom())
        BaseMod.addCard(UnfulfillingAttachment())
        BaseMod.addCard(RemainHere())
        BaseMod.addCard(FullInkySakura())
        BaseMod.addCard(BuryInTheTemplate())
        BaseMod.addCard(SceneryOfPapilio())


        BaseMod.addCard(ReverseTheScreen())
        BaseMod.addCard(UnbornLight())
        BaseMod.addCard(Elegance())
        BaseMod.addCard(EndOfFaramita())
        BaseMod.addCard(Gone())
        BaseMod.addCard(TheForgottenWinter())
        BaseMod.addCard(TheNether())
        BaseMod.addCard(Unreal())
        BaseMod.addCard(Stifle())
        BaseMod.addCard(Snow())
        BaseMod.addCard(Nihility())
        BaseMod.addCard(DeathDancing())
        BaseMod.addCard(PhantomButterflies())
        BaseMod.addCard(SupernaturalNether())
        BaseMod.addCard(SnowingSakura())
        BaseMod.addCard(LivingToDie())


        BaseMod.addCard(DancingButterflies())
        BaseMod.addCard(SpearOfPapilio())
        BaseMod.addCard(SongOfPapilio())
        BaseMod.addCard(FondlingOfPapilio())
        BaseMod.addCard(RedemptionOfDeath())
        BaseMod.addCard(GatherTheSpring())
        BaseMod.addCard(FinalOfFinal())
        BaseMod.addCard(UnpavedWay())
        BaseMod.addCard(SereneSpring())
        BaseMod.addCard(InfiniteSin())
        BaseMod.addCard(ButterfliesRainbow())
        BaseMod.addCard(GhastlyDream())
        BaseMod.addCard(FloatOnMoon())
        BaseMod.addCard(ReviveTheButterflies())


        BaseMod.addCard(AllWander())
        BaseMod.addCard(BecomeGhost())
        BaseMod.addCard(SweetOfPhantom())
        BaseMod.addCard(SteamedPhantom())
        BaseMod.addCard(UnstableWard())
        BaseMod.addCard(ImmigrantPhantom())
        BaseMod.addCard(PhantomVillage())
        BaseMod.addCard(DyingButterflies())
        BaseMod.addCard(PhantomGift())
        BaseMod.addCard(DyingDream())
        BaseMod.addCard(GatherThePhantom())

    }

    override fun receiveEditRelics() {
        BaseMod.addRelicToCustomPool(Yuyukosfan(), CardColorEnum.YUYUKO_COLOR.toString())
    }

    override fun receiveEditKeywords() {
        "Derivate".loadKeywordsString()
        DiaphaneityPower.POWER_ID.loadKeywordsString()
        GhostPower.POWER_ID.loadKeywordsString()
        "Constricted".loadKeywordsString()
        "UpgradeAll".loadKeywordsString()
        "Reset".loadKeywordsString()
        "Discover".loadKeywordsString()
        "Hide".loadKeywordsString()
        "Retrieval".loadKeywordsString()
        "Revive".loadKeywordsString()

        "Sakura(Seal)".loadKeywordsString()
        "Sakura(Suicide)".loadKeywordsString()
        "Sakura(Dormancy)".loadKeywordsString()
        "Sakura(Bloom)".loadKeywordsString()
        "Butterfly(Ghost)".loadKeywordsString()
        "Butterfly(Swallowtail)".loadKeywordsString()
        "Butterfly(Deep Rooted)".loadKeywordsString()
        "Butterfly(Delusion)".loadKeywordsString()
    }

    private fun String.loadKeywordsString() {
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
