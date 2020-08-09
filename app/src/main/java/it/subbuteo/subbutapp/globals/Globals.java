package it.subbuteo.subbutapp.globals;

import java.util.HashMap;
import java.util.Hashtable;

public class Globals {

    public static final int CHAMPIONSHIP = 0;
    public static final int CHAMPIONS_LEAGUE = 1;
    public static final int CUP = 2;
    public static final int SUPERCOPPA = 3;
    public static final int ARCHIVE_CHAMPIONSHIP = 4;
    public static final int ARCHIVE_LEAGUE = 5;
    public static final int ARCHIVE_CUP = 6;
    public static final int ARCHIVE_SUPERCOPPA = 7;

    public static final String url = "";
    public static final String url_hof = "";
    public static final String url_img = "";

    public static final HashMap<String, Integer> plIMg = new HashMap<>();
    public static final HashMap<String, Integer> plIMgCup = new HashMap<>();
    public static final HashMap<String, Integer> plIMgLeague = new HashMap<>();
    public static final HashMap<String, Integer> plIMgSupercoppa = new HashMap<>();

    public static final Hashtable<String, Integer> hallFameMap = new Hashtable<>();
    public static final Hashtable<String, Integer> hallFameCupMap = new Hashtable<>();
    public static final Hashtable<String, Integer> hallFameLeagueMap = new Hashtable<>();
    public static final Hashtable<String, Integer> hallFameSupercoppaMap = new Hashtable<>();

}
