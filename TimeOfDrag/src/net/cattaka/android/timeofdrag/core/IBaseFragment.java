
package net.cattaka.android.timeofdrag.core;

import net.cattaka.android.timeofdrag.db.DbHelper;
import android.content.Context;
import android.support.v4.app.Fragment;

public class IBaseFragment extends Fragment {
    public interface IBaseFragmentAdapter {
        public DbHelper getDbHelper();

        public void replaceFragment(Fragment fragment);
    }

    public Context getContext() {
        return getActivity();
    }

    public IBaseFragmentAdapter getBaseFragmentAdapter() {
        return (IBaseFragmentAdapter)getActivity();
    }

    public DbHelper getDbHelper() {
        return getBaseFragmentAdapter().getDbHelper();
    }

    public void replaceFragment(Fragment fragment) {
        getBaseFragmentAdapter().replaceFragment(fragment);
    }

}
