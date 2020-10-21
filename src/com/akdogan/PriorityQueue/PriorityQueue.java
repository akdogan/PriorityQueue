package com.akdogan.PriorityQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class PriorityQueue
{
    private ArrayList<Task> tasks;
    //private HashMap<Integer, Integer> map;

    private TreeMap<Integer, Integer> map;

    public PriorityQueue(){
        this.tasks = new ArrayList<Task>();
        map = new TreeMap<>();


    }

    public boolean add(Task t){

        if (tasks.size() == 0) {
            addInternal(t, 0);
            return true;
        }
        int prio = t.getPriority();

        if (map.higherKey(prio) == null){
            addInternal(t, 0);
            return true;
        }

        if (map.lowerKey(prio) == null){
            addInternal(t, tasks.size());
            return true;
        }

        if (map.get(prio) != null){
            int index = map.get(prio) + 1;
            addInternal(t, index);
            return true;
        }
        else {
            int index = map.get(map.higherKey(prio)) + 1;
            addInternal(t, index);
            moveDown(prio-1);
            return true;
        }
    }

    private void addInternal(Task t, int index){
        int priority = t.getPriority();
        tasks.add(index, t);
        if (map.containsKey(priority)){
            map.replace(priority, index);
        }
        else {
            map.put(priority, index);
        }
    }

    private void moveDown(int priority){
        for ( int oldPrio : map.keySet()){
            if (oldPrio <= priority){
                map.replace(oldPrio, map.get(oldPrio)+1);
            }
        }
    }



    public void addManual(Task t){
        if (tasks.size() == 0) {
            tasks.add(t);
            map.put(t.getPriority(), 0);
        }
        else {
            int priority = t.getPriority();
            int i = tasks.size()-1;
            boolean found = false;
            while (!found && i >= 0){
                if (priority < tasks.get(i).getPriority()){
                    tasks.add(i + 1, t);
                    found = true;
                }
                else {
                    i--;
                }
            }
            if (!found) tasks.add(0, t);
        }
    }

    private boolean prioExists(int i){
        return map.containsKey(i);
    }


    public boolean hasNext(){
        return !(tasks.size() == 0);
    }

    public Task getNext(){
        return tasks.get(0);
    }

    public boolean contains(String name){
        boolean found = false;
        int i = 0;
        while (!found && i <tasks.size()){
            found = tasks.get(i).getDescription().equals(name);
            if (!found) i++;
        }
        return found;
    }

    public Task get(int i){
        return tasks.get(i);
    }

    public Task doNext(){
        Task t = tasks.get(0);
        tasks.remove(0);
        if (tasks.size() > 0 && tasks.get(0).getPriority() < t.getPriority()){
            map.remove(t.getPriority());
            map.replace(map.lowerKey(t.getPriority()), 0);
        }
        return t;
    }

    public String toString(){
        String str = "";
        for ( Task t : tasks){
            str += t.getPriority() + ": " + t.getDescription() + "\n";
        }
        return str;
    }


}

