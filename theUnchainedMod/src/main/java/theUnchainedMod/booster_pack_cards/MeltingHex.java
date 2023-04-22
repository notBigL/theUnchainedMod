package theUnchainedMod.booster_pack_cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MalleablePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.cards.AbstractDynamicCard;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.patches.CustomTags;
import theUnchainedMod.powers.DamoclesChainPower;
import theUnchainedMod.powers.DeliciousChurroPower;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class MeltingHex extends AbstractDynamicCard {

    public static final String ID = TheUnchainedMod.makeID(MeltingHex.class.getSimpleName());
    public static final String IMG = makeCardPath("MeltingHex.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_BOOSTER;

    private static final int COST = 2;
    private static final int MAGIC_NUMBER = 1; // Malleable Initial Amount

    public MeltingHex() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,p, new MalleablePower(m, 1)));
    }
}
