package it.subbuteo.subbutapp.mainactivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import it.subbuteo.subbutapp.R;
import it.subbuteo.subbutapp.championship.defense.Defense;
import it.subbuteo.subbutapp.championship.halloffame.HallOfFame;
import it.subbuteo.subbutapp.championship.matches.Matches;
import it.subbuteo.subbutapp.championship.ranking.Ranking;
import it.subbuteo.subbutapp.championship.scored.Scored;
import it.subbuteo.subbutapp.cup.halloffamecup.HallOfFameCup;
import it.subbuteo.subbutapp.cup.matches.MatchesCup;
import it.subbuteo.subbutapp.globals.Globals;
import it.subbuteo.subbutapp.league.halloffameleague.HallOfFameLeague;
import it.subbuteo.subbutapp.league.matches.MatchesLeague;
import it.subbuteo.subbutapp.rules.Rules;

public class MainActivity extends AppCompatActivity {

    private static final String SCOREDFRAG_TAG = "sFrag";
    private static final String DEFENSEFRAG_TAG = "dFrag";
    private static final String MATCHESFRAG_TAG = "mFrag";
    private static final String RANKINGFRAG_TAG = "rFrag";
    private static final String HALLOFFAMEFRAG_TAG = "hFrag";
    private static final String CL_MATCHESFRAG_TAG = "clmFrag";
    private static final String CL_HALLOFFAMEFRAG_TAG = "clhFrag";
    private static final String CI_MATCHESFRAG_TAG = "cimFrag";
    private static final String CI_HALLOFFAMEFRAG_TAG = "cihFrag";

    final FragmentManager fragmentManager = getSupportFragmentManager();
    final Fragment matchesFrag = new Matches();
    final Fragment rankingFrag = new Ranking();
    final Fragment scoredFrag = new Scored();
    final Fragment defenseFrag = new Defense();
    final Fragment halloffameFrag = new HallOfFame();
    final Fragment matchesCupFrag = new MatchesCup();
    final Fragment halloffameCupFrag = new HallOfFameCup();
    final Fragment matchesLeagueFrag = new MatchesLeague();
    final Fragment halloffameLeagueFrag = new HallOfFameLeague();
    private Fragment currentFrag = matchesFrag;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private int menuSelection = Globals.CHAMPIONSHIP;

    private ImageButton scored_ranking, defense_ranking, all_matches, ranking, hall_of_fame;

    @Override
    public void onResume() {
        super.onResume();
        fragmentManager.beginTransaction().show(currentFrag).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setTitle(getString(R.string.navigation_drawer_item1));
        setContentView(R.layout.drawer_layout);

        dl = findViewById(R.id.drawer_layout);
        t = new ActionBarDrawerToggle(this, dl, R.string.drawer_open, R.string.drawer_closed);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nv = findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                setAfterDrawerSelection(id);
                return true;
            }
        });
        nv.setItemIconTintList(null);

        fragmentManager.beginTransaction().add(R.id.fragment_layout, matchesLeagueFrag, CL_MATCHESFRAG_TAG).hide(matchesLeagueFrag).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_layout, halloffameLeagueFrag, CL_HALLOFFAMEFRAG_TAG).hide(halloffameLeagueFrag).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_layout, matchesCupFrag, CI_MATCHESFRAG_TAG).hide(matchesCupFrag).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_layout, halloffameCupFrag, CI_HALLOFFAMEFRAG_TAG).hide(halloffameCupFrag).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_layout, scoredFrag, SCOREDFRAG_TAG).hide(scoredFrag).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_layout, defenseFrag, DEFENSEFRAG_TAG).hide(defenseFrag).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_layout, halloffameFrag, HALLOFFAMEFRAG_TAG).hide(halloffameFrag).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_layout, rankingFrag, RANKINGFRAG_TAG).hide(rankingFrag).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_layout, matchesFrag, MATCHESFRAG_TAG).commit();

        scored_ranking = findViewById(R.id.scored_ranking);
        scored_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentFrag != scoredFrag) {
                    fragmentManager.beginTransaction().hide(currentFrag).show(scoredFrag).commit();
                    currentFrag = scoredFrag;
                    setButtonsNotClicked();
                    scored_ranking.setImageResource(R.drawable.scored);
                }
            }
        });

        defense_ranking = findViewById(R.id.defense_ranking);
        defense_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentFrag != defenseFrag) {
                    fragmentManager.beginTransaction().hide(currentFrag).show(defenseFrag).commit();
                    currentFrag = defenseFrag;
                    setButtonsNotClicked();
                    defense_ranking.setImageResource(R.drawable.defense);
                }
            }
        });

        all_matches = findViewById(R.id.all_matches);
        all_matches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuSelection == Globals.CHAMPIONSHIP) {
                    if (currentFrag != matchesFrag) {
                        fragmentManager.beginTransaction().hide(currentFrag).show(matchesFrag).commit();
                        currentFrag = matchesFrag;
                        setButtonsNotClicked();
                        all_matches.setImageResource(R.drawable.matches);
                    }
                }else if(menuSelection == Globals.CUP){
                    if (currentFrag != matchesCupFrag) {
                        fragmentManager.beginTransaction().hide(currentFrag).show(matchesCupFrag).commit();
                        currentFrag = matchesCupFrag;
                        setButtonsNotClicked();
                        all_matches.setImageResource(R.drawable.matches);
                    }
                }else if(menuSelection == Globals.CHAMPIONS_LEAGUE){
                    if (currentFrag != matchesLeagueFrag) {
                        fragmentManager.beginTransaction().hide(currentFrag).show(matchesLeagueFrag).commit();
                        currentFrag = matchesLeagueFrag;
                        setButtonsNotClicked();
                        all_matches.setImageResource(R.drawable.matches);
                    }
                }
            }
        });

        ranking = findViewById(R.id.ranking);
        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentFrag != rankingFrag) {
                    fragmentManager.beginTransaction().hide(currentFrag).show(rankingFrag).commit();
                    currentFrag = rankingFrag;
                    setButtonsNotClicked();
                    ranking.setImageResource(R.drawable.rank);
                }
            }
        });

        hall_of_fame = findViewById(R.id.hall_of_fame);
        hall_of_fame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuSelection == Globals.CHAMPIONSHIP) {
                    if (currentFrag != halloffameFrag) {
                        fragmentManager.beginTransaction().hide(currentFrag).show(halloffameFrag).commit();
                        currentFrag = halloffameFrag;
                        setButtonsNotClicked();
                        hall_of_fame.setImageResource(R.drawable.hall_of_fame);
                    }
                }else if(menuSelection == Globals.CUP){
                    if (currentFrag != halloffameCupFrag) {
                        fragmentManager.beginTransaction().hide(currentFrag).show(halloffameCupFrag).commit();
                        currentFrag = halloffameCupFrag;
                        setButtonsNotClicked();
                        hall_of_fame.setImageResource(R.drawable.hall_of_fame);
                    }
                }else if(menuSelection == Globals.CHAMPIONS_LEAGUE){
                    if (currentFrag != halloffameLeagueFrag) {
                        fragmentManager.beginTransaction().hide(currentFrag).show(halloffameLeagueFrag).commit();
                        currentFrag = halloffameLeagueFrag;
                        setButtonsNotClicked();
                        hall_of_fame.setImageResource(R.drawable.hall_of_fame);
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(t.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    private void setAfterDrawerSelection(int selection){
        switch(selection) {
            case R.id.nav_item1:
                menuSelection = Globals.CHAMPIONSHIP;
                this.setTitle(getString(R.string.navigation_drawer_item1));
                if(!isChampionshipFragment()) {
                    fragmentManager.beginTransaction().hide(currentFrag).show(matchesFrag).commit();
                    currentFrag = matchesFrag;
                }
                setButtons();
                dl.closeDrawers();
                break;
            case R.id.nav_item2:
                menuSelection = Globals.CHAMPIONS_LEAGUE;
                this.setTitle(getString(R.string.navigation_drawer_item2));
                if(!isLeagueFragment()) {
                    fragmentManager.beginTransaction().hide(currentFrag).show(matchesLeagueFrag).commit();
                    currentFrag = matchesLeagueFrag;
                }
                setButtons();
                dl.closeDrawers();
                break;
            case R.id.nav_item3:
                menuSelection = Globals.CUP;
                this.setTitle(getString(R.string.navigation_drawer_item3));
                if(!isCupFragment()) {
                    fragmentManager.beginTransaction().hide(currentFrag).show(matchesCupFrag).commit();
                    currentFrag = matchesCupFrag;
                }
                setButtons();
                dl.closeDrawers();
                break;
            case R.id.nav_item4:
                Rules rules = new Rules(this);
                rules.showRules();
                dl.closeDrawers();
                break;
        }
    }

    private boolean isChampionshipFragment(){
        return ((currentFrag == scoredFrag) ||
                (currentFrag == defenseFrag) ||
                (currentFrag == matchesFrag) ||
                (currentFrag == rankingFrag) ||
                (currentFrag == halloffameFrag));
    }

    private boolean isCupFragment(){
        return ((currentFrag == matchesCupFrag) ||
                (currentFrag == halloffameCupFrag));
    }

    private boolean isLeagueFragment(){
        return ((currentFrag == matchesLeagueFrag) ||
                (currentFrag == halloffameLeagueFrag));
    }

    private void setButtonsNotClicked(){
        scored_ranking.setImageResource(R.drawable.scored_white);
        defense_ranking.setImageResource(R.drawable.defense_white);
        all_matches.setImageResource(R.drawable.matches_white);
        ranking.setImageResource(R.drawable.rank_white);
        hall_of_fame.setImageResource(R.drawable.hall_of_fame_white);
    }

    private void setStartingButtons(){
        scored_ranking.setImageResource(R.drawable.scored_white);
        defense_ranking.setImageResource(R.drawable.defense_white);
        all_matches.setImageResource(R.drawable.matches);
        ranking.setImageResource(R.drawable.rank_white);
        hall_of_fame.setImageResource(R.drawable.hall_of_fame_white);
    }

    private void setButtons(){
        if(menuSelection == Globals.CHAMPIONSHIP){
            scored_ranking.setVisibility(View.VISIBLE);
            defense_ranking.setVisibility(View.VISIBLE);
            ranking.setVisibility(View.VISIBLE);
        }else if(menuSelection == Globals.CUP){
            scored_ranking.setVisibility(View.GONE);
            defense_ranking.setVisibility(View.GONE);
            ranking.setVisibility(View.GONE);
        }else if(menuSelection == Globals.CHAMPIONS_LEAGUE){
            scored_ranking.setVisibility(View.GONE);
            defense_ranking.setVisibility(View.GONE);
            ranking.setVisibility(View.GONE);
        }
        setStartingButtons();
    }

}
