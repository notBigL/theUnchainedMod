package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawPower;
import com.megacrit.cardcrawl.powers.watcher.EnergyDownPower;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.MakeTwoCostCardsFreeAction;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.powers.HeavyShacklesPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.DefaultMod.makeCardPath;

public class HeavyShackles extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(HeavyShackles.class.getSimpleName());
    public static final String IMG = makeCardPath("HeavyShackles.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;
    public static final String UPGRADE_DESCRIPTION = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;

    private static final int COST = 3;

    public HeavyShackles() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new HeavyShacklesPower(p, this.upgraded)));
        AbstractDungeon.actionManager.addToBottom(new MakeTwoCostCardsFreeAction(this.upgraded));
    }
}
