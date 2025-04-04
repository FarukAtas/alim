package android.support.v4.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public abstract class FragmentManager {
    public static final int POP_BACK_STACK_INCLUSIVE = 1;

    public interface BackStackEntry {
        CharSequence getBreadCrumbShortTitle();

        int getBreadCrumbShortTitleRes();

        CharSequence getBreadCrumbTitle();

        int getBreadCrumbTitleRes();

        int getId();

        String getName();
    }

    public interface OnBackStackChangedListener {
        void onBackStackChanged();
    }

    public abstract void addOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener);

    public abstract FragmentTransaction beginTransaction();

    public abstract void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    public abstract boolean executePendingTransactions();

    public abstract Fragment findFragmentById(int r1);

    public abstract Fragment findFragmentByTag(String str);

    public abstract BackStackEntry getBackStackEntryAt(int r1);

    public abstract int getBackStackEntryCount();

    public abstract Fragment getFragment(Bundle bundle, String str);

    public abstract void popBackStack();

    public abstract void popBackStack(int r1, int r2);

    public abstract void popBackStack(String str, int r2);

    public abstract boolean popBackStackImmediate();

    public abstract boolean popBackStackImmediate(int r1, int r2);

    public abstract boolean popBackStackImmediate(String str, int r2);

    public abstract void putFragment(Bundle bundle, String str, Fragment fragment);

    public abstract void removeOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener);

    public abstract Fragment.SavedState saveFragmentInstanceState(Fragment fragment);

    @Deprecated
    public FragmentTransaction openTransaction() {
        return beginTransaction();
    }

    public static void enableDebugLogging(boolean enabled) {
        FragmentManagerImpl.DEBUG = enabled;
    }
}
