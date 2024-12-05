package me.jessicasheng.pokemonGame.view;

import java.time.LocalDate;

public interface UI {
    public LocalDate inputDate(String prompt);

    public boolean inputYesNo(String prompt);

    public void print(Object output);

    public void printFancy(String output);

    public void println(Object output);

    public boolean readBoolean(String prompt);

    public double readDouble(String prompt);

    public double readDouble(String prompt, double min, double max);

    public int readInt(String prompt);

    public int readInt(String prompt, int minValue, int maxValue);

    public int readInt(String prompt, int minValue, int maxValue, int quit);

    public String readln(String prompt);

    public String readln(String prompt, String... matches);

    public Object chooseFrom(String prompt, Object... options);
}
