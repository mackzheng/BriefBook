package com.avl.volley.businessLayer;

import com.avl.volley.businessLayer.main.IMainManager;
import com.avl.volley.businessLayer.main.MainManager;

public class BusinessProtal
{
    public static IMainManager mainManager()
    {
        MainManager  mainManager = new MainManager() ;
        return mainManager;
    }
}