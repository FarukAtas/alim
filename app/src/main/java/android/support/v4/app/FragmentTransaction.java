package android.support.v4.app;

/* loaded from: classes.dex */
public abstract class FragmentTransaction {
    public static final int TRANSIT_ENTER_MASK = 4096;
    public static final int TRANSIT_EXIT_MASK = 8192;
    public static final int TRANSIT_FRAGMENT_CLOSE = 8194;
    public static final int TRANSIT_FRAGMENT_FADE = 4099;
    public static final int TRANSIT_FRAGMENT_OPEN = 4097;
    public static final int TRANSIT_NONE = 0;
    public static final int TRANSIT_UNSET = -1;

    public abstract FragmentTransaction add(int r1, Fragment fragment);

    public abstract FragmentTransaction add(int r1, Fragment fragment, String str);

    public abstract FragmentTransaction add(Fragment fragment, String str);

    public abstract FragmentTransaction addToBackStack(String str);

    public abstract FragmentTransaction attach(Fragment fragment);

    public abstract int commit();

    public abstract int commitAllowingStateLoss();

    public abstract FragmentTransaction detach(Fragment fragment);

    public abstract FragmentTransaction disallowAddToBackStack();

    public abstract FragmentTransaction hide(Fragment fragment);

    public abstract boolean isAddToBackStackAllowed();

    public abstract boolean isEmpty();

    public abstract FragmentTransaction remove(Fragment fragment);

    public abstract FragmentTransaction replace(int r1, Fragment fragment);

    public abstract FragmentTransaction replace(int r1, Fragment fragment, String str);

    public abstract FragmentTransaction setBreadCrumbShortTitle(int r1);

    public abstract FragmentTransaction setBreadCrumbShortTitle(CharSequence charSequence);

    public abstract FragmentTransaction setBreadCrumbTitle(int r1);

    public abstract FragmentTransaction setBreadCrumbTitle(CharSequence charSequence);

    public abstract FragmentTransaction setCustomAnimations(int r1, int r2);

    public abstract FragmentTransaction setCustomAnimations(int r1, int r2, int r3, int r4);

    public abstract FragmentTransaction setTransition(int r1);

    public abstract FragmentTransaction setTransitionStyle(int r1);

    public abstract FragmentTransaction show(Fragment fragment);
}
