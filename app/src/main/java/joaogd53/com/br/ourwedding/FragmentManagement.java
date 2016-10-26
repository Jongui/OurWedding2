package joaogd53.com.br.ourwedding;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class FragmentManagement {
    /*
    Valores estáticos para identificar fragments
     */
    public static final int INIT_FRAGMENT = 1;
    public static final int HONEY_MOON_FRAGMENT = 2;
    public static final int GUESTS_FRAGMENT = 3;
    public static final int CREDITS_FRAGMENT = 4;
    public static final int MAIN_STORES = 5;
    public static final int STORY_FRAGMENT = 6;
    /*
    Atributos de instância
     */
    private Fragment currentFragment;
    private static FragmentManagement fragmentManager;
    private Activity activity;

    private FragmentManagement() {

    }

    public static FragmentManagement getInstance() {
        if (fragmentManager == null) {
            fragmentManager = new FragmentManagement();
        }
        return fragmentManager;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void callFragment(int fragment, Bundle bundle) {
        Fragment f = new Fragment();
        FragmentManager fm = activity.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        String tag = "";
        switch (fragment) {
            case INIT_FRAGMENT:
                fm.popBackStack("control", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                f = new InitFragment();
                tag = "INIT_FRAGMENT";
                break;
            case MAIN_STORES:
                fm.popBackStack("control", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                f = new MainFragment();
                tag = "MAIN_STORES";
                break;
            case HONEY_MOON_FRAGMENT:
                fm.popBackStack("control", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                f = new HoneyMoonFragment();
                tag = "HONEY_MOON_FRAGMENT";
                break;
            case GUESTS_FRAGMENT:
                fm.popBackStack("control", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                f = new GuestsFragment();
                tag = "GUESTS_FRAGMENT";
                break;
            case CREDITS_FRAGMENT:
                fm.popBackStack("control", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                f = new CreditsFragment();
                tag = "CREDITS_FRAGMENT";
                break;
            case STORY_FRAGMENT:
                fm.popBackStack("control", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                f = new StoryFragment();
                tag = "STORY_FRAGMENT";
                break;
        }
        f.setArguments(bundle);
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
//        FragmentTransactionExtended fragmentTransactionExtended = new FragmentTransactionExtended(this.activity, ft, currentFragment, f, R.id.container);
//        fragmentTransactionExtended.addTransition(FragmentTransactionExtended.FADE);
//        fragmentTransactionExtended.commit("control");
        ft.replace(R.id.container, f, tag).addToBackStack("control").commit();
        this.currentFragment = f;
    }
}