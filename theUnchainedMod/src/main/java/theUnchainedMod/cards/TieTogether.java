package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.powers.RelayPower;
import theUnchainedMod.powers.TiedToAnEnemyPower;
import theUnchainedMod.powers.TiedToThePlayerPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.DefaultMod.makeCardPath;

public class TieTogether extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(TieTogether.class.getSimpleName());
    public static final String IMG = makeCardPath("TieTogether.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int MAGIC_NUMBER = 8;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 4;

    public TieTogether() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }
        if (p.hasPower("theUnchainedMod:TiedToAnEnemyPower")) {
            canUse = false;
            this.cantUseMessage = languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
        }
        return canUse;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RelayPower(p, p, this.magicNumber)));
        TiedToThePlayerPower tiedToThePlayerPower = new TiedToThePlayerPower(m, p, p, 1);
        if (m.hasPower("Artifact")) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, tiedToThePlayerPower));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, tiedToThePlayerPower));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TiedToAnEnemyPower(p, p, 1, tiedToThePlayerPower, m)));
        }
    }
}
