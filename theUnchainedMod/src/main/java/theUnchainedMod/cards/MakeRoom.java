package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.GainRelayAction;
import theUnchainedMod.actions.MakeRoomAction;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.powers.MakeRoomPower;
import theUnchainedMod.powers.RelayPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.DefaultMod.makeCardPath;

public class MakeRoom extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(MakeRoom.class.getSimpleName());
    public static final String IMG = makeCardPath("MakeRoom.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;
    public static final String UPGRADE_DESCRIPTION = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;

    private static final int COST = 2;
    private static final int MAGIC_NUMBER = 14;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 4;
    private static final int SECOND_MAGIC_NUMBER = 2;



    public MakeRoom() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainRelayAction(p, magicNumber));
        AbstractDungeon.actionManager.addToBottom(new MakeRoomAction(p, defaultSecondMagicNumber));
    }
}
