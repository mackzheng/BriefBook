package com.avl.volley.baseVolley;

import android.content.Context;

public class VolleyResponse
{
    private Context context;
    private VolleyDelegate delegate;

    public VolleyResponse(Context context ,VolleyDelegate delegate)
    {
        this.context = context;
        this.delegate = delegate;
    }

    public void onSuccess(String response)
    {
        if(delegate!=null)
        {
            delegate.onSuccess(response);
        }
    }

    public void onError(String response)
    {
        if(delegate!=null)
        {
            delegate.onError(response);
        }
    }

}
