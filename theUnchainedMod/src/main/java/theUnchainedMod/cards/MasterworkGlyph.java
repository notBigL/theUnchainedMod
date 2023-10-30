package theUnchainedMod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.GainRelayAction;
import theUnchainedMod.actions.MakeRoomAction;
import theUnchainedMod.characters.TheUnchained;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class MasterworkGlyph extends AbstractDynamicRelayCard {

    public static final String ID = TheUnchainedMod.makeID(MasterworkGlyph.class.getSimpleName());
    public static final String IMG = makeCardPath("MasterworkGlyph.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheUnchained.Enums.UNCHAINED_COLOR;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 9;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 2;


    public MasterworkGlyph() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainRelayAction(p, magicNumber));
        AbstractDungeon.actionManager.addToBottom(new MakeRoomAction(p, magicNumber));
    }
}
