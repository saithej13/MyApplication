package com.vst.myapplication.UI.Register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;

import com.vst.myapplication.R;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.UI.Login.forgetpassword;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.databinding.RegisterNoticeBinding;

public class register_notice  extends BaseFragment {
    RegisterNoticeBinding binding;
    ProjectRepository repository;
    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.register_notice, parent, false);
        repository = new ProjectRepository();
        binding.setLifecycleOwner(viewLifecycleOwner);
        setupUI(inflater,parent,viewLifecycleOwner);
        return binding.getRoot();
    }
    private void setupUI(LayoutInflater inflater, ViewGroup parent, LifecycleOwner viewLifecycleOwner) {
        // Implement any additional UI setup here
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("name","value");
//        repository.getTransList(jsonObject).observe(this, new Observer<JsonObject>() {
//            @Override
//            public void onChanged(JsonObject jsonObject) {
//                Log.d("PRINTBYSAI",""+jsonObject.toString());
//            }
//        });
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
//                        .replace(R.id.frame, new register(), "")
                        .replace(R.id.frame, new forgetpassword(), "")
                        .addToBackStack("")
                        .commitAllowingStateLoss();
            }
        });
    }
}
