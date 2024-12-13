package me.jessicasheng.pokemonGame.model.pokemon;

import me.jessicasheng.pokemonGame.model.BattleCapable;
import me.jessicasheng.pokemonGame.model.trainer.Trainer;

import java.util.Random;

/*
    To-do: Class description
    @author Jessica Sheng
    email: jlsheng@usc.edu
    ITP 265, Fall 2024, Tea section
    Date created: 12/4/24
*/
public class WildPokemon extends Pokemon implements BattleCapable {
    private double catchRate;


    public WildPokemon(String name, PokemonType type1, PokemonType type2, int hp, int baseAttack,
                        int level, PokemonStages stage, double catchRate) {
        super(name, type1, type2, hp, baseAttack, level, stage);
        this.catchRate = catchRate;
    }

    public double getCatchRate() {
        return catchRate;
    }

    /**
     * @param trainer
     */
    @Override
    public void initiateBattle(Object trainer) {
        System.out.println(getName() + " has initiated a battle!");
        if (trainer instanceof Trainer) {
            Trainer thisTrainer = (Trainer) trainer;
            boolean fleeSuccess = thisTrainer.flee();
            if (fleeSuccess) {
                System.out.println(thisTrainer.getName() + " fled from the battle!");
                if (thisTrainer.getListener() != null) thisTrainer.getListener().onFlee(thisTrainer);
                return;
            }

            // if fail to flee, battle
            System.out.println(thisTrainer.getName() + " failed to flee and is forced into battle!");
            if (thisTrainer.getListener() != null) {
                thisTrainer.getListener().onBattleInitiated(this, trainer);
            }

            thisTrainer.progressBattleQuest(thisTrainer);
        }
    }

    /**
     *
     */
    @Override
    public boolean flee() {
        return true;
    }
}
