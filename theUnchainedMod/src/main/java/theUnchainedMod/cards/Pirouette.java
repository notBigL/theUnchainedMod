package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.GainMomentumAction;
import theUnchainedMod.actions.PirouetteAction;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.patches.CustomTags;
import theUnchainedMod.powers.FullSpinPower;
import theUnchainedMod.powers.MomentumPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.DefaultMod.makeCardPath;

public class Pirouette extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(Pirouette.class.getSimpleName());
    public static final String IMG = makeCardPath("Pirouette.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;
    public static final String UPGRADE_DESCRIPTION = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;


    private static final int COST = 0;

    public Pirouette() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        AbstractCard previewCard = new Swirl();
        previewCard.upgrade();
        this.cardsToPreview = previewCard;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Swirl card = new Swirl();
        card.upgrade();
        if (p.hasPower(FullSpinPower.POWER_ID)) {
            card.fullSpinApply(p.getPower(FullSpinPower.POWER_ID).amount);
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, 1, false));
        AbstractDungeon.actionManager.addToBottom(new PirouetteAction());
    }
}
