package yuyuko

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
import org.apache.logging.log4j.LogManager
import yuyuko.cards.yuyuko.AllWander
import yuyuko.cards.yuyuko.BecomeGhost
import yuyuko.cards.yuyuko.Bloom
import yuyuko.cards.yuyuko.Bomb
import yuyuko.cards.yuyuko.BuryInTheTemplate
import yuyuko.cards.yuyuko.ButterfliesLoveFlowers
import yuyuko.cards.yuyuko.ButterfliesRainbow
import yuyuko.cards.yuyuko.Butterfly
import yuyuko.cards.yuyuko.ButterflyDeepRooted
import yuyuko.cards.yuyuko.ButterflyDelusion
import yuyuko.cards.yuyuko.ButterflyGhost
import yuyuko.cards.yuyuko.ButterflySwallowtail
import yuyuko.cards.yuyuko.DancingButterflies
import yuyuko.cards.yuyuko.DeathDancing
import yuyuko.cards.yuyuko.DeathlyGround
import yuyuko.cards.yuyuko.DreamySakura
import yuyuko.cards.yuyuko.DyingButterflies
import yuyuko.cards.yuyuko.DyingDream
import yuyuko.cards.yuyuko.Elegance
import yuyuko.cards.yuyuko.EndOfFaramita
import yuyuko.cards.yuyuko.Explore
import yuyuko.cards.yuyuko.ExploreGhostdom
import yuyuko.cards.yuyuko.FantasyButterflies
import yuyuko.cards.yuyuko.FaramitasTemptation
import yuyuko.cards.yuyuko.FinalOfFinal
import yuyuko.cards.yuyuko.FloatOnMoon
import yuyuko.cards.yuyuko.FondlingOfPapilio
import yuyuko.cards.yuyuko.FullInkySakura
import yuyuko.cards.yuyuko.GatherThePhantom
import yuyuko.cards.yuyuko.GatherTheSpring
import yuyuko.cards.yuyuko.GauzySakura
import yuyuko.cards.yuyuko.GhastlyDream
import yuyuko.cards.yuyuko.GhostdomSakura
import yuyuko.cards.yuyuko.Gone
import yuyuko.cards.yuyuko.ImmigrantPhantom
import yuyuko.cards.yuyuko.InfiniteSin
import yuyuko.cards.yuyuko.LivingToDie
import yuyuko.cards.yuyuko.Lunch
import yuyuko.cards.yuyuko.MirrorOfMind
import yuyuko.cards.yuyuko.MonsterCherryTree
import yuyuko.cards.yuyuko.Nihility
import yuyuko.cards.yuyuko.OpenTheFan
import yuyuko.cards.yuyuko.PhantomButterflies
import yuyuko.cards.yuyuko.PhantomGift
import yuyuko.cards.yuyuko.PhantomVillage
import yuyuko.cards.yuyuko.Photo
import yuyuko.cards.yuyuko.PostponeBloom
import yuyuko.cards.yuyuko.RedemptionOfDeath
import yuyuko.cards.yuyuko.RemainHere
import yuyuko.cards.yuyuko.ReverseTheScreen
import yuyuko.cards.yuyuko.ReviveTheButterflies
import yuyuko.cards.yuyuko.Sakura
import yuyuko.cards.yuyuko.SakuraBloom
import yuyuko.cards.yuyuko.SakuraDormancy
import yuyuko.cards.yuyuko.SakuraSeal
import yuyuko.cards.yuyuko.SakuraSuicide
import yuyuko.cards.yuyuko.SakuraWard
import yuyuko.cards.yuyuko.SceneryOfPapilio
import yuyuko.cards.yuyuko.SereneSpring
import yuyuko.cards.yuyuko.ShowyWithering
import yuyuko.cards.yuyuko.Snow
import yuyuko.cards.yuyuko.SnowingSakura
import yuyuko.cards.yuyuko.SongOfPapilio
import yuyuko.cards.yuyuko.SpearOfPapilio
import yuyuko.cards.yuyuko.SteamedPhantom
import yuyuko.cards.yuyuko.Stifle
import yuyuko.cards.yuyuko.Suicide
import yuyuko.cards.yuyuko.SupernaturalNether
import yuyuko.cards.yuyuko.SweetOfPhantom
import yuyuko.cards.yuyuko.TheForgottenWinter
import yuyuko.cards.yuyuko.TheNether
import yuyuko.cards.yuyuko.TripleSnow
import yuyuko.cards.yuyuko.UnbornLight
import yuyuko.cards.yuyuko.UnfulfillingAttachment
import yuyuko.cards.yuyuko.UnpavedWay
import yuyuko.cards.yuyuko.Unreal
import yuyuko.cards.yuyuko.UnstableWard
import yuyuko.cards.yuyuko.WanderingSoul
import yuyuko.characters.Yuyuko
import yuyuko.patches.CardColorEnum
import yuyuko.patches.PlayerClassEnum
import yuyuko.powers.DiaphaneityPower
import yuyuko.powers.GhostPower
import yuyuko.relics.Yuyukosfan
import java.nio.charset.StandardCharsets


@SpireInitializer
class YuyukoMod : PostInitializeSubscriber, EditCardsSubscriber, EditCharactersSubscriber,
        EditStringsSubscriber, EditKeywordsSubscriber, EditRelicsSubscriber {

    companion object {
        const val MODNAME = "YuyukoMod"
        const val AUTHOR = "Gogo"
        const val DESCRIPTION = "YuyukoMod 0.0.1"

        val logger = LogManager.getLogger("YuyukoMod")

        @JvmStatic
        fun initialize() {
            logger.info("========================= DEMOMOD INIT =========================")

            YuyukoMod()

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
        logger.info("add Yuyuko")

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
        BaseMod.addCard(PostponeBloom())


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
        BaseMod.addCard(TripleSnow())
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
        "Derivate Sakura".loadKeywordsString()
        "Derivate Butterfly".loadKeywordsString()
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
