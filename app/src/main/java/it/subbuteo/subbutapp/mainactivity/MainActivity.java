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

import java.util.Objects;

import it.subbuteo.subbutapp.R;
import it.subbuteo.subbutapp.archive.championship.OldChampionship;
import it.subbuteo.subbutapp.archive.cup.OldCups;
import it.subbuteo.subbutapp.archive.league.OldLeagues;
import it.subbuteo.subbutapp.archive.supercoppa.OldSupercoppa;
import it.subbuteo.subbutapp.championship.defense.Defense;
import it.subbuteo.subbutapp.championship.halloffame.HallOfFame;
import it.subbuteo.subbutapp.championship.matches.Matches;
import it.subbuteo.subbutapp.championship.ranking.Ranking;
import it.subbuteo.subbutapp.championship.scored.Scored;
import it.subbuteo.subbutapp.constitution.Constitution;
import it.subbuteo.subbutapp.cup.halloffamecup.HallOfFameCup;
import it.subbuteo.subbutapp.cup.matches.MatchesCup;
import it.subbuteo.subbutapp.globals.Globals;
import it.subbuteo.subbutapp.league.halloffameleague.HallOfFameLeague;
import it.subbuteo.subbutapp.league.matches.MatchesLeague;
import it.subbuteo.subbutapp.rules.Rules;
import it.subbuteo.subbutapp.supercoppa.halloffamesupercoppa.HallOfFameSupercoppa;
import it.subbuteo.subbutapp.supercoppa.matches.MatchesSupercoppa;

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
    private static final String SC_MATCHESFRAG_TAG = "scmFrag";
    private static final String SC_HALLOFFAMEFRAG_TAG = "schFrag";
    private static final String OLD_CHAMPIONSHIP_TAG = "oldChFrag";
    private static final String OLD_LEAGUE_TAG = "oldLeFrag";
    private static final String OLD_CUP_TAG = "oldCuFrag";
    private static final String OLD_SUPERCOPPA_TAG = "oldSuFrag";

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
    final Fragment matchesSupercoppaFrag = new MatchesSupercoppa();
    final Fragment halloffameSupercoppaFrag = new HallOfFameSupercoppa();
    final Fragment oldChampionshipFrag = new OldChampionship();
    final Fragment oldLeagueFrag = new OldLeagues();
    final Fragment oldCupFrag = new OldCups();
    final Fragment oldSupercoppaFrag = new OldSupercoppa();
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

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

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

        fragmentManager.beginTransaction().add(R.id.fragment_layout, oldSupercoppaFrag, OLD_SUPERCOPPA_TAG).hide(oldSupercoppaFrag).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_layout, oldCupFrag, OLD_CUP_TAG).hide(oldCupFrag).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_layout, oldLeagueFrag, OLD_LEAGUE_TAG).hide(oldLeagueFrag).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_layout, oldChampionshipFrag, OLD_CHAMPIONSHIP_TAG).hide(oldChampionshipFrag).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_layout, matchesSupercoppaFrag, SC_MATCHESFRAG_TAG).hide(matchesSupercoppaFrag).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_layout, halloffameSupercoppaFrag, SC_HALLOFFAMEFRAG_TAG).hide(halloffameSupercoppaFrag).commit();
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
                }else if(menuSelection == Globals.SUPERCOPPA){
                    if (currentFrag != matchesSupercoppaFrag) {
                        fragmentManager.beginTransaction().hide(currentFrag).show(matchesSupercoppaFrag).commit();
                        currentFrag = matchesSupercoppaFrag;
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
                }else if(menuSelection == Globals.SUPERCOPPA){
                    if (currentFrag != halloffameSupercoppaFrag) {
                        fragmentManager.beginTransaction().hide(currentFrag).show(halloffameSupercoppaFrag).commit();
                        currentFrag = halloffameSupercoppaFrag;
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
                menuSelection = Globals.SUPERCOPPA;
                this.setTitle(getString(R.string.navigation_drawer_item4));
                if(!isSupercoppaFragment()) {
                    fragmentManager.beginTransaction().hide(currentFrag).show(matchesSupercoppaFrag).commit();
                    currentFrag = matchesSupercoppaFrag;
                }
                setButtons();
                dl.closeDrawers();
                break;
            case R.id.nav_item6:
                Rules rules = new Rules(this);
                rules.showRules();
                dl.closeDrawers();
                break;
            case R.id.nav_item7:
                Constitution constitution = new Constitution(this);
                constitution.showConstitution();
                dl.closeDrawers();
                break;
            case R.id.archivio_camp:
                menuSelection = Globals.ARCHIVE_CHAMPIONSHIP;
                this.setTitle(getString(R.string.sub_navigation_item1));
                if(currentFrag != oldChampionshipFrag) {
                    fragmentManager.beginTransaction().hide(currentFrag).show(oldChampionshipFrag).commit();
                    currentFrag = oldChampionshipFrag;
                }
                setButtons();
                dl.closeDrawers();
                break;
            case R.id.archivio_cl:
                menuSelection = Globals.ARCHIVE_LEAGUE;
                this.setTitle(getString(R.string.sub_navigation_item2));
                if(currentFrag != oldLeagueFrag) {
                    fragmentManager.beginTransaction().hide(currentFrag).show(oldLeagueFrag).commit();
                    currentFrag = oldLeagueFrag;
                }
                setButtons();
                dl.closeDrawers();
                break;
            case R.id.archivio_ci:
                menuSelection = Globals.ARCHIVE_CUP;
                this.setTitle(getString(R.string.sub_navigation_item3));
                if(currentFrag != oldCupFrag) {
                    fragmentManager.beginTransaction().hide(currentFrag).show(oldCupFrag).commit();
                    currentFrag = oldCupFrag;
                }
                setButtons();
                dl.closeDrawers();
                break;
            case R.id.archivio_sc:
                menuSelection = Globals.ARCHIVE_SUPERCOPPA;
                this.setTitle(getString(R.string.sub_navigation_item4));
                if(currentFrag != oldSupercoppaFrag) {
                    fragmentManager.beginTransaction().hide(currentFrag).show(oldSupercoppaFrag).commit();
                    currentFrag = oldSupercoppaFrag;
                }
                setButtons();
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

    private boolean isSupercoppaFragment(){
        return ((currentFrag == matchesSupercoppaFrag) ||
                (currentFrag == halloffameSupercoppaFrag));
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
            all_matches.setVisibility(View.VISIBLE);
            hall_of_fame.setVisibility(View.VISIBLE);
        }else if(menuSelection == Globals.CUP){
            scored_ranking.setVisibility(View.GONE);
            defense_ranking.setVisibility(View.GONE);
            ranking.setVisibility(View.GONE);
            all_matches.setVisibility(View.VISIBLE);
            hall_of_fame.setVisibility(View.VISIBLE);
        }else if(menuSelection == Globals.CHAMPIONS_LEAGUE){
            scored_ranking.setVisibility(View.GONE);
            defense_ranking.setVisibility(View.GONE);
            ranking.setVisibility(View.GONE);
            all_matches.setVisibility(View.VISIBLE);
            hall_of_fame.setVisibility(View.VISIBLE);
        }else if(menuSelection == Globals.SUPERCOPPA){
            scored_ranking.setVisibility(View.GONE);
            defense_ranking.setVisibility(View.GONE);
            ranking.setVisibility(View.GONE);
            all_matches.setVisibility(View.VISIBLE);
            hall_of_fame.setVisibility(View.VISIBLE);
        }else if(menuSelection == Globals.ARCHIVE_CHAMPIONSHIP ||
                 menuSelection == Globals.ARCHIVE_LEAGUE ||
                 menuSelection == Globals.ARCHIVE_CUP ||
                 menuSelection == Globals.ARCHIVE_SUPERCOPPA){
            scored_ranking.setVisibility(View.GONE);
            defense_ranking.setVisibility(View.GONE);
            ranking.setVisibility(View.GONE);
            all_matches.setVisibility(View.GONE);
            hall_of_fame.setVisibility(View.GONE);
        }
        setStartingButtons();
    }

}
