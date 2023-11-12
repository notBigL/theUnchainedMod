package theUnchainedMod.packs;

import theUnchainedMod.cards.*;

import java.util.ArrayList;

import static theUnchainedMod.TheUnchainedMod.makeID;

public class UnchainedChainPack extends AbstractUnchainedPack {
    public static final String ID = makeID("ChainPack");

    public UnchainedChainPack() {
        super(ID, StareDown.ID, new PackSummary(5, 3, 3, 3, 4));
    }

    public ArrayList<String> getCards() {
        ArrayList<String> cards = new ArrayList<>();
        cards.add(StepBack.ID);
        cards.add(LashOut.ID);
        cards.add(DoubleDown.ID);

        cards.add(StareDown.ID);
        cards.add(Windup.ID);
        cards.add(ReinforcedLink.ID);
        cards.add(QuickSwing.ID);

        cards.add(RelentlessOnslaught.ID);
        cards.add(LawOfInertia.ID);
        cards.add(ChainSaw.ID);
        return cards;
    }
}