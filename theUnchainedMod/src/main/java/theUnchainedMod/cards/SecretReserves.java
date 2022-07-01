package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.characters.TheDefault;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class SecretReserves extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(SecretReserves.class.getSimpleName());
    public static final String IMG = makeCardPath("SecretReserves.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;

    public SecretReserves() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int maladyAmount = 0;
        if(p.hasPower("theUnchainedMod:MaladyPower")) {
            maladyAmount = p.getPower("theUnchainedMod:MaladyPower").amount;
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "theUnchainedMod:MaladyPower"));
        }
        if(maladyAmount != 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, maladyAmount)));
        }
    }
}
