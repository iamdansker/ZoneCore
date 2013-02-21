/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package info.jeppes.ZoneCore;

import java.util.ArrayList;

/**
 * Author: Jeppe Boysen Vennekilde
 *
 * This document is Copyright ©() and is the intellectual property of the author.
 *
 * TERMS AND CONDITIONS
 * 0. USED TERMS
 * OWNER - The original author(s) of the program
 * USER - End user of the program, person installing/using the program.
 *
 * 1. LIABILITY
 * THIS PROGRAM IS PROVIDED 'AS IS' WITH NO WARRANTIES, IMPLIED OR OTHERWISE.
 * THE OWNER OF THIS PROGRAM TAKES NO RESPONSIBILITY FOR ANY DAMAGES INCURRED
 * FROM THE USE OF THIS PROGRAM.
 *
 * 2. REDISTRIBUTION
 * This program may only be distributed where uploaded, mirrored, or otherwise
 * linked to by the OWNER solely. All mirrors of this program must have advance
 * written permission from the OWNER. ANY attempts to make money off of this
 * program (selling, selling modified versions, adfly, sharecash, etc.) are
 * STRICTLY FORBIDDEN, and the OWNER may claim damages or take other action to
 * rectify the situation.
 *
 * 3. DERIVATIVE WORKS/MODIFICATION
 * This program is provided freely and may be decompiled and modified for
 * private use, either with a decompiler or a bytecode editor. Public
 * distribution of modified versions of this program require advance written
 * permission of the OWNER and may be subject to certain terms.
 */

public class AsynchroizedStorageManager implements Runnable{
    private static ArrayList<AsynchronizedStorageHolder> asynchronizedStorageHolders = new ArrayList();
    private static boolean running = false;
    
    @Override
    public void run() {
        save();
    }
    
    public synchronized static void save(){
        ArrayList<AsynchronizedStorageHolder> SchedualSaveList = getAndClearSchedualSaveList();
        for(AsynchronizedStorageHolder storageHolder : SchedualSaveList){
            storageHolder.save();
        }
    }
    
    public static void stop(){
        running = false;
    }
    
    public synchronized static ArrayList<AsynchronizedStorageHolder> getSchedualSaveList(){
        return asynchronizedStorageHolders;
    }
    public synchronized static void clearSchedualSaveList(){
        asynchronizedStorageHolders.clear();
    }
    public synchronized static ArrayList<AsynchronizedStorageHolder> getAndClearSchedualSaveList(){
        ArrayList<AsynchronizedStorageHolder> schedualSaveList = (ArrayList<AsynchronizedStorageHolder>) getSchedualSaveList().clone();
        clearSchedualSaveList();
        return schedualSaveList;
    }
    
    public synchronized static void schedualSave(AsynchronizedStorageHolder storageHolder){
        if(!asynchronizedStorageHolders.contains(storageHolder)){
            asynchronizedStorageHolders.add(storageHolder);
        }
    }
    public synchronized static void unschedualSave(AsynchronizedStorageHolder storageHolder){
        asynchronizedStorageHolders.remove(storageHolder);
    }
}
