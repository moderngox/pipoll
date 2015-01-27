package com.pipoll.data;

import java.util.ArrayList;
import java.util.UUID;

import com.pipoll.R;
import android.content.Context;

public class PollLab {
    private ArrayList<Poll> mPolls;

    private static PollLab sPollLab;
    private Context mAppContext;

    private PollLab(Context appContext) {
        mAppContext = appContext;
        mPolls = new ArrayList<Poll>();
        for (int i = 0; i < 6; i++) {
            Poll p = new Poll();
            
            p.setId("Poll #" + i);
            // set other stuff
            mPolls.add(p);
        }
    }

    public static PollLab get(Context c) {
        if (sPollLab == null) {
            sPollLab = new PollLab(c.getApplicationContext());
        }
        return sPollLab;
    }

    public Poll getPoll(UUID id) {
        for (Poll c : mPolls) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }
    
    public ArrayList<Poll> getPolls() {
        return mPolls;
    }
}

