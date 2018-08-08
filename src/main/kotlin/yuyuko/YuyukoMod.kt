package yuyuko

import basemod.BaseMod
import basemod.ModLabel
import basemod.ModPanel
import basemod.interfaces.EditCardsSubscriber
import basemod.interfaces.EditCharactersSubscriber
import basemod.interfaces.EditKeywordsSubscriber
import basemod.interfaces.EditRelicsSubscriber
import basemod.interfaces.EditStringsSubscriber
import basemod.interfaces.OnStartBattleSubscriber
import basemod.interfaces.PostInitializeSubscriber
import com.badlogic.gdx.Gdx
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.core.Settings.GameLanguage.ZHS
import com.megacrit.cardcrawl.core.Settings.GameLanguage.ZHT
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.localization.CardStrings
import com.megacrit.cardcrawl.localization.CharacterStrings
import com.megacrit.cardcrawl.localization.KeywordStrings
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.localization.RelicStrings
import com.megacrit.cardcrawl.rooms.MonsterRoom
import com.megacrit.cardcrawl.unlock.UnlockTracker
import org.apache.logging.log4j.LogManager
import yuyuko.actions.HideAction
import yuyuko.cards.HideCards
import yuyuko.cards.isHide
import yuyuko.cards.yuyuko.AllWander
import yuyuko.cards.yuyuko.BecomePhantom
import yuyuko.cards.yuyuko.Bloom
import yuyuko.cards.yuyuko.Bomb
import yuyuko.cards.yuyuko.BuryInTheTemplate
import yuyuko.cards.yuyuko.ButterfliesInDream
import yuyuko.cards.yuyuko.ButterfliesRainbow
import yuyuko.cards.yuyuko.Butterfly
import yuyuko.cards.yuyuko.ButterflyDeepRooted
import yuyuko.cards.yuyuko.ButterflyDelusion
import yuyuko.cards.yuyuko.ButterflyGhost
import yuyuko.cards.yuyuko.ButterflySwallowtail
import yuyuko.cards.yuyuko.CerasusSubhirtella
import yuyuko.cards.yuyuko.ChaseTheSukhavati
import yuyuko.cards.yuyuko.Childlike
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
import yuyuko.cards.yuyuko.FullMoon
import yuyuko.cards.yuyuko.GatherTheSpring
import yuyuko.cards.yuyuko.GauzySakura
import yuyuko.cards.yuyuko.GhastlyDream
import yuyuko.cards.yuyuko.GhostSpot
import yuyuko.cards.yuyuko.GhostdomSakura
import yuyuko.cards.yuyuko.Gone
import yuyuko.cards.yuyuko.ImmigrantPhantom
import yuyuko.cards.yuyuko.InfiniteSin
import yuyuko.cards.yuyuko.LingerOverFlower
import yuyuko.cards.yuyuko.LivingToDie
import yuyuko.cards.yuyuko.Lunch
import yuyuko.cards.yuyuko.MiniGhostdom
import yuyuko.cards.yuyuko.MirrorOfMind
import yuyuko.cards.yuyuko.MonsterCherryTree
import yuyuko.cards.yuyuko.Nihility
import yuyuko.cards.yuyuko.OpenTheFan
import yuyuko.cards.yuyuko.PastGraveyard
import yuyuko.cards.yuyuko.PhantomButterflies
import yuyuko.cards.yuyuko.PhantomGift
import yuyuko.cards.yuyuko.PhantomVillage
import yuyuko.cards.yuyuko.Photo
import yuyuko.cards.yuyuko.PostponeBloom
import yuyuko.cards.yuyuko.RedemptionOfDeath
import yuyuko.cards.yuyuko.RemainHere
import yuyuko.cards.yuyuko.ReunionAfterDeath
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
import yuyuko.cards.yuyuko.TheInkySakura
import yuyuko.cards.yuyuko.TheNether
import yuyuko.cards.yuyuko.TrapLamp
import yuyuko.cards.yuyuko.TripleSnow
import yuyuko.cards.yuyuko.UnbornLight
import yuyuko.cards.yuyuko.UnfulfillingAttachment
import yuyuko.cards.yuyuko.UnknownPetal
import yuyuko.cards.yuyuko.UnpavedWay
import yuyuko.cards.yuyuko.Unreal
import yuyuko.cards.yuyuko.UnstableWard
import yuyuko.cards.yuyuko.WanderingSoul
import yuyuko.characters.Yuyuko
import yuyuko.event.EventDispenser
import yuyuko.event.OnDrawEvent
import yuyuko.patches.CardColorEnum
import yuyuko.patches.PlayerClassEnum
import yuyuko.powers.BecomePhantomPower
import yuyuko.powers.DiaphaneityPower
import yuyuko.powers.DizzinessPower
import yuyuko.powers.FanPower
import yuyuko.powers.FloatOnMoonPower
import yuyuko.powers.FullInkySakuraPower
import yuyuko.powers.GhastlyDreamPower
import yuyuko.powers.GhostPower
import yuyuko.powers.LivingToDiePower
import yuyuko.powers.NihilityPower
import yuyuko.powers.PostponeBloomPower
import yuyuko.powers.RemainHerePower
import yuyuko.powers.ReviveTheButterfliesPower
import yuyuko.powers.ShowyWitheringPower
import yuyuko.powers.SupernaturalNetherPower
import yuyuko.powers.TheForgottenWinterPower
import yuyuko.powers.TripleSnowPower
import yuyuko.relics.Coronal
import yuyuko.relics.GhostLamp
import yuyuko.relics.Yuyukosfan
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
                CardColorEnum.YUYUKO_COLOR.toString(),
                Yuyuko.NAME,
                "images/charSelect/yuyukoButton.png",
                "images/charSelect/yuyukoPortrait.png",
                PlayerClassEnum.YUYOKO.toString()
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
        addCard(GhastlyDream())
        addCard(FloatOnMoon())
        addCard(ReviveTheButterflies())


        addCard(AllWander())
        addCard(BecomePhantom())
        addCard(SweetOfPhantom())
        addCard(SteamedPhantom())
        addCard(UnstableWard())
        addCard(ImmigrantPhantom())
        addCard(PhantomVillage())
        addCard(DyingButterflies())
        addCard(PhantomGift())
        addCard(TrapLamp())
        addCard(DyingDream())
        addCard(GhostSpot())

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
    }

    override fun receiveEditRelics() {
        BaseMod.addRelicToCustomPool(Yuyukosfan(), CardColorEnum.YUYUKO_COLOR.toString())
        BaseMod.addRelicToCustomPool(Coronal(), CardColorEnum.YUYUKO_COLOR.toString())
        BaseMod.addRelicToCustomPool(GhostLamp(), CardColorEnum.YUYUKO_COLOR.toString())
        BaseMod.addRelicToCustomPool(yuyuko.relics.TrapLamp(), CardColorEnum.YUYUKO_COLOR.toString())
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

    override fun receiveOnBattleStart(p0: MonsterRoom?) {
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
