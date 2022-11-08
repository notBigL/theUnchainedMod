package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.GainRelayAction;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.powers.TiedToAnEnemyPower;
import theUnchainedMod.powers.TiedToThePlayerPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.DefaultMod.makeCardPath;

public class TetheredMind extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(TetheredMind.class.getSimpleName());
    public static final String IMG = makeCardPath("TieTogether.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 10;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 4;

    public TetheredMind() {
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


    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainRelayAction(p, magicNumber));
        if (m.hasPower("Artifact") && !m.hasPower(TiedToThePlayerPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, 1, false)));
        } else if (!m.hasPower(TiedToThePlayerPower.POWER_ID)) {
            TiedToThePlayerPower tiedToThePlayerPower = new TiedToThePlayerPower(m, p, p);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, tiedToThePlayerPower));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TiedToAnEnemyPower(p, p, tiedToThePlayerPower, m)));
        } else {
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0], true));
        }
    }
}
