package yuyuko

import basemod.BaseMod
import basemod.ModLabel
import basemod.ModPanel
import basemod.interfaces.*
import com.badlogic.gdx.Gdx
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.core.Settings.GameLanguage.ZHS
import com.megacrit.cardcrawl.core.Settings.GameLanguage.ZHT
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.localization.*
import com.megacrit.cardcrawl.rooms.AbstractRoom
import com.megacrit.cardcrawl.unlock.UnlockTracker
import org.apache.logging.log4j.LogManager
import yuyuko.actions.HideAction
import yuyuko.cards.HideCards
import yuyuko.cards.isHide
import yuyuko.cards.yuyuko.*
import yuyuko.cards.yuyuko.TrapLamp
import yuyuko.characters.Yuyuko
import yuyuko.event.EventDispenser
import yuyuko.event.OnDrawEvent
import yuyuko.patches.CardColorEnum
import yuyuko.patches.PlayerClassEnum
import yuyuko.powers.*
import yuyuko.relics.*
import java.nio.charset.StandardCharsets


@SpireInitializer
class YuyukoMod : PostInitializeSubscriber, EditCardsSubscriber, EditCharactersSubscriber,
        EditStringsSubscriber, EditKeywordsSubscriber, EditRelicsSubscriber, OnStartBattleSubscriber {

    companion object {
        const val MODNAME = "YuyukoMod"
        const val AUTHOR = "Gogo"
        const val DESCRIPTION = "YuyukoMod 0.0.1"

        val logger = LogManager.getLogger("YuyukoMod")

        @JvmStatic
        fun initialize() {
            logger.info("========================= YUYUKOMOD INIT =========================")

            YuyukoMod()

            logger.info("======================= YUYUKOMOD INIT DONE ======================")
        }
    }

    init {

        logger.info("subscribing to subscriber")
        BaseMod.subscribe(this)

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
                CardColorEnum.YUYUKO_COLOR,
                Yuyuko.NAME,
                "images/charSelect/yuyukoButton.png",
                "images/charSelect/yuyukoPortrait.png",
                PlayerClassEnum.YUYOKO
        )
    }

    override fun receiveEditCards() {

        addCard(FantasyButterflies())
        addCard(DreamySakura())
        addCard(DeathlyGround())
        addCard(FaramitasTemptation())

        addCard(Sakura())
        addCard(SakuraSeal())
        addCard(SakuraSuicide())
        addCard(SakuraDormancy())
        addCard(SakuraBloom())

        addCard(Butterfly())
        addCard(ButterflyGhost())
        addCard(ButterflySwallowtail())
        addCard(ButterflyDeepRooted())
        addCard(ButterflyDelusion())

        addCard(Soul())
        addCard(Explore())
        addCard(Photo())


        addCard(GhostdomSakura())
        addCard(GauzySakura())
        addCard(Bloom())
        addCard(WanderingSoul())
        addCard(OpenTheFan())
        addCard(LingerOverFlower())
        addCard(CerasusSubhirtella())
        addCard(Suicide())
        addCard(MonsterCherryTree())
        addCard(UnknownPetal())
        addCard(SakuraWard())
        addCard(Bomb())
        addCard(Lunch())
        addCard(ChaseTheSukhavati())
        addCard(Childlike())
        addCard(ShowyWithering())
        addCard(MiniGhostdom())
        addCard(PostponeBloom())
        addCard(FullMoon())
        addCard(MirrorOfMind())
        addCard(ExploreGhostdom())
        addCard(UnfulfillingAttachment())
        addCard(RemainHere())
        addCard(FullInkySakura())
        addCard(BuryInTheTemplate())
        addCard(SceneryOfPapilio())
        addCard(TheInkySakura())
        addCard(ReunionAfterDeath())


        addCard(ReverseTheScreen())
        addCard(UnbornLight())
        addCard(Elegance())
        addCard(EndOfFaramita())
        addCard(Gone())
        addCard(TheForgottenWinter())
        addCard(TheNether())
        addCard(Unreal())
        addCard(Stifle())
        addCard(Snow())
        addCard(TripleSnow())
        addCard(Nihility())
        addCard(DeathDancing())
        addCard(PhantomButterflies())
        addCard(SupernaturalNether())
        addCard(PastGraveyard())
        addCard(SnowingSakura())
        addCard(LivingToDie())


        addCard(DancingButterflies())
        addCard(SpearOfPapilio())
        addCard(SongOfPapilio())
        addCard(FondlingOfPapilio())
        addCard(RedemptionOfDeath())
        addCard(GatherTheSpring())
        addCard(FinalOfFinal())
        addCard(UnpavedWay())
        addCard(SereneSpring())
        addCard(InfiniteSin())
        addCard(ButterfliesRainbow())
        addCard(ButterfliesInDream())
        addCard(DancingSoul())
        addCard(GhastlyDream())
        addCard(InfiniteReviving())
        addCard(FloatOnMoon())
        addCard(ReviveTheButterflies())


        addCard(AllWander())
        addCard(BecomePhantom())
        addCard(SweetOfPhantom())
        addCard(SteamedPhantom())
        addCard(DarkWind())
        addCard(UnstableWard())
        addCard(CityOfDeath())
        addCard(ImmigrantPhantom())
        addCard(PhantomVillage())
        addCard(DyingButterflies())
        addCard(PhantomGift())
        addCard(TrapLamp())
        addCard(DyingSakura())
        addCard(DyingDream())
        addCard(GhostSpot())
        addCard(TicketToHeaven())
        addCard(TheWayToDeath())

        addPowers()
    }

    private fun addCard(card: AbstractCard) {
        BaseMod.addCard(card)
        UnlockTracker.unlockCard(card.cardID)

    }

    private fun addPowers() {
        BaseMod.addPower(BecomePhantomPower::class.java, BecomePhantomPower.POWER_ID)
        BaseMod.addPower(DiaphaneityPower::class.java, DiaphaneityPower.POWER_ID)
        BaseMod.addPower(DizzinessPower::class.java, DizzinessPower.POWER_ID)
        BaseMod.addPower(FanPower::class.java, FanPower.POWER_ID)
        BaseMod.addPower(FloatOnMoonPower::class.java, FloatOnMoonPower.POWER_ID)
        BaseMod.addPower(FullInkySakuraPower::class.java, FullInkySakuraPower.POWER_ID)
        BaseMod.addPower(GhastlyDreamPower::class.java, GhastlyDreamPower.POWER_ID)
        BaseMod.addPower(GhostPower::class.java, GhostPower.POWER_ID)
        BaseMod.addPower(LivingToDiePower::class.java, LivingToDiePower.POWER_ID)
        BaseMod.addPower(NihilityPower::class.java, NihilityPower.POWER_ID)
        BaseMod.addPower(PostponeBloomPower::class.java, PostponeBloomPower.POWER_ID)
        BaseMod.addPower(RemainHerePower::class.java, RemainHerePower.POWER_ID)
        BaseMod.addPower(ReviveTheButterfliesPower::class.java, ReviveTheButterfliesPower.POWER_ID)
        BaseMod.addPower(ShowyWitheringPower::class.java, ShowyWitheringPower.POWER_ID)
        BaseMod.addPower(SupernaturalNetherPower::class.java, SupernaturalNetherPower.POWER_ID)
        BaseMod.addPower(TheForgottenWinterPower::class.java, TheForgottenWinterPower.POWER_ID)
        BaseMod.addPower(TripleSnowPower::class.java, TripleSnowPower.POWER_ID)
        BaseMod.addPower(TheWayToDeathPower::class.java, TheWayToDeathPower.POWER_ID)
    }

    override fun receiveEditRelics() {
        BaseMod.addRelicToCustomPool(Yuyukosfan(), CardColorEnum.YUYUKO_COLOR)
        BaseMod.addRelicToCustomPool(Coronal(), CardColorEnum.YUYUKO_COLOR)
        BaseMod.addRelicToCustomPool(GhostLamp(), CardColorEnum.YUYUKO_COLOR)
        BaseMod.addRelicToCustomPool(yuyuko.relics.TrapLamp(), CardColorEnum.YUYUKO_COLOR)
        BaseMod.addRelicToCustomPool(Obi(), CardColorEnum.YUYUKO_COLOR)
        BaseMod.addRelicToCustomPool(TripToHell(), CardColorEnum.YUYUKO_COLOR)

        BaseMod.addRelicToCustomPool(BlueKimono(), CardColorEnum.YUYUKO_COLOR)
        BaseMod.addRelicToCustomPool(BlueKimonoSakura(), CardColorEnum.YUYUKO_COLOR)
        BaseMod.addRelicToCustomPool(BlueKimonoButterfly(), CardColorEnum.YUYUKO_COLOR)
        BaseMod.addRelicToCustomPool(BlueKimonoPhantom(), CardColorEnum.YUYUKO_COLOR)
        BaseMod.addRelicToCustomPool(BlueKimonoFullMoon(), CardColorEnum.YUYUKO_COLOR)
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
        "Retrieve".loadKeywordsString()
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

        val lang = when (Settings.language) {
            ZHS -> "zhs"
            ZHT -> "zhs"
            else -> "eng"
        }

        val cardStrings = Gdx.files.internal("localization/yuyukomod-$lang-cards.json")
                .readString(StandardCharsets.UTF_8.toString())
        BaseMod.loadCustomStrings(CardStrings::class.java, cardStrings)

        val characterStrings = Gdx.files.internal("localization/yuyukomod-$lang-characters.json")
                .readString(StandardCharsets.UTF_8.toString())
        BaseMod.loadCustomStrings(CharacterStrings::class.java, characterStrings)

        val powerStrings = Gdx.files.internal("localization/yuyukomod-$lang-powers.json")
                .readString(StandardCharsets.UTF_8.toString())
        BaseMod.loadCustomStrings(PowerStrings::class.java, powerStrings)

        val keywordStrings = Gdx.files.internal("localization/yuyukomod-$lang-keywords.json")
                .readString(StandardCharsets.UTF_8.toString())
        BaseMod.loadCustomStrings(KeywordStrings::class.java, keywordStrings)

        val relicsStrings = Gdx.files.internal("localization/yuyukomod-$lang-relics.json")
                .readString(StandardCharsets.UTF_8.toString())
        BaseMod.loadCustomStrings(RelicStrings::class.java, relicsStrings)

    }

    override fun receiveOnBattleStart(p0: AbstractRoom?) {
        EventDispenser.clear()
        EventDispenser.subscribe<OnDrawEvent>(OnDrawEvent.ID) {
            if (card.isHide() && HideCards.shouldHide()) {
                AbstractDungeon.actionManager.addToTop(
                        HideAction(card)
                )
            }
        }
    }

}
