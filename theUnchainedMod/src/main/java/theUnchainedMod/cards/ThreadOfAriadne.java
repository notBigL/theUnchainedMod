package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.powers.AbstractChainPower;
import theUnchainedMod.powers.AbstractMasterChainPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class ThreadOfAriadne extends AbstractDynamicCard {

    public static final String ID = TheUnchainedMod.makeID(ThreadOfAriadne.class.getSimpleName());
    public static final String IMG = makeCardPath("ThreadOfAriadne.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_ORANGE;
    public static final String UPGRADE_DESCRIPTION = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;


    private static final int COST = 2;

    public ThreadOfAriadne() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SkipEnemiesTurnAction());
        this.addToBot(new PressEndTurnButtonAction());
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(!super.canUse(p, m)) return false;

        for (AbstractPower po : p.powers) {
            if (po instanceof AbstractChainPower || po instanceof AbstractMasterChainPower) {
                return true;
            }
        }
        this.cantUseMessage = languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
        return false;
    }
}
