package com.dangerfield.kind.ui.profile;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.dangerfield.kind.R;
import com.dangerfield.kind.api.CurrentUser;


public class SettingsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button signOutButton;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        signOutButton = view.findViewById(R.id.btn_sign_out);

        signOutButton.setOnClickListener(it -> {
            CurrentUser.INSTANCE.signOut(getContext());
            reloadFragment();
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void reloadFragment() {
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
}
